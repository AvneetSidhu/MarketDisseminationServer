package org.MarketDisseminationServer.service;

import org.MarketDisseminationServer.Orderbook.OrderBookEntry;
import org.MarketDisseminationServer.Orderbook.Orderbook;
import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orderbook.Level;
import org.MarketDisseminationServer.Orders.OrderCore;

import java.util.Random;

public class MatchingEngineService {
    private final int securityId;
    public Orderbook orderbook;

    public MatchingEngineService(int securityId) {
        this.securityId = securityId;
        this.orderbook = new Orderbook(securityId);
    }

    public void start() {
        System.out.println("Starting Matching Engine for SecurityID: " + securityId);
        System.out.println("Listening for Orders");
    }

    public void matchOrder(NewOrder order) {
        if (order.isBuySide) {
            matchBuyOrder(order);
        } else {
            matchSellOrder(order);
        }
    }

    public void matchBuyOrder(NewOrder order) {
        int toFill = order.getInitialQuantity();
        while (toFill > 0) {
            Integer bestAsk = orderbook.getBestAsk();

            if (bestAsk == null || order.getPrice() < bestAsk) {
                break;
            }

            // fill order
            Level bestAskLevel = orderbook.getSellSideLevel(bestAsk); // the level with orders that can fill our buy order

            int quantityToFill = Math.min(toFill, bestAskLevel.getOrderBookEntry().getCurrentQuantity());

            bestAskLevel.getOrderBookEntry().decreaseQuantity(quantityToFill);

            toFill -= quantityToFill;

            if(bestAskLevel.getOrderBookEntry().getCurrentQuantity() == 0) {
                orderbook.removeOrder(bestAsk, false);
            }
        }

        // order not completely filled
        if (toFill != 0) {
            OrderBookEntry entryToAdd = new OrderBookEntry(order, toFill);
            orderbook.addOrder(entryToAdd);
        }

    }

    public void matchSellOrder(NewOrder order) {
        int toFill = order.getInitialQuantity();
        while (toFill > 0) {
            Integer bestBid = orderbook.getBestBid();
            if (bestBid == null || order.getPrice() > bestBid) break;
            // fill order
            Level bestBidLevel = orderbook.getBuysideLevel(bestBid); // the level with orders that can fill our buy order

            int quantityToFill = Math.min(toFill, bestBidLevel.getOrderBookEntry().getCurrentQuantity());

            bestBidLevel.getOrderBookEntry().decreaseQuantity(quantityToFill);
            toFill -= quantityToFill;

            if(bestBidLevel.getOrderBookEntry().getCurrentQuantity() == 0) {
                orderbook.removeOrder(bestBid, true);
            }
        }

        // order not completely filled
        if (toFill != 0) {
            OrderBookEntry entryToAdd = new OrderBookEntry(order, toFill);
            orderbook.addOrder(entryToAdd);
        }

    }

    //initialize orders for start up

    public void initializeOrderbook() {
        // 50 levels in the orderbook
        int username;
        int orderId;
        int quantity;
        Random rand = new Random();
        for (int i = 25; i < 50; i++) {
            for (int j = 0; j < 19; j++) {
                username = 1000 + rand.nextInt(9000);
                orderId = 1000 + rand.nextInt(9000);
                quantity = 1 + rand.nextInt(500);
                orderbook.addOrder(new OrderBookEntry(new NewOrder(new OrderCore(username, securityId, orderId), i, quantity, false), quantity));
            }
        }
    }
}
