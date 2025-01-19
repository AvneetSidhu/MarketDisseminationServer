package org.MarketDisseminationServer.Orderbook.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orderbook.Orderbook;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderbookSnapShot {
    public String type;
    public long timestamp;
    public HashMap<Integer, Object[]> bids;
    public HashMap<Integer, Object[]> asks;

    public OrderbookSnapShot(long timestamp, Orderbook orderbook) {
        this.type = "snapshot";
        this.timestamp = timestamp;
        this.bids = sideToHashMap(orderbook.buySide);
        this.asks = sideToHashMap(orderbook.sellSide);
    }

    private AbstractMap.SimpleEntry<Integer, Integer> orderToQuantityPricePair(OrderBookEntry order) {
        return new AbstractMap.SimpleEntry<>(order.getCurrentQuantity(), order.getPrice());
    }

    private AbstractMap.SimpleEntry[] levelToArray(Level level) {
        return level.getOrders().stream()
                .map(this::orderToQuantityPricePair)
                .toArray(AbstractMap.SimpleEntry[]::new);
    }

    private HashMap<Integer, Object[]> sideToHashMap(ConcurrentSkipListMap<Integer, Level> side) {
        HashMap<Integer, Object[]> hashMap = new HashMap<Integer, Object[]>();

        for (Map.Entry<Integer, Level> entry : side.entrySet()) {
            hashMap.put(entry.getKey(), levelToArray(entry.getValue()));
        }
        return hashMap;
    }

    public static String serialize(OrderbookSnapShot snapShot) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(snapShot);
    }
}
