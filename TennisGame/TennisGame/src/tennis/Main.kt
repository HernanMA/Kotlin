package tennis

fun main(args: Array<String>) {
    val finalGame: TennisGame =
        args
            .map { Player.valueOf(it) } // espera valores "P1" o "P2"
            .fold(TennisGame()) { g, p -> g.playerScores(p) }

    println(finalGame.describeScores())
}
