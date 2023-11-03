import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());
     private static Map<String, ClientHandler> namedClients = new ConcurrentHashMap<>(); 

    public static void main(String[] args) {
        // try-with-resources to automatically close the ServerSocket after usage
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ChatServer started, listening on port: " + PORT);
            while (true) {  // Server loop to accept multiple clients
                // Wait and accept incoming client connections.
                Socket clientSocket = serverSocket.accept();
                // Create a new client handler for each connected client.
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // Start a new thread for each client to handle their messages.
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast a message to all connected clients except the sender.
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != sender) {
                clientHandler.sendMessage(sender.name + ": " + message);
            }
        }
    }

    public static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                 // Get the input stream to read data sent by the client.
                InputStream input = socket.getInputStream();
                // Convert byte stream (InputStream) to character stream and buffer it.
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                out = new PrintWriter(output, true);

                // Prompt for and read the client's name
                out.println("Enter your name:");
                name = reader.readLine();
                while (namedClients.containsKey(name)) { 
                    out.println("This name is already taken. Please choose another name.");
                    name = reader.readLine();
                }

                namedClients.put(name, this); 
                clientHandlers.add(this);
                out.println("Welcome " + name + "! You can now start chatting.");

                String clientMessage;
        
                // clientMessage is null when the socket is closed from client side
                while ((clientMessage = reader.readLine()) != null) { 
                    System.out.println(name + ": " + clientMessage);
                    broadcast(clientMessage, this);
                }
        
                socket.close();
                clientHandlers.remove(this);
                namedClients.remove(name);
        
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
