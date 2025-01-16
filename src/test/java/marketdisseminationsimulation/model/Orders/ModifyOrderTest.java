package marketdisseminationsimulation.model.Orders;

import org.MarketDisseminationServer.Orders.ModifyOrder;
import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.Orders.CancelOrder;
import org.MarketDisseminationServer.Orders.NewOrder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModifyOrderTest {

    @Test
    public void testModifyOrderConstructor() {
        // Arrange
        int username = 123;
        int securityId = 456;
        int orderId = 789;
        OrderCore orderCore = new OrderCore(username, securityId, orderId);
        int modifyPrice = 100;
        int modifyQuantity = 10;
        boolean isBuySide = true;

        ModifyOrder modifyOrder = new ModifyOrder(orderCore, modifyPrice, modifyQuantity, isBuySide);

        assertEquals(orderCore, modifyOrder.getOrderCore());
        assertEquals(modifyPrice, modifyOrder.getPrice());
        assertEquals(modifyQuantity, modifyOrder.getQuantity());
        assertTrue(modifyOrder.isBuySide());
    }

    @Test
    public void testCancelOrder() {
        int username = 123;
        int securityId = 456;
        int orderId = 789;
        OrderCore orderCore = new OrderCore(username, securityId, orderId);
        int modifyPrice = 100;
        int modifyQuantity = 10;
        boolean isBuySide = true;

        ModifyOrder modifyOrder = new ModifyOrder(orderCore, modifyPrice, modifyQuantity, isBuySide);
        CancelOrder cancelOrder = modifyOrder.createCancelOrder();
        CancelOrder toCompare = new CancelOrder(orderCore);
        assertEquals(cancelOrder, toCompare);
    }

    @Test
    public void testNewOrder() {
        int username = 123;
        int securityId = 456;
        int orderId = 789;
        OrderCore orderCore = new OrderCore(username, securityId, orderId);
        int price = 100;
        int quantity = 10;
        boolean isBuySide = false;

        ModifyOrder modifyOrder = new ModifyOrder(orderCore, price, quantity, isBuySide);

        NewOrder newOrder = modifyOrder.createNewOrder();

        assertEquals(newOrder, new NewOrder(orderCore, quantity, price, false));
    }
}
