package view;

import java.nio.charset.StandardCharsets;

public class HttpResponseBuilder {

    public String buildResponse(String content) {
        String body = "<h1>" + content + "</h1>\n";
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        int contentLength = bodyBytes.length;

        return "HTTP/1.1 200 OK\r\n" // HTTP response status line
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n" // empty line to separate the headers from the body
                + body; // HTML response body;
    }
}