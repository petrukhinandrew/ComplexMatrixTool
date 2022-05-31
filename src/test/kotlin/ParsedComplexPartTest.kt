import kotlin.test.*

internal class ParsedComplexNumberPartTest {
    @Test
    fun testPartParsing() {
        val testPart1 = ParsedComplexNumberPart("1i")
        val testPart2 = ParsedComplexNumberPart("5")
        val testPart3 = ParsedComplexNumberPart("-2i")
        val testPart4 = ParsedComplexNumberPart("-0")
        assertTrue(testPart1.isImaginary && testPart1.sign == 1 && testPart1.value == 1)
        assertTrue(!testPart2.isImaginary && testPart2.sign == 1 && testPart2.value== 5)
        assertTrue(testPart3.isImaginary && testPart3.sign == -1 && testPart3.value == -2)
        assertTrue(!testPart4.isImaginary && testPart4.sign == -1 && testPart4.value == 0)
    }
}