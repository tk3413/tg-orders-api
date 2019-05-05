package com.tradegecko.ordersdbapi.service;

import com.tradegecko.ordersdbapi.model.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderInfoService {
    List<Order> getOrderInformation(BigInteger orderId, BigInteger requestedTimestamp);
}
