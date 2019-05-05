package com.tradegecko.ordersdbapi.dao.impl;

import com.tradegecko.ordersdbapi.dao.OrderDao;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrders(BigInteger orderId, BigInteger requestedTimestamp){
        return orderRepository.getOrdersByObjectIdAndTimestampIsLessThanEqual(orderId, requestedTimestamp);
    }

}
