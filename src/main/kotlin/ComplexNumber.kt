import kotlin.math.sqrt

data class ParsedComplexPart(var str : String) {

    var sign = 1
    var isImaginary = false
    var value = 0

    init {
        if (str.find { it == '-'} != null)
            sign = -1
        str = str.replace("-+".toRegex(), "")
        if (str.find { it == 'i' } != null)
            isImaginary = true
        str = str.replace("i", "")
        value = str.toInt() * sign
    }

    override fun toString(): String {
        return value.toString() + if (isImaginary) "i" else ""
    }
}

data class Complex(var Re: Int, var Im: Int) {
    constructor(complexNumber: Complex) : this(complexNumber.Re, complexNumber.Im)
    constructor(complexString: String) : this(0, 0) {
        val complexParts = parseComplexParts(complexString)
        complexParts.forEach {part ->
            if (part.isImaginary)
                Im += part.value
            else
                Re += part.value
        }
    }

    private fun validateComplexString(expectedComplex: String) : Boolean  = "[+-][-+]|\\di\\d|ii|i\\di".toRegex().find(expectedComplex) == null

    private fun parseComplexParts(expectedComplex : String) : List<ParsedComplexPart> {
        val trimmedExpectedComplex = expectedComplex.replace("\\s".toRegex(), "")
        if (!validateComplexString(trimmedExpectedComplex))
            throw Exception("Given $trimmedExpectedComplex is not valid")
        val tmpComplexString = StringBuilder()
        val result = mutableListOf<ParsedComplexPart>()
        trimmedExpectedComplex.forEach {
            when {
                it in listOf('+', '-') -> {
                    if (tmpComplexString.isNotEmpty())
                        result.add(ParsedComplexPart(tmpComplexString.toString()))
                    tmpComplexString.clear()
                    tmpComplexString.append(it)
                }
                it.isDigit() || it == 'i' -> tmpComplexString.append(it)
            }
        }
        result.add(ParsedComplexPart(tmpComplexString.toString()))
        return result
    }

    operator fun unaryMinus(): Complex {
        return Complex(-Re, -Im)
    }

    operator fun plus(other: Complex) : Complex {
        return Complex(Re + other.Re, Im + other.Im)
    }

    operator fun minus(other: Complex) : Complex {
        return Complex(this + (-other))
    }

    operator fun times(other: Complex) : Complex {
        return Complex(Re * other.Re - Im * other.Im, Re * other.Im + Im * other.Re)
    }

    override fun toString(): String {
        return "$Re + ${Im}i"
    }
}

operator fun Complex.plusAssign(other: Complex) {
    Re += other.Re
    Im += other.Im
}

operator fun Complex.minusAssign(other: Complex) {
    this += -other
}

operator fun Complex.timesAssign(other: Complex) {
    Re = Re * other.Re - Im * other.Im
    Im = Re * other.Im + Im * other.Re
}

fun Complex.absVal() : Double = sqrt((Re * Re + Im * Im).toDouble())

fun abs(complexNumber : Complex) : Double {
    return complexNumber.absVal()
}
