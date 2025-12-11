fun main() {
    val initialBoard0 = Board.fromInts(
        listOf(0, 1, 0, 1, 0, 1, 0, 0, 0),
        listOf(1, 0, 1, 0, 0, 1, 1, 0, 0),
        listOf(1, 0, 0, 1, 0, 0, 0, 0, 1),
        listOf(0, 1, 0, 1, 0, 0, 0, 0, 1)
    )

    val initialBoard = Board.fromString(
        """
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
            .XXX...........
            ...X...........
            ..X............
        """.trimIndent()
    )

    var game = GameOfLife(initialBoard)

    repeat(10) {
        game.print(aliveChar = "■", deadChar = "·")
        Thread.sleep(300)
        game = game.next()
    }
}
