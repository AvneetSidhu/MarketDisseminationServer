package org.MarketDisseminationServer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.MarketDisseminationServer.Orderbook.DTO.OrderbookSnapShot;
import org.MarketDisseminationServer.Orderbook.DTO.OrderbookUpdate;
import org.MarketDisseminationServer.Orderbook.Orderbook;

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

    public OrderbookSnapShot getOrderbookSnapshot(long TimeStamp, Orderbook orderbook) {
        return new OrderbookSnapShot(TimeStamp, orderbook);
    }
}
