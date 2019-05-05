package com.tradegecko.ordersdbapi;

import com.google.gson.Gson;
import com.tradegecko.ordersdbapi.converter.ConvertOrderInfoToResponse;
import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import com.tradegecko.ordersdbapi.service.impl.OrderInfoServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class OrderController {

    @Autowired
    private OrderInfoServiceImpl orderInfoService;

    @Autowired
    private ConvertOrderInfoToResponse converter;

    private final Logger log = Logger.getLogger(OrdersDbApi.class);

    @RequestMapping(
            path = "/orderInfo",
            method = GET)
    public @ResponseBody ResponseEntity<String> orderInfo(
            @RequestParam(name = "orderId") final BigInteger orderId,
            @RequestParam(name = "timestamp", required = false) BigInteger requestedTimestamp) {

        requestedTimestamp = requestedTimestamp == null ? BigInteger.valueOf(Instant.now().getEpochSecond()) : requestedTimestamp;

        log.info(MessageFormat.format("Request: order info with orderId {0}, and timestamp {1}",
                orderId, requestedTimestamp));

        List<Order> orderInfoList = orderInfoService.getOrderInformation(orderId, requestedTimestamp);

        if (orderInfoList.isEmpty()) {
            log.info(MessageFormat.format("Response for Order {0} and timestamp {1}: Order not found",
                    orderId, requestedTimestamp));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderInfoList.sort(Comparator.comparing(Order::getTimestamp));

        OrderInfoResponse response = findLatestOrder(orderInfoList, requestedTimestamp);

        log.info(MessageFormat.format("Response for Order {0} and timestamp {1}: {2}",
                orderId, requestedTimestamp, response.toString()));
        return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);
    }

    @RequestMapping(
            path = "/health",
            method = GET)
    ResponseEntity<String> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private OrderInfoResponse findLatestOrder(List<Order> orderList, BigInteger requestedTimestamp) {
        OrderInfoResponse response = new OrderInfoResponse();

        for (Order orderInList : orderList) {
            if (orderInList.getTimestamp().compareTo(requestedTimestamp) < 1) {
                response = converter.convert(orderInList);
            }
        }

        return response;
    }
}
