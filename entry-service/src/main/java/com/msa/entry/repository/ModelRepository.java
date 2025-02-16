package com.msa.entry.repository;

import com.msa.entry.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    boolean existsById(Long modelId);
}
