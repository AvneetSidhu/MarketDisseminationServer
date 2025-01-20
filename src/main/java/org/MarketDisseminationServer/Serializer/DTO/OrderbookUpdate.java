package org.MarketDisseminationServer.Serializer.DTO;

public class OrderbookUpdate {
    public String type;
    public UpdateTypes updateType;
    public Integer OrderId;
    public Integer Quantity;
    boolean isBuySide;
    public int username;

    public OrderbookUpdate(Integer priceLevel, Integer quantityDelta, UpdateTypes updateType, int username, boolean isBuySide) {
        type = "update";
        this.updateType = updateType;
        this.OrderId = quantityDelta;
        this.Quantity = priceLevel;
        this.isBuySide = isBuySide;
        this.username = username;
    }
}
