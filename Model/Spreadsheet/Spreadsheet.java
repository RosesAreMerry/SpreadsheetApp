package Spreadsheet;

import java.util.Stack;

import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimentions = 8;
	
	
  public Spreadsheet(int dimentions) {
<<<<<<< HEAD
	  cell= new cell[dimentions][dimentions];
=======
    //TODO(Write Constructor) 
>>>>>>> branch 'master' of https://github.com/RosesAreMerry/SpreadsheetApp.git
  }

  public void printValues() {
<<<<<<< HEAD
	  
    throw new UnsupportedOperationException("Not supported yet.");
=======
    //TODO(Write Method)
>>>>>>> branch 'master' of https://github.com/RosesAreMerry/SpreadsheetApp.git
  }
  
  public void printCellFormula(CellToken cellToken) {
    //TODO(Write Method)
  }
  
  public void printAllFormulas() {
    //TODO(Write Method)
  }
  
  public void changeCellFormula(CellToken cellToken, String formula) {
    //TODO(Write Method)
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
    //TODO(Write Method)
  }

  public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack<Token> formula) {
    //TODO(Write Method)
  }
  
  public int getNumRows() {
    //TODO(Write Method)
    return 0;
  }
  
  public int getNumColumns() {
    //TODO(Write Method)
    return 0;
  }
}