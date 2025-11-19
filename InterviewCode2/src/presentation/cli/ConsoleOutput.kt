package presentation.cli

import domain.ports.OutputPort

class ConsoleOutput : OutputPort {
    override fun println(msg: String) {
        kotlin.io.println(msg)
    }

    override fun header(title: String) {
        kotlin.io.println(title)
    }
}
