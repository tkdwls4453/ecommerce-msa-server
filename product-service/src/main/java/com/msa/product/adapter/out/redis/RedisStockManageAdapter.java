package com.msa.product.adapter.out.redis;

import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.port.out.ShoesQueryPort;
import com.msa.product.application.port.out.ShoesRedisStockManagePort;
import com.msa.product.domain.Shoes;
import com.msa.product.exception.InsufficientStockException;
import com.msa.product.exception.NotFoundShoesException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisStockManageAdapter implements ShoesRedisStockManagePort {
    private final RedisTemplate<String, Integer> redisTemplate;
    private final ShoesQueryPort shoesQueryPort;
    private final RedisScript<Long> decreaseStockScript;

    @Override
    public void decreaseStock(List<OrderItem> orderLine) {

        // 만약 레디스에 존재하지 않는 상품이 있으면 레디스에 먼저 로드하기
        fetchAndCacheStock(orderLine);

        // 레디스에 재고 감소 (원자적으로 처리)
        decreaseFromRedis(orderLine);

        // TODO: 변경 내용 데이터베이스에 동기화 (비동기 처리)

    }

    private void decreaseFromRedis(List<OrderItem> orderLine) {
        List<String> keys = new ArrayList<>();
        List<Integer> args = new ArrayList<>();

        for (OrderItem orderItem : orderLine) {
            keys.add("stock:" + orderItem.itemId());
            args.add(orderItem.quantity());
        }

        log.info("[RedisStockManageAdapter.decreaseFromRedis]: keys: {}, args: {}", keys, args);

        Long result = redisTemplate.execute(
            decreaseStockScript,
            keys,
            args.toArray()
        );

        if (result == -2) throw new InsufficientStockException();
    }

    private void fetchAndCacheStock(List<OrderItem> orderLine) {
        for(OrderItem orderItem : orderLine) {
            String key = "stock:" + orderItem.itemId();
            Integer stock = redisTemplate.opsForValue().get(key);

            if(stock != null) continue;
            Shoes shoes = shoesQueryPort.findById(orderItem.itemId()).orElseThrow(NotFoundShoesException::new);
            redisTemplate.opsForValue().set(key, shoes.getQuantity().quantity());
        }
    }

    @Override
    public void rollbackStock(List<OrderItem> orderLine) {

    }
}
