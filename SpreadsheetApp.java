/*
 * Driver program of a spreadsheet application.
 * Text-based user interface.
 *
 * @author Donald Chinn
 */

public class SpreadsheetApp {
    
    /**
     * Read a string from standard input.
     * All characters up to the first carriage return are read.
     * The return string does not include the carriage return.
     * @return  a line of input from standard input
     */
    public static String readString() {
        BufferedReader inputReader;
        String returnString = "";
        char ch;
        
        inputReader = new BufferedReader (new InputStreamReader(System.in));
        
        // read all characters up to a carriage return and append them
        // to the return String
        try {
             returnString = inputReader.readLine();
        }
        catch (IOException e) {
            System.out.println("Error in reading characters in readString.");
        }
        return returnString;
    }
    
    private static void menuPrintValues(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printValues();
    }
    
    private static void menuPrintCellFormula(Spreadsheet theSpreadsheet) {
        CellToken cellToken = new CellToken();
        String inputString;
    
        System.out.println("Enter the cell: ");
        inputString = readString();
        getCellToken(inputString, 0, cellToken);
    
        System.out.println(printCellToken(cellID));
        System.out.println(": ");
    
        if ((cellToken.getRow() < 0) ||
            (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
            (cellToken.getColumn() < 0) ||
            (cellToken.getColumn() >= theSpreadsheet.getNumColumns())) {
            
            System.out.println("Bad cell.");
            return;
        }
    
        theSpreadsheet.printCellFormula(cellToken);
        System.out.println();
    }
    
    private static void menuPrintAllFormulas(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printAllFormulas();
        System.out.println();
    }
    
    
    private static void menuChangeCellFormula(Spreadsheet theSpreadsheet) {
        String inputCell;
        String inputFormula;
        CellToken cellToken;
        Stack expTreeTokenStack;
        // ExpressionTreeToken expTreeToken;
    
        System.out.println("Enter the cell to change: ");
        inputCell = readString();
        theSpreadsheet.getCellToken (inputCell, 0, cellToken);
    
        // error check to make sure the row and column
        // are within spreadsheet array bounds.
        if ((cellToken.getRow() < 0) ||
            (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
            (cellToken.getColumn() < 0) ||
            (cellToken.getColumn() >= theSpreadsheet.getNumColumns()) ) {
            
            System.out.println("Bad cell.");
            return;
        }
    
        System.out.println("Enter the cell's new formula: ");
        inputFormula = readString();
        expTreeTokenStack = getFormula (inputFormula);
    
        /*
        // This code prints out the expression stack from
        // top to bottom (that is, reverse of postfix).
        while (!expTreeTokenStack.isEmpty())
        {
            expTreeToken = expTreeTokenStack.topAndPop();
            printExpressionTreeToken(expTreeToken);
        }
        */
    
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack);
        System.out.println();
    }
    
    public static void main(String[] args) {
        Spreadsheet theSpreadsheet = new Spreadsheet(8);

        bool done = false;
        String command = "";
    
        System.out.println(">>> Welcome to the TCSS 342 Spreadsheet <<<");
        System.out.println();
        System.out.println();
    
        while (!done) {
            System.out.println("Choose from the following commands:");
            System.out.println();
            System.out.println("p: print out the values in the spreadsheet");
            System.out.println("f: print out a cell's formula");
            System.out.println("a: print all cell formulas");
            System.out.println("c: change the formula of a cell");
    /* BONUS
            System.out.println("r: read in a spreadsheet from a textfile");
            System.out.println("s: save the spreadsheet to a textfile");
     */
            System.out.println();
            System.out.println("q: quit");
    
            System.out.println();
            System.out.println("Enter your command: ");
            command = readString();
    
            // We care only about the first character of the string
            switch (command.charAt(0)) {
                case 'p':
                    menuPrintValues(theSpreadsheet);
                    break;
        
                case 'f':
                    menuPrintCellFormula(theSpreadsheet);
                    break;
        
                case 'a':
                    menuPrintAllFormulas(theSpreadsheet);
                    break;
        
                case 'c':
                    menuChangeCellFormula(theSpreadsheet);
                    break;
        
                    /* BONUS:
                case 'r':
                    menuReadSpreadsheet(theSpreadsheet);
                    break;
        
                case 's':
                    menuSaveSpreadsheet(theSpreadsheet);
                    break;
                    */
        
                case 'q':
                    done = true;
                    break;
        
                default:
                    System.out.println(command.charAt(0) + ": Bad command.");
                    break;
            }
            System.out.println();
    
        }
    
        System.out.println("Thank you for using our spreadsheet.");
    }

}