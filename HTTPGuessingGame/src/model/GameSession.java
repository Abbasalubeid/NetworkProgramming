package model;

import java.util.Random;

public class GameSession {
    private final String sessionId;
    private int numberOfGuesses;
    private final int secretNumber;
    private boolean gameWon = false;

    public GameSession(String sessionId) {
        this.sessionId = sessionId;
        this.numberOfGuesses = 0;
        this.secretNumber = new Random().nextInt(100) + 1;
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
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

            numberOfGuesses++;
            if (guess == secretNumber) {
                gameWon = true;
                return "Correct! The number was " + secretNumber + ". It took you " + numberOfGuesses + " guesses.";
            } else if (guess < secretNumber) {
                return "Higher! You have guessed " + numberOfGuesses + " times.";
            } else {
                return "Lower! You have guessed " + numberOfGuesses + " times.";
            }
        } catch (NumberFormatException e) {
            return "That's not a valid number. Please enter a number between 1 and 100.";
        }
    }
}
