package com.tradegecko.ordersdbapi.utils;

import com.tradegecko.ordersdbapi.converter.ConvertOrderInfoToResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

@Component
public class Utils {

    @Autowired
    ConvertOrderInfoToResponse converter;

    public BigInteger validateTimestamp(Optional<BigInteger> requestedTimestamp) {
        return requestedTimestamp.isEmpty() ? BigInteger.valueOf(Instant.now().getEpochSecond()) : requestedTimestamp.get();
    }

}
