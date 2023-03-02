/*

 */
package Model.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import Cell.Cell;
import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class SpreadsheetTest {


	Function<CellToken, Integer> getCellToken1 = (cell) -> {
		return 2;
	};
	Spreadsheet testSpreadsheet = new Spreadsheet(2);

	Cell testCell = new Cell(1, 1, "1+1", getCellToken1 );

	CellToken theTestCellToken1=new CellToken();

	CellToken theTestCellToken2=new CellToken();


	@Test
	void testPrintValues() {

		theTestCellToken1.setRow(0);
		theTestCellToken1.setColumn(0);
		theTestCellToken2.setRow(1);
		theTestCellToken2.setColumn(0);
		//Change the formula at A1 to 1+1
		testSpreadsheet.changeCellFormula(theTestCellToken1, "1+1");

		//Change the formula at B1 to 2+2
		testSpreadsheet.changeCellFormula(theTestCellToken2, "2+	2");
		//Capture the output

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));

		testSpreadsheet.printValues();

		String expectedOutput = "2040";

		assertEquals(expectedOutput, output.toString().replace("\r\n", ""));
	}

	@Test
	void testPrintCellFormula() {
		fail("Not yet implemented");
	}

	@Test
	void testPrintAllFormulas() {
		fail("Not yet implemented");
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
