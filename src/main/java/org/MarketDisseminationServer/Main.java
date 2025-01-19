package org.MarketDisseminationServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.SneakyThrows;
import org.MarketDisseminationServer.service.Disseminator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    @SneakyThrows
    public static void main(String[] args) throws JsonProcessingException {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Disseminator disseminator1 = new Disseminator(1);
        Disseminator disseminator2 = new Disseminator(2);

    }
}