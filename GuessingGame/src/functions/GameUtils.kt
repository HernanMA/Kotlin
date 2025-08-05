package functions

fun getUserGuess(): Int {
    var guess: Int? = null
    while (guess == null || guess !in 0..10) {
        print("Enter your guess (0-10): ")
        val input = readlnOrNull()
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

fun isCorrectGuess(guess: Int, secret: Int) = guess == secret

fun compareGuess(guess: Int, secret: Int) = if (guess < secret) {
    "Your guess is too low."
} else {
    "Your guess is too high."
}

tailrec fun playAgain(promp:String = "Do you want to play again? (y/n): "): Boolean {

    print(promp)
    val input = readlnOrNull()?.trim()?.lowercase()
    return when (input) {
        "y" ->  true
        "n" ->  false
    //    else -> println("Please enter 'y' for yes or 'n' for no.")
        else -> playAgain("Please enter 'y' for yes or 'n' for no.")
    }
}
