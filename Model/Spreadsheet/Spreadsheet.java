package Spreadsheet;

import java.util.Stack;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimentions = 8;
	
	
  public Spreadsheet(int dimentions) {
	  cell = new Cell[dimentions][dimentions];
  }

  public void printValues() {
    //TODO(Write Method)
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