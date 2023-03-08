/**
 * TCSS 342 Assignment 6B - Spreadsheet App
 */

// I'm not sure if this breaks the code on anyone else's end, but
// I had to change "Model.test" to "test" to get the code to run on
// my end. 
// - Jacob Erickson

package Spreadsheet;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 *Test class for the SpreadSheet App class.
 * @author Louis Lomboy
 * @version 5 March 2023
 */
class SpreadsheetTest {


	/**
	 * Test Spreadsheet object with 2x2 Cells
	 */
	Spreadsheet testSpreadsheet = new Spreadsheet(2);

	/**
	 * Test CellToken for A1
	 */
	static CellToken cellTokenA1 = CellToken.getCellToken("A1");

	/**
	 * Test CellToken for A2
	 */
	static CellToken cellTokenA2 = CellToken.getCellToken("A2");

	/**
	 * Test CellToken for B1
	 */
	static CellToken cellTokenB1 = CellToken.getCellToken("B1");

	/**
	 * Test CellToken for B2
	 */
	static CellToken cellTokenB2 = CellToken.getCellToken("B2");

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 */
	@Test
	void testPrintValues() {


		//Change the formula at A1 to 1+1
		testSpreadsheet.changeCellFormula(cellTokenA1, "1+1");

		//Change the formula at A2 to 2+2
		testSpreadsheet.changeCellFormula(cellTokenA2, "2+	2");

		String expectedOutput = "A1: 2 B1: 4 \nA2: 0 B2: 0 \n";

		assertEquals(expectedOutput, testSpreadsheet.printValues());
	}

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 */
	@Test
	void testPrintCellFormulaAtA1() {
		//Change the formula at A1 to 1+3
		testSpreadsheet.changeCellFormula(cellTokenA1, "1+3");

		String expectedOutput = "1+3";

		assertEquals(expectedOutput, testSpreadsheet.getCellFormula(cellTokenA1));
	}

	/**
	 * Test method for {@link Spreadsheet#getCellFormula(CellToken)}.
	 * Prints the cell formula at B2.
	 */
	@Test
	void testPrintCellFormulaAtB2() {
		//Change the formula at B1 to 8-9
		testSpreadsheet.changeCellFormula(cellTokenB1, "8-9");

		String expectedOutput = "8-9";

		assertEquals(expectedOutput, testSpreadsheet.getCellFormula(cellTokenB1));
	}

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 * Prints the formula in the cells of the spreadsheet.
	 */
	@Test
	void testPrintAllFormulas() {
		testSpreadsheet.changeCellFormula(cellTokenA1, "3+1");
		testSpreadsheet.changeCellFormula(cellTokenA2, "1+A0");
		testSpreadsheet.changeCellFormula(cellTokenB1, "B0-11");

		String expectedOutput = "A1: 3+1 B1: B0-11  \nA2: 1+A0 B2: 0  \n";

		assertEquals(expectedOutput, testSpreadsheet.printAllFormulas());
	}

	/**
	 * Test method for {@link Spreadsheet#changeCellFormula(CellToken, String)}.
	 * Changes the cell formula at A0 to 4+2
	 */
	@Test
	void testChangeCellFormulaOnA0() {
		testSpreadsheet.changeCellFormula(cellTokenA1, "4+2");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		String expectedOutput = "4+2";

		assertEquals(expectedOutput, testSpreadsheet.getCellFormula(cellTokenA1));

	}

	/**
	 * Test method for {@link Spreadsheet#changeCellFormulaAndRecalculate(CellToken, String)}.
	 * Changes the cell on A1 and recalculates the value of the cell.
	 */
	@Test
	void testChangeCellFormulaAndRecalculateOnA1() {
		testSpreadsheet.changeCellFormula(cellTokenA1, "4+2");//A1
		testSpreadsheet.changeCellFormula(cellTokenA2, "4+A1"); //A2
		testSpreadsheet.changeCellFormula(cellTokenB1, "6/2");//B1
		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenB2, "A1-A2"); //B2

		String expectedOutput = "A1: 6 B1: 3 \nA2: 10 B2: -4 \n";

		assertEquals(expectedOutput, testSpreadsheet.printValues());

		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenA1, "6+7");

		String expectedOutput2 = "A1: 13 B1: 3 \nA2: 17 B2: -4 \n";

		assertEquals(expectedOutput2, testSpreadsheet.printValues());
	}

	/**
	 * Test method for the topological sort
	 */
	@Test
	void testTopologicalSortDiamond() {
		// We cant test this method directly, but we can test the
		// changeCellFormulaAndRecalculate method to see if it works.
		// This method is called in the changeCellFormulaAndRecalculate method
		// and is used to sort the cells in the spreadsheet.
		testSpreadsheet.changeCellFormula(cellTokenA1, "5");
		testSpreadsheet.changeCellFormula(cellTokenA2, "A1");
		testSpreadsheet.changeCellFormula(cellTokenB1, "A1");
		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenB2, "A2+B1");

		String expectedString = "A1: 5 B1: 5 \nA2: 5 B2: 10 \n";

		assertEquals(expectedString, testSpreadsheet.printValues());

		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenA1, "6");

		String expectedString2 = "A1: 6 B1: 6 \nA2: 6 B2: 12 \n";

		assertEquals(expectedString2, testSpreadsheet.printValues());
	}

	/**
	 * Test method for the topological sort in a Z shape
	 */
	@Test
	void testTopologicalSortZ() {
		// We cant test this method directly, but we can test the
		// changeCellFormulaAndRecalculate method to see if it works.
		// This method is called in the changeCellFormulaAndRecalculate method
		// and is used to sort the cells in the spreadsheet.
		testSpreadsheet.changeCellFormula(cellTokenA1, "5");
		testSpreadsheet.changeCellFormula(cellTokenA2, "A1");
		testSpreadsheet.changeCellFormula(cellTokenB1, "A2");
		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenB2, "B1");

		String expectedString = "A1: 5 B1: 5 \nA2: 5 B2: 5 \n";

		assertEquals(expectedString, testSpreadsheet.printValues());

		testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenA1, "6");

		String expectedString2 = "A1: 6 B1: 6 \nA2: 6 B2: 6 \n";

		assertEquals(expectedString2, testSpreadsheet.printValues());
	}

	/**
	 * Test method to make sure the topological sort throws an error
	 * when there is a cycle in the graph.
	 */
	@Test
	void testTopologicalCycle() {
		testSpreadsheet.changeCellFormula(cellTokenA1, "A2");

		assertThrows(RuntimeException.class,
		() -> {
			testSpreadsheet.changeCellFormulaAndRecalculate(cellTokenA2, "A1");
		});
	}

	/**
	 * Test method for {@link Spreadsheet#getNumRows()}.
	 */
	@Test
	void testGetNumRows() {

		assertEquals(2, testSpreadsheet.getNumRows());
	}

	/**
	 * Test method for {@link Spreadsheet#getNumColumns()}.
	 */
	@Test
	void testGetNumColumns() {
		assertEquals(2, testSpreadsheet.getNumColumns());
	}

}
