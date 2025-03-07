package com.msa.entry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Model {
    public Model(Long modelId) {
        this.modelId = modelId;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Long modelId;


    public Model() {

    }
}
