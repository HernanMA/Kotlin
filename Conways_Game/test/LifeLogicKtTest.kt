import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CellTest {

    @Test
    fun `fromInt devuelve ALIVE para 1`() {
        val result = Cell.fromInt(1)
        assertEquals(Cell.ALIVE, result)
    }

    @Test
    fun `fromInt devuelve DEAD para 0`() {
        val result = Cell.fromInt(0)
        assertEquals(Cell.DEAD, result)
    }

    @Test
    fun `isAlive es true para ALIVE`() {
        assertTrue(Cell.ALIVE.isAlive())
    }

    @Test
    fun `isAlive es false para DEAD`() {
        assertFalse(Cell.DEAD.isAlive())
    }
}

class BoardCreationTest {

    @Test
    fun `fromInts establece correctamente el numero de filas`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(1, 0, 1)
        )

        assertEquals(2, board.numberOfRows)
    }

    @Test
    fun `fromInts establece correctamente el numero de columnas`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(1, 0, 1)
        )

        assertEquals(3, board.numberOfColumns)
    }

    @Test
    fun `fromInts convierte 1 en celda viva`() {
        val board = Board.fromInts(
            listOf(0, 1)
        )

        val cell = board[0, 1]
        assertTrue(cell.isAlive())
    }

    @Test
    fun `fromInts convierte 0 en celda muerta`() {
        val board = Board.fromInts(
            listOf(0, 1)
        )

        val cell = board[0, 0]
        assertFalse(cell.isAlive())
    }
}

class BoardAccessTest {

    @Test
    fun `get devuelve la celda correcta en una posicion`() {
        val board = Board.fromInts(
            listOf(0, 1),
            listOf(1, 0)
        )

        val cell = board[1, 0]
        assertTrue(cell.isAlive())
    }

    @Test
    fun `numberOfRows refleja el tamano de la lista de filas`() {
        val cells = listOf(
            listOf(Cell.ALIVE),
            listOf(Cell.DEAD),
            listOf(Cell.ALIVE)
        )
        val board = Board(cells)

        assertEquals(3, board.numberOfRows)
    }

    @Test
    fun `numberOfColumns refleja el tamano de la primera fila`() {
        val cells = listOf(
            listOf(Cell.ALIVE, Cell.DEAD, Cell.ALIVE)
        )
        val board = Board(cells)

        assertEquals(3, board.numberOfColumns)
    }
}

class BoardDisplayTest {

    @Test
    fun `toDisplayString usa los caracteres por defecto 1 y 0`() {
        val board = Board.fromInts(
            listOf(0, 1),
            listOf(1, 0)
        )

        val text = board.toDisplayString()

        val expected = """
            0 1
            1 0
        """.trimIndent()

        assertEquals(expected, text)
    }

    @Test
    fun `toDisplayString usa los caracteres personalizados`() {
        val board = Board.fromInts(
            listOf(0, 1),
            listOf(1, 0)
        )

        val text = board.toDisplayString(aliveChar = "X", deadChar = ".")

        val expected = """
            . X
            X .
        """.trimIndent()

        assertEquals(expected, text)
    }

    @Test
    fun `toDisplayString separa filas con saltos de linea`() {
        val board = Board.fromInts(
            listOf(0),
            listOf(1),
            listOf(0)
        )

        val text = board.toDisplayString()

        // Debe contener exactamente 3 lineas
        val lines = text.lines()
        assertEquals(3, lines.size)
    }

    @Test
    fun `toDisplayString separa celdas con espacios`() {
        val board = Board.fromInts(
            listOf(0, 1, 0)
        )

        val text = board.toDisplayString()
        // Primera linea -> "0 1 0"
        assertEquals("0 1 0", text.lines().first())
    }
}

class BoardRulesTest {

    @Test
    fun `celda viva con 0 vecinos vivos muere por soledad`() {
        val board = Board.fromInts(
            listOf(0, 0, 0),
            listOf(0, 1, 0),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertFalse(next[1, 1].isAlive())
    }

    @Test
    fun `celda viva con 1 vecino vivo muere por soledad`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(0, 1, 0),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertFalse(next[1, 1].isAlive())
    }

