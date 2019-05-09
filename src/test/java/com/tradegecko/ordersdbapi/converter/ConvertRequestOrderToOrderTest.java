package com.tradegecko.ordersdbapi.converter;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderRequest;
import com.tradegecko.ordersdbapi.utils.OrderGenerator;
import com.tradegecko.ordersdbapi.utils.TestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import javax.swing.text.TabExpander;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ConvertRequestOrderToOrderTest {

    private OrderGenerator orderGenerator = new OrderGenerator();

    @InjectMocks
    ConvertRequestOrderToOrder converter;

    @Test
    public void testConvertRequestOrderToOrderAllInOrder() {
        Order order = orderGenerator.createTestOrder(1, 12345);
        order.setShippingProvider(TestConstants.TEST_SHIPPING_PROVIDER);
        order.setShipDate(TestConstants.TEST_SHIP_DATE);
        OrderRequest orderRequest = new OrderRequest();

        Order result = converter.convert(Pair.of(order, orderRequest));
        assert result != null;
    }


    @Test
    public void testConvertRequestOrderToOrderAllInRequest() {
        Order order = new Order();
        OrderRequest orderRequest = orderGenerator.createNewOrderRequest(1, 12345);
        orderRequest.setShipDate(TestConstants.TEST_SHIP_DATE);
        orderRequest.setShippingProvider(TestConstants.TEST_SHIPPING_PROVIDER);

        Order result = converter.convert(Pair.of(order, orderRequest));
        assert result != null;
    }
}