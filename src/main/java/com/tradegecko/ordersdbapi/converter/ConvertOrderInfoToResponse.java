package com.tradegecko.ordersdbapi.converter;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConvertOrderInfoToResponse implements Converter<Order, OrderInfoResponse> {

    @Override
    public OrderInfoResponse convert(Order order) {
        OrderInfoResponse orderInfoResponse = new OrderInfoResponse();
            orderInfoResponse.setShipDate(order.getShipDate());
            orderInfoResponse.setShippingProvider(order.getShippingProvider());
            orderInfoResponse.setStatus(order.getStatus());
            orderInfoResponse.setTimestamp(order.getTimestamp());
            orderInfoResponse.setCustomerName(order.getCustomerName());
            orderInfoResponse.setCustomerAddress(order.getCustomerAddress());
        return orderInfoResponse;
    }
}
