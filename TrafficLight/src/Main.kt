package trafficlight

fun main() {
    val ctrl = TrafficLightController()
    var t = 0
    println("t=${t}s  -> ${ctrl.currentState.name} (${ctrl.timeToTransition}s remaining)")

    repeat(30) {
        ctrl.tick()
        t += 1
        println("t=${t}s -> ${ctrl.currentState.name} (${ctrl.timeToTransition}s remaining)")

        if (t == 8) {
            println("  [pedestrian] button pressed")
            ctrl.letPedestrianCross()
        }

        if (t == 22) {
            println("  [night-mode] flashing yellow ON")
            ctrl.toggleYellowFlashingMode()
        }
        if (t == 28) {
            println("  [night-mode] flashing yellow OFF")
            ctrl.toggleYellowFlashingMode()
        }
    }
}
