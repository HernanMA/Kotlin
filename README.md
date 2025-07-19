# Easy One (guessing game v1)
This documentation describes the process of creating a **guessing game in Kotlin**, where the user's objective is to guess a secret number between 0 and 10. This project is designed to reinforce basic Kotlin knowledge.

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
