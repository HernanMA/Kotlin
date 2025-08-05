import UtilsAvg.*

fun main() {
    var continueOption: String? = "y"

    do {
        val amount = requestAmount()
        if (amount == null) {
            println("Invalid amount. Only positive integers are accepted.")
            continue
        }

        val numbers = requestNumbers(amount)
        val average = calculateAverage(numbers)
        showResult(average)

        continueOption = requestContinuation()
    } while (continueOption == "y")

    println("Program finished.")
}
