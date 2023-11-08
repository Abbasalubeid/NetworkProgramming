import java.net.*;
import java.io.*;
import java.util.*;

public class GameClient {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of games you want to play: ");

        int numberOfGames = scanner.nextInt();
        
        String url = "http://localhost:8080";
        int totalGuesses = 0;

        // Play the game 100 times
        for (int i = 1; i <= numberOfGames; i++) {
            String cookie = null;
            boolean gameWon = false;
            int guesses = 0;

            // Start a new game session
            cookie = startNewGame(url);

            // Keep guessing until the game is won
            while (!gameWon) {
                int guess = new Random().nextInt(100) + 1; // Random guess between 1 and 100
                String response = sendGuess(url, guess, cookie);

                gameWon = response.contains("Correct!"); // Check if the guess was correct
                guesses++;
            }

            totalGuesses += guesses;
            System.out.println("Game " + i + " won in " + guesses + " guesses");
        }

        double averageGuesses = (double) totalGuesses / 100;
        System.out.println("Average number of guesses: " + averageGuesses);
    }

    private static String startNewGame(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get cookie from header
        String cookie = connection.getHeaderField("Set-Cookie");
        if (cookie != null) {
            cookie = cookie.split(";")[0]; // We only want the sessionId part of the cookie
        }

        connection.disconnect();
        return cookie;
    }

    private static String sendGuess(String urlString, int guess, String cookie) throws IOException {
        URL url = new URL(urlString + "?guess=" + guess);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (cookie != null) {
            connection.setRequestProperty("Cookie", cookie);
        }

        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        connection.disconnect();

        return response.toString();
    }
}
