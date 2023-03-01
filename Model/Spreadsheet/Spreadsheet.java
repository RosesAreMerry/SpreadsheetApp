package Spreadsheet;

import java.util.Stack;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimensions = 4;
	
	
	
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
	  for (int i=0; i<cell.length; i++)
	  {
		  for(int j=0;j<cell[0].length;j++){
			  System.out.println(cell[i][j].getValue()+ " ");
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
	}
	theformula="0";
	
	
	
	
  }  
  /**
   *  print formulas of all the cells in the spreadsheet 
   */
  public void printAllFormulas(){
	  for(int i=0;i<this.getNumRows();i++) {
		  for(int j=0;j<this.getNumColumns();j++) {
			  String theformula=cell[i][j].getFormula();
			  System.out.println(theformula);
		  }
	  }    
  }
  
  /**
   * changes the cellformula to a new formula 
   * @param cellToken
   * @param formula
   */
  
  public void changeCellFormula(CellToken cellToken, String formula) {
	  int row=cellToken.getRow(); //get the row.  
	  int column=cellToken.getColumn(); //get the column.
	  cell[row][column].setFormula(formula);
	  System.out.println(cell[row][column].getFormula());
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
   
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