package marketdisseminationsimulation.model.Orderbook.DTO;

import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orderbook.Orderbook;
import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;
import org.junit.jupiter.api.BeforeEach;

public class OrderbookSnapShotTest {

    private Orderbook orderbook;
    private NewOrder newOrder1;
    private NewOrder newOrder2;
    private Level level90;
    private Level level100;
    private Level level50;
    private Level level60;

    @BeforeEach
    void setUp() {
        level90 = new Level(90, new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 90, 100, true), 100));
        level100 = new Level(100, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, true), 50));
        level50 = new Level(50, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, false), 100));
        level60 = new Level(60, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, false), 50));
        orderbook = new Orderbook();
        orderbook.buySide.put(90, level90);
        orderbook.buySide.put(100, level100);
        orderbook.sellSide.put(50, level50);
        orderbook.sellSide.put(60, level60);
    }

    void testSerialize() {

    }

}
