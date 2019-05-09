package com.tradegecko.ordersdbapi.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class OrderInfoResponse {

    private String customerName;

    private String customerAddress;

    private String status;

    private String shipDate;

    private String shippingProvider;

    private BigInteger timestamp;
}
