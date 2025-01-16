package org.MarketDisseminationServer.Orders;

import lombok.Getter;

@Getter
public class ModifyOrder {

    private final OrderCore orderCore;
    private final int quantity;
    private final int price;
    private final boolean isBuySide;

    public ModifyOrder(OrderCore orderCore, int modifyPrice, int modifyQuantity, boolean isBuySide) {
        this.orderCore = orderCore;
        this.price = modifyPrice;
        this.quantity = modifyQuantity;
        this.isBuySide = isBuySide;
    }

    public CancelOrder createCancelOrder() {
        return new CancelOrder(orderCore);
    }

    public NewOrder createNewOrder() {
        //create new OrderCore for new IDs
        return new NewOrder(orderCore, price, quantity, isBuySide);
    }
}
