/*
Тут нужно понять следующую мысль:
    data class придуман для хранения данных и по идее не должен иметь методов для какой-то обработки данных
    хотя сам по себе он поддерживает весь доступный обычным классам функционал

В идеальном мире, от data class мы хотим только поля с данными и методы .toString() и .hashCode()
При работе с числами будет удобно добавить операторы +, -, *, / (в комплексных числах не так удобно делить, как в действительных, но это не сложно реализовать)
Еще, если мы говорим о комплексных числах или, например, точках на плоскости / в трехмерном пространстве, можно добавить метод получения модуля

Мы обсудили класс чисел, теперь мы готовы обсудить класс матриц
Для них нам так же понадобятся операторы +,-,*
    Не совсем понятно, как делить матрицы, но иногда для матрицы можно найти обратную и мы можем написать функцию для проверки, чо обратная матрица существует
Так же отметим, что матрицы выводить немного сложнее, чем числа, а нам не хочется перегружать метод .toString()
Для реализации можно создать несколько дополнительных приватных функций, которые метод .toString() будет использовать

Теперь, когда примерно понятно какой функционал хочется получить, поймем еще одну мысль:
    У любого числа есть метод .toString(), а значит мы можем сделать класс матриц таким, чтобы он корректно работал вне зависимости,
    предложили ему целые числа, комплексные или дробные
Для этого будем пользоваться generic'ами

 */
fun <T> List<MutableList<T>>.copy() = this.map{ it.toMutableList() }

/*
 * UI interface:
 *      $i, i: Int - matrix
 *      +, -, * prints matrix
 *      $i +=, -=, *= $j updates matrix at $i with $j
 *      we can add .T() for matrix transposing
 *      print $i to print that is stored in $i
 *      $i = s, s: String for saving new matrix. must be checked whether something is already saved in $i
 *      exit to close the app
 *      help
 */

// now the task is to implement this interface
const val helpMessage = "HELP MESSAGE MUST BE SPECIFIED"
fun handleCommand(storage: MutableMap<String, ComplexMatrix>, command: List<String>): Boolean {
    when {
        command[0] in listOf("print", "exit", "help") -> {
            when(command[0]) {
                "exit" -> return false
                "help" -> {
                    println(helpMessage)
                }
                "print" -> {
                    if (command.size == 1) {
                        println("No matrix specified to print")
                        return true
                    }
                    if (command[1] in storage)
                        println(storage[command[1]])
                    else
                        println("No matrix saved with index ${command[1]}")
                }
            }
        }
        command[0].first() == '$' -> {

        }
        else -> {
            println("COMMAND NOT FOUND")
        }
    }
    return true
}


fun main () {
//    val storage: MutableMap<String, ComplexMatrix> = mutableMapOf()
//    var nextCommand = readLine()!!.split(' ').filter{ it.isNotEmpty() }
//    while (handleCommand(storage, nextCommand)) {
//        nextCommand = readLine()!!.split(' ').filter { it.isNotEmpty() }
//    }
    val m = ComplexMatrix("1,2,3;4,5,6")
    print(m)
}