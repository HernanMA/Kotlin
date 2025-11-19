package domain.model

data class MatchRecord(
    val id: String,
    val player1: String,
    val player2: String,
    val mode: String,
    val sets: List<SetSummary>,
    val winner: String,
    val createdAtEpochMillis: Long
)
