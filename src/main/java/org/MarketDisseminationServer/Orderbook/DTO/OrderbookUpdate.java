package org.MarketDisseminationServer.Orderbook.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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

    public static String serialize(ArrayList<OrderbookUpdate> update) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(update);
    }
}
