import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import controller.HttpRequestHandler;
import model.GameSession;

public class MainServer {
    private static final int PORT = 8080;
    public static final ConcurrentHashMap<String, GameSession> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);

        // Continuously listen for incoming connections
        while (true) {
            // Accept the incoming client connection
            Socket clientSocket = serverSocket.accept();
            // Pass the sessions map to the handler
            new Thread(new HttpRequestHandler(clientSocket, sessions)).start();
        }
    }
}
