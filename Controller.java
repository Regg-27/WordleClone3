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
        if (!model.submitGuess(guess)) {
            view.showMessage("Word must be " + Model.WORD_LENGTH + " letters and valid.");
        }
        view.updateAll();
    }

    public void resetGame() {
        model.resetGame();
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

