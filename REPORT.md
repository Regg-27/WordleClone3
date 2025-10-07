WordleClone3 Report

Design Decisions

MVC Architecture
The project follows the MVC pattern:
 Model manages game logic, word list, and save/load features.
 View handles all JavaFX UI components.
 Controller links user input from the view to the model.


Dictionary Loading
Instead of hardcoding words, the program loads them from an external text file (`Dictionary`) stored in the resources folder. This makes it easy to modify or expand the word list without recompiling.

Save/Load Mechanism
The game uses Java’s builtin `ObjectOutputStream` and `ObjectInputStream` for serialization to save and restore the game state (guesses, target word, and game status).  

This was chosen for simplicity and because it handles list serialization automatically.


Challenges and Solutions

 1. JavaFX Runtime Missing
Issue: “JavaFX runtime components are missing” error appeared after refactoring.  
Solution: Reconfigured IntelliJ run configuration with the correct `modulepath` and `addmodules` options pointing to the JavaFX SDK.

 2. Package Conflicts
Issue: IntelliJ created nested packages like `org.example.wordleclone2.wordleclone2`, breaking imports.  
Solution: Moved all `.java` files into one clean package (`org.example.wordleclone3`) and deleted the `moduleinfo.java` file.

 3. Dictionary File Not Loading
Issue: Game defaulted to "apple" because the file path was incorrect.  
Solution: Placed `Dictionary` inside `src/main/resources` and loaded it via `getResourceAsStream`.

 4. Color Feedback Logic
Issue: Letters repeated in guesses caused incorrect color results.  
Solution: Simplified comparison logic and improved UI updates to handle letters individually.

 5. Test Isolation
Issue: Tests depended on external files.  
Solution: Used reflection to manually set the target word and validate logic without external dependencies.



 Lessons Learned

1. Proper package structure matters — nested packages or misplaced files can break compilation.
2. JavaFX configuration must be consistent across projects, especially when copying code.
3. MVC structure greatly simplifies debugging and scaling, especially for games with visual and logical layers.
4. Serialization is an easy and powerful way to implement save/load without external libraries.
5. Automated testing ensures that core logic (like validation and save/load) works even when the UI changes.



 Future Improvements

 Add custom difficulty levels (e.g., 6letter words).  
 Add multiple save slots.  
 Integrate sound effects or animations.  
