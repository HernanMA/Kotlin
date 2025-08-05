import Math.sum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MainKtTest {
    @Test
    fun name() {
        var x = sum(1.0, 4.0)
        assertEquals(5.0, x)
    }
}