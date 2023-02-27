package Cell;

import java.util.Stack;
import java.util.function.Function;

import Cell.Tokens.CellToken;
import Cell.Tokens.LiteralToken;
import Cell.Tokens.OperatorToken;
import Cell.Tokens.Token;

public class Cell {

  private int row;
  private int column;
  private String formula;
  private Stack<Token> postfixFormula;
  private Function<CellToken, Integer> getCellValue;
  private int value;

  public Cell(int row, int column, String formula, Function<CellToken, Integer> getCellValue) {
    setRow(row);
    setColumn(column);
    setFormula(formula);
    this.getCellValue = getCellValue;
    recalculate();
  }

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
  
  public void setFormula(String formula) {
    this.formula = formula;
    postfixFormula = getFormula(formula);
  }
  
  public String getFormula() {
    return formula;
  }
  
  public int getValue() {
    return value;
  }

  public void recalculate() {
    Integer val1 = null;
    Integer val2 = null;
    int result = 0;

    while (!postfixFormula.isEmpty()) {
      Token token = postfixFormula.pop();
      Integer value = null;
      if (token instanceof LiteralToken) {
        value = ((LiteralToken) token).getValue();
      } else if (token instanceof CellToken) {
        value = getCellValue.apply((CellToken) token);
      } else if (token instanceof OperatorToken) {
        if (val1 != null && val2 != null) {
          value = ((OperatorToken) token).evaluate(val1, val2);
          postfixFormula.push(new LiteralToken(value));
          val1 = null;
          val2 = null;
        } else if (val1 != null) {
          val2 = ((OperatorToken) token).evaluate(val1, 0);
        } else {
          val1 = ((OperatorToken) token).evaluate(0, 0);
        }
        continue;
      }
      if (value != null) {
        if (val1 == null) {
          val1 = value;
        } else if (val2 == null) {
          val2 = value;
        }
      }
      if (postfixFormula.size() == 0) {
        result = value;
      }
    }
    value = result;
  }

  /**
   * getFormula
   * 
   * Given a string that represents a formula that is an infix
   * expression, return a stack of Tokens so that the expression,
   * when read from the bottom of the stack to the top of the stack,
   * is a postfix expression.
   * 
   * A formula is defined as a sequence of tokens that represents
   * a legal infix expression.
   * 
   * A token can consist of a numeric literal, a cell reference, or an
   * operator (+, -, *, /).
   * 
   * Multiplication (*) and division (/) have higher precedence than
   * addition (+) and subtraction (-).  Among operations within the same
   * level of precedence, grouping is from left to right.
   * 
   * This algorithm follows the algorithm described in Weiss, pages 105-108.
   */
  public static Stack<Token> getFormula(String formula) {
    Stack<Token> returnStack = new Stack<Token>();  // stack of Tokens (representing a postfix expression)
    boolean error = false;
    char ch = ' ';

    int literalValue = 0;

    int index = 0;  // index into formula
    Stack<Token> operatorStack = new Stack<Token>();  // stack of operators

    while (index < formula.length() ) {
        // get rid of leading whitespace characters
        while (index < formula.length() ) {
            ch = formula.charAt(index);
            if (!Character.isWhitespace(ch)) {
                break;
            }
            index++;
        }

        if (index == formula.length() ) {
            error = true;
            break;
        }

        // ASSERT: ch now contains the first character of the next token.
        if (OperatorToken.isOperator(ch)) {
            // We found an operator token
            switch (ch) {
                case OperatorToken.Plus:
                case OperatorToken.Minus:
                case OperatorToken.Mult:
                case OperatorToken.Div:
                case OperatorToken.LeftParen:
                    // push operatorTokens onto the output stack until
                    // we reach an operator on the operator stack that has
                    // lower priority than the current one.
                    OperatorToken stackOperator;
                    while (!operatorStack.isEmpty()) {
                        stackOperator = (OperatorToken) operatorStack.firstElement();
                        if ( (OperatorToken.priority(stackOperator.getOperator()) >= OperatorToken.priority(ch)) &&
                            (stackOperator.getOperator() != OperatorToken.LeftParen) ) {

                            // output the operator to the return stack    
                            operatorStack.pop();
                            returnStack.push(stackOperator);
                        } else {
                            break;
                        }
                    }
                    break;

                default:
                    // This case should NEVER happen
                    System.out.println("Error in getFormula.");
                    System.exit(0);
                    break;
            }
            // push the operator on the operator stack
            operatorStack.push(new OperatorToken(ch));

            index++;

        } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
            OperatorToken stackOperator;
            stackOperator = (OperatorToken) operatorStack.pop();
            // This code does not handle operatorStack underflow.
            while (stackOperator.getOperator() != OperatorToken.LeftParen) {
                // pop operators off the stack until a LeftParen appears and
                // place the operators on the output stack
                returnStack.push(stackOperator);
                stackOperator = (OperatorToken) operatorStack.pop();
            }

            index++;
        } else if (Character.isDigit(ch)) {
            // We found a literal token
            literalValue = ch - '0';
            index++;
            while (index < formula.length()) {
                ch = formula.charAt(index);
                if (Character.isDigit(ch)) {
                    literalValue = (literalValue * 10) + (ch - '0');
                    index++;
                } else {
                    break;
                }
            }
            // place the literal on the output stack
            returnStack.push(new LiteralToken(literalValue));

        } else if (Character.isUpperCase(ch)) {
            // We found a cell reference token
            CellToken cellToken = new CellToken();
            index = CellToken.getCellToken(formula, index, cellToken);
            if (cellToken.getRow() == CellToken.BADCELL) {
                error = true;
                break;
            } else {
                // place the cell reference on the output stack
                returnStack.push(cellToken);
            }

        } else {
            error = true;
            break;
        }
    }

    // pop all remaining operators off the operator stack
    while (!operatorStack.isEmpty()) {
        returnStack.push(operatorStack.pop());
    }

    if (error) {
        // a parse error; return the empty stack
        returnStack.clear();
    }
    Stack<Token> temp = new Stack<Token>();
   
    while (returnStack.empty() == false)
    {
      temp.push(returnStack.peek());
      returnStack.pop();
    }  
   

    return temp;
  }
  
}
