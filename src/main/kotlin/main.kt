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

fun main () {
    val storage: MutableMap<String, ComplexMatrix> = mutableMapOf()
    var nextCommand = readLine()!!.split(' ').filter{ it.isNotEmpty() }
    while (handleCommand(storage, nextCommand)) {
        nextCommand = readLine()!!.split(' ').filter { it.isNotEmpty() }
    }
}