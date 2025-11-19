package domain.scoring

import domain.model.GameState
import domain.model.Side
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ScoringEngineTest {
    private val engine = ScoringEngine()

    @Test fun `P1 gana 4 puntos seguidos y se lleva el game`() {
        var state = GameState()
        val seq = listOf(Side.P1, Side.P1, Side.P1, Side.P1)
        var last: ScoreEvent = ScoreEvent.Progressed
        for (side in seq) {
            val result = engine.next(state, side)
            state = result.first
            last = result.second
        }


        assertTrue(state.finished)
        assertEquals(Side.P1, state.winner)
        assertTrue(last is ScoreEvent.GameWon)
    }

    @Test fun `40-40 lleva a deuce`() {
        var s = GameState()
        listOf(Side.P1, Side.P2, Side.P1, Side.P2, Side.P1, Side.P2).forEach {
            s = engine.next(s, it).first
        }
        assertTrue(s.deuce)
    }

    @Test fun `next en game terminado lanza error`() {
        val s = GameState(finished = true, winner = Side.P1)
        assertThrows<IllegalArgumentException> {
            engine.next(s, Side.P2)
        }
    }
}
