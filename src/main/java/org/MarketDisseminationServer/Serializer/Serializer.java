package org.MarketDisseminationServer.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.MarketDisseminationServer.Serializer.DTO.ClientRequest;
import org.MarketDisseminationServer.Serializer.DTO.OrderbookSnapShot;
import org.MarketDisseminationServer.Serializer.DTO.OrderbookUpdate;

import java.util.ArrayList;


public class Serializer {
    ObjectMapper mapper = new ObjectMapper();

    public Serializer() {
        mapper = new ObjectMapper();
    }

    public static String serialize(OrderbookSnapShot orderbookSnapShot) throws JsonProcessingException {
        return mapper.writeValueAsString(orderbookSnapShot);
    }

    public static String serialize(ArrayList<OrderbookUpdate> orderbookUpdate) throws JsonProcessingException {
        return mapper.writeValueAsString(orderbookUpdate);
    }

    public ClientRequest deserialize(String message) throws JsonProcessingException {
        return mapper.readValue(message, ClientRequest.class);
    }
}
