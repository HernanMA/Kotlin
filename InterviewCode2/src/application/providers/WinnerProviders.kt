package application.providers

import application.PointWinnerProvider
import domain.model.GameState
import domain.model.Side
import domain.ports.InputPort
import kotlin.random.Random

class RandomWinnerProvider : PointWinnerProvider {
    override fun next(state: GameState): Side =
        if (Random.nextBoolean()) Side.P1 else Side.P2
}

class PromptWinnerProvider(
    private val input: InputPort,
    private val p1: String,
    private val p2: String
) : PointWinnerProvider {
    override fun next(state: GameState): Side {
        val c = input.choice("Who wins the next point?", listOf(p1, p2))
        return if (c == 1) Side.P1 else Side.P2
    }
}
