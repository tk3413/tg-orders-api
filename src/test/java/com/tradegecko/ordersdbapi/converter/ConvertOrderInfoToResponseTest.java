package com.tradegecko.ordersdbapi.converter;

import com.tradegecko.ordersdbapi.model.Order;
import com.tradegecko.ordersdbapi.model.OrderInfoResponse;
import com.tradegecko.ordersdbapi.utils.OrderGenerator;
import static com.tradegecko.ordersdbapi.utils.TestConstants.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConvertOrderInfoToResponseTest {

    private ConvertOrderInfoToResponse converter = new ConvertOrderInfoToResponse();

    private OrderGenerator orderGenerator = new OrderGenerator();

    @Test
    public void testConverter(){
        Order testOrder = orderGenerator.createTestOrder(1, 1234);
        testOrder = orderGenerator.updateTestOrderToPaid(testOrder, 4567);
        testOrder = orderGenerator.updateTestOrderToShipped(testOrder, 7890);
        OrderInfoResponse response = converter.convert(testOrder);

        assert response != null;
        assert response.getShipDate().equals(TEST_SHIP_DATE);
        assert (response.getShippingProvider().equals(TEST_SHIPPING_PROVIDER));
        assert (response.getStatus().equals(Order.OrderStatus.SHIPPED.toString()));
        assert (response.getCustomerAddress().equals(TEST_CUSTOMER_ADDRESS));
        assert (response.getCustomerName().equals(TEST_CUSTOMER_NAME));
        assert (response.getTimestamp().toString().equals("7890"));
    }

}
