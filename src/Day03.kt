fun main() {
    val alphabetLetters = "abcdefghijklmnopqrstuvwxyz"
    val alphabet = ("-$alphabetLetters${alphabetLetters.uppercase()}").toSet()

    fun part1(input: List<String>): Int {
        var sum = 0

        input.forEach { line ->
            val middleIndex = line.length / 2
            val firstRucksack = line.substring(0, middleIndex).toSet()
            val secondRucksack = line.substring(middleIndex).toSet()

            val sharedChar = firstRucksack.intersect(secondRucksack).first()

            sum += alphabet.indexOf(sharedChar)
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val groups = input.chunked(3)

        groups.forEach { rucksacks ->
            val firstRucksack = rucksacks[0].toSet()
            val secondRucksack = rucksacks[1].toSet()
            val thirdRucksack = rucksacks[2].toSet()

            val sharedChar = firstRucksack.intersect(secondRucksack).intersect(thirdRucksack).first()

            sum += alphabet.indexOf(sharedChar)
        }

        return sum
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)
}
