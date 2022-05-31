import kotlin.test.*

internal class ComplexMatrixTest {
    @Test
    fun testInitialValidation() {
        assertFails {
            ComplexMatrix(
                height = 2, width = 2, data = listOf(
                    mutableListOf(Complex(1, 1), Complex()),
                    mutableListOf(Complex())
                )
            )
        }

        assertFails {
            ComplexMatrix(
                data = listOf(
                    mutableListOf(Complex(), Complex(1, 2), Complex(5, 6)),
                    mutableListOf(Complex())
                )
            )
        }

        assertFails {
            ComplexMatrix(
                data = listOf(
                    mutableListOf(Complex(), Complex(1, 2), Complex(5, 6)),
                    mutableListOf()
                )
            )
        }

        assertFails {
            ComplexMatrix(
                height = 1, width = 2, data = listOf(
                    mutableListOf(Complex(1, 1)),
                    mutableListOf(Complex())
                )
            )
        }
    }

    @Test
    fun testStringConstructor() {
        assertEquals(
            ComplexMatrix("1,2,3;4,5,6").data,
            listOf(
                mutableListOf(Complex(1, 0), Complex(2, 0), Complex(3, 0)),
                mutableListOf(Complex(4, 0), Complex(5, 0), Complex(6, 0))
            )
        )

        assertEquals(
            ComplexMatrix("1i,2 + 2i,3;4,5i,6i + 6").data,
            listOf(
                mutableListOf(Complex(0, 1), Complex(2, 2), Complex(3, 0)),
                mutableListOf(Complex(4, 0), Complex(0, 5), Complex(6, 6))
            )
        )

        assertFails { ComplexMatrix(",,,4,5;1,2,4,5") }

        assertFails { ComplexMatrix(",4,5;6,7,8,9") }

        assertEquals(
            ComplexMatrix("100500i,2 + 2i,3;4,5i,6i + 6;4,5i,6i + 6").data,
            listOf(
                mutableListOf(Complex(0, 100500), Complex(2, 2), Complex(3, 0)),
                mutableListOf(Complex(4, 0), Complex(0, 5), Complex(6, 6)),
                mutableListOf(Complex(4, 0), Complex(0, 5), Complex(6, 6))
            )
        )
    }

    @Test
    fun testOperatorUnaryMinus() {
        val matrix = ComplexMatrix("1,2,3;4,5,6;7,8,9")
        assertEquals(-matrix, ComplexMatrix("-1,-2,-3;-4,-5,-6;-7,-8,-9"))
        matrix[0][0] = Complex(-10, -10)
        assertEquals(-matrix, ComplexMatrix("10 + 10i,-2,-3;-4,-5,-6;-7,-8,-9"))

        val anotherMatrix = ComplexMatrix("1 + 1i,2 + 2i,3i;4,5 - 1i ,6;7i,-8i,9i")
        assertEquals(-anotherMatrix, ComplexMatrix("-1 - 1i,-2 -2i,-3i;-4,-5 + 1i,-6;-7i,8i,-9i"))

    }

    @Test
    fun testNoCopyOnAssignment() {

    }

    @Test
    fun testOperatorPlus() {

    }

    @Test
    fun testOperatorTimes() {

    }

}