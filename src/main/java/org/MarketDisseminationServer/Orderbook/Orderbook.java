package org.MarketDisseminationServer.Orderbook;

import org.MarketDisseminationServer.Orderbook.Level;

import java.util.concurrent.ConcurrentSkipListMap;

//holds levels
public class Orderbook {
    public ConcurrentSkipListMap<Integer, Level> buySide;
    public ConcurrentSkipListMap<Integer, Level> sellSide;

    public Orderbook(int SecurityId) {
        buySide = new ConcurrentSkipListMap<>();
        sellSide = new ConcurrentSkipListMap<>();
    }

    public Level getBuysideLevel(int price) {
        return buySide.get(price);
    }

    public Level getSellSideLevel(int price) {
        return sellSide.get(price);
    }

    public void addOrder(OrderBookEntry entry) {
        ConcurrentSkipListMap<Integer, Level> side = entry.isBuySide ? buySide : sellSide;

        if (side.get(entry.getPrice()) == null) {
            side.put(entry.getPrice(), new Level(entry.getPrice(), entry));
        } else {
            side.get(entry.getPrice()).addOrderBookEntry(entry);
        }
    }

    public void removeOrder(int priceLevel, boolean isBuySide) {
        Level level = isBuySide ? getBuysideLevel(priceLevel) : getSellSideLevel(priceLevel);
        level.removeOrderBookEntry();

        if (level.isEmpty()) {
            ConcurrentSkipListMap<Integer, Level> side = isBuySide ? buySide : sellSide;
            side.remove(priceLevel);
        }
    }

    public Integer getBestBid() {
        return buySide.isEmpty() ? null : buySide.lastKey();
    }

    public Integer getBestAsk() {
        return sellSide.isEmpty() ? null : sellSide.firstKey();
    }
}
