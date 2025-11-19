import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import trafficlight.*

class TrafficLightControllerTest {
    val ctrl = TrafficLightController()


    // --- Helpers ---
    private fun advance(ctrl: TrafficLightController, ticks: Int) {
        repeat(ticks) { ctrl.tick() }
    }

    @Test
    fun `starts in RED with 10 seconds`() {
        assertEquals(Red, ctrl.currentState)
        assertEquals(10, ctrl.timeToTransition)
    }

    @Test
    fun `tick decreases timer and switches state when reaching zero`() {
        // RED 10 -> after 9 ticks it should be 1 second remaining
        advance(ctrl, 9)
        assertEquals(Red, ctrl.currentState)
        assertEquals(1, ctrl.timeToTransition)

        // next tick: switches to GREEN with 7 seconds
        advance(ctrl, 1)
        assertEquals(Green, ctrl.currentState)
        assertEquals(7, ctrl.timeToTransition)
    }

    @Test
    fun `completes full RED - GREEN - YELLOW - RED cycle with expected durations`() {
        // RED (10 ticks)
        advance(ctrl, 10)
        assertEquals(Green, ctrl.currentState)
        assertEquals(7, ctrl.timeToTransition)

        // GREEN (7 ticks)
        advance(ctrl, 7)
        assertEquals(Yellow, ctrl.currentState)
        assertEquals(3, ctrl.timeToTransition)

        // YELLOW (3 ticks)
        advance(ctrl, 3)
        assertEquals(Red, ctrl.currentState)
        assertEquals(10, ctrl.timeToTransition)
    }

    @Test
    fun `reset returns to RED and restores timer`() {
        advance(ctrl, 12) // reach GREEN
        assertEquals(Green, ctrl.currentState)

        ctrl.reset()
        assertEquals(Red, ctrl.currentState)
        assertEquals(10, ctrl.timeToTransition)
    }

    @Test
    fun `letPedestrianCross only has effect when GREEN`() {

        // In RED: should do nothing
        ctrl.letPedestrianCross()
        assertEquals(Red, ctrl.currentState)
        assertEquals(10, ctrl.timeToTransition)

        // Move to GREEN
        advance(ctrl, 10)
        assertEquals(Green, ctrl.currentState)
        assertEquals(7, ctrl.timeToTransition)

        // In GREEN: should immediately switch to YELLOW with 3 seconds
        ctrl.letPedestrianCross()
        assertEquals(Yellow, ctrl.currentState)
        assertEquals(3, ctrl.timeToTransition)
    }

    @Test
    fun `toggleYellowFlashingMode enables flashing and alternates ON OFF each tick`() {
        // Enable flashing from RED
        ctrl.toggleYellowFlashingMode()
        assertTrue(ctrl.currentState is FlashingYellowMode)
        assertEquals(1, ctrl.timeToTransition)
        assertEquals(FlashingYellowOn, ctrl.currentState)

        // Alternate 4 steps: ON -> OFF -> ON -> OFF
        advance(ctrl, 1)
        assertEquals(FlashingYellowOff, ctrl.currentState)
        advance(ctrl, 1)
        assertEquals(FlashingYellowOn, ctrl.currentState)
        advance(ctrl, 1)
        assertEquals(FlashingYellowOff, ctrl.currentState)
        advance(ctrl, 1)
        assertEquals(FlashingYellowOn, ctrl.currentState)
    }

    @Test
    fun `toggleYellowFlashingMode disables flashing and returns to RED`() {
        ctrl.toggleYellowFlashingMode() // ON
        assertTrue(ctrl.currentState is FlashingYellowMode)

        ctrl.toggleYellowFlashingMode() // OFF
        assertEquals(Red, ctrl.currentState)
        assertEquals(10, ctrl.timeToTransition)
    }

    @Test
    fun `state durations are as expected`() {
        assertEquals(10, Red.duration)
        assertEquals(7, Green.duration)
        assertEquals(3, Yellow.duration)
        assertEquals(1, FlashingYellowOn.duration)
        assertEquals(1, FlashingYellowOff.duration)
    }

    @Test
    fun `state next references are correct in normal mode`() {
        assertEquals(Green, Red.next)
        assertEquals(Yellow, Green.next)
        assertEquals(Red, Yellow.next)
    }

    @Test
    fun `switching state resets timeToTransition to new state's duration`() {

        // from RED (10) after 10 ticks -> GREEN (7)
        advance(ctrl, 10)
        assertEquals(Green, ctrl.currentState)
        assertEquals(7, ctrl.timeToTransition)

        // after 7 ticks -> YELLOW (3)
        advance(ctrl, 7)
        assertEquals(Yellow, ctrl.currentState)
        assertEquals(3, ctrl.timeToTransition)
    }
}
