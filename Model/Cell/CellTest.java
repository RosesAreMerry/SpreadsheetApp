/**
 * TCSS 342 Winter 2023 - Assignment 6B Spreadsheet App
 */

package Cell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Cell.Cell;
import Cell.Tokens.CellToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a Junit Test class for the Cell class.
 * @author Louis Lomboy
 * @version 7 March 2023
 */
class CellTest {


    /**
     * Test Cell Object A1
     */
    Cell myCell1;

    /**
     * Test Cell Object B2
     */
    Cell myCell2;

    /**
     * Test Cell Object C3
     */
    Cell myCell3;

    /**
     * Test Cell Object D4
     */
    Cell myCell4;

    /**
     * Function to create Cell Objects from Cell token
     */
    Function<CellToken, Cell> myGetCell1;
    Function<CellToken, Cell> myGetCell2;
    Function<CellToken, Cell> myGetCell3;
    Function<CellToken, Cell> myGetCell4;

    /**
     * Initializes the Cell Objects for each test.
     */
    @BeforeEach
    void setUp() {
        CellToken.getCellToken("A1");
        CellToken.getCellToken("B2");
        CellToken.getCellToken("C3");
        CellToken.getCellToken("D4");
        myGetCell1 = (c) -> {
            return null;
        };
        myGetCell2 = (c) -> {
            return null;
        };
        myGetCell3 = (c) -> {
            return null;
        };
        myGetCell4 = (c) -> {
            return null;
        };
        myCell1  = new Cell(0, 0, "1+1", myGetCell1);
        myCell2 = new Cell(1, 1, "2+2", myGetCell2);
        myCell3 = new Cell(2, 2, "A1+1", myGetCell3);
        myCell4 = new Cell(3, 3, "B2/4", myGetCell4);

    }


    /**
     * Test method for {@link Cell#getRow()}.
     */
    @Test
    void testGetRowA1() {

        assertEquals(0, myCell1.getRow());
    }

    /**
     * Test method for {@link Cell#getRow()}.
     */
    @Test
    void testGetRowC4() {
        assertEquals(3, myCell4.getRow());
    }

    /**
     * Test method for {@link Cell#getColumn()}.
     */
    @Test
    void testGetColumnB2() {
        assertEquals(4, myCell2.getColumn());
    }

    /**
     * Test method for {@link Cell#getColumn()}.
     */
    @Test
    void testGetColumnC3() {
        assertEquals(2, myCell3.getColumn());
    }

    /**
     * Test method for {@link Cell#setRow(int)}.
     */
    @Test
    void testSetRowA1ToA9() {
        myCell1.setRow(9);
        assertEquals(9, myCell1.getRow());
    }

    /**
     * test method for {@link Cell#setColumn(int)}.
     */
    @Test
    void testSetColumnD4ToZ4() {
        myCell4.setColumn(25);
        assertEquals(25, myCell4.getColumn());
    }

    /**
     * Combining set column and set row tests.
     */
    @Test
    void testSetColumnSetRowTogether() {
        myCell2.setRow(5);
        myCell2.setColumn(3);

        assertEquals(5, myCell2.getRow());
        assertEquals(3, myCell2.getColumn());
    }

    /**
     * Test method for {@link Cell#getFormula()}.
     */
    @Test
    void tesGetFormulaC3() {
        assertEquals("A1+1", myCell3.getFormula());
    }

    /**
     * Test method for {@link Cell#setFormula(String)}.
     */
    @Test
    void testSetFormulaA1() {
        myCell1.setFormula("C3+1");
        assertEquals("C3+1", myCell1.getFormula());
    }


    /**
     * Test method for {@link Cell#getValue()}.
     */
    @Test
    void testGetValueB2() {
        assertEquals(4, myCell2.getValue());
    }


    @Test
    void testGetDependenciesNull() {
        ArrayList<Cell> testArray= new ArrayList<>();
        assertEquals(testArray, myCell1.getDependencies());
    }
}