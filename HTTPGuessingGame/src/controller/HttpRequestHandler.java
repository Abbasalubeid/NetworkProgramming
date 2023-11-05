package controller;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import view.HttpResponseBuilder;
import model.GameSession;

public class HttpRequestHandler implements Runnable {
    private final Socket clientSocket;
    private GameSession gameSession;

    public HttpRequestHandler(Socket socket) {
        this.clientSocket = socket;
        this.gameSession = new GameSession("single");
    }

    @Override
    public void run() {
        try {
            // Input stream to read clients request
            InputStream input = clientSocket.getInputStream();
            // Convert byte stream (InputStream) to character stream and buffer it.
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            // Get output stream to send data to the client.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            // Read the request line
            String requestLine = in.readLine();

            if (requestLine == null || requestLine.isEmpty()) {
                sendResponse(out, 400, "Bad Request: The request line is empty.");
                return;
            }

            if (requestLine.contains("favicon.ico")) {
                sendResponse(out, 404, "Not Found: The requested resource was not found on this server.");
                return;
            }

            // Split the request into parts
            String[] requestParts = requestLine.split(" ")[1].split("\\?");
            System.out.println(Arrays.toString(requestParts));
            String path = requestParts[0];
            String query = requestParts.length > 1 ? requestParts[1] : "";

            // parse query parameters
            if (path.equals("/") && query.startsWith("guess=")) {
                String guessStr = query.split("=").length > 1 ? query.split("=")[1] : "";
                String message = gameSession.guessNumber(guessStr);
                int guessCount = gameSession.getNumberOfGuesses();
                sendGamePage(out, message, guessCount);
            } else {
                // If no guess was provided, show the game page with the starting message
                sendGamePage(out, "Try to guess the number between 1 and 100", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendResponse(PrintWriter out, int statusCode, String content) {
        HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
        String response = responseBuilder.buildResponse(statusCode, content);
        out.print(response);
        out.flush();
    }

    private void sendGamePage(PrintWriter out, String message, int guessCount) {
        HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
        String response = responseBuilder.buildGamePage(message, guessCount);
        out.print(response);
        out.flush();
    }
}
