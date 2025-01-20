package org.MarketDisseminationServer.Serializer.DTO;

import lombok.Getter;
import org.MarketDisseminationServer.Orders.NewOrder;

import java.util.ArrayList;

@Getter
public class BroadcastUpdate {

    NewOrder originalOrder;
    ArrayList<OrderbookUpdate> orderbookUpdates;

    public BroadcastUpdate(NewOrder order, ArrayList<OrderbookUpdate> updates) {
        this.originalOrder = order;
        this.orderbookUpdates = updates;
    }
}
