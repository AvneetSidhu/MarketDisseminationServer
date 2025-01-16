package org.MarketDisseminationServer.Orders;

import lombok.Getter;

@Getter
public class CancelOrder {
    private final OrderCore orderCore;

    public CancelOrder(OrderCore orderCore) {
        this.orderCore = orderCore;
    }
}
