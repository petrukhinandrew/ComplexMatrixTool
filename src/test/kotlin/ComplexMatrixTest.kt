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
        val matrix = ComplexMatrix("1,2i,3i;4,5i,6i")
        val matrixCopy = ComplexMatrix(matrix)
        assertEquals(matrix, matrixCopy)
        matrix[0][0] = Complex(2, 2)
        assertNotEquals(matrix[0][0], matrixCopy[0][0])
        matrixCopy[1][1] = Complex()
        assertNotEquals(matrix[1][1], matrixCopy[1][1])
        assertNotEquals(matrix, matrixCopy)
    }

    @Test
    fun testOperatorPlus() {
        var m = ComplexMatrix("1,2i,3;4,5,6i")
        assertEquals(
            m + ComplexMatrix("1i,2,3i;4i,5i,6"),
            ComplexMatrix("1 + 1i, 2 + 2i, 3 + 3i; 4 + 4i, 5 + 5i, 6 + 6i")
        )
        m += m
        assertEquals(m, ComplexMatrix("2,4i,6;8,10,12i"))
        assertFails { m + ComplexMatrix("1,1;2,2") }
        assertFails { m + ComplexMatrix("1,1,1;2,2,2,2") }
    }

    @Test
    fun testOperatorTimes() {
        var e = ComplexMatrix("1,0,0;0,1,0;0,0,1")
        assertEquals(e * ComplexMatrix("1,2,3;4,5,6;7,8,9"), ComplexMatrix("1,2,3;4,5,6;7,8,9"))
        assertEquals(e * ComplexMatrix("1,2,3;4,5,6;7,8,9") * e, ComplexMatrix("1,2,3;4,5,6;7,8,9"))
        assertEquals(e * e, e)
        e = ComplexMatrix("1,1;0,1")
        e *= ComplexMatrix("2;3")
        assertEquals(e, ComplexMatrix("5;3"))
        assertFails { e * ComplexMatrix("5; 6") }
        e = ComplexMatrix("1,2,3;4,5,6")
        assertFails { e * ComplexMatrix("1;2") }
    }

    @Test
    fun testMatrixTimesInt() {
        assertEquals(5 * ComplexMatrix("1,1;2,2"), ComplexMatrix("5,5;10,10"))
        assertEquals(5 * ComplexMatrix("1,1;2,2") * 5, ComplexMatrix("25,25;50,50"))
        assertEquals(ComplexMatrix("1,1;2,2") * 5, ComplexMatrix("5,5;10,10"))
        assertEquals(5 * ComplexMatrix("1i,1i;2i,2i"), ComplexMatrix("5i,5i;10i,10i"))
        assertEquals(5 * ComplexMatrix("1,1i;2,2i") * 5, ComplexMatrix("25,25i;50,50i"))
        assertEquals(
            ComplexMatrix("1,2,3,4,5;6,7,8,9,10;1,2,3,4,5") * 0,
            ComplexMatrix("0,0,0,0,0;0,0,0,0,0;0,0,0,0,0")
        )
    }
}