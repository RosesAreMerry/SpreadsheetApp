package Model.Cell.Tokens ;

public class CellToken extends Token {
  public static final int BADCELL = -1;

  private int row;
  private int column;

  public int getRow() {
    return row;
  }
  
  public int getColumn() {
    return column;
  }
  
  public void setRow(int row) {
    this.row = row;
  }
  
  public void setColumn(int column) {
    this.column = column;
  }

  /**
   *  Given a CellToken, print it out as it appears on the
   *  spreadsheet (e.g., "A3")
   *  @param cellToken  a CellToken
   *  @return  the cellToken's coordinates
   */
  public String printCellToken () {
    char ch;
    String returnString = "";
    int col;
    int largest = 26;  // minimum col number with number_of_digits digits
    int number_of_digits = 2;

    col = getColumn();

    // compute the biggest power of 26 that is less than or equal to col
    // We don't check for overflow of largest here.
    while (largest <= col) {
        largest = largest * 26;
        number_of_digits++;
    }
    largest = largest / 26;
    number_of_digits--;

    // append the column label, one character at a time
    while (number_of_digits > 1) {
        ch = (char) ((char) ((col / largest) - 1) + 'A');
        returnString += ch;
        col = col % largest;
        largest = largest  / 26;
        number_of_digits--;
    }

    // handle last digit
    ch = (char) (col + 'A');
    returnString += ch;

    // append the row as an integer
    returnString += getRow();

    return returnString;
  }
  
  /**
   * getCellToken
   * 
   * Assuming that the next chars in a String (at the given startIndex)
   * is a cell reference, set cellToken's column and row to the
   * cell's column and row.
   * If the cell reference is invalid, the row and column of the return CellToken
   * are both set to BadCell (which should be a final int that equals -1).
   * Also, return the index of the position in the string after processing
   * the cell reference.
   * (Possible improvement: instead of returning a CellToken with row and
   * column equal to BadCell, throw an exception that indicates a parsing error.)
   * 
   * A cell reference is defined to be a sequence of CAPITAL letters,
   * followed by a sequence of digits (0-9).  The letters refer to
   * columns as follows: A = 0, B = 1, C = 2, ..., Z = 25, AA = 26,
   * AB = 27, ..., AZ = 51, BA = 52, ..., ZA = 676, ..., ZZ = 701,
   * AAA = 702.  The digits represent the row number.
   *
   * @param inputString  the input string
   * @param startIndex  the index of the first char to process
   * @param cellToken  a cellToken (essentially a return value)
   * @return  index corresponding to the position in the string just after the cell reference
   */
  public static final int getCellToken (String inputString, int startIndex, CellToken cellToken) {
    char ch;
    int column = 0;
    int row = 0;
    int index = startIndex;

    // handle a bad startIndex
    // handle  a bad startIndex
    if ((startIndex < 0) || (startIndex >= inputString.length() )) {
        cellToken.setColumn(CellToken.BADCELL);
        cellToken.setRow(CellToken.BADCELL);
        return index;
    }

    // get rid of leading whitespace characters
    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (!Character.isWhitespace(ch)) {
            break;
        }
        index++;
    }
    if (index == inputString.length()) {
        // reached the end of the string before finding a capital letter
        cellToken.setColumn(CellToken.BADCELL);
        cellToken.setRow(CellToken.BADCELL);
        return index;
    }

    // ASSERT: index now points to the first non-whitespace character

    ch = inputString.charAt(index);            
    // process CAPITAL alphabetic characters to calculate the column
    if (!Character.isUpperCase(ch)) {
        cellToken.setColumn(CellToken.BADCELL);
        cellToken.setRow(CellToken.BADCELL);
        return index;
    } else {
        column = ch - 'A';
        index++;
    }

    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (Character.isUpperCase(ch)) {
            column = ((column + 1) * 26) + (ch - 'A');
            index++;
        } else {
            break;
        }
    }
    if (index == inputString.length() ) {
        // reached the end of the string before fully parsing the cell reference
        cellToken.setColumn(CellToken.BADCELL);
        cellToken.setRow(CellToken.BADCELL);
        return index;
    }

    // ASSERT: We have processed leading whitespace and the
    // capital letters of the cell reference

    // read numeric characters to calculate the row
    if (Character.isDigit(ch)) {
        row = ch - '0';
        index++;
    } else {
        cellToken.setColumn(CellToken.BADCELL);
        cellToken.setRow(CellToken.BADCELL);
        return index;
    }

    while (index < inputString.length() ) {
        ch = inputString.charAt(index);            
        if (Character.isDigit(ch)) {
            row = (row * 10) + (ch - '0');
            index++;
        } else {
            break;
        }
    }

    // successfully parsed a cell reference
    cellToken.setColumn(column);
    cellToken.setRow(row);
    return index;
  }

}
