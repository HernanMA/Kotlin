# Lv 1. Juego de Adivinanza
This document describes the process of creating a **guessing game in Kotlin**, where the user's objective is to guess a secret number between 0 and 10. This project is designed to reinforce basic Kotlin knowledge.

--- 
### Defining the Secret Number

1. Once the project is created in IntelliJ IDEA, the first step is to access the main executable file, which by default is **`Main.kt`**.
	
2. Inside this file, we will define the variable that will store the secret number. We will use the keyword **`val`** (for _value_), since the secret number will not change once generated, making it an immutable variable.
    
3. To generate the random number, we will use the **`random()`** function from the **`Math`** object. Although `Math` is part of the Kotlin standard library, it's important to clarify that `Math` is a Java class, and Kotlin simply uses it. The `random()` function generates a random decimal number between 0.0 (inclusive) and 1.0 (exclusive). To get a number between 0 and 10, we will multiply this result by `10`.
    
4. Finally, to ensure the number is an integer and not a decimal, we will apply the **`.roundToInt()`** extension function. This function will round the random number to the nearest integer.
---
### Capturing the User's Guess

1. We will create another variable, also using **`val`**, to store the number the user enters.
    
2. To prompt for user input, we use the **`readLine()`** function. This function reads a complete line of text from the standard input (the console).
    
3. Since `readLine()` returns a **`String`** (text), and we need to compare the entered number with our secret `Int`, we must convert this `String` to an **`Int`**. For this, we will use the **`Integer.parseInt()`** extension function on the result of `readLine()`.
---
### Logic to Verify the Guess

1. We will implement an **`if-else`** conditional structure to compare the number entered by the user with the secret number.
    
2. If the guess is correct, a congratulatory message will be printed.
    
3. If the guess is incorrect, the **`else`** block will execute and display a message indicating that the user was wrong. Additionally, it will reveal what the secret number was. To embed the value of the variable directly that stores the secret number into the text string, we will use string interpolation with the **`$`** symbol before the variable name (`$secretNumber`).


### Handling Invalid Input (Error Handling)

Now, let's consider what happens if the user doesn't enter a valid number. I've added robust error handling to guide them.

1. **Initializing Input Variables:** We'll introduce two new variables:
    
    - **`UserGuess: Int? = null`**: This variable will store the user's validated guess. We use `Int?` (a nullable integer) because initially, there's no guess, or the input might be invalid.
        
    - **`IsValidInput = false`**: This is a **Boolean** flag that will control a loop, ensuring we keep asking for input until a valid number is provided.
        
2. **Continuous Input Loop (`while` loop):** We wrap our input capturing logic within a **`while (!IsValidInput)`** loop. This means the code inside the loop will repeat as long as `IsValidInput` is `false`.
    
3. **Reading User Input:** We still use **`readLine()`** to capture the user's input as a **`String`**.
    
4. **Converting with Error Prevention (`try-catch` block):**
    
    - We use a **`try-catch`** block to safely attempt the conversion from `String` to `Int`.
        
    - **`try` block**: Inside this block, we attempt to convert `userInput` to an integer using the **`.toInt()`** extension function. We use the safe call operator **`?`** (`userInput?.toInt()`) because `readLine()` can return `null` if, for example, the end of the input stream is reached.
        
    - **Checking for Null `UserGuess`**: After the attempted conversion, we check if `UserGuess` is not `null`. If it's not `null`, it means the conversion was successful, so we set `IsValidInput` to `true` to exit the loop. If it is `null` (meaning `userInput` was `null`), we print a message asking the user to try again.
        
    - **`catch (e: NumberFormatException)` block**: If the user types something that **cannot** be converted into an integer (like "hello" or "abc"), a **`NumberFormatException`** will occur. The `catch` block intercepts this error, preventing the program from crashing, and prints a user-friendly error message, prompting them to enter a real number. The `while` loop then continues, asking for input again.

---

### Finalizing the Game Logic

With the input safely handled, the final part is the same as before, determining if the user was right or wrong.

1. **Conditional Check (`if-else`):** We use an **`if-else`** structure to compare the user's valid guess (`UserGuess`) with the `SecretNumber`.
    
2. **Outcome Messages:**
    
    - If `UserGuess` is equal to `SecretNumber`, a congratulatory message is displayed.
        
    - If they are not equal, a message indicating the user was wrong is shown, and the `SecretNumber` is revealed using **string interpolation (`$`)**.