import org.junit.Assert.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameOfLifeBoardTest {

    @Test
    fun `boardOfInts crea tablero con dimensiones correctas`() {
        val board = boardOfInts(
            listOf(0, 1, 0),
            listOf(1, 0, 1)
        )

        assertEquals(2, board.size)          // 2 filas
        assertEquals(3, board[0].size)       // 3 columnas
        assertEquals(3, board[1].size)       // también 3 columnas en la segunda fila
    }

    @Test
    fun `boardOfInts convierte 1 a true y 0 a false`() {
        val board = boardOfInts(
            listOf(0, 1),
            listOf(1, 0)
        )

        // Fila 0
        assertFalse(board[0][0]) // 0 -> false
        assertTrue(board[0][1])  // 1 -> true

        // Fila 1
        assertTrue(board[1][0])  // 1 -> true
        assertFalse(board[1][1]) // 0 -> false
    }

    @Test
    fun `initialBoard tiene el patrón esperado`() {
        // Tomamos el initialBoard definido en el archivo de producción
        // (asegúrate que sea visible desde test, p.e. 'val' top-level público)

        assertEquals(4, initialBoard.size)          // 4 filas
        assertEquals(9, initialBoard[0].size)       // 9 columnas

        // Ejemplo: verificamos algunas celdas específicas
        // Primera fila: [0,1,0,1,0,1,0,0,0]
        assertFalse(initialBoard[0][0])
        assertTrue(initialBoard[0][1])
        assertFalse(initialBoard[0][2])
        assertTrue(initialBoard[0][3])

        // Última fila: [0,1,0,1,0,0,0,0,1]
        assertFalse(initialBoard[3][0])
        assertTrue(initialBoard[3][1])
        assertTrue(initialBoard[3][3])
        assertTrue(initialBoard[3][8])
    }

    @Test
    fun `boardToString genera el string correcto`() {
        val board = boardOfInts(
            listOf(0, 1, 0),
            listOf(1, 0, 1)
        )

        val expected = """
            0 1 0
            1 0 1
        """.trimIndent()

        val result = boardToString(board)

        assertEquals(expected, result)
    }
}
