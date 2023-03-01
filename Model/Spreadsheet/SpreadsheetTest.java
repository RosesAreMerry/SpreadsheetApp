package Spreadsheet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;

class SpreadsheetTest {
	Function<CellToken, Integer> getCellToken = (cell) -> {
        return 2;
};
	int row=4;
	int column=4;
	String Formula= "5+3";
	Spreadsheet spreadsheet = new Spreadsheet(4);
    Cell cell= new Cell(row,column, Formula,getCellToken);
    CellToken celltoken= new CellToken();

	@Test
	void testPrintValues() {
		spreadsheet.printValues();
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
		String s = "5+3";
		spreadsheet.changeCellFormula(celltoken, s);
		assertEquals(s,  cell.getFormula());
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
