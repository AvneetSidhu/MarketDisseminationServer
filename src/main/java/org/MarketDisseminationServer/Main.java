package org.MarketDisseminationServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.MarketDisseminationServer.service.DisseminationServer;

import java.net.InetSocketAddress;


public class Main {

    @SneakyThrows
    public static void main(String[] args) throws JsonProcessingException {
        DisseminationServer server = new DisseminationServer(new InetSocketAddress("localhost", 8080));
        server.start();
    }
}