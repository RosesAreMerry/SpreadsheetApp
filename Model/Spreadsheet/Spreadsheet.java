package Spreadsheet;

import java.util.Stack;
import java.util.function.Function;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimensions;;
	private Function<CellToken, Integer> lookupCell = (c) -> {
		return cell[c.getRow()][c.getColumn()].getValue();
		};
	
	
	
/**
 *  constructor	initializes the spreadsheet
 */
  public Spreadsheet(int dimensions) {
	  Spreadsheet.dimensions= dimensions;
	  cell = new Cell[dimensions][dimensions];
	  
  }
 /**
  *  prints the values of the cell
  */
  public void printValues() {
	  int value=0;
	  for (int i=0; i<cell.length; i++)
	  {
		  for(int j=0;j<cell[0].length;j++){
			  if(cell[i][j]!=null) {
			  value=cell[i][j].getValue();
			  }
			  else {
				  value=0;
			  }
			  System.out.println(value);
		  }
	  }
  }
  /**
   *  prints the formula of a single cell
   */
  public void printCellFormula(CellToken cellToken) {
	String theformula;
	int row=cellToken.getRow(); //get the row.  
	int column=cellToken.getColumn(); //get the column.
	if(cell[row][column]!= null) {
	   theformula=cell[row][column].getFormula(); //get theformula in that specific row and column.
		System.out.println(theformula);
	}
	else {
	theformula="0";
	System.out.println(theformula);
	}
	
	
  }  
  /**
   *  print formulas of all the cells in the spreadsheet 
   */
  public void printAllFormulas(){	  
	  for(int i=0;i<this.getNumRows();i++) {
		  String theformula= "0";
		  for(int j=0;j<this.getNumColumns();j++) {
			  if(cell[i][j]!=null) {
			  theformula=cell[i][j].getFormula();
			  System.out.println(theformula);
			  }
			  else {
				  theformula="0";
				  System.out.println(theformula);
	}
 }
		  
		 
	  }    
  }
  
  /**
   * changes the cellformula to a new formula 
   * @param cellToken
   * @param formula
   */
  
  public void changeCellFormula(CellToken cellToken, String formula) {
	  String noFormula= "0";
	  int row=cellToken.getRow(); //get the row.  
	  int column=cellToken.getColumn(); //get the column.
	  if (cell[row][column]!= null) {
		  cell[row][column].setFormula(formula);
		 
	  }
	  else {		  
		 Cell newCell= new Cell(row, column, formula, lookupCell);
		 cell[row][column]=newCell;
		 
	  }
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
	  int cellrow= cellToken.getRow();
	  int cellcoulumn= cellToken.getColumn();
	  
     
  }

  public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack<Token> formula) {
    //TODO(Write Method)
  }
  
  /**
   * returns the number of rows in the spreadsheet 
   */
  public int getNumRows() {
    
    return cell.length;
  }
 /**
  * returns the number of columns in the spreadsheet 
  * @return
  */
  public int getNumColumns() {
    
    return cell[0].length;
  }
}