/*
 * This is a spreadsheet class that that has various functionalities 
 */

/**
 * a spreadsheet class has various functionalities 
 * @author malihahossain 
 * @author Rosemary
 * @version 8th March 2023
 */
package Spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import Cell.Cell;
import Cell.Tokens.CellToken;

public class Spreadsheet {
	//two dimensional array of cells
	private Cell[][] cell;
	private static int myDimensions;
	private Function<CellToken, Cell> lookupCell = (c) -> {
		return cell[c.getRow()][c.getColumn()];
	};
	
	
	
/**
 *  constructor	initializes the spreadsheet
 *  @param dimension
**/
  public Spreadsheet(int dimensions) {
	  Spreadsheet.myDimensions= dimensions;
	  cell = new Cell[dimensions][dimensions];
  }
  
  /**
   * Clears the spreadsheet.
   */
  public void clear() {
	  for (int i = 0; i < myDimensions; i++) {
		  for (int j = 0; j < myDimensions; j++) {
			  if (cell[i][j] != null) {
				  cell[i][j] = null;
			  }
		  }
	  }
  }
  
  
 /**
  *  prints the values of a cell
  *  @returns the string
  */
  public String printValues() {
	  StringBuilder sb = new StringBuilder();
		for (int i = 0; i < myDimensions; i++) {
			for (int j = 0; j < myDimensions; j++) {
				char column = (char) (j % 26 + 65);
				if (cell[i][j] != null) {
					sb.append(column).append(i+1).append(": ").append(cell[i][j].getValue()).append(" ");
				} else {
					sb.append(column).append(i+1).append(": 0 ");
				}
			}
			sb.append("\n");
		}		
		return sb.toString();
  }

  /**
   * gets the formula of a single cell
   * @param cellToken
   */
  public String getCellFormula(CellToken cellToken) {
		String theFormula;
		int row = cellToken.getRow(); //get the row.  
		int column = cellToken.getColumn(); //get the column.
		if(cell[row][column] != null) {
			theFormula = cell[row][column].getFormula(); //get the formula in that specific row and column.
		} else {
			theFormula = "";
		}

		return theFormula;
	}

 /**
  * prints the formula of a single cell
  * @param cellToken
  * @return value
 */
  public int getCellValue(CellToken cellToken) {
		int row = cellToken.getRow(); //get the row.  
		int column = cellToken.getColumn(); //get the column.
		if(cell[row][column] != null) {
			return (cell[row][column].getValue()); //get the formula in that specific row and column and return
		} else {
			return 0;
		}
	}

  /**
   * print formulas of all the cells in the spreadsheet 
   * @return string- the formula at the cell
   */
  public String printAllFormulas(){	  
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < myDimensions; i++) {
			for (int j = 0; j < myDimensions; j++) {
				char column = (char) (j % 26 + 65);
				if (cell[i][j] != null) {
					sb.append(column).append(i+1).append(": ").append(cell[i][j].getFormula()).append(" ");
				} else {
					sb.append(column).append(i+1).append(": 0 ");
				}
			}
				sb.append(" \n");
		}
		return sb.toString();
}
  
  /**
   * Sets the current cellFomula to a new cellformula
   * @param CellToken celltoken
   * @param string formula
   * @throws InvalidParameterException
   */
  public void changeCellFormula(CellToken celltoken, String formula) throws InvalidParameterException {
	  int row = celltoken.getRow(); 
	  int column = celltoken.getColumn(); 
	  //set the formula to new formula 
	  if (cell[row][column] != null) {
		  cell[row][column].setFormula(formula);
		  cell[row][column].recalculate();
	  }
	  else {		  
		 Cell newCell = new Cell(row, column, formula, lookupCell);
		 cell[row][column] = newCell;
	  }
}
  /**
   * 
   * @param cellToken- a cellToken of type string
   * @param formula- a formula of type string
   * @throws InvalidParameterException
   */
  public void changeCellFormula(String cellToken, String formula) throws InvalidParameterException {
	  CellToken newCellToken = CellToken.getCellToken(cellToken);
	  changeCellFormula(newCellToken, formula);
  }
  
