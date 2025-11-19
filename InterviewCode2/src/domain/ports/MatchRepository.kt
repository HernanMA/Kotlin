package domain.ports

import domain.model.MatchRecord

interface MatchRepository {
    fun append(match: MatchRecord)
    fun readAll(): List<MatchRecord>
}
