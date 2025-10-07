package org.example.wordleclone3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class View extends Application {

    private Controller controller;
    private Label[][] boxes = new Label[Model.MAX_GUESSES][Model.WORD_LENGTH];
    private Map<Character, Button> keyboardButtons = new HashMap<>();
    private TextField guessInput;
    private Label messageLabel;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");

        messageLabel = new Label("");
        messageLabel.setFont(Font.font(14));
        messageLabel.setTextFill(Color.RED);

        VBox board = new VBox(5);
        board.setAlignment(Pos.CENTER);
        for (int row = 0; row < Model.MAX_GUESSES; row++) {
            HBox rowBox = new HBox(5);
            rowBox.setAlignment(Pos.CENTER);
            for (int col = 0; col < Model.WORD_LENGTH; col++) {
                Label box = new Label("");
                box.setPrefSize(60, 68);
                box.setAlignment(Pos.CENTER);
                box.setStyle("-fx-border-color: black; -fx-background-color: white;");
                box.setFont(new Font(36));
                boxes[row][col] = box;
                rowBox.getChildren().add(box);
            }
            board.getChildren().add(rowBox);
        }

        guessInput = new TextField();
        guessInput.setPrefWidth(150);
        guessInput.setAlignment(Pos.CENTER);
        guessInput.setPromptText("Enter 5-letter word");

        Button checkButton = new Button("Check");
        checkButton.setOnAction(e -> {
            controller.submitGuess(guessInput.getText());
            guessInput.clear();
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> controller.resetGame());

        Button saveButton = new Button("Save Game");
        saveButton.setOnAction(e -> controller.saveGame());

        Button loadButton = new Button("Load Game");
        loadButton.setOnAction(e -> controller.loadGame());

        HBox inputBox = new HBox(10, guessInput, checkButton, resetButton, saveButton, loadButton);
        inputBox.setAlignment(Pos.CENTER);
        VBox inputArea = new VBox(5, messageLabel, inputBox);
        inputArea.setAlignment(Pos.CENTER);

        VBox keyboard = buildKeyboard();
        root.getChildren().addAll(board, inputArea, keyboard);

        Scene scene = new Scene(root, 750, 950);
        stage.setTitle("WordleClone3 MVC");
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                controller.submitGuess(guessInput.getText());
                guessInput.clear();
            }
        });

        Model model = new Model();
        controller = new Controller(model, this);
    }

    private VBox buildKeyboard() {
        VBox keyboardBox = new VBox(5);
        keyboardBox.setAlignment(Pos.CENTER);
        String[] keys = {"QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM"};
        for (String row : keys) {
            HBox rowBox = new HBox(5);
            rowBox.setAlignment(Pos.CENTER);
            for (char c : row.toCharArray()) {
                Button keyButton = new Button(String.valueOf(c));
                keyButton.setPrefSize(40, 40);
                keyButton.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: black;");
                keyButton.setOnAction(e -> {
                    if (guessInput.getText().length() < Model.WORD_LENGTH) {
                        guessInput.appendText(String.valueOf(c).toLowerCase());
                    }
                });
                keyboardButtons.put(c, keyButton);
                rowBox.getChildren().add(keyButton);
            }
            keyboardBox.getChildren().add(rowBox);
        }
        return keyboardBox;
    }

    public void updateAll() {
        Model model = controller.getModel();
        int row = 0;
        for (String guess : model.getGuesses()) {
            String[] result = new String[Model.WORD_LENGTH];
            String target = model.getTargetWord();
            for (int i = 0; i < Model.WORD_LENGTH; i++) {
                char letter = guess.charAt(i);
                if (letter == target.charAt(i)) result[i] = "green";
                else if (target.contains(String.valueOf(letter))) result[i] = "yellow";
                else result[i] = "gray";

                updateKeyboardColor(letter, result[i]);
            }
            updateRow(row, guess, result);
            row++;
        }
        for (; row < Model.MAX_GUESSES; row++) clearRow(row);

        if (model.isGameOver()) {
            if (!model.getGuesses().isEmpty() &&
                    model.getGuesses().get(model.getGuesses().size() - 1).equals(model.getTargetWord())) {
                showMessage("You guessed it!");
            } else {
                showMessage("Out of guesses! Word was: " + model.getTargetWord().toUpperCase());
            }
        } else showMessage("");
    }

    public void updateRow(int row, String guess, String[] result) {
        for (int col = 0; col < Model.WORD_LENGTH; col++) {
            boxes[row][col].setText(String.valueOf(guess.charAt(col)).toUpperCase());
            switch (result[col]) {
                case "green" -> boxes[row][col].setStyle("-fx-background-color: #57ff57; -fx-border-color: black;");
                case "yellow" -> boxes[row][col].setStyle("-fx-background-color: #E8F56A; -fx-border-color: black;");
                case "gray" -> boxes[row][col].setStyle("-fx-background-color: #C7C7C9; -fx-border-color: black;");
            }
        }
    }

    public void clearRow(int row) {
        for (int col = 0; col < Model.WORD_LENGTH; col++) {
            boxes[row][col].setText("");
            boxes[row][col].setStyle("-fx-background-color: white; -fx-border-color: black;");
        }
    }

    public void updateKeyboardColor(char letter, String color) {
        Button key = keyboardButtons.get(Character.toUpperCase(letter));
        if (key == null) return;
        switch (color) {
            case "green" -> key.setStyle("-fx-background-color: #57ff57; -fx-border-color: black;");
            case "yellow" -> key.setStyle("-fx-background-color: #E8F56A; -fx-border-color: black;");
            case "gray" -> {
                if (!key.getStyle().contains("#57ff57"))
                    key.setStyle("-fx-background-color: #C7C7C9; -fx-border-color: black;");
            }
        }
    }

    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        launch();
    }
}

