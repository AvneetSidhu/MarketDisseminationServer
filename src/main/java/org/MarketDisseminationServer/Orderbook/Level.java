package org.MarketDisseminationServer.Orderbook;

import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedQueue;

//class that represents a level in the orderbook
@Getter
public class Level {
    public final int price;
    public ConcurrentLinkedQueue<OrderBookEntry> orders;

    public Level(int price, OrderBookEntry head) {
        this.price = price;
        this.orders = new ConcurrentLinkedQueue<>();
        this.orders.add(head);
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public void addOrderBookEntry(OrderBookEntry entry) {
        orders.add(entry);
    }

    public void removeOrderBookEntry(){
        orders.poll();
    }

    public int getLevelOrderCount() {
        return orders.size();
    }

    public OrderBookEntry getOrderBookEntry() {
        return orders.peek();
    }

    public int getLevelOrderQuantity() {
        int levelQuantity = 0;
        for (OrderBookEntry entry : orders) {
            levelQuantity += entry.currentQuantity;
        }
        return levelQuantity;
    }

}


