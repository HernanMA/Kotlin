package presentation.cli

import domain.ports.InputPort

class PlayerPrompter(private val input: InputPort) {
    fun askBothPlayers(): Pair<String, String> {
        val p1 = input.line("Enter player 1 name")
        val p2 = input.line("Enter player 2 name")
        return p1 to p2
    }
}
