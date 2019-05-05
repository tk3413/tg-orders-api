package com.tradegecko.ordersdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class OrdersDbApi {

	public static void main(String[] args) {
		SpringApplication.run(OrdersDbApi.class, args);
	}
}
