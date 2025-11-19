package bootstrap

import application.PlayMatch
import application.PlaySet
import application.SystemTimeProvider
import application.providers.PromptWinnerProvider
import application.providers.RandomWinnerProvider
import domain.ports.MatchRepository
import infrastructure.store.MatchRepositoryInMemory
import menu.ContinuationManager
import menu.ContinueAction
import presentation.cli.*

fun main() {
    val input = ConsoleInput()
    val output = ConsoleOutput()
    val repo: MatchRepository = MatchRepositoryInMemory()
    val narrator = ConsoleNarrator()
    val engine = domain.scoring.ScoringEngine()
    val playSet = PlaySet(engine, narrator, output)
    val playMatch = PlayMatch(playSet, repo, output, SystemTimeProvider)
    val cont = ContinuationManager(input)
    val players = PlayerPrompter(input)

    loop@ while (true) {
        output.println("")
        output.println("=== Tennis Menu ===")
        val choice = input.choice(
            "Select an option",
            listOf(
                "Random mode: play a match (best-of: 1/3/5)",
                "Manual mode: play a match (best-of: 1/3/5)",
                "View stored matches",
                "Exit"
            )
        )
        when (choice) {
            1 -> {
                val (p1, p2) = players.askBothPlayers()
                val bestOf = askBestOf(input)
                val provider = RandomWinnerProvider()
                playMatch.execute(p1, p2, bestOf, provider, "RANDOM")
                while (cont.afterMatch() == ContinueAction.PLAY_AGAIN) {
                    playMatch.execute(p1, p2, bestOf, provider, "RANDOM")
                }
            }
            2 -> {
                val (p1, p2) = players.askBothPlayers()
                val bestOf = askBestOf(input)
                val provider = PromptWinnerProvider(input, p1, p2)
                playMatch.execute(p1, p2, bestOf, provider, "MANUAL")
                while (cont.afterMatch() == ContinueAction.PLAY_AGAIN) {
                    playMatch.execute(p1, p2, bestOf, provider, "MANUAL")
                }
            }
            3 -> {
                val records = repo.readAll()
                PrettyPrinter.printMatches(records)
            }
            4 -> {
                output.println("Goodbye.")
                break@loop
            }
        }
    }
}

private fun askBestOf(input: domain.ports.InputPort): Int {
    while (true) {
        val n = input.intInRange("Best of how many sets? (1, 3 or 5)", 1..5)
        if (n == 1 || n == 3 || n == 5) return n
        println("Please choose 1, 3 or 5.")
    }
}
