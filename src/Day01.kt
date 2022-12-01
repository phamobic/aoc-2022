fun main() {
    fun insertCaloriesToSortedMax(topMaxCalories: MutableList<Long>, calories: Long) {
        val index = topMaxCalories.indexOfFirst { calories > it }

        if (index < 0) return

        topMaxCalories.add(index, calories)
        topMaxCalories.removeLast()
    }

    fun getMaxCaloriesSum(numberOfElves: Int, caloriesInput: List<String>): Long {
        val topMaxCalories = MutableList(numberOfElves) { 0L }
        var currentElfCalories = 0L

        caloriesInput.forEach { line ->
            if (line.isBlank()) {
                insertCaloriesToSortedMax(topMaxCalories, currentElfCalories)
                currentElfCalories = 0L
            } else {
                currentElfCalories += line.toLong()
            }
        }
        insertCaloriesToSortedMax(topMaxCalories, currentElfCalories)

        return topMaxCalories.sum()
    }

    fun part1(input: List<String>): Long = getMaxCaloriesSum(1, input)

    fun part2(input: List<String>): Long = getMaxCaloriesSum(3, input)

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000L)
    check(part2(testInput) == 45000L)
}
