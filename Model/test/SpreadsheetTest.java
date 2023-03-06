/**
 * TCSS 342 Assignment 6B - Spreadsheet App
 */
package Model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
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
	 * Test CellToken for A0
	 */
	static CellToken theTestCellToken1=new CellToken();

	/**
	 * Test CellToken for A1
	 */
	static CellToken theTestCellToken2=new CellToken();

	/**
	 * Test CellToken for B0
	 */
	static CellToken theTestCellToken3=new CellToken();

	/**
	 * Test CellToken for B1
	 */
	static CellToken theTestCellToken4=new CellToken();

	/**
	 * Sets the CellToken and their designated locations
	 */
	@BeforeAll
	static void beforeAll()
	{
		theTestCellToken1.setRow(0);
		theTestCellToken1.setColumn(0); //A0
		theTestCellToken2.setRow(0);
		theTestCellToken2.setColumn(1); //A1
		theTestCellToken3.setRow(1);
		theTestCellToken3.setColumn(0);// B0
		theTestCellToken4.setRow(1);
		theTestCellToken4.setColumn(1);// B1
	}

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 */
	@Test
	void testPrintValues() {


		//Change the formula at A1 to 1+1
		testSpreadsheet.changeCellFormula(theTestCellToken1, "1+1");

		//Change the formula at A2 to 2+2
		testSpreadsheet.changeCellFormula(theTestCellToken2, "2+	2");
		//Capture the output

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printValues();

		String expectedOutput = "A1:2 A2:4 \nB1:0 B2:0 \n";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 */
	@Test
	void testPrintCellFormulaAtA1() {
		//Change the formula at A1 to 1+3
		testSpreadsheet.changeCellFormula(theTestCellToken1, "1+3");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printCellFormula(theTestCellToken1);

		String expectedOutput = "1+3";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}
	/**
	 * Test method for {@link Spreadsheet#printCellFormula(CellToken)}.
	 * Prints the cell formula at B2.
	 */
	@Test
	void testPrintCellFormulaAtB2() {
		//Change the formula at B1 to 8-9
		testSpreadsheet.changeCellFormula(theTestCellToken3, "8-9");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printCellFormula(theTestCellToken3);

		String expectedOutput = "8-9";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	/**
	 * Test method for {@link Spreadsheet#printAllFormulas()}.
	 * Prints the formula in the cells of the spreadsheet.
	 */
	@Test
	void testPrintAllFormulas() {
		testSpreadsheet.changeCellFormula(theTestCellToken1, "3+1");
		testSpreadsheet.changeCellFormula(theTestCellToken2, "1+A0");
		testSpreadsheet.changeCellFormula(theTestCellToken3, "B0-11");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printAllFormulas();

		String expectedOutput = "A1:3+1 A2:1+A0 \nB1:B0-11 B2: 0\n";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	/**
	 * Test method for {@link Spreadsheet#changeCellFormula(CellToken, String)}.
	 * Changes the cell formula at A0 to 4+2
	 */
	@Test
	void testChangeCellFormulaOnA0() {
		testSpreadsheet.changeCellFormula(theTestCellToken1, "4+2");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printCellFormula(theTestCellToken1);

		String expectedOutput = "4+2";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));

	}

	/**
	 * Test method for {@link Spreadsheet#changeCellFormulaAndRecalculate(CellToken, String)}.
	 * Changes the cell on A1 and recalculates the value of the cell.
	 */
	@Test
	void testChangeCellFormulaAndRecalculateOnA1() {
		testSpreadsheet.changeCellFormula(theTestCellToken1, "4+2");//A1
		testSpreadsheet.changeCellFormula(theTestCellToken2, "4+A1"); //A2
		testSpreadsheet.changeCellFormula(theTestCellToken3, "0");//B1
		testSpreadsheet.changeCellFormula(theTestCellToken4, "2+2"); //B2

		testSpreadsheet.changeCellFormulaAndRecalculate(theTestCellToken2, "1+1");
		//ByteArrayOutputStream output = new ByteArrayOutputStream();
		//System.setOut(new PrintStream(output));

		testSpreadsheet.printValues();


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
