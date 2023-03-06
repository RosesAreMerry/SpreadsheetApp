
	import java.util.function.Function;

	import Cell.Cell;
	import Cell.Tokens.CellToken;
	import Spreadsheet.Spreadsheet;


	public class TestSpreadsheet {

	  public static void main(String[] args) {
		  
		  Spreadsheet testSpreadsheet = new Spreadsheet(2);

			/**
			 * Test CellToken for A0
			 */
			 CellToken theTestCellToken1=new CellToken();

			/**
			 * Test CellToken for A1
			 */
		    CellToken theTestCellToken2=new CellToken();

			/**
			 * Test CellToken for B0
			 */
			 CellToken theTestCellToken3=new CellToken();

			/**
			 * Test CellToken for B1
			 */
			 CellToken theTestCellToken4=new CellToken();

		    
//	    Function<CellToken, Integer> getCellToken = (cell) -> {
//	      return 0;
//	    };
//	    
//	    CellToken celltoken=new CellToken();
//	    
//	     celltoken.setColumn(1);
//	     celltoken.setRow(1);
//	     
//	     CellToken celltoken1=new CellToken();
//	     celltoken1.setColumn(0);
//	     celltoken1.setRow(0);
//	     
//	     CellToken celltoken2=new CellToken();
//	     celltoken2.setRow(0);
//	     celltoken2.setColumn(1);
	     
//	     CellToken celltoken3=new CellToken();
//	     celltoken2.setRow(1);
//	     celltoken2.setColumn(0);
	     
	    
			theTestCellToken1.setRow(0);
			theTestCellToken1.setColumn(0); //A0
			theTestCellToken2.setRow(0);
			theTestCellToken2.setColumn(1); //A1
			theTestCellToken3.setRow(1);
			theTestCellToken3.setColumn(0);// B0
			theTestCellToken4.setRow(1);
			theTestCellToken4.setColumn(1);// B1   
	    
	    
	    Spreadsheet spreadsheet= new Spreadsheet(2);
	    spreadsheet.changeCellFormula( theTestCellToken1, "5+5");
	    spreadsheet.changeCellFormula( theTestCellToken2, "5+4");
	    spreadsheet.changeCellFormula( theTestCellToken3,  "(B1)/A0");
	    spreadsheet.changeCellFormula( theTestCellToken4,  "B0+B1");
	    //spreadsheet.changeCellFormulaAndRecalculate(celltoken2, null);
	    //spreadsheet.changeCellFormulaAndRecalculate(theTestCellToken2, " A0+B1");
	    System.out.println(spreadsheet.printAllFormulas());
	    System.out.println(spreadsheet.printValues());
	   
	  
	    System.out.println();
	    //spreadsheet.printCellFormula(celltoken3);
	    //spreadsheet.printCellFormula(celltoken);
	    
	    
//	    Cell cell = new Cell(1, 1, "5*(5-1*5/2)*3", getCellToken);
//	    System.out.println(cell.getValue());
//	    System.out.println(cell.getFormula());
	    
	    

//	    System.out.println("the number of column: "+spreadsheet.getNumColumns());
//	    System.out.println("changing the formula : ");
//	    spreadsheet.changeCellFormula(celltoken, "5+3");
//	    spreadsheet.printAllFormulas();
//	    System.out.println("changing the formula : ");
//	    spreadsheet.printAllFormulas();
//	    spreadsheet.changeCellFormula(celltoken, "5+8"); 
//	    System.out.println("print : ");
//	    spreadsheet.printCellFormula(celltoken);
//	    System.out.println("printall values : ");
//	    spreadsheet.printValues();
//	    System.out.println("printall formula : ");
//	    spreadsheet.printAllFormulas();
	    
	    

	  }

	}


