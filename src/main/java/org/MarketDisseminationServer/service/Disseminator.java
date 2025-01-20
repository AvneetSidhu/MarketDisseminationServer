package org.MarketDisseminationServer.service;

import org.MarketDisseminationServer.Serializer.DTO.OrderbookSnapShot;
import org.MarketDisseminationServer.Serializer.DTO.OrderbookUpdate;

import java.time.Instant;
import java.util.ArrayList;

public class Disseminator {

    int securityID;
    OrderGeneratorService orderGeneratorService;
    MatchingEngineService matchingEngineService;

    public Disseminator(int securityID) {
        this.securityID = securityID;
        this.orderGeneratorService = new OrderGeneratorService(securityID);
        this.matchingEngineService = new MatchingEngineService(securityID);
    }

    public ArrayList<OrderbookUpdate> matchOrder() {
        return matchingEngineService.matchOrder(orderGeneratorService.generateNewOrder());
    }

    public OrderbookSnapShot getOrderbookSnapshot() {
        String timestamp = Instant.now().toString();
        ;
        return new OrderbookSnapShot(timestamp, matchingEngineService.getOrderbook());
    }
}
