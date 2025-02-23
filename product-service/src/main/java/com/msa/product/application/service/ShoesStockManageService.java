package com.msa.product.application.service;

import com.msa.product.application.port.in.DecreaseStockCommand;
import com.msa.product.application.port.in.OrderItem;
import com.msa.product.application.port.in.ProductStockUseCase;
import com.msa.product.application.port.out.ShoesQueryPort;
import com.msa.product.application.port.out.ShoesStockManagePort;
import com.msa.product.domain.Shoes;
import com.msa.product.exception.NotFoundShoesException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ShoesStockManageService implements ProductStockUseCase {

    private final ShoesQueryPort productQueryPort;
    private final ShoesStockManagePort shoesStockManagePort;

    @Override
    public void decreaseStock(DecreaseStockCommand command) {
        Map<Long, Integer> orderQuantityMap = command.orderLine().stream()
            .collect(Collectors.toMap(OrderItem::itemId, OrderItem::quantity));

        List<Long> idList = orderQuantityMap.keySet().stream().toList();
        List<Shoes> shoesList = productQueryPort.findByIdIn(idList);

        if(idList.size() != shoesList.size()) {
            throw new NotFoundShoesException();
        }

        shoesList.forEach(
            shoes -> shoes.decreaseQuantity(orderQuantityMap.get(shoes.getShoesId()))
        );

        shoesStockManagePort.updateStock(shoesList);
    }
}
