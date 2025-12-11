    // -----------------------------
    // Basic domain: the cell
    // -----------------------------
enum class Cell {
        DEAD,
        ALIVE;

        fun isAlive(): Boolean = this == ALIVE


        companion object {

            fun fromChar(c: Char): Cell {
                return when (c) {
                    'X' -> ALIVE
                    else -> DEAD
                }
            }

            fun fromInt(value: Int): Cell =
                if (value == 1) ALIVE else DEAD
        }
    }

    // -----------------------------------
    // Domain: the board (global state)
    // -----------------------------------
data class Board(val cells: List<List<Cell>>) {

    val numberOfRows: Int get() = cells.size
    val numberOfColumns: Int get() = if (cells.isNotEmpty()) cells[0].size else 0

    operator fun get(rowIndex: Int, colIndex: Int): Cell = cells[rowIndex][colIndex]

    fun toDisplayString(aliveChar: String = "1", deadChar: String = "0"): String =
        cells.joinToString("\n") { row ->
            row.joinToString(" ") { cell ->
                if (cell.isAlive()) aliveChar else deadChar
            }
        }

    fun print(aliveChar: String = "1", deadChar: String = "0") {
        println(toDisplayString(aliveChar, deadChar))
        println()
    }

    //
    // Generate the next generation of the board by applying the rules of the game.
    //
    fun next(): Board {
        val nextGenerationCells = List(numberOfRows) { rowIndex ->
            List(numberOfColumns) { colIndex ->
                val currentCell = this[rowIndex, colIndex]
                val aliveNeighborCount = countAliveNeighbors(rowIndex, colIndex)
                determineNextCellState(currentCell, aliveNeighborCount)
            }
        }

        return Board(nextGenerationCells)
    }

    // -----------------------------
    // Internal logic of the board
    // -----------------------------
    private fun countAliveNeighbors(rowIndex: Int, colIndex: Int): Int {
        var aliveNeighbors = 0

        for (deltaRow in -1..1) {
            for (deltaCol in -1..1) {

                // Ignore current cell
                if (deltaRow == 0 && deltaCol == 0) continue

                val neighborRow = rowIndex + deltaRow
                val neighborCol = colIndex + deltaCol

                // Verification of limits
                if (neighborRow in 0 until numberOfRows &&
                    neighborCol in 0 until numberOfColumns &&
                    this[neighborRow, neighborCol].isAlive()
                ) {
                    aliveNeighbors++
                }
            }
        }

        return aliveNeighbors
    }

    private fun determineNextCellState(cell: Cell, aliveNeighborCount: Int): Cell =
        if (cell.isAlive()) {
            if (aliveNeighborCount == 2 || aliveNeighborCount == 3) Cell.ALIVE else Cell.DEAD
        } else {
            if (aliveNeighborCount == 3) Cell.ALIVE else Cell.DEAD
        }

    companion object {
        fun fromInts(vararg rows: List<Int>): Board =
            Board(
                rows.map { row ->
                    row.map(Cell::fromInt)
                }
            )

        fun fromString(rows: String): Board {
            val cells: List<List<Cell>> = rows.split("\n").map { row: String ->
                row.map(Cell::fromChar)
            }

            return Board(cells)
        }
    }
}

data class GameOfLife(
    val board: Board,
    val generation: Int = 0
) {

    fun next(): GameOfLife =
        copy(board = board.next(), generation = generation + 1)

    fun print(aliveChar: String = "1", deadChar: String = "0") {
        println("Generación $generation")
        board.print(aliveChar, deadChar)
    }
}
