package com.tradegecko.ordersdbapi.repository;

import com.tradegecko.ordersdbapi.model.Order;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;
import java.util.List;

public interface OrderRepository extends Repository<Order, BigInteger> {

    List<Order> getOrdersByObjectIdAndTimestampIsLessThanEqual(BigInteger objectId, BigInteger timestamp);
}
