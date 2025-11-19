package Ales

fun main(args: Array<String>) {
    args.map {
        Player.valueOf(it)
    }.fold(listOf<Score>(LoveLove)) { scores, nextPointWinner ->
        scores + scores.last().playerScores(nextPointWinner)
    }.forEach {
        println(it.score)
    }
}
