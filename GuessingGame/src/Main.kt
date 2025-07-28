fun main() {
    println("🎮 Welcome to Catching the Number")

    do {
        val secretNumber = (0..10).random()
        var hasGuessedCorrectly = false

        while (!hasGuessedCorrectly) {
            val userGuess = getUserGuess()

            if (isCorrectGuess(userGuess, secretNumber)) {
                println("Congrats! You guessed it right.")
                hasGuessedCorrectly = true
            } else {
                println("Wrong guess.")
                println(compareGuess(userGuess, secretNumber))
            }
        }

    } while (playAgain())

    println("👋 Thanks for playing. Goodbye!")
}
