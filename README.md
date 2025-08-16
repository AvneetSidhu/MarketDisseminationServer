# Trading Engine Simulator
A market dissemination simulation written in Java using Java-WebSocket for real-time communication. This project simulates a trading engine that maintains a limit orderbook, processes buy and sell orders, and provides real-time updates to clients.

## Features
Real-Time Market Data: A multi-channel WebSocket server that sends real-time mock market updates to connected clients.

Limit orderbook Simulation: Implements time-price priority and FIFO matching for multiple fictional securities. The matching engine takes in orders and compares tries to provide them the best sell or buy price at that moment. If an acceptable price is found based on the limit set in the order, the order is filled either partially or completely. If an order is partially filled, then the remaining quantity is added to the orderbook. Orders are organized into levels on the buy side and sell side in the order of arrival.

State Synchronization: Uses state-synchronization to ensure the client always has the latest orderbook state.

Observer Pattern: Updates clients with changes to the orderbook via the observer pattern for efficient notification delivery and state management.

Serialization: Utilizes Jackson to serialize and deserialize messages between the server and client.

## Project Architecture
The project leverages the following key components:

#### Trading Engine: 
Implements the core logic for maintaining a limit orderbook.
Supports time-price priority and FIFO matching for orders.

Order Generator: Creates fictional buy and sell orders for the matching engine, to simulate real-time orders.

#### WebSocket Server: 
A multi-channel WebSocket server that handles concurrent client connections.
Pushes low-latency updates of the orderbook to all connected clients.

## How It Works
#### Client Connection:
Clients connect to the WebSocket server and subscribe to updates for specific securities. Client connections are organized by subscription to securities in a ConcurrentHashMap (Channels).

#### Order Processing: 
Buy and sell orders are generated, processed and added to the orderbook.
The orderbook matches orders based on time-price priority and FIFO principles.

#### Real-Time Updates:
Clients receive real-time updates about the orderbook’s state, such as new orders, executed orders, and changes in order quantities.

#### State Sync: 
The server periodically sends a snapshot of the current state of the orderbook to clients to ensure they are in sync.

## Purpose
I created this Trading Engine Simulator to learn about trading engines, how they work and how they are implemented. To do this I first researched orderbooks, matching algorithms and realted financial system concepts. I also wanted to build something that involved real-time communication between applications and since the financial industry is a field where it is essential to have efficient, low-latency communication, I figured this would be a good project to learn about a real-time communication network protocol. 
