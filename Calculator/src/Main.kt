import Math.*

fun main() {

    println("Welcome to your Kotlin Calculator!!")
    println("What do you want to do?")

    val Num1 = GetNumbers("What's your first number?")
    val Operation = GetOperation("What kind of operation?")
    val Num2 = GetNumbers("What's your second number?")

    try {
        val result = when (Operation) {
            "+" -> sum(Num1, Num2)
            "-" -> subtract(Num1, Num2)
            "*" -> multiply(Num1, Num2)
            "/" -> divide(Num1, Num2)
            else -> throw IllegalArgumentException("Invalid operation")
        }
        println("The result is $result")
    } catch (e: IllegalArgumentException) {
        println("ERROR: ${e.message}")
    }
}