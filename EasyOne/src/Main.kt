import kotlin.math.roundToInt

fun main() {
     val SecretNumber = (Math.random() * 10).roundToInt()

    println("I'm thinking of a number between 0 and 10. Can you guess it? Try it")
    val Guess = Integer.parseInt(readLine())

    if  (SecretNumber == Guess) {
        println("You're Rigth, congrats")
    } else {
        println("You're Wrong, think again, the number is $SecretNumber")
    }
}