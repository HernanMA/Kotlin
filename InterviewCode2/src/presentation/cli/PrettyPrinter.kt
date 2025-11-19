package presentation.cli

import domain.model.MatchRecord

object PrettyPrinter {
    fun printMatches(records: List<MatchRecord>) {
        if (records.isEmpty()) {
            println("No matches stored yet.")
            return
        }
        records.forEachIndexed { i, r ->
            val p1 = r.player1
            val p2 = r.player2
            val p1Sets = r.sets.count { it.winner == p1 }
            val p2Sets = r.sets.count { it.winner == p2 }

            println("Match ${i + 1}: $p1 vs $p2  [mode: ${r.mode}]")
            println("  Sets: $p1Sets - $p2Sets  (winner: ${r.winner})")

            val lastSet = r.sets.lastOrNull()
            if (lastSet != null) {
                println("  Last set: ${lastSet.winner} won")
                lastSet.pointTimeline.takeLast(3).forEach { line ->
                    println("    $line")
                }
                println("  Final label: ${lastSet.finalScoreLabel}")
            }
            println("-".repeat(60))
        }
    }
}
