package com.tradegecko.ordersdbapi.dao;

import com.tradegecko.ordersdbapi.model.Order;

import java.math.BigInteger;
import java.util.List;

public interface OrderDao {
    List<Order> getOrders(BigInteger orderId, BigInteger requestedTimestamp);

}
