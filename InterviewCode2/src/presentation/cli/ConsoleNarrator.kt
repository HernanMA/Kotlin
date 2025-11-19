package presentation.cli

import domain.model.GameState
import domain.model.Side
import domain.ports.NarrationPort
import domain.scoring.ScoreEvent
import domain.scoring.ScoreFormatter


class ConsoleNarrator : NarrationPort {
    override fun lineFor(state: GameState, p1Name: String, p2Name: String, lastEvent: ScoreEvent): String {
        return when (lastEvent) {
            is ScoreEvent.GameWon -> {
                val w = if (lastEvent.winner == Side.P1) p1Name else p2Name
                "Narrator: game ends, $w converts to win."
            }
            is ScoreEvent.Advantage -> {
                val who = if (lastEvent.player == Side.P1) p1Name else p2Name
                "Narrator: $who takes the edge with Advantage."
            }
            ScoreEvent.Deuce -> "Narrator: the game is tied dramatically at Deuce."
            ScoreEvent.Progressed -> "Narrator: score is ${ScoreFormatter.label(state)}."
        }
    }

    override fun setHeader(): String = "Set:"
}
