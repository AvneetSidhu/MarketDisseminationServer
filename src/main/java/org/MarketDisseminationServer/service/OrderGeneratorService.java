package org.MarketDisseminationServer.service;

import org.MarketDisseminationServer.Orders.NewOrder;
import org.MarketDisseminationServer.Orders.OrderCore;

import java.util.Random;

public class OrderGeneratorService {

    private final int securityId;
    private Random rand = new Random();
    private boolean isBuySide;

    public OrderGeneratorService(int securityId) {
        this.securityId = securityId;
    }


    public NewOrder generateNewOrder() {
        int username;
        int orderId;
        int quantity;
        rand = new Random();
        username = 1000 + rand.nextInt(9000);
        orderId = 1000 + rand.nextInt(9000);
        quantity = 1 + rand.nextInt(500);
        return new NewOrder(new OrderCore(username, securityId, orderId), 25 + rand.nextInt(51), quantity, Math.random() > 0.5);
    }
}
