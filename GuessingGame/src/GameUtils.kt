fun getUserGuess(): Int {
    var guess: Int? = null
    while (guess == null || guess !in 0..10) {
        print("Enter your guess (0-10): ")
        val input = readLine()
        try {
            val num = input?.toInt()
            if (num != null && num in 0..10) {
                guess = num
            } else {
                println("Please enter a number between 0 and 10.")
            }
        } catch (e: NumberFormatException) {
            println("Invalid input. Try again.")
        }
    }
    return guess
}

fun isCorrectGuess(guess: Int, secret: Int): Boolean {
    return guess == secret
}

fun compareGuess(guess: Int, secret: Int): String {
    return if (guess < secret) {
        "Your guess is too low."
    } else {
        "Your guess is too high."
    }
}

fun playAgain(): Boolean {
    while (true) {
        print("Do you want to play again? (y/n): ")
        val input = readLine()?.lowercase()
        when (input) {
            "y" -> return true
            "n" -> return false
            else -> println("Please enter 'y' for yes or 'n' for no.")
        }
    }
}
