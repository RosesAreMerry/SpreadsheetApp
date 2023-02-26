package Cell.Tokens;

public class Token {

  /**
   * Return a string associated with a token
   * @param expTreeToken  an ExpressionTreeToken
   * @return a String associated with expTreeToken
   */
  public String printExpressionTreeToken (Token expTreeToken) {
    String returnString = "";

    if (expTreeToken instanceof OperatorToken) {
        returnString = ((OperatorToken) expTreeToken).getOperator() + " ";
    } else if (expTreeToken instanceof CellToken) {
        returnString = ((CellToken) expTreeToken).printCellToken() + " ";
    } else if (expTreeToken instanceof LiteralToken) {
        returnString = ((LiteralToken) expTreeToken).getValue() + " ";
    } else {
        // This case should NEVER happen
        System.out.println("Error in printExpressionTreeToken.");
        System.exit(0);
    }
    return returnString;
  }


}
