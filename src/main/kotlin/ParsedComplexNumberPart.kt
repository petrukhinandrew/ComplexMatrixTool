data class ParsedComplexNumberPart(var str: String) {

    var sign = 1
    var isImaginary = false
    var value = 0

    init {
        if (str.find { it == '-' } != null)
            sign = -1
        str = str.replace("-+".toRegex(), "")
        if (str.find { it == 'i' } != null) {
            isImaginary = true
            value = if (value == 0) 1 else value
        }
        str = str.replace("i", "")
        value = str.toInt() * sign
    }

    override fun toString(): String {
        return value.toString() + if (isImaginary) "i" else ""
    }
}