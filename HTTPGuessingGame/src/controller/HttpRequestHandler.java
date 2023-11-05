package controller;

import java.io.*;
import java.net.Socket;
import view.HttpResponseBuilder;

public class HttpRequestHandler implements Runnable {
    private final Socket clientSocket;

    public HttpRequestHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Input stream to read clients request
            InputStream input = clientSocket.getInputStream();
            // Convert byte stream (InputStream) to character stream and buffer it.
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            // Get output stream to send data to the client.
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read the request line
            String requestLine = in.readLine();
            
            if (requestLine == null || requestLine.isEmpty()) {
                sendResponse(out, 400, "Bad Request: The request line is empty.");
                return;
            }

            System.out.println(requestLine);

            if (requestLine.contains("favicon.ico")) {
                sendResponse(out, 404, "Not Found: The requested resource was not found on this server.");
                return;
            }

            sendResponse(out, 200, "Welcome! You have accessed this server via HTTP.");

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
}
