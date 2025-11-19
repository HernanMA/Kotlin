package domain.ports

interface InputPort {
    fun line(label: String): String
    fun intInRange(label: String, range: IntRange): Int
    fun choice(label: String, options: List<String>): Int
    fun confirm(label: String): Boolean
}
