package marketdisseminationsimulation.model.Orderbook;

import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class LevelTest {

    private NewOrder newOrder1;
    private NewOrder newOrder2;
    private Level level;
    private OrderBookEntry order1;
    private OrderBookEntry order2;

    @BeforeEach
    void setUp() {
        newOrder1 = new NewOrder(new OrderCore(12345, 1, 6789), 50, 100, true);
        newOrder2 = new NewOrder(new OrderCore(54321, 1, 9876), 50, 75, true);
        order1 = new OrderBookEntry(newOrder1,100);
        order2 = new OrderBookEntry(newOrder2,50);
        level = new Level(50, order1);
    }

    @Test
    void testAddOrderBookEntry() {
        level.addOrderBookEntry(order2);
        assertEquals(2, level.getLevelOrderCount());
        assertEquals(150, level.getLevelOrderQuantity());
    }

    @Test
    void testRemoveOrderBookEntry() {
        level.addOrderBookEntry(order2);
        level.removeOrderBookEntry();
        assertEquals(50, level.getLevelOrderQuantity());
        assertEquals(1, level.getLevelOrderCount());
    }

    @Test
    void testIsEmpty() {
        assertFalse(level.isEmpty());
        level.removeOrderBookEntry();
        assertTrue(level.isEmpty());
    }

    @Test
    void testGetLevelOrderCount() {

        assertEquals(1, level.getLevelOrderCount());
        level.addOrderBookEntry(order2);
        assertEquals(2, level.getLevelOrderCount());
    }

    @Test
    void testGetLevelOrderQuantity() {

        assertEquals(100, level.getLevelOrderQuantity());
        level.addOrderBookEntry(order2);
        assertEquals(150, level.getLevelOrderQuantity());
    }

    @Test
    void testGetOrderBookEntry() {
        assertEquals(order1, level.getOrderBookEntry());
    }
}
