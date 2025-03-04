for i = 1, #KEYS do
    local currentStock = tonumber(redis.call('GET', KEYS[i]))
    local decreaseAmount = tonumber(ARGV[i])

    if currentStock == nil then
        return -1 -- 재고 키가 없으면 -1 반환
    end

    if currentStock < decreaseAmount then
        return -2 -- 재고 부족 시 -2 반환
    end
end

-- 모든 검증이 완료되면 실제로 감소
for i = 1, #KEYS do
    redis.call('DECRBY', KEYS[i], ARGV[i])
end

return 0 -- 성공적으로 처리 완료
