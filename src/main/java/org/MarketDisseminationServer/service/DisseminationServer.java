package org.MarketDisseminationServer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.MarketDisseminationServer.Serializer.Serializer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class DisseminationServer extends WebSocketServer {

    private final ConcurrentHashMap<Integer, Set<WebSocket>> subscribers;

    private final ExecutorService executor;

    private final Disseminator d1;
    private final Disseminator d2;

    private final Serializer serializer;

    public DisseminationServer(InetSocketAddress address) {
        super(address);

        subscribers = new ConcurrentHashMap<>();
        executor = Executors.newCachedThreadPool();

        d1 = new Disseminator(1);
        d2 = new Disseminator(2);

        serializer = new Serializer();
    }

    public Set<WebSocket> getSubscribers(Integer id) {
        return subscribers.get(id);
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

    public void sendUpdates(int securityID) {
        executor.submit(() -> {
            try {
                while (true) {
                    // Submit a new task to the executor to broadcast the update concurrently
                    executor.submit(() -> {
                        try {
                            System.out.println("Sending update...");
                            if (securityID == 1) {
                                broadcast(Serializer.serialize(d1.matchOrder()), securityID);
                            } else {
                                broadcast(Serializer.serialize(d2.matchOrder()), securityID);
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            System.out.println("Error processing the update.");
                        }
                    });

                    // Introduce a delay (e.g., 500 milliseconds) before submitting the next update
                    Thread.sleep(500);  // 500 milliseconds between updates
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restore the interrupt flag
                System.out.println("Update task was interrupted.");
            }
        });
    }

    public void broadcast(String message, int securityID) {
        Set<WebSocket> subscribers = getSubscribers(securityID);
        subscribers.forEach(subscriber -> {
            executor.submit(() -> {
                try {
                    subscriber.send(message);
                } catch (Exception e) {
                    System.err.println("Error sending message to subscriber: " + e.getMessage());
                }
            });
        });
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            System.out.println(message);
            JsonNode jsonNode = mapper.readTree(message);
            String action = jsonNode.get("action").asText();
            int securityID = jsonNode.get("securityID").asInt();
            if (action.equals("subscribe")) {
                subscribers.computeIfAbsent(securityID, k -> new HashSet<>()).add(conn);
                System.out.println("Subscribed to securityID " + securityID);
                if (securityID == 1) {
                    conn.send(Serializer.serialize(d1.getOrderbookSnapshot()));
                } else {
                    conn.send(Serializer.serialize(d2.getOrderbookSnapshot()));
                }
            } else {
                subscribers.get(securityID).remove(conn);
                System.out.println("user unsubscribed from securityID " + securityID);
            }
        } catch (Exception e) {
            System.out.println("user at " + conn.getRemoteSocketAddress() + " sent a message that could not be parsed");
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Starting WebSocket Server: Listening on port: " + getPort());
        sendUpdates(1);
        sendUpdates(2);
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
