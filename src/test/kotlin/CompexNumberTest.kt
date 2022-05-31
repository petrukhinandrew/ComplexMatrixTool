import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class ComplexNumberTest {
    @Test
    fun testStringConstructor() {
        assertEquals(Complex("1 + 2i").Re, 1)
        assertEquals(Complex("2 + 2i").Re, 2)
        assertEquals(Complex("0 + 2i").Re, 0)
        assertEquals(Complex("2i").Re, 0)
        assertEquals(Complex("100500").Re, 100500)
        assertEquals(Complex("1 + 2i").Im, 2)
        assertEquals(Complex("1").Im, 0)
        assertEquals(Complex("1 + 2i + 3i").Im, 5)
        assertEquals(Complex("6i").Im, 6)

        assertFails { Complex("1 + + 2") }
        assertFails { Complex("1 + ii") }
        assertFails { Complex("1 + i2") }
    }

    @Test
    fun testComplexConstructor() {
        val compl = Complex(1, 2)
        val anotherCompl = Complex(compl)
        compl.Re += 5
        assertEquals(anotherCompl.Re, 1)
        assertEquals(anotherCompl.Im, 2)
        assertEquals(compl.Re, 6)
    }

    @Test
    fun testOperatorPlus() {
        var compl = Complex()
        assertEquals(compl + compl, Complex())
        assertEquals(compl + Complex(1, 1), Complex(1, 1))

        compl += Complex(5, 6)

        assertEquals(compl.Re, 5)
        assertEquals(compl.Im, 6)
        compl += compl

        assertEquals(compl.Re, 10)
        assertEquals(compl.Im, 12)
    }

    @Test
    fun testOperatorMinus() {
        var compl = Complex(10, 10)
        compl -= Complex(1,1)
        assertEquals(compl.Re, 9)
        assertEquals(compl.Im, 9)

        assertEquals(compl - compl, Complex(0, 0))
    }

    @Test
    fun testOperatorTimes() {
        val compl = Complex(1,1)
        assertEquals(compl * compl, Complex(0, 2))
        assertEquals(compl * Complex(2, 0), Complex(2, 2))
        assertEquals(compl * Complex(0, 2), Complex(-2, 2))
    }
}