package domain.model

data class GameState(
    val p1: Point = Point.LOVE,
    val p2: Point = Point.LOVE,
    val deuce: Boolean = false,
    val finished: Boolean = false,
    val winner: Side? = null
)
