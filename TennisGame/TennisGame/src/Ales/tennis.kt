package Ales

import Ales.Player.P1
import Ales.Player.P2

enum class Player(val id: String) {
    P1("Player 1"),
    P2("Player 2")
}

sealed class Score(val score: String) {
    abstract fun playerScores(player: Player) : Score
}

object LoveLove : Score("Love-Love") {
    override fun playerScores(player: Player) = FifteenLove(player)
}

class FifteenLove(private val leader: Player) : Score(formatScore(leader, "Fifteen", "Love")) {
    override fun playerScores(player: Player) = if (leader == player) ThirtyLove(leader) else FifteenAll
}

class ThirtyLove(private val leader: Player) : Score(formatScore(leader, "Thirty", "Love")) {
    override fun playerScores(player: Player) = if (leader == player) FortyLove(leader) else ThirtyFifteen(leader)
}

class FortyLove(private val leader: Player) : Score(formatScore(leader, "Forty", "Love")) {
    override fun playerScores(player: Player) = if (leader == player) GameOver(leader) else FortyFifteen(leader)
}

object FifteenAll : Score("Fifteen-Fifteen") {
    override fun playerScores(player: Player) = ThirtyFifteen(player)
}

class ThirtyFifteen(private val leader: Player) : Score(formatScore(leader, "Thirty", "Fifteen")) {
    override fun playerScores(player: Player) = if (leader == player) FortyFifteen(leader) else ThirtyAll
}

object ThirtyAll : Score("Thirty-Thirty") {
    override fun playerScores(player: Player) = FortyThirty(player)
}

class FortyFifteen(private val leader: Player) : Score(formatScore(leader, "Forty","Fifteen")) {
    override fun playerScores(player: Player) = if (leader == player) GameOver(leader) else FortyThirty(leader)
}

class FortyThirty(private val leader: Player) : Score(formatScore(leader, "Forty","Thirty")) {
    override fun playerScores(player: Player) = if (leader == player) GameOver(leader) else Deuce
}

object Deuce : Score("Deuce") {
    override fun playerScores(player: Player) = Advantage(player)
}

class Advantage(private val leader: Player) : Score("Advantage ${leader.id}") {
    override fun playerScores(player: Player) = if (leader == player) GameOver(leader) else Deuce
}

class GameOver(private val leader: Player) : Score("${leader.id} wins") {
    override fun playerScores(player: Player) = throw IllegalStateException("Game already finished")
}

private fun formatScore(leader: Player, higherScore: String, lowerScore: String) = when (leader) {
    P1 -> "$higherScore-$lowerScore"
    P2 -> "$lowerScore-$higherScore"
}
