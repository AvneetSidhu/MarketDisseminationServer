package org.MarketDisseminationServer.service;

import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DisseminationServer extends WebSocketServer {

    ObjectMapper mapper;
    ConcurrentHashMap<Integer, Set<WebSocket>> connections ;

    private final ExecutorService executor;

    public DisseminationServer(InetSocketAddress address) {
        super(address);
        mapper = new ObjectMapper();
        connections = new ConcurrentHashMap<>();
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("Connected to " + conn.getRemoteSocketAddress());
        conn.send("Connection to dissemination server successful!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Disconnected from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);
            String action = jsonNode.get("action").asText();
            int securityID = jsonNode.get("securityID").asInt();
            if (action.equals("subscribe")) {
                connections.computeIfAbsent(securityID, k -> new HashSet<>()).add(conn);
                System.out.println("Subscribed to securityID " + securityID);
            } else {
                connections.get(securityID).remove(conn);
                System.out.println("user unsubscribed from securityID " + securityID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Starting WebSocket Server");
    }

    public void broadcast(int securityID, String message) {
        Set<WebSocket> connectionSet = connections.get(securityID);
        if (connectionSet != null) {
            for (WebSocket connection : connectionSet) {
                executor.submit(() -> {
                    try {
                        connection.send(message);
                    } catch (Exception e) {
                        System.err.println("Error sending message to channel: " + e.getMessage());
                    }
                });
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}
