package domain.ports

interface OutputPort {
    fun println(msg: String)
    fun header(title: String)
}