    @Test
    fun `celda viva con 2 vecinos vivos sobrevive`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(0, 1, 1),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertTrue(next[1, 1].isAlive())
    }

    @Test
    fun `celda viva con 3 vecinos vivos sobrevive`() {
        val board = Board.fromInts(
            listOf(1, 1, 0),
            listOf(0, 1, 1),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertTrue(next[1, 1].isAlive())
    }

    @Test
    fun `celda viva con 4 vecinos vivos muere por sobrepoblacion`() {
        val board = Board.fromInts(
            listOf(1, 1, 1),
            listOf(0, 1, 1),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertFalse(next[1, 1].isAlive())
    }

    @Test
    fun `celda muerta con exactamente 3 vecinos vivos nace`() {
        val board = Board.fromInts(
            listOf(1, 1, 0),
            listOf(0, 0, 1),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertTrue(next[1, 1].isAlive())
    }

    @Test
    fun `celda muerta con 2 vecinos vivos permanece muerta`() {
        val board = Board.fromInts(
            listOf(1, 0, 0),
            listOf(0, 0, 1),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertFalse(next[1, 1].isAlive())
    }

    @Test
    fun `tablero totalmente vacio permanece vacio`() {
        val board = Board.fromInts(
            listOf(0, 0, 0),
            listOf(0, 0, 0),
            listOf(0, 0, 0)
        )

        val next = board.next()

        assertEquals(board, next)
    }

    @Test
    fun `conteo de vecinos respeta los limites del tablero`() {
        val board = Board.fromInts(
            listOf(1, 1),
            listOf(1, 0)
        )

        // La celda (0,0) tiene 2 vecinos vivos (0,1) y (1,0)
        val next = board.next()

        // Regla: viva con 2 vecinos -> sobrevive
        assertTrue(next[0, 0].isAlive())
    }
}

class BoardPatternsTest {

    @Test
    fun `bloque 2x2 es un still life`() {
        val block = Board.fromInts(
            listOf(0, 0, 0, 0),
            listOf(0, 1, 1, 0),
            listOf(0, 1, 1, 0),
            listOf(0, 0, 0, 0)
        )

        val next = block.next()

        assertEquals(block, next)
    }

    @Test
    fun `blinker horizontal se convierte en blinker vertical en una generacion`() {
        val horizontal = Board.fromInts(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 1, 1, 1, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0)
        )

        val vertical = Board.fromInts(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 0, 0, 0)
        )

        val next = horizontal.next()

        assertEquals(vertical, next)
    }

    @Test
    fun `blinker vertical se convierte en blinker horizontal en una generacion`() {
        val vertical = Board.fromInts(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 0, 0, 0)
        )

        val horizontal = Board.fromInts(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 1, 1, 1, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0)
        )

        val next = vertical.next()

        assertEquals(horizontal, next)
    }
}

class GameOfLifeTest {

    @Test
    fun `la generacion inicial es cero`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(0, 1, 0),
            listOf(0, 1, 0)
        )

        val game = GameOfLife(board)

        assertEquals(0, game.generation)
    }

    @Test
    fun `next incrementa la generacion en uno`() {
        val board = Board.fromInts(
            listOf(0, 1, 0),
            listOf(0, 1, 0),
            listOf(0, 1, 0)
        )

        val game = GameOfLife(board)
        val nextGame = game.next()

        assertEquals(1, nextGame.generation)
    }

    @Test
    fun `next actualiza el tablero a la siguiente generacion`() {
        val horizontalBlinker = Board.fromInts(
            listOf(0, 0, 0),
            listOf(1, 1, 1),
            listOf(0, 0, 0)
        )

        val verticalBlinker = Board.fromInts(
            listOf(0, 1, 0),
            listOf(0, 1, 0),
            listOf(0, 1, 0)
        )

        val game = GameOfLife(horizontalBlinker)
        val nextGame = game.next()

        assertEquals(verticalBlinker, nextGame.board)
    }
}
