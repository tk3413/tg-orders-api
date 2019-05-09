package com.tradegecko.ordersdbapi.service.impl;

import com.tradegecko.ordersdbapi.dao.impl.OrderDaoImpl;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import com.tradegecko.ordersdbapi.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderDaoImpl orderDao;

    public List<Order> getAllOlderOrders(BigInteger orderId, BigInteger requestedTimestamp) {
        return orderDao.getAllOlderOrders(orderId, requestedTimestamp);
    }

    public Order save(Order requestedOrder) {
        return orderDao.save(requestedOrder);
    };

}
