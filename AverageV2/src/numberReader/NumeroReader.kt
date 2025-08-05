package numberReader

class NumberReader {
    fun readNumbers(amount: Int): List<Int> {
        val numbers = mutableListOf<Int>()
        var i = 1

        while (numbers.size < amount) {
            print("Enter number $i: ")
            val input = readlnOrNull()?.trim()?.toIntOrNull()

            if (input != null) {
                numbers.add(input)
                i++
            } else {
                println("Invalid number. Try again.")
            }
        }

        return numbers
    }

    fun readNumbersRecursive(amount: Int): List<Int> {

        // base case
        if (amount == 0) {
            return emptyList()
        } else {
            // read one number
            print("Enter number : ")
            val number = readlnOrNull()?.trim()?.toIntOrNull() ?: 0
            return listOf(number) + readNumbersRecursive(amount - 1)

        }


        // recursive case


    }
}
