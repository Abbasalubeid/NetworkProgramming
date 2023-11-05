import java.net.*;
import controller.HttpRequestHandler;

public class MainServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);

        // Continuously listen for incoming connections
        while (true) {
            // Accept the incoming client connection
            Socket clientSocket = serverSocket.accept();
            new Thread(new HttpRequestHandler(clientSocket)).start();
        }
    }
}
