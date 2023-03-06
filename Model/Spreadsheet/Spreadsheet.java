package Spreadsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import Cell.Cell;
import Cell.Tokens.CellToken;

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
		} else {
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
					sb.append(row).append(j + 1).append(": 0");
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
	  int row = cellToken.getRow(); // get the row.  
	  int column = cellToken.getColumn(); // get the column.
	  if (cell[row][column] != null) {
		  cell[row][column].setFormula(formula);
			recalculateAll();
	  }
	  else {		  
		 Cell newCell= new Cell(row, column, formula, lookupCell);
		 cell[row][column]=newCell;		 
	  }
  }
  
  public void changeCellFormulaAndRecalculate(CellToken cellToken, String formula) {
	  changeCellFormula(cellToken, formula);
	  recalculateAll();
  }

	/**
	 * Create a topological sort of the spreadsheet.
	 */
	public ArrayList<Cell> topologicalSort() {
		Map<Cell, ArrayList<Cell>> topMap = new HashMap<Cell, ArrayList<Cell>>();

		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
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
				indegree.put(dependency, indegree.get(dependency) + 1);
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
				// 4c1. Decrement its indegree
				indegree.put(dependency, indegree.get(dependency) - 1);
				// 4c2. If its indegree is 0, add it to the queue
				if (indegree.get(dependency) == 0) {
					queue.add(dependency);
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
	 */

	private void recalculateAll() {
		ArrayList<Cell> sortedCells = topologicalSort();
		for (Cell cell : sortedCells) {
			cell.recalculate();
		}
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