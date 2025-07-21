import kotlin.math.roundToInt

fun main() {

    val SecretNumber = (Math.random() * 10).roundToInt()
    println("Welcome to Catching the number")

    var UserGuess: Int? = null
    var IsValidInput = false

    while (!IsValidInput) {
        print("Enter your guess 0-10: ")
        val userInput = readLine()

        try {
            UserGuess = userInput?.toInt()
            if (UserGuess != null) {
                IsValidInput = true
            } else {
                println("Try again with a real number")
            }
        } catch (e: NumberFormatException) {
            println("ERROR! Invalid input, try again with a real number")
        }
    }

    if (UserGuess == SecretNumber) {
        println("Congrats, you're right!")
    } else  {
        println("I'm so sorry, you're wrong :(, the number was $SecretNumber")
    }
}