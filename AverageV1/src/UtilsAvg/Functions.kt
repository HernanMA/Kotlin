package UtilsAvg

fun requestAmount(): Int? {
    print("How many numbers do you want to enter?: ")
    return readLine()?.trim()?.toIntOrNull()?.takeIf { it > 0 }
}

fun requestNumbers(amount: Int): List<Int> {
    val numbers = mutableListOf<Int>()
    var i = 1

    while (numbers.size < amount) {
        print("Enter number $i: ")
        val input = readLine()?.trim()?.toIntOrNull()

        if (input != null) {
            numbers.add(input)
            i++
        } else {
            println("Invalid number. Please try again.")
        }
    }

    return numbers
}

fun calculateAverage(numbers: List<Int>): Double {
    return numbers.average()
}

fun showResult(average: Double) {
    println("The average is: $average")
}

fun requestContinuation(): String? {
    print("Do you want to calculate another average? (y/n): ")
    return readLine()?.lowercase()
}
