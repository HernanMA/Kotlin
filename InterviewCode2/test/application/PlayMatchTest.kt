package application

import domain.model.GameState
import domain.model.MatchRecord
import domain.model.Side
import domain.ports.MatchRepository
import domain.ports.NarrationPort
import domain.ports.OutputPort
import domain.scoring.ScoreEvent
import domain.scoring.ScoringEngine
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PlayMatchTest {
    private class FakeRepo : MatchRepository {
        val stored = mutableListOf<MatchRecord>()
        override fun append(match: MatchRecord) { stored += match }
        override fun readAll() = stored
    }
    private class FakeTime : TimeProvider { override fun nowMillis() = 1234567890L }
    private class DummyOutput : OutputPort {
        override fun println(msg: String) {}
        override fun header(title: String) {}
    }

    @Test fun `PlayMatch guarda un match completo en repo`() {
        val repo = FakeRepo()
        val playSet = PlaySet(ScoringEngine(), object : NarrationPort {
            override fun lineFor(s: GameState, p1: String, p2: String, e: ScoreEvent) = "LINE"
        }, DummyOutput())

        val playMatch = PlayMatch(playSet, repo, DummyOutput(), FakeTime())
        val provider = PointWinnerProvider { Side.P1 } // siempre gana P1

        val (sets, winner) = playMatch.execute("A", "B", 1, provider, "TEST")

        assertEquals(Side.P1, winner)
        assertEquals(1, sets.size)
        assertEquals(1, repo.readAll().size)
        assertEquals("A", repo.readAll().first().winner)
        assertEquals(1234567890L, repo.readAll().first().createdAtEpochMillis)
    }
}
