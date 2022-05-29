data class ComplexMatrix(val stroke: Char = '*', val height: Int, val width: Int, val data: List<MutableList<Complex>>) {
    constructor(height: Int, width: Int) : this(
        '*', height, width,
        List<MutableList<Complex>>(height) { MutableList<Complex>(width) { Complex(0, 0) } }
    )

    constructor(other: ComplexMatrix) : this(
        other.stroke, other.height, other.width, other.data.copy()
    )
    init {
        require(initialValuesValidation())
    }
    /*
     * операции с матрицами
     */
    private fun initialValuesValidation(): Boolean = (height == data.size && data.all { it.size == width })
    private fun plusValidation(other: ComplexMatrix): Boolean = (height == other.height && width == other.width)
    private fun timesValidation(other: ComplexMatrix): Boolean = (width == other.height)

    operator fun unaryMinus(): ComplexMatrix {
        val minusMatrix = ComplexMatrix(this)
        for (i in 0 until height)
            for (j in 0 until width)
                minusMatrix[i][j] = -this[i][j]
        return minusMatrix
    }

    operator fun plus(other: ComplexMatrix): ComplexMatrix {
        require(plusValidation(other))
        val newMatrix = ComplexMatrix(this)
        for (i in 0 until height)
            for (j in 0 until width)
                newMatrix.data[i][j] = this[i][j] + other[i][j]
        return newMatrix
    }

    operator fun minus(other: ComplexMatrix): ComplexMatrix = this + (-other)

    operator fun times(other: ComplexMatrix): ComplexMatrix {
        require(timesValidation(other))
        val newMatrix = ComplexMatrix(height, other.width)
        for (i in 0 until height)
            for (j in 0 until width)
                for (c in 0 until width)
                    newMatrix.data[i][j] += data[i][c] * other.data[c][j]
        return newMatrix
    }

    /*
     * теперь научимся печатать матрицу
     * у data class в идеальном мире нет каких-то методов, которые обрабатывают данные
     * но чтобы сделать код более читаемым, разобьем метод toString на несколько маленьких
     */

    // сначала сделаем себе аналог умножения строки на число, как в Python
    // почему бы и нет, так удобнее, а еще мы так може
    private operator fun Char.times(n: Int): String {
        val resultString = StringBuilder()
        repeat(n) {
            resultString.append(this)
        }
        return resultString.toString()
    }

    /*
     * мы хотим, чтобы все ячейки матрицы имели одинаковую ширину
     * для этого возьмем максимум по длинам всех чисел в матрице
     */
    private fun getCellWidth(): Int =
        data.maxOf { it.maxOf { complexNumber -> complexNumber.toString().length } }

    private fun getRowLength(): Int = (getCellWidth() + 3) * (width) + 1

    private fun borderLine(lineLength: Int): String = stroke * lineLength + "\n"

    private fun paddingLine(lineLength: Int, cellWidth: Int): String {
        val paddingLine = StringBuilder()
        for (i in 1..lineLength) {
            if (i % (cellWidth + 3) == 1)
                paddingLine.append(stroke)
            else
                paddingLine.append(' ')
        }
        paddingLine.append("\n")
        return paddingLine.toString()
    }

    private fun numberLine(row: List<Complex>, cellWidth: Int): String {
        val numberLine = StringBuilder()
        numberLine.append(stroke)
        row.forEach {
            numberLine.append(' ')
            numberLine.append(it.toString().padEnd(cellWidth + 1, ' '))
            numberLine.append("$stroke")
        }
        numberLine.append("\n")
        return numberLine.toString()
    }

    private fun matrixRow(row: List<Complex>, lineLength: Int, cellWidth: Int): String {
        val matrixRow = StringBuilder()
        matrixRow.append(paddingLine(lineLength, cellWidth))
        matrixRow.append(numberLine(row, cellWidth))
        matrixRow.append(paddingLine(lineLength, cellWidth))
        matrixRow.append(borderLine(lineLength))
        return matrixRow.toString()
    }

    /*
     * стандартная функция print пытается привести неизвестный тип к строке, поэтому мы сделаем одну большую строку и вернем ее
     */
    override fun toString(): String {
        val matrixString = StringBuilder()
        val lineLength = getRowLength()
        val cellWidth = getCellWidth()
        matrixString.append(borderLine(lineLength))
        data.forEach { row ->
            matrixString.append(matrixRow(row, lineLength, cellWidth))
        }
        return matrixString.toString()
    }
}

operator fun ComplexMatrix.get(i: Int) = data[i]