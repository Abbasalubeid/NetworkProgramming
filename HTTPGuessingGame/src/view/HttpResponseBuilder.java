package view;

import java.nio.charset.StandardCharsets;

public class HttpResponseBuilder {

    public String buildResponse(int statusCode, String content) {
        String statusLine;
        switch (statusCode) {
            case 200:
                statusLine = "HTTP/1.1 200 OK";
                break;
            case 400:
                statusLine = "HTTP/1.1 400 Bad Request";
                break;
            case 404:
                statusLine = "HTTP/1.1 404 Not Found";
                break;
            default:
                statusLine = "HTTP/1.1 500 Internal Server Error";
                break;
        }

        String body = (content != null && !content.isEmpty()) ? "<h1>" + content + "</h1>\n" : "";
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        int contentLength = bodyBytes.length;

        return statusLine + "\r\n"
                + "Content-Type: text/html; charset=UTF-8\r\n"
                + "Content-Length: " + contentLength + "\r\n"
                + "\r\n"
                + body;
    }

}