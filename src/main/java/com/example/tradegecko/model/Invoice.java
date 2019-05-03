package com.example.tradegecko.model;

import lombok.Data;

import java.math.BigInteger;

@Data
class Invoice {
    private BigInteger orderId;

    private BigInteger[] productIds;

    private String status;

    private double total;

    private String timestamp;
}
