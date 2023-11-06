package view;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpResponseBuilder {

    private String buildHeader(int statusCode, String cookie) {
        String statusLine = getStatusLine(statusCode);
        String header = statusLine + "\r\n"
            + "Content-Type: text/html; charset=UTF-8\r\n";
        
        if (cookie != null && !cookie.isEmpty()) {
            header += "Set-Cookie: sessionId=" + cookie + "; Path=/; HttpOnly\r\n";
        }
        
        header += "\r\n"; // End of header section
        return header;
    }
    
    
    private String getStatusLine(int statusCode) {
        switch (statusCode) {
            case 200: return "HTTP/1.1 200 OK";
            case 400: return "HTTP/1.1 400 Bad Request";
            case 404: return "HTTP/1.1 404 Not Found";
            default: return "HTTP/1.1 500 Internal Server Error";
        }
    }

    public String buildResponse(int statusCode, String content) {
        return buildResponse(statusCode, content, null);
    }
    
    // Overloaded method for response with cookie
    public String buildResponse(int statusCode, String content, String cookie) {
        String body = (content != null && !content.isEmpty()) ? content : "";
        String header = buildHeader(statusCode, cookie);
        return header + body;
    }
    
    public String buildGamePage(String message, Set<Integer> guesses) {
        String body = "<html><head><title>Guessing Game</title></head><body>"
                + "<div style='text-align: center; margin-top: 50px;'>"
                + "<h2>Guess the number between 1 and 100</h2>"
                + "<p>" + message + "</p>"
                + "<form action='/' method='get'>"
                + "Guess: <input type='text' name='guess'><br><br>"
                + "<input type='submit' value='Try'>"
                + "</form>"
                + "<p>Your guesses: " + guesses + "</p>"
                + "</div></body></html>";

        return buildResponse(200, body);
    }

    public String buildGamePageWithCookie(String message, String cookie) {
        String body = "<html><head><title>Guessing Game</title></head><body>"
                + "<div style='text-align: center; margin-top: 50px;'>"
                + "<h2>Guess the number between 1 and 100</h2>"
                + "<p>" + message + "</p>"
                + "<form action='/' method='get'>"
                + "Guess: <input type='text' name='guess'><br><br>"
                + "<input type='submit' value='Try'>"
                + "</form>"
                + "</div></body></html>";

        return buildResponse(200, body, cookie); // Call with cookie
    }
}