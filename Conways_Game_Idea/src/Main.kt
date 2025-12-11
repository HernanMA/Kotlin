fun main() {
    var board = initialBoard

    repeat(10) { gen ->
        println("Generación $gen")
        printBoard(board)

        board = nextBoard(board)

        // Opcional: pequeña pausa para ver la "animación"
        Thread.sleep(300)
    }
}
