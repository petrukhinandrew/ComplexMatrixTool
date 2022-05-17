data class ComplexMatrix(val stroke : Char, val height: Int, val width: Int, val data: List<List<Complex>>) {
    constructor(height: Int, width: Int) : this(
        '*', height, width,
        List<List<Complex>>(height) { List<Complex>(width) { Complex(0, 0) } }
    )
    private fun findMaxNumberLength() : Int {
        return data.maxOf { it.maxOf { it.toString().length } }
    }
    private fun getRowLength() : Int = (findMaxNumberLength() + 3) * (width) + 1

    fun drawLine() {
        val lineLength = getRowLength()
        print(stroke * lineLength)
        println()
    }
    private fun printSepLine() {
        for (i in 1..getRowLength()) {
            if (i % (findMaxNumberLength() + 3) == 1)
                print(stroke)
            else
                print(' ')
        }
        println()
    }
    private fun printNumberLine(row : List<Complex>) {
        print(stroke)
        row.forEach {
//            print(' ') // for padEnd
            print(it.toString().padStart(findMaxNumberLength() + 1,' '))
            print(' ') // for padStart
            print(stroke)
        }
        println()
    }
    fun printRow(row : List<Complex>) { // я тут запутался если лучше передавать именно строчку или просто номер строки и брать из поля data
        printSepLine()
        printNumberLine(row)
        printSepLine()
        drawLine()
    }
    private operator fun Char.times(n : Int) : String {
        val resultString = StringBuilder()
        repeat(n) {
            resultString.append(this)
        }
        return resultString.toString()
    }
    fun drawMatrix() {
        drawLine()
        data.forEach {
            printRow(it)
        }
    }
}