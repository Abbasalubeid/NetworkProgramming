import java.io.*;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class KTHImapClient {
    private String imapHost = "webmail.kth.se";
    private int imapPort = 993;
    private BufferedReader reader;
    private PrintStream writer;

    public void connect() throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket imapSocket = (SSLSocket) factory.createSocket(imapHost, imapPort);
        reader = new BufferedReader(new InputStreamReader(imapSocket.getInputStream()));
        writer = new PrintStream(imapSocket.getOutputStream());

        String serverGreeting = reader.readLine();
        System.out.println("Server Greeting: " + serverGreeting);

    }

    public void login(String username, String password) throws IOException {
        sendCommand("a001 LOGIN " + username + " " + password + "\r\n");
    }

    private void sendCommand(String command) throws IOException {
        String commandTag = command.split(" ")[0];
        writer.println(command);
    
        // Read the response from the server
        String response = reader.readLine();
        while (response != null) {
            System.out.println(response);
            if (response.startsWith(commandTag)) {
                break; // Exit the loop if the response starts with the command tag
            }
            response = reader.readLine();
        }
    }

    public void listInbox() throws IOException {
        sendCommand("a002 SELECT INBOX\r\n");
        sendCommand("a003 FETCH 1:* (UID FLAGS BODY[HEADER.FIELDS (SUBJECT FROM DATE)])\r\n");
    }

    public void fetchEmail(int emailNumber) throws IOException {
        sendCommand("a002 SELECT INBOX\r\n");
        sendCommand("a003 FETCH " + emailNumber + " BODY[TEXT]\r\n");
    }

    public void logout() throws IOException {
        sendCommand("A005 LOGOUT\r\n");
    }
}
