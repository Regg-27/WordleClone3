package org.example.wordleclone3;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setController(this);
        view.updateAll();
    }

    public void submitGuess(String guess) {
        boolean valid = model.submitGuess(guess);
        view.updateAll(); // always update tiles

        if (!valid) {
            view.showMessage("Word must be " + Model.WORD_LENGTH + " letters and valid.");
            view.keepInput(guess); // restore text if invalid
        } else {
            view.clearInput(); // only clear for valid guesses
        }
    }


    public void resetGame() {
        model.resetGame();
        view.resetKeyboardColors();
        view.updateAll();
    }


    public Model getModel() {
        return model;
    }

    public void saveGame() {
        model.saveGame();
        view.showMessage("Game saved!");
    }

    public void loadGame() {
        model.loadGame();
        view.updateAll();
        view.showMessage("Game loaded!");
    }
}

