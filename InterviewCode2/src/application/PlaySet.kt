package application

import domain.model.GameState
import domain.model.SetSummary
import domain.model.Side
import domain.ports.NarrationPort
import domain.ports.OutputPort
import domain.scoring.ScoreEvent
import domain.scoring.ScoreFormatter
import domain.scoring.ScoringEngine

class PlaySet(
    private val engine: ScoringEngine,
    private val narrator: NarrationPort,
    private val output: OutputPort
) {
    fun execute(p1: String, p2: String, provider: PointWinnerProvider): Pair<SetSummary, Side> {
        var state = GameState()
        val timeline = mutableListOf<String>()

        output.println(narrator.setHeader())

        while (!state.finished) {
            val winner = provider.next(state)
            val (newState, event: ScoreEvent) = engine.next(state, winner)
            state = newState

            val who = if (winner == Side.P1) p1 else p2
            val label = ScoreFormatter.label(state)
            output.println("  $who wins a point → $label")

            val nline = narrator.lineFor(state, p1, p2, event)
            timeline += "Point: $who → $label | $nline"
        }

        val wSide = state.winner!!
        val wName = if (wSide == Side.P1) p1 else p2
        output.println("  $wName wins the set")

        val summary = SetSummary(
            winner = wName,
            pointTimeline = timeline,
            finalScoreLabel = ScoreFormatter.label(state)
        )
        return summary to wSide
    }
}
