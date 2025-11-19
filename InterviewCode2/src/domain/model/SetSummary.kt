package domain.model

data class SetSummary(
    val winner: String,
    val pointTimeline: List<String>,
    val finalScoreLabel: String
)
