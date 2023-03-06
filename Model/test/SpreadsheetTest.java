/*

 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import Cell.Cell;
import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SpreadsheetTest {


	Spreadsheet testSpreadsheet = new Spreadsheet(2);

<<<<<<< HEAD
	Cell testCell;
=======
>>>>>>> branch 'master' of https://github.com/RosesAreMerry/SpreadsheetApp.git

	static CellToken theTestCellToken1=new CellToken();

	static CellToken theTestCellToken2=new CellToken();

	static CellToken theTestCellToken3=new CellToken();

	@BeforeAll
	static void beforeAll()
	{
		theTestCellToken1.setRow(0);
		theTestCellToken1.setColumn(0);
		theTestCellToken2.setRow(0);
		theTestCellToken2.setColumn(1);
		theTestCellToken3.setRow(1);
		theTestCellToken3.setColumn(0);
	}
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

		String expectedOutput = "2400";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

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
	@Test
	void testPrintCellFormulaatB2() {
		//Change the formula at B1 to 8-9
		testSpreadsheet.changeCellFormula(theTestCellToken3, "8-9");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printCellFormula(theTestCellToken3);

		String expectedOutput = "8-9";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	@Test
	void testPrintAllFormulas() {
		testSpreadsheet.changeCellFormula(theTestCellToken1, "3+1");
		testSpreadsheet.changeCellFormula(theTestCellToken2, "1+A0");
		testSpreadsheet.changeCellFormula(theTestCellToken3, "B0-11");

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printAllFormulas();

		String expectedOutput = "A1:3+1 A2:1+A0 \n" +
				"B1:B0-11 B2: 0\n";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	@Test
	void testChangeCellFormula() {
	}

	@Test
	void testGetNumRows() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNumColumns() {
		fail("Not yet implemented");
	}

}
