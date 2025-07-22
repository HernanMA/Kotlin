package Math

fun GetNumbers(Message: String): Double {
    while (true) {
        print(Message)
        val Entry = readLine()
        try {
            return Entry?.toDouble() ?: throw IllegalArgumentException("Entry not valid")
        } catch (e: NumberFormatException) {
            println("ERROR, please enter a valid number")
        } catch (e: IllegalArgumentException) {
            println("ERROR, ${e.message}")
        }
    }
}

fun GetOperation(Message: String): String {
    while (true) {
        print(Message)
        val Operation = readLine()?.trim()
        if (Operation in listOf("+", "-", "*", "/")) {
            return Operation!!
        } else {
            println("ERROR: Operation not valid")
        }
    }
}