import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tennis.Player
import tennis.Score
import tennis.TennisGame
import java.util.stream.Stream

class TennisGameTest {

    companion object {
        @JvmStatic
        fun casesDescribe(): Stream<Arguments> = Stream.of(
            // Estado inicial
            Arguments.of(arrayOf<Player>(), "Love-Love"),

            // Progresión normal
            Arguments.of(arrayOf(Player.P1), "Fifteen-Love"),
            Arguments.of(arrayOf(Player.P1, Player.P1), "Thirty-Love"),
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1), "Forty-Love"),
            Arguments.of(arrayOf(Player.P2), "Love-Fifteen"),
            Arguments.of(arrayOf(Player.P2, Player.P2), "Love-Thirty"),
            Arguments.of(arrayOf(Player.P2, Player.P2, Player.P2), "Love-Forty"),

            // Victorias directas (40 contra <40)
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P1), "Player 1 wins"),
            Arguments.of(arrayOf(Player.P2, Player.P2, Player.P2, Player.P2), "Player 2 wins"),

            // Deuce
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2), "Deuce"),

            // Ventajas
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P1), "Advantage Player 1"),
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P2), "Advantage Player 2"),

            // Vuelve a deuce desde ventaja
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P1, Player.P2), "Deuce"),
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P2, Player.P1), "Deuce"),

            // Gana desde ventaja
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P1, Player.P1), "Player 1 wins"),
            Arguments.of(arrayOf(Player.P1, Player.P1, Player.P1, Player.P2, Player.P2, Player.P2, Player.P2, Player.P2), "Player 2 wins"),
        )
    }

    @ParameterizedTest
    @MethodSource("casesDescribe")
    fun `describe scores for many sequences`(seq: Array<Player>, expected: String) {
        val game = seq.fold(TennisGame()) { g, p -> g.playerScores(p) }
        assertEquals(expected, game.describeScores())
    }

    @Test
    fun `scoring after win throws`() {
        val gameWon = arrayOf(Player.P1, Player.P1, Player.P1, Player.P1)
            .fold(TennisGame()) { g, p -> g.playerScores(p) }

        val ex = assertThrows(IllegalStateException::class.java) {
            gameWon.playerScores(Player.P1)
        }
        assertTrue(ex.message!!.contains("Game already finished"))
    }

    @Test
    fun `progression nextScore mirrors enum`() {
        assertEquals(Score.FIFTEEN, Score.LOVE.nextScore())
        assertEquals(Score.THIRTY, Score.FIFTEEN.nextScore())
        assertEquals(Score.FORTY, Score.THIRTY.nextScore())
        assertEquals(Score.WIN, Score.FORTY.nextScore())
        assertEquals(Score.WIN, Score.ADVANTAGE.nextScore())
        assertThrows(IllegalStateException::class.java) { Score.WIN.nextScore() }
    }
}
