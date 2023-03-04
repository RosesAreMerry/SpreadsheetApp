package Spreadsheet;

import java.util.LinkedList;
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
	  CellToken celltoken= new CellToken();
	  String token= null;
	  int value=0;
	  for (int i=0; i<cell.length; i++)
	  {
		  for(int j=0;j<cell[0].length;j++){
			  if(cell[i][j]!=null) {			  
			   celltoken.setColumn(j);
			   celltoken.setRow(i);
			   token=celltoken.printCellToken();
			  //System.out.print(token);
			  value=cell[i][j].getValue();
			  
			  }
			  else {
				  celltoken.setColumn(j);
				  celltoken.setRow(i);
				  token=celltoken.printCellToken();
				  value=0;
			  }
			  
			  System.out.println( token+ ": "+ value);
		  }
	  }
  }
  /**
   *  prints the formula of a single cell
   */
  public void printCellFormula(CellToken cellToken) {
	 CellToken celltoken= new CellToken();
	 String token =null;
	String theformula;
	int row=cellToken.getRow(); //get the row.  
	int column=cellToken.getColumn(); //get the column.
	if(cell[row][column]!= null) {
		   celltoken.setColumn(column);
		   celltoken.setRow(row);
		   token=celltoken.printCellToken();
	       theformula=cell[row][column].getFormula(); //get theformula in that specific row and column.
		System.out.println(token + ": "+ theformula);
	}
	else {
		celltoken.setColumn(column);
		celltoken.setRow(row);
		token=celltoken.printCellToken();
		theformula="0";
		System.out.println(token + theformula);
	}
	
	
  }  
  /**
   *  print formulas of all the cells in the spreadsheet 
   */
  public void printAllFormulas(){
	  String token= null;
	  CellToken celltoken= new CellToken();
	  for(int i=0;i<this.getNumRows();i++) {
		  String theformula= "0";
		  for(int j=0;j<this.getNumColumns();j++) {
			  if(cell[i][j]!=null) {
			   celltoken.setColumn(j);
			   celltoken.setRow(i);
			   token=celltoken.printCellToken();
			   theformula=cell[i][j].getFormula();
			   System.out.println(token + ": "+theformula);
			  }
			  else {
				  celltoken.setColumn(j);
				  celltoken.setRow(i);
				  token=celltoken.printCellToken();
				  System.out.println(token + ":"+ "0");

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
		  cell[row][column].recalculate();
	  }
	  else {		  
		 Cell newCell= new Cell(row, column, formula, lookupCell);
		 cell[row][column]=newCell;		 
	  }
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
	  changeCellFormula(cellToken, formula);
	  int row= cellToken.getRow();
	  int column= cellToken.getColumn();
	  //CellToken[] fomulatokens= cell[row][column].getDependencies();
	  //int numvertices= cell.length*cell[0].length;
	  int [][] graph = new int[row][column]; //matrix representation of the graph
	 
	  
	  
	  
	  
	 
	  
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