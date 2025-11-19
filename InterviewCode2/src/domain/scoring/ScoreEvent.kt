package domain.scoring

import domain.model.Side

sealed interface ScoreEvent {
    data object Deuce : ScoreEvent
    data class Advantage(val player: Side) : ScoreEvent
    data class GameWon(val winner: Side) : ScoreEvent
    data object Progressed : ScoreEvent
}
