package com.tradegecko.ordersdbapi.dao;

import com.tradegecko.ordersdbapi.dao.impl.OrderDaoImpl;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import com.tradegecko.ordersdbapi.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoImplTest {
    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderDaoImpl orderDaoimpl;

    @Test
    public void testOrderDaoGet() {
        List<Order> orderList = new ArrayList<>();

        when(orderRepository.getOrdersByObjectIdAndTimestampIsLessThanEqual(BigInteger.valueOf(1), BigInteger.valueOf(123456)))
                .thenReturn(orderList);
        assert orderDaoimpl.getAllOlderOrders(BigInteger.valueOf(1), BigInteger.valueOf(123456)).equals(orderList);
    }

    @Test
    public void testOrderDaoSave() {
        Order order = new Order();

        when(orderRepository.save(order)).thenReturn(order);

        assert orderDaoimpl.save(order).equals(order);
    }
}