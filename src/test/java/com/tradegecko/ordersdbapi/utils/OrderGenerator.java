package com.tradegecko.ordersdbapi.utils;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class OrderGenerator {

    public Order createTestOrder(int objectId, int timestamp){
        Order testOrder = new Order();
        testOrder.setId(BigInteger.valueOf(objectId));
        testOrder.setObjectId(BigInteger.valueOf(objectId));
        testOrder.setCustomerName(TestConstants.TEST_CUSTOMER_NAME);
        testOrder.setCustomerAddress(TestConstants.TEST_CUSTOMER_ADDRESS);
        testOrder.setStatus(Order.OrderStatus.UNPAID.toString());
        testOrder.setTimestamp(BigInteger.valueOf(timestamp));
        return testOrder;
    }

    public Order updateTestOrderToPaid(Order testOrder, int timestamp){
        Order updatedOrder = new Order();
        updatedOrder.setCustomerName(testOrder.getCustomerName());
        updatedOrder.setCustomerAddress(testOrder.getCustomerAddress());
        updatedOrder.setId(testOrder.getId().add(BigInteger.valueOf(1)));
        updatedOrder.setObjectId(testOrder.getObjectId());
        updatedOrder.setStatus(Order.OrderStatus.PAID.toString());
        updatedOrder.setTimestamp(BigInteger.valueOf(timestamp));
        return updatedOrder;
    }

    public Order updateTestOrderToShipped(Order testOrder, int timestamp){
        Order updatedOrder = new Order();
        updatedOrder.setId(testOrder.getId().add(BigInteger.valueOf(1)));
        updatedOrder.setObjectId(testOrder.getObjectId());
        updatedOrder.setStatus(Order.OrderStatus.SHIPPED.toString());
        updatedOrder.setShipDate(TestConstants.TEST_SHIP_DATE);
        updatedOrder.setShippingProvider(TestConstants.TEST_SHIPPING_PROVIDER);
        updatedOrder.setTimestamp(BigInteger.valueOf(timestamp));
        updatedOrder.setCustomerName(testOrder.getCustomerName());
        updatedOrder.setCustomerAddress(testOrder.getCustomerAddress());
        return updatedOrder;
    }

    public OrderInfoResponse createTestOrderResponseUnpaid(int timestamp){
        OrderInfoResponse testOrderResponse = new OrderInfoResponse();
        testOrderResponse.setCustomerName(TestConstants.TEST_CUSTOMER_NAME);
        testOrderResponse.setCustomerAddress(TestConstants.TEST_CUSTOMER_ADDRESS);
        testOrderResponse.setTimestamp(BigInteger.valueOf(timestamp));
        testOrderResponse.setStatus(Order.OrderStatus.UNPAID.toString());
        return testOrderResponse;
    }

    public OrderInfoResponse createTestOrderResponsePaid(int timestamp){
        OrderInfoResponse testOrderResponse = new OrderInfoResponse();
        testOrderResponse.setCustomerName(TestConstants.TEST_CUSTOMER_NAME);
        testOrderResponse.setCustomerAddress(TestConstants.TEST_CUSTOMER_ADDRESS);
        testOrderResponse.setTimestamp(BigInteger.valueOf(timestamp));
        testOrderResponse.setStatus(Order.OrderStatus.PAID.toString());
        return testOrderResponse;
    }
}
