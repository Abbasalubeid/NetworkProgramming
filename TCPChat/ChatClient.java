import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // try-with-resources to automatically close the Socket after usage
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {

            Thread receiveThread = new Thread(new ReceiveMessagesThread(socket));
            receiveThread.start();

            new Thread(new SendMessagesThread(socket)).start();

            System.out.println("Connected to server on:");
            System.out.println("IP: " + SERVER_IP);
            System.out.println("Server Port: " + SERVER_PORT);
            System.out.println("Client Local Port: " + socket.getLocalPort()); 
            System.out.println("You can start typing messages!");

            // Main thread waits for the receiveThread to finish
            receiveThread.join();

        } catch (ConnectException e) {
            System.out.println("Unable to connect to the server at IP: " + SERVER_IP + " and port: " + SERVER_PORT
                    + ". Ensure the server is up and running.");
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + SERVER_IP + ". Ensure you have the correct IP address.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class ReceiveMessagesThread extends Thread {
        private Socket socket;
        private BufferedReader reader;

        public ReceiveMessagesThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            while (true) {
                try {
                    InputStream input = socket.getInputStream();
                    // Convert byte stream (InputStream) to character stream and buffer it.
                    reader = new BufferedReader(new InputStreamReader(input));
                    // Read a line from the server.
                    String response = reader.readLine();
                    // If the server closes the connection, reader.readLine() will return null.
                    if (response == null) {
                        System.out.println("\nDisconnected from server.");
                        System.exit(0);
                        break;
                    }
                    System.out.println("\n" + response);
                } catch (IOException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    break;
                }
            }
        }
    }

    public static class SendMessagesThread implements Runnable {
        private PrintWriter out;

        public SendMessagesThread(Socket socket) throws IOException {
            // Get output stream to send data to the server.
            OutputStream output = socket.getOutputStream();
            out = new PrintWriter(output, true);
        }

        @Override
        public void run() {
            // Scanner to read user input from the console.
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Read user's message from the console.
                String message = scanner.nextLine();
                // Write user's message to server
                out.println(message);

            }
        }
    }
}
