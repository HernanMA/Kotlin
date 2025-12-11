// STEP 1 Board representation
typealias Board = List<List<Boolean>>

// STEP 2 Helper to create a Board using 0 and 1
fun boardOfInts(vararg rows: List<Int>): Board {
    return rows.map { row ->
        row.map { it == 1 } // 1 = vivo, 0 = muerto
    }
}

// STEP 3 Example starting board
val initialBoard: Board = boardOfInts(
    listOf(0,1,0,1,0,1,0,0,0),
    listOf(1,0,1,0,0,1,1,0,0),
    listOf(1,0,0,1,0,0,0,0,1),
    listOf(0,1,0,1,0,0,0,0,1)
)

// STEP 4 Converts the board to a String
fun boardToString(board: Board): String {
    return board.joinToString("\n") { row ->
        row.joinToString(" ") { cell ->
            if (cell) "X" else "."
        }
    }
}

// STEP 5 Print the dashboard to the console
fun printBoard(board: Board) {
    println(boardToString(board))
    println()
}

// ----------------------
// STEP 6: Count neighbors
// ----------------------
fun countAliveNeighbors(board: Board, row: Int, col: Int): Int {
    val rows = board.size
    val cols = board[0].size

    var count = 0

    // We traverse the 3x3 square around (row, col)
    for (dr in -1..1) {
        for (dc in -1..1) {
            // Saltar la propia celda
            if (dr == 0 && dc == 0) continue

            val nr = row + dr
            val nc = col + dc

            // Check that it is inside the board
            if (nr in 0 until rows && nc in 0 until cols) {
                if (board[nr][nc]) {
                    count++
                }
            }
        }
    }

    return count
}

// --------------------------
// STEP 7: cell rule
// --------------------------
fun nextCellState(isAlive: Boolean, aliveNeighbors: Int): Boolean {
    return if (isAlive) {
        // A living cell survives if it has 2 or 3 living neighbors
        aliveNeighbors == 2 || aliveNeighbors == 3
    } else {
        // A dead cell is created if it has exactly 3 living neighbors
        aliveNeighbors == 3
    }
}

// -------------------------------------
// STEP 8: Generate the following board
// -------------------------------------
fun nextBoard(board: Board): Board {
    val rows = board.size
    val cols = board[0].size

    return List(rows) { r ->
        List(cols) { c ->
            val aliveNeighbors = countAliveNeighbors(board, r, c)
            val current = board[r][c]
            nextCellState(current, aliveNeighbors)
        }
    }
}
