package marketdisseminationsimulation.model.Orderbook;

import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class OrderBookEntryTest {

    private NewOrder newOrder;
    private OrderBookEntry orderBookEntry;

    @BeforeEach
    void setUp() {
        newOrder = new NewOrder(new OrderCore(12345, 1, 6789), 50, 100, true);
        orderBookEntry = new OrderBookEntry(newOrder,100);
    }

    @Test
    void testIncreaseQuantity() {
        orderBookEntry.increaseQuantity(10);
        assertEquals(110, orderBookEntry.getCurrentQuantity());
    }

    @Test
    void testDecreaseQuantity() {
        orderBookEntry.decreaseQuantity(10);
        assertEquals(90, orderBookEntry.getCurrentQuantity());
    }

    @Test
    void testDecreaseQuantityThrowsExceptionWhenExceeds() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderBookEntry.decreaseQuantity(200);
        });
        assertEquals("Quantity exceeds maximum quantity", exception.getMessage());
    }

    @Test
    void testOrderBookEntryInitialValues() {
        assertEquals(newOrder.getPrice(), orderBookEntry.getPrice());
        assertEquals(newOrder.isBuySide(), orderBookEntry.isBuySide());
        assertEquals(newOrder.getUsername(), orderBookEntry.getUsername());
        assertEquals(newOrder.getSecurityId(), orderBookEntry.getSecurityId());
        assertEquals(newOrder.getOrderId(), orderBookEntry.getOrderId());
    }

    @Test
    void testCreationTimeIsSet() {
        assertNotNull(orderBookEntry.getCreationTime());
    }
}
