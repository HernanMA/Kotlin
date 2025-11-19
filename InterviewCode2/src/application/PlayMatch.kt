package application

import domain.model.SetSummary
import domain.model.Side
import domain.ports.MatchRepository
import domain.ports.OutputPort
import java.util.UUID

class PlayMatch(
    private val playSet: PlaySet,
    private val repo: MatchRepository,
    private val output: OutputPort,
    private val time: TimeProvider
) {
    fun execute(
        p1: String,
        p2: String,
        bestOf: Int,
        provider: PointWinnerProvider,
        mode: String
    ): Pair<List<SetSummary>, Side> {
        require(bestOf in setOf(1, 3, 5)) { "bestOf must be 1, 3 or 5" }
        output.println("Starting $mode match between $p1 and $p2 (best of $bestOf sets)")

        val needed = bestOf / 2 + 1
        val sets = mutableListOf<SetSummary>()
        var p1Sets = 0
        var p2Sets = 0

        while (p1Sets < needed && p2Sets < needed) {
            val (setSummary, winnerSide) = playSet.execute(p1, p2, provider)
            sets += setSummary
            if (winnerSide == Side.P1) p1Sets++ else p2Sets++
            output.println("Sets so far: $p1Sets-$p2Sets")
        }

        val matchWinnerSide = if (p1Sets > p2Sets) Side.P1 else Side.P2
        val matchWinnerName = if (matchWinnerSide == Side.P1) p1 else p2
        output.println("Match winner: $matchWinnerName")

        repo.append(
            domain.model.MatchRecord(
                id = UUID.randomUUID().toString(),
                player1 = p1,
                player2 = p2,
                mode = mode,
                sets = sets,
                winner = matchWinnerName,
                createdAtEpochMillis = time.nowMillis()
            )
        )

        return sets to matchWinnerSide
    }
}
