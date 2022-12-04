private const val RANGES_DELIMITER = ","
private const val START_END_DELIMITER = "-"

private fun getParsedRanges(rangesText: String): List<IntRange> =
    rangesText.split(RANGES_DELIMITER)
        .map { it.split(START_END_DELIMITER) }
        .map { it.first().toInt()..it.last().toInt() }

private fun IntRange.contains(range: IntRange): Boolean =
    contains(range.first) && contains(range.last)

private fun IntRange.overlapWith(range: IntRange): Boolean =
    contains(range.first) || contains(range.last) || range.contains(first) || range.contains(last)

fun main() {

    fun part1(input: List<String>): Int {
        var count = 0

        input.forEach { line ->
            val ranges = getParsedRanges(line)

            val firstRange = ranges.first()
            val secondRange = ranges.last()

            if (firstRange.contains(secondRange) || secondRange.contains(firstRange)) {
                count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0

        input.forEach { line ->
            val ranges = getParsedRanges(line)

            val firstRange = ranges.first()
            val secondRange = ranges.last()

            if (firstRange.overlapWith(secondRange)) {
                count++
            }
        }

        return count
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)
}
