const val helpMessage = """Complex Matrix Tool:
    Use `$\i`, where i is Int to specify or get access to the matrix
    Use `$\i + - * $\j`, where i and j are Int to print result of operation, applied to matrices
    Use `$\i += -= *= $\j`, where i and j are Int to updated saved matrices
    Use `print $\i` to print matrix
    Use `exit` to exit application
"""

fun handleCommand(storage: MutableMap<String, ComplexMatrix>, command: List<String>): Boolean {
    if (command.isEmpty())
        return true
    when {
        command[0] in listOf("print", "exit", "help") -> {
            when(command[0]) {
                "exit" -> return false
                "help" -> println(helpMessage)
                "print" -> print(storage[command[1]])
            }
            return true
        }
        command[0].first() == '$' && command[2].first() == '$' -> {
            require(storage[command[2]] != null)
            if (command[1] == "=") {
                storage[command[0]] = storage[command[2]]!!
                return true
            }
            require(storage[command[0]] != null)
            when (command[1]) {
                "+=" -> storage[command[0]] = storage[command[0]]!! + storage[command[2]]!!
                "-=" -> storage[command[0]] = storage[command[0]]!! - storage[command[2]]!!
                "*=" -> storage[command[0]] = storage[command[0]]!! * storage[command[2]]!!
                "+" -> print(storage[command[0]]!! + storage[command[2]]!!)
                "-" -> print(storage[command[0]]!! - storage[command[2]]!!)
                "*" -> print(storage[command[0]]!! * storage[command[2]]!!)
            }
            return true
        }

        command[0].first() == '$' -> {
            if (command[1] == "=") {
                storage[command[0]] = ComplexMatrix(command[2])
                return true
            }
            require(storage[command[0]] != null)
            when (command[1]) {
                "+=" -> storage[command[0]] = storage[command[0]]!! + ComplexMatrix(command[2])
                "-=" -> storage[command[0]] = storage[command[0]]!! - ComplexMatrix(command[2])
                "*=" -> storage[command[0]] = storage[command[0]]!! * ComplexMatrix(command[2])
                "+" -> print(storage[command[0]]!! + ComplexMatrix(command[2]))
                "-" -> print(storage[command[0]]!! - ComplexMatrix(command[2]))
                "*" -> print(storage[command[0]]!! * ComplexMatrix(command[2]))
            }
            return true
        }
        else -> {
            println("Command not found or unrecognized.")
        }
    }
    return true
}