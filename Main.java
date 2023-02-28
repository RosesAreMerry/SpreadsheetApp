import java.util.function.Function;

import Cell.Cell;
import Cell.Tokens.CellToken;


public class Main {

  public static void main(String[] args) {
	    
    Function<CellToken, Integer> getCellToken = (cell) -> {
      return 0;
    };

    Cell cell = new Cell(1, 1, "5*(5-1*5/2)*3", getCellToken);
    System.out.println(cell.getValue());
    System.out.println(cell.getFormula());
    

  }

}
