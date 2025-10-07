package org.example.wordleclone3;

import java.io.*;
import java.util.*;

public class Model implements Serializable {

    public static final int MAX_GUESSES = 6;
    public static final int WORD_LENGTH = 5;

    private List<String> guesses = new ArrayList<>();
    private Set<String> dictionary = new HashSet<>();
    private List<String> wordList = new ArrayList<>();
    private String targetWord;
    private boolean gameOver = false;

    public Model() {
        loadDictionary();
        pickRandomWord();
    }

    public boolean submitGuess(String guess) {
        guess = guess.toLowerCase();
        if (gameOver || guess.length() != WORD_LENGTH || !dictionary.contains(guess)) return false;
        guesses.add(guess);
        if (guess.equals(targetWord) || guesses.size() >= MAX_GUESSES) {
            gameOver = true;
        }
        return true;
    }

    public void resetGame() {
        guesses.clear();
        gameOver = false;
        pickRandomWord();
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void loadDictionary() {
        try {
            InputStream file = getClass().getResourceAsStream("/Dictionary");
            if (file == null) throw new RuntimeException("Dictionary file not found.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String word;
            while ((word = reader.readLine()) != null) {
                word = word.trim().toLowerCase();
                if (word.length() == WORD_LENGTH) {
                    dictionary.add(word);
                    wordList.add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickRandomWord() {
        if (wordList.isEmpty()) {
            targetWord = "apple";
        } else {
            targetWord = wordList.get(new Random().nextInt(wordList.size()));
        }
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedGame.dat"))) {
            out.writeObject(guesses);
            out.writeObject(targetWord);
            out.writeBoolean(gameOver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedGame.dat"))) {
            guesses = (List<String>) in.readObject();
            targetWord = (String) in.readObject();
            gameOver = in.readBoolean();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

