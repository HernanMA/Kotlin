package input

class InputHandler {
    fun askForAmount(): Int? {
        print("How many numbers do you want to enter?: ")
        return readlnOrNull()?.trim()?.toIntOrNull()?.takeIf { it > 0 }
    }

    fun askToContinue(): String {
        var response: String?
        do {
            print("Do you want to calculate another average? (y/n): ")
            response = readlnOrNull()?.lowercase()?.trim()
        } while (response != "y" && response != "n")
        return response
    }
}
