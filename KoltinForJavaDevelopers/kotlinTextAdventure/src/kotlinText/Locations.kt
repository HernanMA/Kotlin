package com.kotlinText
import java.io.File

fun readLocationInfo() : Map<Int, Location> {

    val locations = mutableMapOf<Int, Location>()

    File("kotlinTextAdventure/src/kotlinText/texts/locations_big.txt").reader().forEachLine() {
        val tokens = it.split("`")

        val location = Location(tokens[0].toInt(), tokens[1])
        locations[location.locationID] = location
    }

    File("kotlinTextAdventure/src/kotlinText/texts/directions_big.txt").reader().forEachLine() {
        val tokens = it.split(",")

        locations[tokens[0].toInt()]?.addExit(tokens[1], tokens[2].toInt())
    }

    return locations
}
