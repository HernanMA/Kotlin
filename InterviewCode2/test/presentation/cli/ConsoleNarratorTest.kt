package presentation.cli

import domain.model.GameState
import domain.model.Point
import domain.model.Side
import domain.scoring.ScoreEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ConsoleNarratorTest {
    private val narrator = ConsoleNarrator()

    @Test fun `Advantage P2 narra correctamente`() {
        val s = GameState(p2 = Point.ADVANTAGE, deuce = true)
        val line = narrator.lineFor(s, "Alice", "Bob", ScoreEvent.Advantage(Side.P2))
        assertTrue(line.contains("Bob"))
        assertTrue(line.contains("Advantage"))
    }

    @Test fun `GameWon P1 narra con nombre correcto`() {
        val s = GameState(finished = true, winner = Side.P1)
        val line = narrator.lineFor(s, "Alice", "Bob", ScoreEvent.GameWon(Side.P1))
        assertTrue(line.contains("Alice"))
    }
}
