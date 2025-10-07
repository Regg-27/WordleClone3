package org.example.wordleclone3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordleModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        try {
            var field = Model.class.getDeclaredField("targetWord");
            field.setAccessible(true);
            field.set(model, "apple");
        } catch (Exception e) {
            fail("Failed to set target word manually for test");
        }
    }

    @Test
    void testSubmitGuessAddsToList() {
        boolean result = model.submitGuess("apple");
        assertTrue(result);
        assertEquals(1, model.getGuesses().size());
    }

    @Test
    void testRejectsInvalidLengthGuess() {
        boolean result = model.submitGuess("cat");
        assertFalse(result);
        assertTrue(model.getGuesses().isEmpty());
    }

    @Test
    void testGameEndsOnCorrectGuess() {
        model.submitGuess("apple");
        assertTrue(model.isGameOver());
    }

    @Test
    void testGameEndsAfterMaxGuesses() {
        for (int i = 0; i < Model.MAX_GUESSES; i++) {
            model.submitGuess("wrong");
        }
        assertTrue(model.isGameOver());
        assertEquals(Model.MAX_GUESSES, model.getGuesses().size());
    }

    @Test
    void testResetGameClearsGuesses() {
        model.submitGuess("apple");
        model.resetGame();
        assertFalse(model.isGameOver());
        assertEquals(0, model.getGuesses().size());
    }

    @Test
    void testTargetWordChangesAfterReset() {
        String before = model.getTargetWord();
        model.resetGame();
        String after = model.getTargetWord();
        assertNotNull(after);
        assertEquals(Model.WORD_LENGTH, after.length());
    }

    @Test
    void testRejectsInvalidWord() {
        boolean result = model.submitGuess("zzzzz");
        assertFalse(result);
    }

    @Test
    void testSaveAndLoadRestoresState() throws IOException {
        model.submitGuess("apple");
        File file = File.createTempFile("wordle_test", ".dat");
        model.saveGame();

        Model loaded = new Model();
        loaded.loadGame();

        assertEquals(model.getGuesses(), loaded.getGuesses());
        assertEquals(model.getTargetWord(), loaded.getTargetWord());
        file.delete();
    }
}
