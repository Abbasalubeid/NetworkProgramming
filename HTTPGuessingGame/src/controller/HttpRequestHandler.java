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
            if (requestLine != null && !requestLine.isEmpty()) {
                System.out.println(requestLine);

                //TODO: More parsing request

                HttpResponseBuilder responseBuilder = new HttpResponseBuilder();
                String response = responseBuilder.buildResponse("Welcome! You have accessed this server via HTTP.");

                // Send the response
                out.print(response);
            }

            // Close streams and socket
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
