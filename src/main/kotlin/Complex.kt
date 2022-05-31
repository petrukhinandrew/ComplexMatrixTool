import kotlin.math.sqrt

data class Complex(var Re: Int = 0, var Im: Int = 0) {
    constructor(complexNumber: Complex) : this(complexNumber.Re, complexNumber.Im)
    constructor(complexString: String) : this(0, 0) {
        val complexParts = parseComplexParts(complexString)
        complexParts.forEach { part ->
            if (part.isImaginary)
                Im += part.value
            else
                Re += part.value
        }
    }

    private fun complexStringValidation(expectedComplex: String): Boolean =
        "[+-][-+]|\\di\\d|ii|i\\di|i\\d".toRegex().find(expectedComplex) == null

    private fun parseComplexParts(expectedComplex: String): List<ParsedComplexNumberPart> { // TODO(make +i == +1i)
        val trimmedExpectedComplex = expectedComplex.replace("\\s".toRegex(), "")
        if (!complexStringValidation(trimmedExpectedComplex))
            throw Exception("Given $trimmedExpectedComplex is not valid")
        val tmpComplexString = StringBuilder()
        val result = mutableListOf<ParsedComplexNumberPart>()
        trimmedExpectedComplex.forEach {
            when {
                it in listOf('+', '-') -> {
                    if (tmpComplexString.isNotEmpty())
                        result.add(ParsedComplexNumberPart(tmpComplexString.toString()))
                    tmpComplexString.clear()
                    tmpComplexString.append(it)
                }
                it.isDigit() || it == 'i' -> tmpComplexString.append(it)
            }
        }
        result.add(ParsedComplexNumberPart(tmpComplexString.toString()))
        return result
    }

    operator fun unaryMinus(): Complex {
        return Complex(-Re, -Im)
    }

    operator fun plus(other: Complex): Complex {
        return Complex(Re + other.Re, Im + other.Im)
    }

    operator fun minus(other: Complex): Complex {
        return Complex(this + (-other))
    }

    operator fun times(other: Complex): Complex {
        return Complex(Re * other.Re - Im * other.Im, Re * other.Im + Im * other.Re)
    }

    override fun toString(): String {
        return "$Re + ${Im}i"
    }
}

fun Complex.absVal(): Double = sqrt((Re * Re + Im * Im).toDouble())

fun abs(complexNumber: Complex): Double {
    return complexNumber.absVal()
}
