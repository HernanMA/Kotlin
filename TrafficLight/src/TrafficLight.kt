package trafficlight

class TrafficLightController {
    private var _currentState: State = Red
    private var _timeToTransition = _currentState.duration
    val currentState: State get() = _currentState
    val timeToTransition: Int get() = _timeToTransition

    fun tick() {
        if (--_timeToTransition == 0) {
            switchTo(_currentState.next)
        }
    }

    fun reset(): Unit = switchTo(Red)

    fun toggleYellowFlashingMode() {
        if (_currentState is FlashingYellowMode) {
            switchTo(Red)
        } else {
            switchTo(FlashingYellowOn)
        }
    }

    fun letPedestrianCross() {
        if (_currentState is Green) switchTo(Yellow)
    }

    private fun switchTo(state: State) {
        _currentState = state
        _timeToTransition = _currentState.duration
    }
}

sealed class State {
    abstract val duration: Int
    abstract val name: String
    abstract val next: State
}

object Red : State() {
    override val duration = 10
    override val name = "RED"
    override val next = Green
}

object Green : State() {
    override val duration = 7
    override val name = "GREEN"
    override val next = Yellow
}

object Yellow : State() {
    override val duration = 3
    override val name = "YELLOW"
    override val next = Red
}

sealed class FlashingYellowMode : State()

object FlashingYellowOn : FlashingYellowMode() {
    override val duration = 1
    override val name = "YELLOW_FLASH_ON"
    override val next = FlashingYellowOff
}

object FlashingYellowOff : FlashingYellowMode() {
    override val duration = 1
    override val name = "YELLOW_FLASH_OFF"
    override val next = FlashingYellowOn
}
