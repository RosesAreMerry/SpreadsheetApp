import java.util.function.Function;

import Cell.Cell;
import Cell.Tokens.CellToken;
import Spreadsheet.Spreadsheet;


public class Main {

  public static void main(String[] args) {
	    
    Function<CellToken, Integer> getCellToken = (cell) -> {
      return 0;
    };
    
   
    CellToken celltoken=new CellToken();
    celltoken.setRow(2);
    celltoken.setColumn(2);
    
    Spreadsheet spreadsheet= new Spreadsheet(4);
    Cell cell = new Cell(1, 1, "5*(5-1*5/2)*3", getCellToken);
    System.out.println(cell.getValue());
    System.out.println(cell.getFormula());
    System.out.println("the number of column: "+spreadsheet.getNumColumns());
    spreadsheet.changeCellFormula(celltoken, "5+3");    
    spreadsheet.printCellFormula(celltoken);
    spreadsheet.printValues();
    
    

  }

}
