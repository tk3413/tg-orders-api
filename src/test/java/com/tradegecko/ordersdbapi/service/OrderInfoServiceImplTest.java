package com.tradegecko.ordersdbapi.service;

import com.tradegecko.ordersdbapi.dao.impl.OrderDaoImpl;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.service.impl.OrderInfoServiceImpl;
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
public class OrderInfoServiceImplTest {

    @Mock
    OrderDaoImpl orderDao;

    @InjectMocks
    OrderInfoServiceImpl orderInfoService;

    @Test
    public void testOrderInfoService() {
        List<Order> orderList = new ArrayList<>();

        when(orderDao.getOrders(BigInteger.valueOf(1), BigInteger.valueOf(12345))).thenReturn(orderList);
        assert orderInfoService.getOrderInformation(BigInteger.valueOf(1), BigInteger.valueOf(12345)).equals(orderList);
    }
}