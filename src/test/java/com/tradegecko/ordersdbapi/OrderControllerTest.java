package com.tradegecko.ordersdbapi;

import com.google.gson.Gson;
import com.tradegecko.ordersdbapi.converter.ConvertOrderInfoToResponse;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import com.tradegecko.ordersdbapi.service.impl.OrderInfoServiceImpl;
import com.tradegecko.ordersdbapi.utils.OrderGenerator;
import com.tradegecko.ordersdbapi.utils.TestConstants;
import com.tradegecko.ordersdbapi.utils.Utils;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private OrderGenerator orderGenerator = new OrderGenerator();

    @Mock
    OrderInfoServiceImpl orderInfoService;

    @Mock
    ConvertOrderInfoToResponse converter;

    @Mock
    Utils utils;

    @InjectMocks
    OrderController controller;

    // GET tests
    //
    @Test
    public void testControllerFindNewestOrderGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(16660);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        Order oldOrder = orderGenerator.createTestOrder(1, 12340);
        Order newOrder = orderGenerator.updateTestOrderToPaid(oldOrder, 15550);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldOrder);
        orderList.add(newOrder);
        when(orderInfoService.getAllOlderOrders(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedResponse = orderGenerator.createTestOrderResponsePaid(15550);
        Mockito.lenient().when(converter.convert(newOrder)).thenReturn(mockedResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.of(requestedTimestamp));

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerFindOldOrderGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(12345);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        Order oldOrder = orderGenerator.createTestOrder(1, 12340);
        Order newOrder = orderGenerator.updateTestOrderToPaid(oldOrder, 15550);

        List<Order> orderList = new ArrayList<>();
            orderList.add(oldOrder);
            orderList.add(newOrder);
        when(orderInfoService.getAllOlderOrders(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedResponse = orderGenerator.createTestOrderResponseUnpaid(12340);
        when(converter.convert(oldOrder)).thenReturn(mockedResponse);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.of(requestedTimestamp));

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerFindTheLatestOfOlderOrdersGivenTimestamp(){
        BigInteger requestedTimestamp = BigInteger.valueOf(14592);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        Order oldestOrder = orderGenerator.createTestOrder(1, 12340);
        Order newerOrder = orderGenerator.updateTestOrderToPaid(oldestOrder, 13455);
        Order newOrder = orderGenerator.updateTestOrderToShipped(newerOrder, 17890);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldestOrder);
        orderList.add(newerOrder);
        orderList.add(newOrder);
        when(orderInfoService.getAllOlderOrders(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        OrderInfoResponse mockedNewerResponse = orderGenerator.createTestOrderResponsePaid(13455);
        when(converter.convert(newerOrder)).thenReturn(mockedNewerResponse);


        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.of(requestedTimestamp));

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

        Mockito.lenient().when(orderInfoService.getAllOlderOrders(BigInteger.valueOf(1), requestedTimestamp)).thenReturn(orderList);

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.of(BigInteger.valueOf(11110)));

        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testControllerOrderNotFoundGivenTimestamp(){
        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.of(BigInteger.valueOf(12345)));

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
        when(orderInfoService.getAllOlderOrders(isA(BigInteger.class), isA(BigInteger.class))).thenReturn(orderList);

        OrderInfoResponse mockedNewResponse = orderGenerator.createTestOrderResponsePaid(13455);
        when(converter.convert(isA(Order.class))).thenReturn(mockedNewResponse);

        when(utils.validateTimestamp(any())).thenReturn(BigInteger.valueOf(12345));

        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.empty());

        ResponseEntity<String> expectedResponse = new ResponseEntity<>(new Gson().toJson(mockedNewResponse), HttpStatus.OK);

        assert(response.getStatusCode() == HttpStatus.OK);
        assert(Objects.equals(expectedResponse.getBody(), response.getBody()));
    }

    @Test
    public void testControllerOrderNotFoundNotGivenTimestamp(){
        ResponseEntity response = controller.orderInfo(BigInteger.valueOf(1), Optional.empty());
        assert(response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testHealthCheck() {
        assert controller.health().equals(new ResponseEntity(HttpStatus.OK));
    }

    // POST tests
    //
    @Test
    public void testPostNoOlderOrders() {
        BigInteger requestedTimestamp = BigInteger.valueOf(16660);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        OrderRequest newOrderRequest = orderGenerator.createNewOrderRequest(1, 12345);

        List<Order> orderList = new ArrayList<>();
        when(orderInfoService.getAllOlderOrders(any(), any())).thenReturn(orderList);

        Order generatedTestOrder = orderGenerator.createTestOrder(1, 12345);
        when(orderInfoService.save(any())).thenReturn(generatedTestOrder);

        ResponseEntity response = controller.newOrder(newOrderRequest);

        assert response.getStatusCode().equals(HttpStatus.CREATED);
        assert Objects.equals(response.getBody(), new Gson().toJson(generatedTestOrder));
    }

    @Test
    public void testPostWithOlderOrder() {
        BigInteger requestedTimestamp = BigInteger.valueOf(16660);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        OrderRequest newOrderRequest = orderGenerator.createNewOrderRequest(1, 12345);

        Order oldOrder = orderGenerator.createTestOrder(1, 12340);

        List<Order> orderList = new ArrayList<>();
            orderList.add(oldOrder);
        when(orderInfoService.getAllOlderOrders(any(), any())).thenReturn(orderList);

        Order generatedTestOrder = orderGenerator.createTestOrder(1, 22347);
        when(orderInfoService.save(any())).thenReturn(generatedTestOrder);

        ResponseEntity response = controller.newOrder(newOrderRequest);

        assert response.getStatusCode().equals(HttpStatus.CREATED);
        assert Objects.equals(response.getBody(), new Gson().toJson(generatedTestOrder));
    }

    @Test
    public void testPostWithOlderEmptyOrder() {
        BigInteger requestedTimestamp = BigInteger.valueOf(16660);
        when(utils.validateTimestamp(any())).thenReturn(requestedTimestamp);

        OrderRequest newOrderRequest = orderGenerator.createNewOrderRequest(1, 12345);

        Order oldOrder = new Order();
            oldOrder.setTimestamp(BigInteger.valueOf(1233));
            oldOrder.setObjectId(BigInteger.valueOf(1));
            oldOrder.setCustomerName(TestConstants.TEST_CUSTOMER_NAME);

        List<Order> orderList = new ArrayList<>();
        orderList.add(oldOrder);
        when(orderInfoService.getAllOlderOrders(any(), any())).thenReturn(orderList);

        Order generatedTestOrder = orderGenerator.createTestOrder(1, 22347);
        when(orderInfoService.save(any())).thenReturn(generatedTestOrder);

        ResponseEntity response = controller.newOrder(newOrderRequest);

        assert response.getStatusCode().equals(HttpStatus.CREATED);
        assert Objects.equals(response.getBody(), new Gson().toJson(generatedTestOrder));
    }
}
