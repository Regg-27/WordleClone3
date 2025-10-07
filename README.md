# WordleClone3
CS 3650 - Assignment 2: Graphics Implementations with a Wordle Clone


A JavaFX-based clone of the popular Wordle game.  
Players have six attempts to guess a random five letter word.  
After each guess, the letters are color coded to show how close the guess is to the target word.


How to Run

Requirements
- Java 17 or newer  
- JavaFX SDK (version 17+ recommended)
- IntelliJ IDEA (or another Java IDE with JavaFX support)

Setup Instructions

1. **Create a new JavaFX project** in IntelliJ.  
2. Copy the provided source files into `src/main/java/org/example/wordleclone3/`.  
3. Place a dictionary file named `Dictionary` (plain text, one 5-letter word per line) inside `src/main/resources/`.  

5. Run the View.java file



Features

- Randomly selects a target word from the dictionary.  
- Validates guesses against the dictionary.  
- Color-coded feedback system:  
- Green = correct letter and position  
- Yellow = correct letter, wrong position  
- Gray = incorrect letter  
- Up to 6 attempts per round.  
- Save and load game functionality.  
- Reset to start a new game.  
- Simple, responsive JavaFX UI with an on-screen keyboard.  



Controls

- Type your 5-letter guess using your keyboard or the on-screen buttons.  
- Press **Enter** to submit your guess.  
- Press **Backspace** or **←** to delete the last letter.  
- Use the **Save** and **Load** buttons to store or restore your progress.  
- Use the **Reset** button to start a new game.



Known Issues

- The dictionary must exist in the `resources` folder, or the game will default to "apple."  
- The keyboard color-coding may not update for repeated letters in a word.  
- Saved games overwrite the existing save file (`savedGame.dat`)—no multi-save support yet.  
- Closing the app mid-save may cause an incomplete `savedGame.dat` file.



Testing

A `WordleModelTest.java` file is provided under `src/test/java/org/example/wordleclone3/`.  
To run tests:

1. Run the test class from IntelliJ 
