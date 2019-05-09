package com.tradegecko.ordersdbapi.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTest {

    @InjectMocks
    Utils utils;

    @Test
    public void testValidateTimestamp() {
        assert utils.validateTimestamp(Optional.of(BigInteger.valueOf(12345689))).equals(BigInteger.valueOf(12345689));
        assert utils.validateTimestamp(Optional.empty()).equals(BigInteger.valueOf(Instant.now().getEpochSecond()));
    }
}
