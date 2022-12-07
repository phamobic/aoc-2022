fun main() {

    fun part(input: List<String>, markerSize: Int): Int {
        val signal = input.first()
        val marker = mutableListOf<Char>()
        var count = 0

        signal.forEach { character ->
            count++

            marker.add(character)

            if (marker.size > markerSize) {
                marker.removeFirst()
            }

            if (marker.toSet().size == markerSize) {
                return count
            }
        }

        return count
    }

    fun part1(input: List<String>): Int = part(input = input, markerSize = 4)
    fun part2(input: List<String>): Int = part(input = input, markerSize = 14)

    val testInput1 = readInput("Day06_test_1")
    val testInput2 = readInput("Day06_test_2")
    val testInput3 = readInput("Day06_test_3")
    val testInput4 = readInput("Day06_test_4")
    val testInput5 = readInput("Day06_test_5")
    check(part1(testInput1) == 7)
    check(part1(testInput2) == 5)
    check(part1(testInput3) == 6)
    check(part1(testInput4) == 10)
    check(part1(testInput5) == 11)
    check(part2(testInput1) == 19)
    check(part2(testInput2) == 23)
    check(part2(testInput3) == 23)
    check(part2(testInput4) == 29)
    check(part2(testInput5) == 26)
}
