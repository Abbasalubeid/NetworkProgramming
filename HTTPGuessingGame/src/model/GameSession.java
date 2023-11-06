package model;

import java.util.*;

public class GameSession {
    private final String sessionId;
    private final Set<Integer> guesses;
    private final int secretNumber;
    private boolean gameWon = false;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.guesses = new LinkedHashSet<>(); // Maintain order of guesses
        this.secretNumber = new Random().nextInt(100) + 1;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Set<Integer> getGuesses() {
        return guesses;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public String guessNumber(String guessStr) {
        try {
            int guess = Integer.parseInt(guessStr);
            if (guess < 1 || guess > 100) {
                return "Your guess is out of the valid range (1-100). Please try again.";
            }

            guesses.add(guess);
            if (guess == secretNumber) {
                gameWon = true;
                return "Correct! The number was " + secretNumber + ". Your guesses: " + guesses + ".";
            } else if (guess < secretNumber) {
                 return "Higher! Your guesses: " + guesses + ".";
            } else {
                return "Lower! Your guesses: " + guesses + ".";
            }
        } catch (NumberFormatException e) {
            return "That's not a valid number. Please enter a number between 1 and 100.";
        }
    }
}
