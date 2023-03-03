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
	private Function<CellToken, Cell> lookupCell = (c) -> {
		return cell[c.getRow()][c.getColumn()];
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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				char row = (char) (i % 26 + 65);
				if (cell[i][j] != null) {
					sb.append(row).append(j + 1).append(":").append(cell[i][j].getValue()).append(" ");
				} else {
					sb.append(row).append(j + 1).append(":0 ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
  }
  /**
   *  prints the formula of a single cell
   */
  public void printCellFormula(CellToken cellToken) {
		String theFormula;
		int row = cellToken.getRow(); //get the row.  
		int column = cellToken.getColumn(); //get the column.
		if(cell[row][column] != null) {
			theFormula = cell[row][column].getFormula(); //get the formula in that specific row and column.
		}
		else {
			theFormula = "";
		}
		System.out.println(theFormula);
	}


  /**
   *  print formulas of all the cells in the spreadsheet 
   */
  public void printAllFormulas(){	  
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				char row = (char) (i % 26 + 65);
				if (cell[i][j] != null) {
					sb.append(row).append(j + 1).append(":").append(cell[i][j].getFormula()).append(" ");
				} else {
					sb.append(row).append(j + 1).append(":0 ");
				}
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
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
	  int cellrow= cellToken.getRow();
	  int cellcoulumn= cellToken.getColumn();
	  changeCellFormula( cellToken, formula);
	  
	  
	  //CellToken[] d= cell[cellrow][cellcoulumn].getDependencies();
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