package marketdisseminationsimulation.model.Orders;

import org.MarketDisseminationServer.Orders.OrderCore;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderCoreTest
{
    @Test
    public void testOrderCoreConstructor () {
        int username = 12345;
        int securityId = 1;
        int orderId = 6789;

        OrderCore orderCore = new OrderCore(username, securityId, orderId);

        assertEquals(username, orderCore.username());
        assertEquals(securityId, orderCore.securityId());
        assertEquals(orderId, orderCore.orderId());
    }
}
