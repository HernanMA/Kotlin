package domain.scoring

import domain.model.GameState
import domain.model.Point

object ScoreFormatter {
    fun label(state: GameState): String {
        if (state.finished) return "Game"
        if (state.deuce) {
            return when {
                state.p1 == Point.ADVANTAGE -> "Advantage P1"
                state.p2 == Point.ADVANTAGE -> "Advantage P2"
                else -> "Deuce"
            }
        }
        return "${label(state.p1)} - ${label(state.p2)}"
    }

    private fun label(p: Point) = when (p) {
        Point.LOVE -> "Love"
        Point.FIFTEEN -> "15"
        Point.THIRTY -> "30"
        Point.FORTY -> "40"
        Point.ADVANTAGE -> "Adv."
        Point.GAME -> "Game"
    }
}
