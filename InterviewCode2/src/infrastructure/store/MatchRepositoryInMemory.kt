package infrastructure.store

import domain.model.MatchRecord
import domain.ports.MatchRepository
import java.util.concurrent.CopyOnWriteArrayList

class MatchRepositoryInMemory : MatchRepository {
    private val matches = CopyOnWriteArrayList<MatchRecord>()

    override fun append(match: MatchRecord) {
        matches += match
    }

    override fun readAll(): List<MatchRecord> = matches.toList()
}
