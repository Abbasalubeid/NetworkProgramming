import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ChatServer started, listening on port: " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != excludeUser) {
                clientHandler.sendMessage(message);
            }
        }
    }

    public static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                out = new PrintWriter(output, true);
                String clientMessage;
        
                while (true) {
                    clientMessage = reader.readLine();
                    if (clientMessage.equals("bye")) {
                        break;
                    }
                    System.out.println("Received: " + clientMessage);
                    broadcast(clientMessage, this);
                }
        
                socket.close();
                clientHandlers.remove(this);
        
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
