package org.MarketDisseminationServer.service;

import lombok.Getter;
import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orderbook.Orderbook;
import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Serializer.DTO.OrderbookUpdate;
import org.MarketDisseminationServer.Serializer.DTO.UpdateTypes;

import java.util.ArrayList;

@Getter
public class MatchingEngineService {
    private final int securityId;
    public Orderbook orderbook;

    public MatchingEngineService(int securityId) {
        this.securityId = securityId;
        this.orderbook = new Orderbook();
    }

    public ArrayList<OrderbookUpdate> matchOrder(NewOrder order) {
//        if (order.isBuySide) {
//            return matchBuyOrder(order);
//        } else {
//            return matchSellOrder(order);
//        }
        return order.isBuySide ? matchBuyOrder(order) : matchSellOrder(order);
    }

    public ArrayList<OrderbookUpdate> matchBuyOrder(NewOrder order) {
        int toFill = order.getInitialQuantity();
        int quantityToFill;

        OrderBookEntry entryToAdd;
        ArrayList<OrderbookUpdate> updates = new ArrayList<OrderbookUpdate>();

        while (toFill > 0) {

            Integer bestAsk = orderbook.getBestAsk();
            if (bestAsk == null || order.getPrice() < bestAsk) break;

            Level bestAskLevel = orderbook.getSellSideLevel(bestAsk); // the level with orders that can fill our buy order
            quantityToFill = Math.min(toFill, bestAskLevel.getOrderBookEntry().getCurrentQuantity());
            bestAskLevel.getOrderBookEntry().decreaseQuantity(quantityToFill);
            toFill -= quantityToFill;

            if (bestAskLevel.getOrderBookEntry().getCurrentQuantity() == 0) {
                orderbook.removeOrder(bestAsk, false);
                updates.add(new OrderbookUpdate(bestAsk, quantityToFill, UpdateTypes.DECREASEQUANTITY, order.getUsername(), false));
            } else {
                updates.add(new OrderbookUpdate(bestAsk, null, UpdateTypes.REMOVE, order.getUsername(), false));
            }
        }

        if (toFill != 0) {
            entryToAdd = new OrderBookEntry(order, toFill);
            orderbook.addOrder(entryToAdd);
            updates.add(new OrderbookUpdate(entryToAdd.getPrice(), toFill, UpdateTypes.ADD, order.getUsername(), true));
        }

        return !updates.isEmpty() ? updates : null;
    }

    public ArrayList<OrderbookUpdate> matchSellOrder(NewOrder order) {
        int toFill = order.getInitialQuantity();
        int quantityToFill;

        OrderBookEntry entryToAdd;
        ArrayList<OrderbookUpdate> updates = new ArrayList<OrderbookUpdate>();

        while (toFill > 0) {
            Integer bestBid = orderbook.getBestBid();
            if (bestBid == null || order.getPrice() > bestBid) break;

            Level bestBidLevel = orderbook.getBuysideLevel(bestBid);
            quantityToFill = Math.min(toFill, bestBidLevel.getOrderBookEntry().getCurrentQuantity());
            bestBidLevel.getOrderBookEntry().decreaseQuantity(quantityToFill);
            toFill -= quantityToFill;

            if (bestBidLevel.getOrderBookEntry().getCurrentQuantity() == 0) {
                orderbook.removeOrder(bestBid, true);
                updates.add(new OrderbookUpdate(bestBid, quantityToFill, UpdateTypes.DECREASEQUANTITY, order.getUsername(), false));
            } else {
                updates.add(new OrderbookUpdate(bestBid, null, UpdateTypes.REMOVE, order.getUsername(), false));
            }
        }

        if (toFill != 0) {
            entryToAdd = new OrderBookEntry(order, toFill);
            orderbook.addOrder(entryToAdd);
            updates.add(new OrderbookUpdate(entryToAdd.getPrice(), toFill, UpdateTypes.ADD, order.getUsername(), false));
        }

        return !updates.isEmpty() ? updates : null;
    }
}
