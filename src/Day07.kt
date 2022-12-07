private const val ROOT = "/"
private const val PARENT_DIRECTORY = ".."
private const val MARK_DIRECTORY = "dir"
private const val FILE_SIZE_DELIMITER = " "

private const val COMMAND_CHANGE_DIRECTORY = "$ cd "
private const val COMMAND_LIST_DIRECTORY = "$ ls"

private const val MAX_DIRECTORY_SIZE = 100000
private const val MAX_DISK_SPACE = 70000000
private const val MIN_SPACE_FOR_UPDATE = 30000000

data class File(
    val name: String,
    val size: Long,
)

data class Directory(
    val name: String,
    val parentDirectory: Directory? = null,
) {
    private val _directories = mutableListOf<Directory>()
    private val _files = mutableListOf<File>()

    fun addFile(file: File) {
        if (_files.none { it.name == file.name }) {
            _files.add(file)
        }
    }

    fun addSubDirectory(directory: Directory) {
        if (_directories.none { it.name == directory.name }) {
            _directories.add(directory)
        }
    }

    fun findSubdirectory(name: String): Directory? =
        _directories.find { it.name == name }

    fun getSubDirectorySizes(): List<Long> {
        val sizes = mutableListOf<Long>()
        var directSubDirectoriesSum = 0L

        _directories.forEach { directory ->
            val subSizes = directory.getSubDirectorySizes()
            sizes.addAll(subSizes)
            directSubDirectoriesSum += subSizes.last()
        }

        val filesSize = _files.sumOf { it.size }
        sizes.add(directSubDirectoriesSum + filesSize)

        return sizes
    }

    override fun toString(): String =
        "Name: $name, directories: $_directories, files: $_files"
}

fun main() {

    fun getFilesTree(input: List<String>): Directory {
        val rootDirectory = Directory(ROOT)
        var currentDirectory = rootDirectory

        input.forEachIndexed { index, line ->
            when {
                line.startsWith(COMMAND_CHANGE_DIRECTORY) -> {
                    val directoryName = line.removePrefix(COMMAND_CHANGE_DIRECTORY)

                    currentDirectory = when (directoryName) {
                        ROOT -> rootDirectory
                        PARENT_DIRECTORY -> currentDirectory.parentDirectory
                        else -> currentDirectory.findSubdirectory(directoryName)
                    }
                        ?: throw IllegalArgumentException()
                }
                line.startsWith(COMMAND_LIST_DIRECTORY) -> {
                    // Do nothing
                }
                else -> {
                    val data = line.split(FILE_SIZE_DELIMITER)
                    val info = data.first()
                    val name = data.last()

                    if (info == MARK_DIRECTORY) {
                        currentDirectory.addSubDirectory(
                            Directory(
                                name = name,
                                parentDirectory = currentDirectory
                            )
                        )
                    } else {
                        currentDirectory.addFile(
                            File(
                                name = name,
                                size = info.toLong()
                            )
                        )
                    }
                }
            }
        }

        return rootDirectory
    }

    fun part1(input: List<String>): Long {
        val rootDirectory = getFilesTree(input)
        val directorySizes = rootDirectory.getSubDirectorySizes()

        return directorySizes.filter { it <= MAX_DIRECTORY_SIZE }.sum()
    }

    fun part2(input: List<String>): Long {
        val rootDirectory = getFilesTree(input)
        val directorySizes = rootDirectory.getSubDirectorySizes()

        val usedSpace = directorySizes.last()
        val spaceToDelete = MIN_SPACE_FOR_UPDATE - (MAX_DISK_SPACE - usedSpace)

        return directorySizes.filter { it >= spaceToDelete }.min()
    }

    val testInput1 = readInput("Day07_test")
    check(part1(testInput1) == 95437L)
    check(part2(testInput1) == 24933642L)
}
