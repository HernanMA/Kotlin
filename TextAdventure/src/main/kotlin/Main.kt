package org.example

fun main(args: Array<String>) {

    val locations = readLocationInfo()
    var loc = 64

    while (true) {

        val location = locations[loc] ?: Location(
            0,
            "Something went wrong, The game will terminate"
        )
        println(location.description)

        if (location.locationID == 0) {
            break
        }

        println("Available exits are: ")
        location.exits.keys.forEach {
            println("$it, ")
        }

        val direction = readlnOrNull()?.uppercase() ?: "Z"
        if (location.exits.containsKey(direction)) {
            loc = location.exits[direction]!!
        } else {
            println("Unknown direction")
        }
    }
}
