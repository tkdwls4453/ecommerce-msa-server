package com.msa.product.adapter.out.persistence;

import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoesQueryJpaRepository extends JpaRepository<ShoesEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ShoesEntity  s where s.shoesId in :idList")
    List<ShoesEntity> findByShoesIdInWithPessimisticLock(@Param("idList") List<Long> idList);
}
