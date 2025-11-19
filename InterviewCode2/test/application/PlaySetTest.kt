package application

import domain.model.GameState
import domain.model.Side
import domain.ports.NarrationPort
import domain.ports.OutputPort
import domain.scoring.ScoreEvent
import domain.scoring.ScoringEngine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PlaySetTest {
    private class FakeProvider(private val seq: Iterator<Side>) : PointWinnerProvider {
        override fun next(state: GameState) = seq.next()
    }
    private class SpyOutput : OutputPort {
        val lines = mutableListOf<String>()
        override fun println(msg: String) { lines += msg }
        override fun header(title: String) { lines += "HEADER: $title" }
    }
    private class StubNarrator : NarrationPort {
        override fun lineFor(s: GameState, p1: String, p2: String, e: ScoreEvent) = "LINE"
    }

    @Test fun `PlaySet devuelve ganador correcto`() {
        val playSet = PlaySet(ScoringEngine(), StubNarrator(), SpyOutput())
        val provider = FakeProvider(listOf(Side.P1, Side.P1, Side.P1, Side.P1).iterator())

        val (summary, winner) = playSet.execute("A", "B", provider)

        assertEquals(Side.P1, winner)
        assertEquals("A", summary.winner)
        assertEquals("Game", summary.finalScoreLabel)
        assertTrue(summary.pointTimeline.isNotEmpty())
    }
}
