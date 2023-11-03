# TCP-Based Simple Chat Application

## TCP
TCP, or Transmission Control Protocol is a key protocol within the Internet Protocol. TCP operates in the transport layer, and it is used for exchanging data reliably and ensuring that data is delivered in order and without errors.

## About This Project
The goal of this small project is to delve into the workings of TCP at a practical level. This simple application demonstrates the TCP protocol in action by enabling real-time communication between two programs.  By using Java's socket programming capabilities, this application demonstrates the initiation and management of a TCP connection, data transmission, and handling multiple clients using multithreading.

## Files Included

- `ChatServer.java`: This is the heart of the application on the server-side. It listens for incoming client connections using `ServerSocket` and creates a new thread (`ClientHandler`) for each client that connects, allowing multiple clients to be managed concurrently.

- `ChatClient.java`: Represents the client-side portion of the chat system. It creates two threads upon execution: one for sending messages to the server and another for receiving messages from the server. This ensures that message sending and receiving are handled independently.

## How to Use

To get the chat system up and running, follow these steps:

1. Compile both Java files using the `javac` command
```bash
   javac ChatServer.java
   javac ChatClient.java
```
2. Launch the server in one terminal window:
```bash
java ChatServer
```
3. Open a new terminal window for each client that wishes to connect to the server. Start a client by running:
```bash 
java ChatClient
```

You can open as many clients as you want by starting a new terminal instance for each one. Each client will be able to send messages to and receive messages from all other clients connected to the server, allowing you to test the full capabilities of the TCP-based communication system.

## TCP Headers and Flags Analysis

For a detailed analysis of TCP headers and an explanation of various TCP flags within the context of this project, see:
[TCPHeaderAndFlags](TCPHeaderAndFlags.md)


## Network Traffic Analysis with Wireshark

This project incorporates the use of Wireshark to analyze the TCP communication between the chat clients and server. Wireshark is an essential tool for observing the underlying TCP packets and the sequence of the TCP handshake, data transmission, and connection teardown processes in real-time. Through packet inspection, you can gain deeper insights into how TCP operates and troubleshoot any potential issues with the network communication.

For detailed steps on capturing and analyzing traffic with Wireshark, see:
[WiresharkGuide](WiresharkGuide.md)

## Remote Usage with SSH Reverse Tunnel

To run the `ChatServer` on a local machine and the `ChatClient` on a remote machine, you can use SSH reverse tunneling. This approach allows you to securely connect the client on a remote machine to the server running on your local machine, even if the server is behind a firewall or NAT.

For more information, see:
[sshReverseTunnel](sshReverseTunnel.md)
