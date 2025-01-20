package com.commerce.ecommercemsaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.msa.common", "com.commerce.ecommercemsaserver"})
@SpringBootApplication
public class EcommerceMsaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceMsaServerApplication.class, args);
	}

}
