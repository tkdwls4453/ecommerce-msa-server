package com.msa.product.adapter.out.persistence;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ShoesCommandJpaRepository extends JpaRepository<ShoesEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update ShoesEntity s set s.quantity = :quantity  where s.shoesId = :shoesId")
    void updateStock(@Param("shoesId") Long shoesId, @Param("quantity") Integer quantity);
}
