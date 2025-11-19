// TennisGame.kt (versión refactor mínima)
package tennis

enum class Player { P1, P2 }

enum class Score(val str: String) {
    LOVE("Love")    { override fun nextScore() = FIFTEEN },
    FIFTEEN("Fifteen"){ override fun nextScore() = THIRTY },
    THIRTY("Thirty"){ override fun nextScore() = FORTY },
    FORTY("Forty")  { override fun nextScore() = WIN },
    ADVANTAGE("Advantage"){ override fun nextScore() = WIN },
    WIN("Win")      { override fun nextScore(): Nothing = throw IllegalStateException("Gaaaa") };
    abstract fun nextScore(): Score
}

data class TennisGame(val score1: Score = Score.LOVE, val score2: Score = Score.LOVE) {

    fun describeScores(): String = when {
        score1 == Score.WIN -> "Player 1 wins"
        score2 == Score.WIN -> "Player 2 wins"
        score1 == Score.FORTY && score2 == Score.FORTY -> "Deuce"
        score1 == Score.ADVANTAGE && score2 == Score.FORTY -> "Advantage Player 1"
        score2 == Score.ADVANTAGE && score1 == Score.FORTY -> "Advantage Player 2"
        else -> "${score1.str}-${score2.str}"
    }

    fun playerScores(player: Player): TennisGame {
        if (score1 == Score.WIN || score2 == Score.WIN)
            throw IllegalStateException("Game already finished")

        // indices 0=P1, 1=P2 para escribir lógica simétrica
        val scores = arrayOf(score1, score2)
        val i = if (player == Player.P1) 0 else 1      // anota
        val j = 1 - i                                   // rival

        val a = scores[i]; val b = scores[j]

        val next = when {
            // 40-40 -> ventaja del que anota
            a == Score.FORTY && b == Score.FORTY -> Score.ADVANTAGE to Score.FORTY
            // Rival tenía ventaja -> vuelve a deuce (40-40)
            b == Score.ADVANTAGE -> Score.FORTY to Score.FORTY
            // El que anota está en 40 y el rival por debajo de 40 -> gana
            a == Score.FORTY && b.ordinal < Score.FORTY.ordinal -> Score.WIN to b
            // El que anota con ventaja -> gana
            a == Score.ADVANTAGE -> Score.WIN to b
            // Progreso normal
            else -> a.nextScore() to b
        }

        scores[i] = next.first; scores[j] = next.second
        return copy(score1 = scores[0], score2 = scores[1])
    }
}
