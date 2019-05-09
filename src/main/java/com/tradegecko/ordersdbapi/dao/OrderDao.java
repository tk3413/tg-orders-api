package com.tradegecko.ordersdbapi.dao;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderRequest;

import java.math.BigInteger;
import java.util.List;

public interface OrderDao {
    List<Order> getAllOlderOrders(BigInteger orderId, BigInteger requestedTimestamp);

    Order save(Order requestedOrder);
}
