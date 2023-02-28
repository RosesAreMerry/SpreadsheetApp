package Spreadsheet;

import java.util.Stack;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Cell.Tokens.Token;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int dimensions = 4;
	
	int numrows;
	int nucolumns;
	
	
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
	int row=cellToken.getRow(); //get the row.  
	int column=cellToken.getColumn(); //get the column.
	String theformula=cell[row][column].getFormula(); //get theformula in that specific row and column. 
	
	System.out.print(theformula);
	
  }  
  public void printAllFormulas(){
	  for(int i=0;i<this.getNumRows();i++) {
		  for(int j=0;j<this.getNumColumns();j++) {
			  String theformula=cell[i][j].getFormula();
			  System.out.println(theformula);
		  }
	  }    
  }
  
  public void changeCellFormula(CellToken cellToken, String formula) {
	  int row=cellToken.getRow(); //get the row.  
	  int column=cellToken.getColumn(); //get the column.
	  cell[row][column].setFormula(formula);
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
   
  }

  public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack<Token> formula) {
    //TODO(Write Method)
  }
  
  public int getNumRows() {
    
    return cell.length;
  }
  
  public int getNumColumns() {
    
    return cell[0].length;
  }
}