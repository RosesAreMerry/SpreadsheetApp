package Spreadsheet;

import java.util.Stack;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimensions = 4;
	
	
  public Spreadsheet(int dimensions) {
	  this.dimensions= dimensions;
	  cell = new Cell[dimensions][dimensions];
  }

  public void printValues() {	  
	  for (int i=0; i<cell.length; i++)
	  {
		  for(int j=0;j<cell[0].length;j++){
			  System.out.println(cell[i][j].getValue()+ " ");
		  }
	  }
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