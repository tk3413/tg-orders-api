package com.example.tradegecko.model;

import lombok.Data;

@Data
class Order {
    private Customer customer;

    private String status;

    private String shipDate;

    private String shippingProvider;

    private String timestamp;
}
