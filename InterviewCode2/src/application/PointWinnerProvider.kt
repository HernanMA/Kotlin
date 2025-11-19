package application

import domain.model.GameState
import domain.model.Side


fun interface PointWinnerProvider {
    fun next(state: GameState): Side
}
