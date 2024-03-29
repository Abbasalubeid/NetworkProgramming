import java.io.*;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Base64;

public class KTHSmtpClient {
    private String smtpHost = "smtp.kth.se";
    private int smtpPort = 587;
    private BufferedReader reader;
    private PrintStream writer;
    private Socket smtpSocket;

    public void connect() throws IOException {
        smtpSocket = new Socket(smtpHost, smtpPort);
        reader = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
        writer = new PrintStream(smtpSocket.getOutputStream());

        // Read the server greeting
        String serverGreeting = readResponse();
        if (serverGreeting == null || !serverGreeting.startsWith("220")) {
            throw new IOException("Failed to connect to SMTP server. Server greeting not received or invalid.");
        }
        System.out.println("Connected to SMTP Server. Server Greeting: " + serverGreeting);

        sendCommand("HELO " + smtpHost);
    }

    public void startTls() throws IOException {
        sendCommand("STARTTLS");

        // Upgrade the connection to TLS
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket(smtpSocket, smtpHost, smtpPort, true);
        sslSocket.startHandshake();

        System.out.println("Upgraded connection to TLS.");

        reader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        writer = new PrintStream(sslSocket.getOutputStream());

        sendCommand("HELO " + smtpHost); // Send HELO again after STARTTLS
    }

    public void login(String username, String password) throws IOException {
        System.out.println("Authenticating with SMTP server...");

        sendCommand("AUTH LOGIN");
        sendCommand(Base64.getEncoder().encodeToString(username.getBytes()));
        sendSensitiveCommand(Base64.getEncoder().encodeToString(password.getBytes()));
    }

    public void sendEmail(String from, String to, String subject, String body) throws IOException {
        System.out.println("Preparing to send email...");

        // SMTP Envelope
        sendCommand("MAIL FROM:<" + from + ">");
        sendCommand("RCPT TO:<" + to + ">");
        sendCommand("DATA");

        // Send the email headers and body
        writer.println("From: " + from);
        writer.println("To: " + to);
        writer.println("Subject: " + subject);
        writer.println();  // Empty line to separate headers from body
        writer.println(body);
        writer.println(".");  // Line with only a period to end the email content
        writer.flush();
    
        readResponse();
        
        System.out.println("Email sent successfully.");
        
        sendCommand("QUIT");
    }

    private void sendCommand(String command) throws IOException {
        System.out.println("Client: " + command);
        
        writer.println(command);
        writer.flush();
        readResponse();
    }

    private void sendSensitiveCommand(String command) throws IOException {
        System.out.println("Client: *********");
        
        writer.println(command);
        writer.flush();
        readResponse();
    }

    private String readResponse() throws IOException {
        String response = reader.readLine();
        System.out.println("Server: " + response);
        return response;
    }
}
