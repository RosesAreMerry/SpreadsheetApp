package Model.Spreadsheet;

import java.util.Stack;

import Model.Cell.Tokens.CellToken;
import Model.Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimentions = 8;
	
	
  public Spreadsheet(int dimentions) {
	  cell= new cell[dimentions][dimentions];
  }

  public void printValues() {
	  
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void printCellFormula(CellToken cellToken) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void printAllFormulas() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void changeCellFormula(CellToken cellToken, String formula) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack<Token> formula) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public int getNumRows() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public int getNumColumns() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}