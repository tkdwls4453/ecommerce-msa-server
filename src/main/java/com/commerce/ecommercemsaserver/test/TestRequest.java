package com.commerce.ecommercemsaserver.test;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TestRequest {

    @NotEmpty
    private String name;

    @DecimalMin(value = "10")
    private int age;
}
