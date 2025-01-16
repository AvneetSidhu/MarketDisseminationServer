package marketdisseminationsimulation.model.Orders;


import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.Orders.NewOrder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NewOrderTest {

    @Test
    void testNewOrderInitialization() {
        OrderCore core = new OrderCore(12345,1,6789);
        NewOrder order = new NewOrder(core, 10, 100, true);

        assertEquals(10, order.getPrice());
        assertEquals(6789, order.getOrderId());
        assertTrue(order.isBuySide());
        assertEquals(100, order.getInitialQuantity());
        assertEquals(100, order.getInitialQuantity());
        assertEquals(12345, order.getUsername());
    }
}
