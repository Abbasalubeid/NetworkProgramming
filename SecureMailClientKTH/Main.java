import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();

        String operation = console.readLine("Enter 'receive' to receive emails or 'send' to send an email: ");

        if (!operation.equals("receive") && !operation.equals("send")) {
                System.out.println("Invalid operation. Please enter 'receive' or 'send'.");
                System.exit(1);
            }

        String username = console.readLine("Enter your KTH username: ");
        char[] passwordArray = console.readPassword("Enter your KTH password: ");
        String password = new String(passwordArray);

        try {
            if (operation.equalsIgnoreCase("receive")) {
                KTHImapClient imapClient = new KTHImapClient();

                System.out.println("Connecting to host webmail.kth.se at port 993");
                imapClient.connect();
                
                imapClient.login(username, password);

                System.out.println("List the inbox");
                imapClient.listInbox();

                String emailNumberStr = console.readLine("\nEnter email number to retrieve: ");
                int emailNumber = Integer.parseInt(emailNumberStr);
                System.out.println(" ");
                System.out.println("Fetch email " + emailNumber); 
                imapClient.fetchEmail(emailNumber);

                imapClient.logout();
            } else if (operation.equalsIgnoreCase("send")) {
                KTHSmtpClient smtpClient = new KTHSmtpClient();
            
                System.out.println("Connecting to host smtp.kth.se at port 587");
                smtpClient.connect();
                
                smtpClient.startTls();
                smtpClient.login(username, password);

                String subject = console.readLine("Enter subject for the email: ");
                String body = console.readLine("Enter body for the email: ");
                smtpClient.sendEmail(username + "@kth.se", username + "@kth.se", subject, body);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Clear password data and close resources
            java.util.Arrays.fill(passwordArray, ' ');
        }
    }
}
