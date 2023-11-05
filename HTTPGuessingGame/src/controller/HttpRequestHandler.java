package controller;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import view.HttpResponseBuilder;
import model.GameSession;

public class HttpRequestHandler implements Runnable {
    private final Socket clientSocket;
    private GameSession gameSession;
    private final ConcurrentHashMap<String, GameSession> sessions;

    public HttpRequestHandler(Socket socket, ConcurrentHashMap<String, GameSession> sessions) {
        this.clientSocket = socket;
        this.sessions = sessions;
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

            List<String> headers = new ArrayList<>();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                headers.add(line);
            }

            String sessionId = null;
            for (String header : headers) {
                System.out.println(header);
                if (header.startsWith("Cookie: ")) {
                    String[] cookies = header.substring(8).split("; ");
                    for (String cookie : cookies) {
                        if (cookie.startsWith("sessionId=")) {
                            sessionId = cookie.split("=")[1];
                            break;
                        }
                    }
                }
            }

            if (sessionId != null) {
                sendResponse(out, 200, "You have been here....: " + sessionId);
            }
            else{
                sendResponse(out, 200, "We dont know you yet, take a cookie");

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