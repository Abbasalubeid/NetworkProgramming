import java.io.*;
import java.io.Console;
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
        if (serverGreeting == null || !serverGreeting.contains("OK")) {
            throw new IOException("Failed to connect to IMAP server.");
        }
        
        System.out.println("Connected to IMAP Server. Server Greeting: " + serverGreeting);

    }

    public void login(String username, String password) throws IOException {
        System.out.println("Logging in with username: " + username);
        sendCommand("a001 LOGIN " + username + " " + password + "\r\n");
    }

    private void sendCommand(String command) throws IOException {
        String commandTag = command.split(" ")[0];
        writer.println(command);
        
        // Check if the command contains a password, and replace it with asterisks for logging
        String logCommand = command.toLowerCase().startsWith("a001 login") ?
        command.split(" ")[0] + " LOGIN " + command.split(" ")[2] + " *****\r\n" : command;
        System.out.println("Client: " + logCommand.trim());

        
        // Read the response from the server
        String response = reader.readLine();
        while (response != null) {
            System.out.println("Server: " + response);
            if (response.startsWith(commandTag)) {

                if (response.contains("NO LOGIN"))
                    throw new IOException("Login failed. Check your credentials.");
                
                break; // Exit the loop if the response starts with the command tag
            }
            response = reader.readLine();
        }
    }

    public void listInbox() throws IOException {
        sendCommand("a002 SELECT INBOX\r\n");
        sendCommand("a003 FETCH 1:* (BODY[HEADER.FIELDS (SUBJECT FROM)])\r\n");
    }

    public void fetchEmail(int emailNumber) throws IOException {
        sendCommand("a002 SELECT INBOX\r\n");
        sendCommand("a003 FETCH " + emailNumber + " BODY[TEXT]\r\n");
    }

    public void logout() throws IOException {
        sendCommand("A005 LOGOUT\r\n");
    }
}
