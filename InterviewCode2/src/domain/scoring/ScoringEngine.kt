package domain.scoring

import domain.model.GameState
import domain.model.Point
import domain.model.Side

class ScoringEngine {

    fun next(state: GameState, winner: Side): Pair<GameState, ScoreEvent> {
        require(!state.finished) { "Game already finished" }

        val w = pointOf(state, winner)
        val l = pointOf(state, opposite(winner))

        if (state.deuce) {
            return when {
                w == Point.ADVANTAGE -> {
                    val s = setPoint(state, winner, Point.GAME).copy(finished = true, winner = winner)
                    s to ScoreEvent.GameWon(winner)
                }
                l == Point.ADVANTAGE -> {
                    val s1 = setPoint(state, winner, Point.FORTY)
                    val s2 = setPoint(s1, opposite(winner), Point.FORTY)
                    s2.copy(deuce = true) to ScoreEvent.Deuce
                }
                else -> {
                    val s = setPoint(state, winner, Point.ADVANTAGE).copy(deuce = true)
                    s to ScoreEvent.Advantage(winner)
                }
            }
        }

        return when (w) {
            Point.LOVE    -> setPoint(state, winner, Point.FIFTEEN) to ScoreEvent.Progressed
            Point.FIFTEEN -> setPoint(state, winner, Point.THIRTY)  to ScoreEvent.Progressed
            Point.THIRTY  -> {
                if (l == Point.FORTY) {
                    val s = setPoint(state, winner, Point.FORTY).copy(deuce = true)
                    s to ScoreEvent.Deuce
                } else {
                    setPoint(state, winner, Point.FORTY) to ScoreEvent.Progressed
                }
            }
            Point.FORTY   -> {
                if (l == Point.FORTY) {
                    state.copy(deuce = true) to ScoreEvent.Deuce
                } else {
                    val s = setPoint(state, winner, Point.GAME).copy(finished = true, winner = winner)
                    s to ScoreEvent.GameWon(winner)
                }
            }
            Point.ADVANTAGE -> {
                val s = setPoint(state, winner, Point.GAME).copy(finished = true, winner = winner)
                s to ScoreEvent.GameWon(winner)
            }
            Point.GAME -> error("Invariant broken: finished=true should prevent this branch")
        }
    }

    private fun pointOf(s: GameState, side: Side) = if (side == Side.P1) s.p1 else s.p2
    private fun setPoint(s: GameState, side: Side, v: Point): GameState =
        if (side == Side.P1) s.copy(p1 = v) else s.copy(p2 = v)
    private fun opposite(side: Side) = if (side == Side.P1) Side.P2 else Side.P1
}
