import java.util.function.Function;

import Cell.Cell;
import Cell.Tokens.CellToken;

public class Main {

  public static void main(String[] args) {
    Function<CellToken, Integer> getCellToken = (cell) -> {
      return 0;
    };
    Cell cell = new Cell(1, 1, "2*2+1+5+3*2", getCellToken);

    System.out.println(cell.getValue());
    System.out.println(cell.getFormula());

  }

}
