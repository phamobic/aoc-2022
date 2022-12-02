private enum class RoundEnd(
    val score: Int
) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    fun getOppositeEnd(): RoundEnd = when (this) {
        LOSE -> WIN
        DRAW -> DRAW
        WIN -> LOSE
    }
}

private sealed class Shape {
    abstract val score: Int
    abstract val opponentShapeToRoundEndMap: Map<Shape, RoundEnd>

    object ROCK : Shape() {
        override val score: Int = 1

        override val opponentShapeToRoundEndMap: Map<Shape, RoundEnd> = mapOf(
            PAPER to RoundEnd.LOSE,
            ROCK to RoundEnd.DRAW,
            SCISSORS to RoundEnd.WIN,
        )
    }

    object PAPER : Shape() {
        override val score: Int = 2

        override val opponentShapeToRoundEndMap: Map<Shape, RoundEnd> = mapOf(
            PAPER to RoundEnd.DRAW,
            ROCK to RoundEnd.WIN,
            SCISSORS to RoundEnd.LOSE,
        )
    }

    object SCISSORS : Shape() {
        override val score: Int = 3

        override val opponentShapeToRoundEndMap: Map<Shape, RoundEnd> = mapOf(
            PAPER to RoundEnd.WIN,
            ROCK to RoundEnd.LOSE,
            SCISSORS to RoundEnd.DRAW,
        )
    }

    fun getRoundEndAgainst(shape: Shape): RoundEnd =
        opponentShapeToRoundEndMap[shape] ?: throw IllegalArgumentException()

    fun getOpponentShapeWithEnd(roundEnd: RoundEnd): Shape =
        opponentShapeToRoundEndMap.entries.find { it.value == roundEnd }?.key
            ?: throw IllegalArgumentException()
}

private fun Char.toOpponentShape(): Shape = when (this) {
    'A' -> Shape.ROCK
    'B' -> Shape.PAPER
    'C' -> Shape.SCISSORS
    else -> throw IllegalArgumentException()
}

private fun Char.toMyShape(): Shape = when (this) {
    'X' -> Shape.ROCK
    'Y' -> Shape.PAPER
    'Z' -> Shape.SCISSORS
    else -> throw IllegalArgumentException()
}

private fun Char.toRoundEnd(): RoundEnd = when (this) {
    'X' -> RoundEnd.LOSE
    'Y' -> RoundEnd.DRAW
    'Z' -> RoundEnd.WIN
    else -> throw IllegalArgumentException()
}

fun main() {
    fun part1(input: List<String>): Int {
        var score = 0

        input.forEach { line ->
            val opponentShape = line.first().toOpponentShape()
            val myShape = line.last().toMyShape()
            val roundEnd = myShape.getRoundEndAgainst(opponentShape)

            score += roundEnd.score + myShape.score
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        input.forEach { line ->
            val opponentShape = line.first().toOpponentShape()
            val roundEndForMe = line.last().toRoundEnd()
            val roundEndForOpponent = roundEndForMe.getOppositeEnd()
            val myShape = opponentShape.getOpponentShapeWithEnd(roundEndForOpponent)

            score += roundEndForMe.score + myShape.score
        }

        return score
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)
}
