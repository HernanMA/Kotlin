package domain.ports

import domain.model.GameState
import domain.scoring.ScoreEvent

interface NarrationPort {
    fun lineFor(state: GameState, p1Name: String, p2Name: String, lastEvent: ScoreEvent): String
    fun setHeader(): String = "Set:"
}
