package org.MarketDisseminationServer.Orderbook;

import lombok.Getter;
import org.MarketDisseminationServer.Orders.NewOrder;

import java.time.Instant;

// we only want increase and decrease functionality on orders that have been placed and processed into the orderbook
@Getter
public class OrderBookEntry {
    public Instant creationTime;
    public NewOrder currentOrder;
    public int price;
    public int currentQuantity;
    public boolean isBuySide;
    public int username;
    public int securityId;
    public int orderId;
    public OrderBookEntry next;

    public OrderBookEntry(NewOrder currentOrder, int currentQuantity) {
        this.currentOrder = currentOrder;
        this.price = currentOrder.getPrice();
        this.creationTime = Instant.now();
        this.price = currentOrder.getPrice();
        this.currentQuantity = currentQuantity;
        this.isBuySide = currentOrder.isBuySide();
        this.username = currentOrder.getUsername();
        this.securityId = currentOrder.getSecurityId();
        this.orderId = currentOrder.getOrderId();
        this.next = null;
    }

    public void increaseQuantity(int quantityDelta) {
        this.currentQuantity += quantityDelta;
    }

    public void decreaseQuantity(int quantityDelta) {
        if (this.currentQuantity - quantityDelta < 0) {
            throw new IllegalArgumentException("Quantity exceeds maximum quantity");
        }
        this.currentQuantity -= quantityDelta;
    }

}
