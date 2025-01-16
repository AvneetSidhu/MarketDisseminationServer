package org.MarketDisseminationServer.Orderbook;

// read only version of a record
public record OrderRecord(int orderId, int quantity, int price, boolean isBuySide) {
}
