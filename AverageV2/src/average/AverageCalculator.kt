package average

class AverageCalculator {
    fun calculate(numbers: List<Int>) = numbers.average()

    fun showResult(numbers: List<Int>, average: Double) {
        println("Numbers entered: $numbers")
        println("The average is: $average")
    }
}
