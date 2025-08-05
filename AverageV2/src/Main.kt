import input.InputHandler
import numberReader.NumberReader
import average.AverageCalculator

fun main() {
    val inputHandler = InputHandler()
    val numberReader = NumberReader()
    val averageCalculator = AverageCalculator()

    var continuePlaying = "y"

    do {
        val amount = inputHandler.askForAmount()
        if (amount == null) {
            println("Invalid amount. Only positive integers are allowed.")
            continue
        }

        val numbers = numberReader.readNumbersRecursive(amount)
        val average = averageCalculator.calculate(numbers)
        averageCalculator.showResult(numbers, average)

        continuePlaying = inputHandler.askToContinue()
    } while (continuePlaying == "y")

    println("Program ended.")
}
