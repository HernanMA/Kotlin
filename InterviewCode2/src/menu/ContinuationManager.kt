package menu

import domain.ports.InputPort


class ContinuationManager(private val input: InputPort) {
    fun afterMatch(): ContinueAction {
        val choice = input.choice(
            "What would you like to do next?",
            listOf("Play another match", "Return to main menu", "Exit")
        )
        return when (choice) {
            1 -> ContinueAction.PLAY_AGAIN
            2 -> ContinueAction.MENU
            else -> ContinueAction.EXIT
        }
    }
}

enum class ContinueAction { PLAY_AGAIN, MENU, EXIT }
