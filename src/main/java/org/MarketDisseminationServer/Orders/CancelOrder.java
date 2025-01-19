package org.MarketDisseminationServer.Orders;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CancelOrder {
    private final OrderCore orderCore;

    public CancelOrder(OrderCore orderCore) {
        this.orderCore = orderCore;
    }
}
