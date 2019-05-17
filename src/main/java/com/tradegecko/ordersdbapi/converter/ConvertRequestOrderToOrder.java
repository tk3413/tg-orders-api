package com.tradegecko.ordersdbapi.converter;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ConvertRequestOrderToOrder implements Converter<Pair<Order, OrderRequest>, Order> {

    @Override
    public Order convert(Pair<Order, OrderRequest> orderOrderRequestPair) {

        Order existingOrder = orderOrderRequestPair.getFirst();
        OrderRequest orderRequest = orderOrderRequestPair.getSecond();

        Order newOrder = new Order();
        newOrder.setCustomerName(orderRequest.getCustomerName() != null ?
                orderRequest.getCustomerName() :
                existingOrder.getCustomerName());
        newOrder.setCustomerAddress(orderRequest.getCustomerAddress() != null ?
                orderRequest.getCustomerAddress() :
                existingOrder.getCustomerAddress());
        newOrder.setObjectId(orderRequest.getObjectId() != null ?
                orderRequest.getObjectId() :
                existingOrder.getObjectId());
        newOrder.setShipDate(orderRequest.getShipDate() != null ?
                orderRequest.getShipDate() :
                existingOrder.getShipDate());
        newOrder.setShippingProvider(orderRequest.getShippingProvider() != null ?
                orderRequest.getShippingProvider() :
                existingOrder.getShippingProvider());
        newOrder.setStatus(orderRequest.getStatus() != null ?
                orderRequest.getStatus() :
                existingOrder.getStatus());
        return newOrder;
    }
}
