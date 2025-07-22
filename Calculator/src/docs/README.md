

This document describes the process of creating a **basic calculator in Kotlin**, structured into three separate files for better organization and reusability. This project will reinforce your knowledge of functions, packages, input handling, and basic arithmetic operations in Kotlin.

---

### `Main.kt`: Orchestrating the Calculator

This is the entry point of your calculator application. It's responsible for welcoming the user, gathering the necessary inputs (two numbers and an operation), and then displaying the result or any errors.

1. **Package Imports:**

    - **`import Math.*`**: This line imports all the functions defined within your `Math` package (like `sum`, `subtract`, `GetNumbers`, `GetOperation`). This allows you to call these functions directly in `main.kt` without needing to specify `Math.sum()`, etc.

2. **Welcome Message:**

    - The program starts by printing a friendly welcome message to the user, setting the stage for the calculator.

3. **Gathering Inputs:**

    - **`val Num1 = GetNumbers("What's your first number?")`**: This line calls the `GetNumbers` function (from your `Math` package) to prompt the user for their first number. The message "What's your first number?" is passed as an argument to guide the user. The valid numeric input is then stored in the immutable variable `Num1`.

    - **`val Operation = GetOperation("What kind of operation?")`**: Similarly, this calls the `GetOperation` function to ask the user for the desired arithmetic operation (+, -, *, /). The validated operation symbol is stored in `Operation`.

    - **`val Num2 = GetNumbers("What's your second number?")`**: Finally, `GetNumbers` is called again to get the second number, which is stored in `Num2`.

4. **Performing the Calculation (`try-catch` with `when` expression):**

    - The calculation logic is enclosed within a **`try-catch`** block to handle potential issues, especially division by zero or invalid operations.

    - **`val Result = when (Operation) { ... }`**: A **`when` expression** is used here as a more concise and readable alternative to multiple `if-else if` statements. It checks the value of the `Operation` variable:

        - **`"+" -> sum(Num1, Num2)`**: If `Operation` is `"+"`, it calls the `sum` function with `Num1` and `Num2` and assigns the returned value to `Result`.

        - Similar logic applies to **`-` (subtract), `*` (multiply), and `/` (divide)**.

        - **`else -> throw IllegalArgumentException("Invalid operation")`**: This is a crucial **error handling** step. If the `GetOperation` function somehow allows an invalid operator to slip through (or if you were to change its logic in the future), this `else` branch would catch it, throwing an `IllegalArgumentException` with a specific error message.

    - **`println("The result is $Result")`**: If the calculation is successful, the `Result` is printed to the console using **string interpolation**.

    - **`catch (e: IllegalArgumentException)`**: This block catches any `IllegalArgumentException` that might be thrown during the calculation (e.g., from the `else` branch in the `when` expression, or potentially from a division by zero handled by the `divide` function if it were implemented that way, though in this case `divide` simply returns `Infinity` for `x/0`). It then prints a user-friendly error message using `e.message` to display the specific error that occurred.


---

### `Operaciones.kt`: Defining Core Arithmetic Operations

This file, located within the **`Math` package**, is dedicated to containing the fundamental arithmetic functions. By placing them in a separate package, you make them reusable in other parts of your application or even in different projects.

1. **`package Math`**: This line declares that all the functions within this file belong to the `Math` package.

2. **`fun sum(a: Double, b: Double): Double`**:

    - This function takes two **`Double`** parameters (`a` and `b`).

    - It returns their sum, also as a **`Double`**.

3. **`fun subtract(a: Double, b: Double): Double`**:

    - Takes two `Double` parameters.

    - Returns their difference as a `Double`.

4. **`fun multiply(a: Double, b: Double): Double`**:

    - Takes two `Double` parameters.

    - Returns their product as a `Double`.

5. **`fun divide(a: Double, b: Double): Double`**:

    - Takes two `Double` parameters.

    - Returns their quotient as a `Double`.

    - **Note on Division by Zero**: In Kotlin (and many languages), dividing a non-zero `Double` by `0.0` will result in `Infinity` or `NaN` (Not a Number) rather than throwing an exception. Your current `main` logic would print "The result is Infinity" in such a case, which is standard floating-point behavior. If you wanted to explicitly handle this as an error, you'd add a check within this function or in the `main`'s `when` expression.


---

### `funciones.kt`: Robust Input Handling

This file, also part of the **`Math` package**, contains functions specifically designed to handle user input securely and validate it. This prevents the program from crashing due to incorrect entries.

1. **`package Math`**: As with `Operaciones.kt`, this file also belongs to the `Math` package.

2. **`fun GetNumbers(Message: String): Double`**:

    - This function is designed to repeatedly ask the user for a number until a valid one is provided.

    - **`while (true)`**: This creates an **infinite loop**, which will only be exited when a valid number is successfully returned.

    - **`print(Message)`**: It displays the prompt message passed as an argument (e.g., "What's your first number?").

    - **`val Entry = readLine()`**: Reads the user's input as a `String`.

    - **`try-catch` block**: This is critical for robust input validation:

        - **`try { return Entry?.toDouble() ?: throw IllegalArgumentException("Entry not valid") }`**:

            - **`Entry?.toDouble()`**: This attempts to convert the `String` input (`Entry`) into a **`Double`**. The **safe call operator `?`** handles cases where `readLine()` might return `null` (e.g., if the user immediately presses Enter without typing anything, or if the input stream closes). If `Entry` is `null`, `?.toDouble()` will also result in `null`.

            - **`?: throw IllegalArgumentException("Entry not valid")`**: This is the **Elvis operator**. If `Entry?.toDouble()` results in `null` (meaning the input was either `null` or couldn't be converted), the Elvis operator throws an **`IllegalArgumentException`** with the message "Entry not valid". This specifically handles cases where the user just presses Enter or provides an empty string.

            - If the conversion to `Double` is successful, the function immediately **`return`**s that `Double` value, exiting the `while` loop.

        - **`catch (e: NumberFormatException)`**: This block catches exceptions if the user enters text that is clearly not a number (e.g., "hello", "abc"). It prints an `ERROR` message and the `while` loop continues, prompting the user again.

        - **`catch (e: IllegalArgumentException)`**: This block catches the `IllegalArgumentException` specifically thrown by the Elvis operator if the input was `null` or empty. It prints the specific error message, and the loop continues.

3. **`fun GetOperation(Message: String): String`**:

    - This function is designed to get a valid arithmetic operation symbol from the user.

    - **`while (true)`**: Again, an infinite loop ensures valid input is received.

    - **`print(Message)`**: Displays the prompt for the operation.

    - **`val Operation = readLine()?.trim()`**: Reads the input, and **`.trim()`** removes any leading or trailing whitespace (like spaces) from the user's input, making the comparison more reliable.

    - **`if (Operation in listOf("+", "-", "*", "/"))`**: This checks if the `Operation` entered by the user is present in the specified `listOf` valid operators.

        - If it is a valid operator, **`return Operation!!`**: The function returns the validated `Operation`. The **`!!` (not-null assertion operator)** is used here because, at this point, we are confident `Operation` cannot be `null` (since it passed the `in listOf(...)` check, meaning it must have been one of the non-null strings in the list).

        - **`else { println("ERROR: Operation not valid") }`**: If the entered operation is not in the list, an `ERROR` message is printed, and the `while` loop continues.
---
