import java.io.Console;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();

        String username = console.readLine("Enter your KTH username: ");
        char[] passwordArray = console.readPassword("Enter your KTH password: ");
        String password = new String(passwordArray);

        try {
            KTHImapClient client = new KTHImapClient();
            client.connect();
            client.login(username, password);

            client.listInbox();

            String emailNumberStr = console.readLine("\nEnter email number to retrieve: ");
            int emailNumber = Integer.parseInt(emailNumberStr);
            System.out.println(" ");
            client.fetchEmail(emailNumber);

            client.logout();

        } catch (Exception e) {
           System.err.println("Error: " + e.getMessage());
        } finally {
            // Clear password data
            java.util.Arrays.fill(passwordArray, ' ');
        }
    }
}