/**
 * changes the cell formula and recalculates the values of the spreadsheet.   
 * @param cellToken
 * @param formula
 * @throws InvalidParameterException, RuntimeException
 */
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) throws InvalidParameterException, RuntimeException {
	  changeCellFormula(cellToken, formula);
	  recalculateAll();
  }

  /**
   * changes the cell formula and recalculates the values of the spreadsheet.   
   * @param stringToken
   * @param formula
   * @ throws InvalidParameterException, RuntimeException
   */
  public void changeCellFormulaAndRecalculate(String stringToken, String formula) throws InvalidParameterException, RuntimeException {
  	  changeCellFormula(stringToken, formula);
  	  recalculateAll();
 }
  
   /**
	 * Create a topological sort of the spreadsheet.
	 * @return sorted Arraylist according to their topological order
	*/
	public ArrayList<Cell> topologicalSort() throws RuntimeException {
		Map<Cell, ArrayList<Cell>> topMap = new HashMap<Cell, ArrayList<Cell>>();

		for (int i = 0; i < myDimensions; i++) {
			for (int j = 0; j < myDimensions; j++) {
				Cell thisCell = cell[i][j];
				if (thisCell != null) {
					topMap.put(thisCell, thisCell.getDependencies());
				}
			}
		}

		// Things that need to get done:
		// 1. Create a map of all the cells and their dependencies
		// 2. Create a map of all the cells to their indegree

		Map<Cell, Integer> indegree = new HashMap<Cell, Integer>();
		for (Cell cell : topMap.keySet()) {
			indegree.put(cell, 0);
		}
		for (Cell cell : topMap.keySet()) {
			for (Cell dependency : topMap.get(cell)) {
				if (indegree.containsKey(dependency)) {
					indegree.put(dependency, indegree.get(dependency) + 1);
				}
			}
		}
		// 3. Create a queue of all the cells with indegree 0

		ArrayList<Cell> queue = new ArrayList<Cell>();
		for (Cell cell : indegree.keySet()) {
			if (indegree.get(cell) == 0) {
				queue.add(cell);
			}
		}
		// 4. While the queue is not empty:

		ArrayList<Cell> sortedList = new ArrayList<Cell>();

		while (queue.size() > 0) {
			// 4a. Pop a cell off the queue
			Cell cell = queue.remove(0);
			// 4b. Add it to the sorted list
			sortedList.add(cell);
			// 4c. For each of its dependencies:
			for (Cell dependency : topMap.get(cell)) {
				if (indegree.containsKey(dependency)) {
					// 4c1. Decrement its indegree
					indegree.put(dependency, indegree.get(dependency) - 1);
					// 4c2. If its indegree is 0, add it to the queue
					if (indegree.get(dependency) == 0) {
						queue.add(dependency);
					}
				}
			}
		}
		// 5. If the sorted list is not the same size as the map, there is a cycle
		if (sortedList.size() != topMap.size()) {
			throw new RuntimeException("Cycle detected");
		}
		// 6. Return the sorted list
		return sortedList;
	}

	/**
	 * Calculate the indegree
	 * @throws RuntimeException
	*/

	private void recalculateAll() throws RuntimeException {
		ArrayList<Cell> sortedCells = topologicalSort();
		// A topological sort will give us the cells that have no references first,
		// And the cells that other cells depend on later.
		// However, if we want to recalculate the spreadsheet, we need to start
		// with the cells that other cells depend on, and work our way back to the
		// cells that have no references.
		// However, reversing the list is expensive so we just traverse it backwards.
		for (int i = sortedCells.size() - 1; i >= 0; i--) {
			sortedCells.get(i).recalculate();
		}
	}
  
  /**
   * @return the number of rows in the spreadsheet 
   */
  public int getNumRows() {
    return cell.length;
  }
 /**
  * @return the number of columns in the spreadsheet
  */
  public int getNumColumns() {
    
    return cell[0].length;
  }
  /**
   * Generates a complete file of the spreadsheet.
   * @return The formatted text file of the spreadsheet.
   */
  public String generateFile() {
	  
	  StringBuilder sb = new StringBuilder();
	  
	  sb.append(myDimensions);
	  
	  for (int row = 0; row < cell.length; row++) {
		  for (int column = 0; column < cell[0].length; column++) {
			  if (cell[row][column] != null) {
				  sb.append("\n");
				  sb.append(generateColumnLabel(column));
				  sb.append(row + 1);
				  sb.append(';');
				  sb.append(cell[row][column].getFormula());
			  }
		  }
	  }
	  
	  return sb.toString();
  }
  /**
   * Returns a Spreadsheet object from a given file.
   * @param theFile The file to load the spreadsheet from.
   * @return The generated spreadsheet.
   */
  public static Spreadsheet generateLoadedSpreadsheet(File theFile) throws FileNotFoundException {

	  	Scanner scan = new Scanner(theFile);

	  	myDimensions = Integer.valueOf(scan.nextLine());

	  	Spreadsheet returnSpreadsheet = new Spreadsheet(myDimensions);

	  	while (scan.hasNext()) {
	  		String cellData = scan.nextLine();
			int seperationIndex = cellData.indexOf(';');
			String cellID = cellData.substring(0, seperationIndex);
			String cellFormula = cellData.substring(seperationIndex + 1, cellData.length());
			returnSpreadsheet.changeCellFormula(cellID, cellFormula);
	  	}

	  	scan.close();
	  	
	  	returnSpreadsheet.recalculateAll();

	  	return returnSpreadsheet;
  }
  /**
	 * Generates a column ID from a column index value.
	 * @param theColumn The column index
	 * @return The column ID.
	 */
	public static String generateColumnLabel(int theColumn) {	
		theColumn++;
		final String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int base = 1;
		int power = 0;
		String cellID = "";
		while (theColumn >= base) {
			power++;
			int count = (int) Math.pow(alphanumeric.length(), power - 1);
			int index = (((theColumn - 1) / count) + alphanumeric.length());
			if (power > 1) {
				index--;
			}
			index %= alphanumeric.length();
			cellID = alphanumeric.substring(index, index+1) + cellID;
			count *= alphanumeric.length();
			base += count;
		}
		return cellID;
	}
  
}