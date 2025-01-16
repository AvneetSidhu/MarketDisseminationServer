package marketdisseminationsimulation.model.Orderbook;

import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.Orderbook.Orderbook;
import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderbookTest {
    private Orderbook orderbook;
    private NewOrder newOrder1;
    private NewOrder newOrder2;
    private Level level90;
    private Level level100;
    private Level level50;
    private Level level60;


    @BeforeEach
    void setUp() {
        level90 = new Level(90, new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 90, 100, true),100));
        level100 = new Level(100, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, true),50));
        level50 = new Level(50, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, false),100));
        level60 = new Level(60, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, false),50));
        orderbook = new Orderbook(1);
        orderbook.buySide.put(90, level90);
        orderbook.buySide.put(100, level100);
        orderbook.sellSide.put(50, level50);
        orderbook.sellSide.put(60, level60);
    }

    @Test
    void testGetBuySideLevel() {
        assertEquals(level90, orderbook.getBuysideLevel(90));
        assertEquals(level100,orderbook.getBuysideLevel(100));
        assertNull(orderbook.getBuysideLevel(95));
    }

    @Test
    void testGetSellSideLevel() {
        assertEquals(level50, orderbook.getSellSideLevel(50));
        assertEquals(level60, orderbook.getSellSideLevel(60));
        assertNull(orderbook.getSellSideLevel(55));
    }

    @Test
    void testAddOrder() {
        OrderBookEntry order1 = new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 110, 100, true),100);
        OrderBookEntry order2 = new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 40, 75, false),50);

        orderbook.addOrder(order1);
        orderbook.addOrder(order2);

        assertEquals(3, orderbook.buySide.size());
        assertEquals(3, orderbook.sellSide.size());
        assertEquals(1, orderbook.getBuysideLevel(110).getLevelOrderCount());
        assertEquals(1, orderbook.getSellSideLevel(40).getLevelOrderCount());
        assertEquals(order1, orderbook.getBuysideLevel(110).getOrderBookEntry());
        assertEquals(order2, orderbook.getSellSideLevel(40).getOrderBookEntry());
    }
//
    @Test
    void testRemoveOrder() {
        assertEquals(100, orderbook.getBestBid());
        orderbook.removeOrder(100, true);
        assertEquals(90, orderbook.getBestBid());
        orderbook.removeOrder(90, true);
        assertNull(orderbook.getBestBid());
        assertNull(orderbook.buySide.get(100));
        assertNull(orderbook.sellSide.get(90));
    }

    @Test
    void testGetBestBid() {
        assertEquals(100, orderbook.getBestBid());
        orderbook.addOrder(new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 200, 75, true),50));
        assertEquals(200, orderbook.getBestBid());
    }

    @Test
    void testGetBestAsk() {
        assertEquals(50, orderbook.getBestAsk());
        orderbook.addOrder(new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 10, 75, false),50));
        assertEquals(10, orderbook.getBestAsk());
    }
}
