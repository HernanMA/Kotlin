package infrastructure

import domain.model.MatchRecord
import infrastructure.store.MatchRepositoryInMemory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MatchRepositoryInMemoryTest {
    @Test fun `append y readAll funcionan`() {
        val repo = MatchRepositoryInMemory()
        val match = MatchRecord("1", "A", "B", "TEST", emptyList(), "A", 1L)
        repo.append(match)

        val all = repo.readAll()
        assertEquals(1, all.size)
        assertEquals("A", all.first().winner)
    }
}
