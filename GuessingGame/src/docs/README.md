# Kotlin Guessing Game: Enhanced and Modular

This document describes the process of creating an **enhanced guessing game in Kotlin**, where the user's objective is to guess a secret number between 0 and 10. This version is more robust, includes a "play again" feature, and is **modularized into two files** for better organization.

---

### `Main.kt`: The Game's Conductor

This is the main entry point of your guessing game. It orchestrates the game flow, from welcoming the player to managing rounds and handling whether the user wants to play again.

1. **Welcome Message:**

   - The game starts by printing a friendly welcome message: `"🎮 Welcome to Catching the Number"`.

2. **Game Loop (`do-while`):**

   - The entire game is wrapped in a **`do-while (playAgain())`** loop.

      - The `do` block executes at least once, meaning the game will always run for at least one round.

      - After each round, it calls the **`playAgain()`** function (from `GameUtils`). If `playAgain()` returns `true`, the loop continues for another round; otherwise, it ends.

3. **Initializing Each Round:**

   - **`val secretNumber = (0..10).random()`**: Inside the `do` loop, a new **`secretNumber`** is generated for each game round.

      - `(0..10)` creates a range of integers from 0 to 10 (inclusive).

      - **`.random()`** is a Kotlin extension function that picks a random number from this range, ensuring the secret is always between 0 and 10.

   - **`var hasGuessedCorrectly = false`**: A **mutable Boolean variable** is initialized to `false`. This flag controls the inner guessing loop, indicating whether the user has guessed correctly in the current round.

4. **Guessing Loop (`while`):**

   - **`while (!hasGuessedCorrectly)`**: This inner loop continues to prompt the user for guesses as long as `hasGuessedCorrectly` is `false`.

5. **Getting User Input:**

   - **`val userGuess = getUserGuess()`**: This line calls the **`getUserGuess()`** function (defined in `GameUtils.kt`). This function is responsible for prompting the user, validating their input, and ensuring a valid number between 0 and 10 is returned. The valid guess is stored in `userGuess`.

6. **Checking the Guess:**

   - **`if (isCorrectGuess(userGuess, secretNumber))`**: The `if` statement calls the **`isCorrectGuess()`** function (from `GameUtils.kt`) to check if the `userGuess` matches the `secretNumber`.

      - **If `true` (Correct Guess):**

         - `"Congrats! You guessed it right."` is printed.

         - **`hasGuessedCorrectly = true`**: The `hasGuessedCorrectly` flag is set to `true`, which will terminate the inner `while` loop, ending the current round.

      - **If `false` (Incorrect Guess):**

         - `"Wrong guess."` is printed.

         - **`println(compareGuess(userGuess, secretNumber))`**: The **`compareGuess()`** function (from `GameUtils.kt`) is called to provide feedback to the user (e.g., "Your guess is too low."). The returned string is then printed.

7. **Farewell Message:**

   - Once the `do-while` loop ends (meaning `playAgain()` returned `false`), a goodbye message `"👋 Thanks for playing. Goodbye!"` is displayed.


---

### `GameUtils.kt`: Utility Functions for the Game

This file contains several helper functions that encapsulate specific pieces of logic, making your `main` function cleaner and more readable.

1. **`fun getUserGuess(): Int`**:

   - This function is dedicated to safely acquiring a valid integer guess from the user.

   - **`var guess: Int? = null`**: A **nullable integer variable** `guess` is initialized to `null`. It will hold the user's validated input.

   - **`while (guess == null || guess !in 0..10)`**: An **input validation loop** that continues as long as `guess` is `null` (meaning no valid number yet) or if the `guess` is outside the required range of 0 to 10.

   - **`print("Enter your guess (0-10): ")`**: Prompts the user for input.

   - **`val input = readLine()`**: Reads the user's raw input as a `String`.

   - **`try-catch` block for Robust Conversion:**

      - **`try { val num = input?.toInt() ... }`**: Attempts to convert the `input` string to an `Int` using the **safe call operator `?.`**.

      - **`if (num != null && num in 0..10)`**: Checks if the converted `num` is not `null` (meaning successful conversion) AND if it falls within the `0..10` range. If both conditions are true, the `guess` variable is updated, and the `while` loop condition will eventually become false, exiting the loop.

      - **`else { println("Please enter a number between 0 and 10.") }`**: If `num` is `null` (empty input) or outside the range, an appropriate error message is displayed.

      - **`catch (e: NumberFormatException)`**: Catches errors if the user types non-numeric input (e.g., "abc"). It prints an "Invalid input" message, and the loop continues.

   - **`return guess`**: Once a valid guess is obtained, it's returned as a non-nullable `Int`.

2. **`fun isCorrectGuess(guess: Int, secret: Int): Boolean`**:

   - This simple function takes the `guess` and the `secret` number as `Int` parameters.

   - It returns a **`Boolean`** (`true` or `false`) indicating whether the `guess` is exactly equal to the `secret` number.

3. **`fun compareGuess(guess: Int, secret: Int): String`**:

   - This function provides helpful feedback when the guess is incorrect.

   - It takes the `guess` and `secret` numbers.

   - It uses an **`if-else` expression** (returning a `String` directly) to determine if the `guess` is `too low` or `too high` compared to the `secret`, returning the corresponding message.

4. **`fun playAgain(): Boolean`**:

   - This function prompts the user if they want to play another round.

   - **`while (true)`**: An **infinite loop** ensures the function keeps asking until a valid 'y' or 'n' input is received.

   - **`print("Do you want to play again? (y/n): ")`**: Prompts the user.

   - **`val input = readLine()?.lowercase()`**: Reads the input and converts it to **lowercase** using **`.lowercase()`** to make the comparison case-insensitive (e.g., 'Y' becomes 'y').

   - **`when (input)`**: A `when` expression checks the normalized `input`:

      - **`"y" -> return true`**: If 'y', the function returns `true`, and the game continues.

      - **`"n" -> return false`**: If 'n', the function returns `false`, ending the game.

      - **`else -> println("Please enter 'y' for yes or 'n' for no.")`**: For any other input, an error message is displayed, and the `while` loop continues to prompt again.
            
