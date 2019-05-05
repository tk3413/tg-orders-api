package com.tradegecko.ordersdbapi.utils;

import org.springframework.stereotype.Component;

@Component
public final class TestConstants {
    private TestConstants(){}

    public static final String TEST_SHIPPING_PROVIDER = "DHL";
    public static final String TEST_CUSTOMER_ADDRESS = "Home St";
    public static final String TEST_CUSTOMER_NAME = "Alice";
    public static final String TEST_SHIP_DATE = "2019-03-01";
}
