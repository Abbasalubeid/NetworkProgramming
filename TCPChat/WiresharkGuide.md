# Guide for Capturing Traffic of the TCPChat App Using Wireshark

1. **Initial Setup**:
    - Open Wireshark.
    - Select the **Loopback: lo0** option. This is for capturing local traffic to and from your machine.

2. **Start the Server**:
    - Command: `java ChatServer`

    **Wireshark Output**:
    - Nothing happens in Wireshark since the server is only started and waiting for incoming connections.

3. **Start Client One**:
    - Open a new terminal and run: `java ChatClient`

    **Wireshark Output**:
    - The first line is the client initiating a connection to the server. This is indicated by the `[SYN]` flag, which means the client is requesting to synchronize sequence numbers to start a connection.
    - The second line has the `[SYN, ACK]` flags. This is the server acknowledging the client's request and asking to synchronize its own sequence numbers.
    - The third line with the `[ACK]` flag is the client acknowledging the server's request, completing the 3-way handshake process.
    - A line might appear indicating a `[TCP Window Update]`. This signifies that the TCP window size is being adjusted, which helps in controlling the flow of data in the TCP connection. Note: This flag can appear at various times and is not strictly tied to the initiation of a connection but more on the behavior and conditions of the TCP connection.

4. **Send a Message from Client One**:

    **Wireshark Output**:
    - The first line with `[PSH, ACK]` flags indicates that the client is pushing data (the message) to the server and acknowledging any previous messages.
    - The subsequent line with the `[ACK]` flag is the server acknowledging the receipt of the client's message.

5. **Start Client Two**:
    - Open another terminal and run: `java ChatClient`

    **Wireshark Output**:
    - Similar to Client One, the next three lines represent the TCP 3-way handshake process between Client Two and the server.
    - A line might again indicate the `[TCP Window Update]`, representing an adjustment of the TCP window size. 

6. **Send a Message from Client Two**:

    **Wireshark Output**:
    - The first line with `[PSH, ACK]` flags is Client Two pushing the message to the server.
    - The subsequent line with the `[ACK]` flag is the server acknowledging the receipt of the message.
    - The next line with `[PSH, ACK]` flags indicates the server forwarding the message from Client Two to Client One.
    - The following `[ACK]` is Client One acknowledging the receipt of the message.

7. **Terminate Client One**:
    - Close the terminal of Client One.

    **Wireshark Output**:
    - The first line with the `[FIN, ACK]` flags is Client One signaling the termination of the connection.
    - The subsequent lines are the server acknowledging the termination and then also signaling its own termination of the connection with Client One.

8. **Terminate Client Two**:
    - Close the terminal of Client Two.

    **Wireshark Output**:
    - The process is similar to the termination of Client One. Lines with `[FIN, ACK]` flags indicate the termination signals, followed by acknowledgments.
