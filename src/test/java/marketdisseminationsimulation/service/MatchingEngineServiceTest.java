package marketdisseminationsimulation.service;

import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;
import org.MarketDisseminationServer.service.MatchingEngineService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatchingEngineServiceTest {

    private MatchingEngineService matchingEngineService;
    private final int securityId = 12345;
    Level level90Buy;
    Level level100Buy;
    Level level90Sell;
    Level level100Sell;

    @BeforeEach
    void setUp() {
        level90Buy = new Level(90, new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 90, 100, true),100));
        level100Buy = new Level(100, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, true),75));
        level90Sell = new Level(90, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 90, 50, false),50));
        level100Sell = new Level(100, new OrderBookEntry(new NewOrder(new OrderCore(54321, 1, 9876), 100, 50, false),50));

        matchingEngineService = new MatchingEngineService(1);
    }

    @Test
    void testMatchBuyOrder_partiallyFilledFromSingle() {
        matchingEngineService.orderbook.sellSide.put(90,level90Sell);
        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, true));

        // sell side level 90 should be wiped and buy side level 100 should be created with 25 units remaining.
        assertEquals(25, matchingEngineService.orderbook.buySide.get(100).getOrderBookEntry().getCurrentQuantity());
        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
    }

    @Test
    void testMatchBuyOrder_fullyFilledFromSingle() {
        matchingEngineService.orderbook.sellSide.put(90,level90Sell);
        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 100, 50, true));

        //sell side level 90 wiped, no level added to buy side ; fully filled orders
        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
    }

    @Test
    void testMatchBuyOrder_multiOrderFill() {
        matchingEngineService.orderbook.sellSide.put(90,level90Sell);
        matchingEngineService.orderbook.addOrder(new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 90, 50, false),50));

        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 90, 100, true));

        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
    }

    @Test
    void testMatchBuyOrder_multiLevelFill() {
        matchingEngineService.orderbook.sellSide.put(90,level90Sell);
        matchingEngineService.orderbook.sellSide.put(100,level100Sell);

        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 110, 100, true));

        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
    }


    @Test
    void testMatchSellOrder_partiallyFilledFromSingle(){
        matchingEngineService.orderbook.buySide.put(100,level100Buy);
        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 100, 100, false));

        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
        assertEquals(25, matchingEngineService.orderbook.sellSide.get(100).getOrderBookEntry().getCurrentQuantity());
    }

    @Test
    void testMatchSellOrder_fullyFilledFromSingle() {
        matchingEngineService.orderbook.buySide.put(100,level100Buy);
        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 100, 75, false));

        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
    }

    @Test
    void testMatchSellOrder_multiOrderFill() {
        matchingEngineService.orderbook.buySide.put(100, level100Buy);
        matchingEngineService.orderbook.addOrder(new OrderBookEntry(new NewOrder(new OrderCore(12345, 1, 6789), 100, 75, true),75));

        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 90, 150, false));

        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
    }

    @Test
    void testMatchSellOrder_multilevelFill() {
        matchingEngineService.orderbook.buySide.put(90,level90Buy);
        matchingEngineService.orderbook.buySide.put(100,level100Buy);

        matchingEngineService.matchOrder(new NewOrder(new OrderCore(54321, 1, 9876), 70, 175, false));

        assertTrue(matchingEngineService.orderbook.buySide.isEmpty());
        assertTrue(matchingEngineService.orderbook.sellSide.isEmpty());
    }

}
