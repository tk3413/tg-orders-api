package com.tradegecko.ordersdbapi;

import com.google.gson.Gson;
import com.tradegecko.ordersdbapi.converter.ConvertOrderInfoToResponse;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import com.tradegecko.ordersdbapi.service.impl.OrderInfoServiceImpl;
import com.tradegecko.ordersdbapi.utils.OrderGenerator;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private OrderGenerator orderGenerator = new OrderGenerator();

    @Mock
    OrderInfoServiceImpl orderInfoService;

    @Mock
    ConvertOrderInfoToResponse converter;

    @InjectMocks
    OrderController controller;

    @Test
    public void testControllerFindNewestOrderGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(16660);
        Order oldOrder = orderGenerator.createTestOrder(1, 12340);
        Order newOrder = orderGenerator.updateTestOrderToPaid(oldOrder, 15550);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldOrder);
        orderList.add(newOrder);
        when(orderInfoService.getOrderInformation(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedResponse = orderGenerator.createTestOrderResponsePaid(15550);
        Mockito.lenient().when(converter.convert(newOrder)).thenReturn(mockedResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), requestedTimestamp);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerFindOldOrderGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(12345);
        Order oldOrder = orderGenerator.createTestOrder(1, 12340);
        Order newOrder = orderGenerator.updateTestOrderToPaid(oldOrder, 15550);

        List<Order> orderList = new ArrayList<>();
            orderList.add(oldOrder);
            orderList.add(newOrder);
        when(orderInfoService.getOrderInformation(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedResponse = orderGenerator.createTestOrderResponseUnpaid(12340);
        when(converter.convert(oldOrder)).thenReturn(mockedResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), requestedTimestamp);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerFindTheLatestOfOlderOrdersGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(14592);
        Order oldestOrder = orderGenerator.createTestOrder(1, 12340);
        Order newerOrder = orderGenerator.updateTestOrderToPaid(oldestOrder, 13455);
        Order newOrder = orderGenerator.updateTestOrderToShipped(newerOrder, 17890);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldestOrder);
        orderList.add(newerOrder);
        orderList.add(newOrder);
        when(orderInfoService.getOrderInformation(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedNewerResponse = orderGenerator.createTestOrderResponsePaid(13455);
        when(converter.convert(newerOrder)).thenReturn(mockedNewerResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), requestedTimestamp);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedNewerResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerFindNoneBecauseOrderDidNotExistYetGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(11111);
        Order order = orderGenerator.createTestOrder(1, 12340);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        Mockito.lenient().when(orderInfoService.getOrderInformation(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), BigInteger.valueOf(11110));

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testControllerOrderNotFoundGivenTimestamp(){
        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), BigInteger.valueOf(12345));

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testControllerGetLatestOrderNotGivenTimestamp(){
        Order oldestOrder = orderGenerator.createTestOrder(1, 12340);
        Order newerOrder = orderGenerator.updateTestOrderToPaid(oldestOrder, 13455);
        Order newOrder = orderGenerator.updateTestOrderToShipped(newerOrder, 17890);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldestOrder);
        orderList.add(newerOrder);
        orderList.add(newOrder);
        when(orderInfoService.getOrderInformation(isA(BigInteger.class), isA(BigInteger.class))).thenReturn(orderList);

        OrderInfoResponse mockedNewResponse = orderGenerator.createTestOrderResponsePaid(13455);
        when(converter.convert(isA(Order.class))).thenReturn(mockedNewResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), null);

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedNewResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerOrderNotFoundNotGivenTimestamp(){
        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), null);

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testHealthCheck() {
        assert controller.health().equals(new ResponseEntity(HttpStatus.OK));
    }
}
