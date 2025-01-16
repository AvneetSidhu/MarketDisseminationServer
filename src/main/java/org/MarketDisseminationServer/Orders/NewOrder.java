package org.MarketDisseminationServer.Orders;

import lombok.Getter;

@Getter
public class NewOrder {

    private final OrderCore orderCore;
    private final int price;
    private final int initialQuantity;
    public final boolean isBuySide;

    public NewOrder(OrderCore orderCore, int price, int quantity, boolean isBuySide) {
        this.orderCore = orderCore;
        this.price = price;
        this.initialQuantity = quantity;
        this.isBuySide = isBuySide;
    }

    public int getUsername() {
        return orderCore.username();
    }

    public int getSecurityId() {
        return orderCore.securityId();
    }

    public int getOrderId() {
        return orderCore.orderId();
    }

}
