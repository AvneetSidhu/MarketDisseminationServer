# Trading Engine Simulator
A market dissemination simulation written in Java using Java-WebSocket for real-time communication. This project simulates a trading engine that maintains a limit order book, processes buy and sell orders, and provides real-time updates to clients.

## Features
Real-Time Market Data: A multi-channel WebSocket server that sends real-time mock market updates to connected clients.

Limit Order Book Simulation: Implements time-price priority and FIFO matching for multiple fictional securities.

State Synchronization: Uses state-synchronization to ensure the client always has the latest order book state.

Observer Pattern: Updates clients with changes to the order book via the observer pattern for efficient notification delivery.

Serialization: Utilizes Jackson to serialize and deserialize messages between the server and client.

## Project Architecture
The project leverages the following key components:

Trading Engine: Implements the core logic for maintaining a limit order book.
Supports time-price priority and FIFO matching for orders.

WebSocket Server: A multi-channel WebSocket server that handles concurrent client connections.
Pushes low-latency updates of the order book to all connected clients.

Synchronization Mechanisms:

State Synchronization: Periodically sends a full snapshot of the order book to the clients and only updates the changes (deltas) going forward.

Observer Pattern: Clients are notified of updates (such as order additions or removals) in real time.

Serialization: Utilizes the Jackson library to handle JSON serialization and deserialization of messages sent to and from clients.

## How It Works
Client Connection:

Clients connect to the WebSocket server and subscribe to updates for specific securities. Client connections are organized by subscription to securities in a ConcurrentHashMap (Channels).

Order Processing:

Buy and sell orders are generated, processed and added to the order book.
The order book matches orders based on time-price priority and FIFO principles.

Real-Time Updates:

Clients receive real-time updates about the order book’s state, such as new orders, executed orders, and changes in order quantities.
State Sync:

The server periodically sends a snapshot of the current state of the order book to clients to ensure they are in sync.
