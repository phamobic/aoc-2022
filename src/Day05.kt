import java.util.Stack

private const val CRATE_DELIMITER = " "
private const val CRATE_START_MARK = '['
private const val SPACES_IN_CRATE_PLACE = 4

private const val COMMAND_DELIMITER = " "
private const val COMMAND_MOVE = "move"
private const val COMMAND_FROM = "from"
private const val COMMAND_TO = "to"

private fun List<Stack<Char>>.moveCrates(
    numberOfCrates: Int,
    from: Int,
    to: Int,
    inSingleBlock: Boolean,
) {
    if (inSingleBlock) {
        val tempStack = Stack<Char>()

        repeat(numberOfCrates) {
            val stack = this[from].pop()
            tempStack.push(stack)
        }

        repeat(numberOfCrates) {
            val stack = tempStack.pop()
            this[to].push(stack)
        }
    } else {
        repeat(numberOfCrates) {
            val stack = this[from].pop()
            this[to].push(stack)
        }
    }
}

fun main() {

    fun part(input: List<String>, inSingleBlock: Boolean): String {
        val stacks = mutableListOf<Stack<Char>>()
        val lineIterator = input.listIterator()

        // Init stacks
        while (lineIterator.hasNext()) {
            val line = lineIterator.next()
            val cratesRow = line.split(CRATE_DELIMITER)

            if (cratesRow.any { it == "1" }) break   // This is an index row

            var stackIndex = 0
            var emptySpaceCount = 0

            cratesRow.forEach { crate ->
                if (crate.isNotBlank()) {
                    if (emptySpaceCount >= SPACES_IN_CRATE_PLACE) {
                        stackIndex += emptySpaceCount / SPACES_IN_CRATE_PLACE
                    }

                    // Stack is created on a non existing index yet
                    if (stackIndex > stacks.lastIndex) {
                        val diff = stackIndex - stacks.lastIndex

                        repeat(diff) {
                            stacks.add(Stack<Char>())
                        }
                    }

                    val crateLabel = crate.first { it != CRATE_START_MARK }
                    stacks[stackIndex].add(0, crateLabel)

                    stackIndex++
                    emptySpaceCount = 0
                } else {
                    emptySpaceCount++
                }
            }
        }

        // Empty line
        lineIterator.next()

        // Commands
        while (lineIterator.hasNext()) {
            val command = lineIterator.next()

            val strippedCommand = command
                .replace(COMMAND_MOVE, "")
                .replace(COMMAND_FROM, "")
                .replace(COMMAND_TO, "")

            val numbers = strippedCommand
                .split(COMMAND_DELIMITER)
                .filter { it.isNotBlank() }
                .map { it.toInt() }

            val count = numbers[0]
            val fromColumnIndex = numbers[1] - 1
            val toColumnIndex = numbers[2] - 1

            stacks.moveCrates(
                numberOfCrates = count,
                from = fromColumnIndex,
                to = toColumnIndex,
                inSingleBlock = inSingleBlock,
            )
        }

        return stacks.joinToString(separator = "") { it.pop().toString() }
    }

    fun part1(input: List<String>): String = part(input = input, inSingleBlock = false)
    fun part2(input: List<String>): String = part(input = input, inSingleBlock = true)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")
}
