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

            String currentGuess = "";
            // Split the request into parts
            String[] requestParts = requestLine.split(" ")[1].split("\\?");
            String path = requestParts[0];
            String query = requestParts.length > 1 ? requestParts[1] : "";

            // parse query parameters
            if (path.equals("/") && query.startsWith("guess=")) {
                String guessStr = query.split("=").length > 1 ? query.split("=")[1] : "";
                currentGuess = guessStr;
            }
            if (sessionId != null && sessions.containsKey(sessionId)) {
                gameSession = sessions.get(sessionId);
                String message = gameSession.guessNumber(currentGuess);
                int guessCount = gameSession.getNumberOfGuesses();
                String welcome = "Welcome back " + sessionId + "\n";
                message += welcome;
                sendGamePage(out, message, guessCount); // Rerender
            } else {
                // Create a new session ID and GameSession
                sessionId = UUID.randomUUID().toString();
                gameSession = new GameSession(sessionId);
                sessions.put(sessionId, gameSession);
                sendNewGamePage(out, "Try to guess the number between 1 and 100", 0, sessionId);
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

    private void sendNewGamePage(PrintWriter out, String message, int guessCount, String cookie) {
        HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
        String response = responseBuilder.buildGamePageWithCookie(message, guessCount, cookie);
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