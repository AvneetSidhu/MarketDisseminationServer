package marketdisseminationsimulation.model.Orders;

import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.Orders.CancelOrder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CancelOrderTest {

    @Test
    public void testCancelOrderConstructor() {
        // Arrange
        int username = 123;
        int securityId = 456;
        int orderId = 789;
        OrderCore core = new OrderCore(username, securityId, orderId);

        CancelOrder cancelOrder = new CancelOrder(core);
        OrderCore toCompare = cancelOrder.getOrderCore();
        assertEquals(core, toCompare);
    }
}
