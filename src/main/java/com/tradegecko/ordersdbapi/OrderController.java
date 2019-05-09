package com.tradegecko.ordersdbapi;

import com.google.gson.Gson;
//import com.tradegecko.ordersdbapi.converter.ConversionServiceProvider;
import com.tradegecko.ordersdbapi.converter.ConvertOrderInfoToResponse;
import com.tradegecko.ordersdbapi.converter.ConvertRequestOrderToOrder;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import com.tradegecko.ordersdbapi.service.impl.OrderInfoServiceImpl;
import com.tradegecko.ordersdbapi.utils.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.of;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class OrderController {

    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Autowired
    private ConvertOrderInfoToResponse convertOrderInfoToResponse;

    @Autowired
    private ConvertRequestOrderToOrder convertRequestOrderToOrder;

    @Autowired
    Utils utils;

    private final Logger log = Logger.getLogger(OrdersDbApi.class);

    @RequestMapping(
            path = "/orderInfo",
            method = GET)
    public @ResponseBody ResponseEntity<String> orderInfo(
            @RequestParam(name = "orderId") final BigInteger orderId,
            @RequestParam(name = "timestamp", required = false) Optional<BigInteger> requestedTimestamp) {

        BigInteger timestamp = utils.validateTimestamp(requestedTimestamp);

        log.info(MessageFormat.format("Request: order info with orderId {0}, and timestamp {1}",
                orderId, timestamp));

        List<Order> oldOrdersList = orderInfoService.getAllOlderOrders(orderId, timestamp);

        Optional<OrderInfoResponse> mostRecentOrder = findLatestOrderAndFormatAsResponse(oldOrdersList, timestamp);

        return mostRecentOrder.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(new Gson().toJson(mostRecentOrder.get()), HttpStatus.OK);
    }

    @RequestMapping(
            path = "/orderInfo",
            method = POST)
    public @ResponseBody ResponseEntity<String> newOrder(@RequestBody OrderRequest requestOrder) {

        BigInteger requestedTimestamp = utils.validateTimestamp(Optional.ofNullable(requestOrder.getTimestamp()));

        Optional<Order> existingOrder = Optional.empty();

        if(requestOrder.getObjectId() != null) {
            List<Order> orderInfoList = orderInfoService.getAllOlderOrders(requestOrder.getObjectId(), requestedTimestamp);
            existingOrder = findMostRecentOrder(orderInfoList, requestedTimestamp);
        }

        Order newOrder = new Order();
        if(existingOrder.isEmpty()) {
                newOrder.setCustomerName(requestOrder.getCustomerName());
                newOrder.setCustomerAddress(requestOrder.getCustomerAddress());
                newOrder.setObjectId(requestOrder.getObjectId());
                newOrder.setShipDate(requestOrder.getShipDate());
                newOrder.setShippingProvider(requestOrder.getShipDate());
                newOrder.setStatus(requestOrder.getStatus());
                newOrder.setId(requestOrder.getObjectId().add(requestedTimestamp));
        } else {
            Order previousOrder = existingOrder.get();
            newOrder = convertRequestOrderToOrder.convert(Pair.of(previousOrder, requestOrder));
        }

        Objects.requireNonNull(newOrder).setTimestamp(requestedTimestamp);
        try {
            Order response = orderInfoService.save(newOrder);
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.CREATED);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(
            path = "/health",
            method = GET)
    ResponseEntity<String> health() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Optional<OrderInfoResponse> findLatestOrderAndFormatAsResponse(List<Order> orderList, BigInteger requestedTimestamp) {
        Optional<Order> existingOrder = findMostRecentOrder(orderList, requestedTimestamp);

        return existingOrder.isEmpty() ? Optional.empty() : Optional.ofNullable(convertOrderInfoToResponse.convert(existingOrder.get()));
    }

    private Optional<Order> findMostRecentOrder(List<Order> orderList, BigInteger requestedTimestamp) {
        Optional<Order> existingOrder = Optional.empty();

        orderList.sort(Comparator.comparing(Order::getTimestamp));

        for (Order orderInList : orderList) {
            if (orderInList.getTimestamp().compareTo(requestedTimestamp) < 1) {
                existingOrder = of(orderInList);
            }
        }

        return existingOrder;
    }
}
