package presentation.cli

import domain.ports.InputPort


class ConsoleInput : InputPort {
    override fun line(label: String): String {
        while (true) {
            print("$label: ")
            val line = readlnOrNull()?.trim()
            if (!line.isNullOrEmpty()) return line
            println("Please enter a non-empty value.")
        }
    }

    override fun intInRange(label: String, range: IntRange): Int {
        while (true) {
            print("$label ${range.first}-${range.last}: ")
            val n = readlnOrNull()?.trim()?.toIntOrNull()
            if (n != null && n in range) return n
            println("Invalid number.")
        }
    }

    override fun choice(label: String, options: List<String>): Int {
        println(label)
        options.forEachIndexed { i, opt -> println("${i + 1}) $opt") }
        return intInRange("Choose", 1..options.size)
    }

    override fun confirm(label: String): Boolean {
        while (true) {
            print("$label [y/n]: ")
            when (readlnOrNull()?.trim()?.lowercase()) {
                "y", "yes" -> return true
                "n", "no"  -> return false
            }
            println("Answer with y or n.")
        }
    }
}
