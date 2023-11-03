# Running ChatServer on Local Machine and ChatClient on Remote Machine

## 1. Set Up the Server on Your Local Machine

1. **Have the `ChatServer` Java program on your local computer.**
2. **Compile and run the program**:
   - This will start the server, making it listen on the specified port (e.g., 1234).

## 2. Set Up the Reverse SSH Tunnel

1. **Open a terminal on your local computer**.
2. **Establish a reverse SSH tunnel** using the command:
```bash
   ssh -R 1234:localhost:1234 yourUsername@remote-machine-address.com
```

<!-- ssh -R 1234:localhost:1234 alubeid@student-shell.sys.kth.se -->
   
This forwards any connections made to port 1234 on remote-machine-address.com to port 1234 on your local machine.
After executing the command, you should be logged into your account on remote-machine-address.com

## 3. Set Up the Client on Your Remote Machine

1. **Transfer the `ChatClient` Java program to the remote machine**. You can use `scp` or your preferred method:
```bash
   scp path/to/ChatClient.java yourUsername@remote-machine-address.com:/path/on/remote/machine
```

<!-- scp TCPChat/ChatClient.java alubeid@student-shell.sys.kth.se:~/Private -->

2. **Navigate to the directory** where the `ChatClient` program is located on the remote machine's terminal.
3. **Compile the `ChatClient` Java program**.
4. **Run the `ChatClient` program**:
   - It will connect to `127.0.0.1` (localhost) on port 1234. Due to the reverse SSH tunnel, this connection will be forwarded to your local machine's `ChatServer`.

## 4. Chatting

1. **Start the chat session**:
   - The `ChatClient` on the remote machine can now communicate with the `ChatServer` on your local machine.

## 5. Ending the Session

1. **End the chat session**:
   - Type "bye" in the client or terminate the server on your local machine.
2. **Close the SSH connection**:
   - Type `exit` or press `CTRL + D`.


