package Cell;

import java.util.Stack;
import java.util.function.Function;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import Cell.Tokens.CellToken;
import Cell.Tokens.LiteralToken;
import Cell.Tokens.OperatorToken;
import Cell.Tokens.Token;

/**
 * The Cell class handles keeping the function of a cell and calculating its value.
 * To calculate the value of a cell, it uses a postfix formula provided by the function getFormula.
 * It also uses a function to get the value of a cell token from the Spreadsheet.
 * 
 * @author Rosemary
 */
public class Cell {

  private int row;
  private int column;
  private String formula;
  private Stack<Token> postfixFormula;
  private final Function<CellToken, Cell> getCellValue;
  private int value;

  public Cell(int row, int column, String formula, Function<CellToken, Cell> getCellValue) {
    setRow(row);
    setColumn(column);
    setFormula(formula);
    this.getCellValue = getCellValue;
    recalculate();
  }

  /**
   * Return the row of this cell.
   */
  public int getRow() {
    return row;
  }

  /**
   * Return the column of this cell.
   */
  public int getColumn() {
    return column;
  }
  
  /**
   * Set the row of this cell.
   * @param row
   */
  public void setRow(int row) {
    this.row = row;
  }
  
  /**
   * Set the column of this cell.
   * @param column
   */
  public void setColumn(int column) {
    this.column = column;
  }
  
  /**
   * Set the formula of this cell and calculate the postfix formula.
   * @param formula
   * @throws InvalidParameterException
   */
  public void setFormula(String formula) throws InvalidParameterException {
    this.formula = formula;
    postfixFormula = getFormula(formula);
  }
  
  /**
   * Return the formula of this cell.
   */
  public String getFormula() {
    return formula;
  }
  
  /**
   * Return the value of this cell.
   */
  public int getValue() {
    return value;
  }


  /** 
   * Function that returns a list of all cells that this cell depends on.
   * 
   * @return ArrayList<Cell> list of all cells that this cell depends on.
   */
  public ArrayList<Cell> getDependencies() {
    ArrayList<Cell> dependencies = new ArrayList<Cell>();
      for (Token token : postfixFormula) {
          if (token instanceof CellToken) {
              dependencies.add(getCellValue.apply((CellToken) token));
          }
      }
      return dependencies;
  }

  /**
   * Recalculate the value of this cell using the postfix formula.
   * 
   * @throws RuntimeException
   */
  public void recalculate() throws RuntimeException {

    // initialize the stack of values.
    Stack<Integer> currentValues = new Stack<Integer>();

    // prepare return variable.
    int result = 0;

    // copy the postfix formula to a new stack.
    Stack<Token> formula = new Stack<Token>();
    formula.addAll(postfixFormula);

    // while the formula is not empty, pop the top token and evaluate it.
    while (!formula.isEmpty()) {
      Token token = formula.pop();

      if (token instanceof LiteralToken) {
        
        // if the token is a literal, push it onto the stack of values.
        currentValues.push(((LiteralToken) token).getValue());

      } else if (token instanceof CellToken) {
        
        // if the token is a cell, push the value of the cell onto the stack of values.
        Cell cell = getCellValue.apply((CellToken) token);

        if (cell != null) {

          // if the cell is negated, push the negated value onto the stack of values.
          if (((CellToken) token).isNegated()) {
            currentValues.push(-getCellValue.apply((CellToken) token).getValue());
          } else {
            currentValues.push(getCellValue.apply((CellToken) token).getValue());
          }
        } else {
          // if the cell is null, push 0 onto the stack of values.
          currentValues.push(0);
        }
      } else if (token instanceof OperatorToken) {
        // if the token is an operator, pop the top two values from the stack of values,
        // evaluate the operator with those values, and push the result onto the formula stack.
        if (currentValues.size() >= 2) {
          int value = ((OperatorToken) token).evaluate(currentValues.pop(), currentValues.pop());
          formula.push(new LiteralToken(value));
        } else {
          // if there are not enough values on the stack of values, this is a sign that the formula is invalid.
          throw new RuntimeException("Bad Formula: " + this.formula);
        }
      }

      // if the formula is a single literal, set the result to that literal.
      if (formula.size() == 1 && formula.peek() instanceof LiteralToken) {

        if (currentValues.size() > 0) {
          // if there are still values on the stack of values, this is a sign that the formula is invalid.
          throw new RuntimeException("Bad Formula: " + this.formula);
        }

        result = ((LiteralToken) formula.pop()).getValue();
      }

      if (formula.isEmpty() && currentValues.size() == 1) {
        // if the formula is empty and there is one value on the stack of values, set the result to that value.
        // This is the case when the formula is a single cell reference or literal.
        result = currentValues.pop();
      }
    }

    // set the value of this cell to the result.
    this.value = result;
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
   * 
   * @throws InvalidParameterException if the formula is not a legal infix expression.
   */
  public static Stack<Token> getFormula(String formula) throws InvalidParameterException {
    Stack<Token> returnStack = new Stack<Token>();  // stack of Tokens (representing a postfix expression)
    boolean error = false;
    char ch = ' ';

    int literalValue = 0;

    int index = 0;  // index into formula
    Stack<Token> operatorStack = new Stack<Token>();  // stack of operators

    int parenthesesCount = 0; // count of left parentheses (increases priority of operators)

    boolean lastCharWasValue = false; // Keep track of whether last char was value so - operators
    // can be handled correctly and multiple values throw a bad formula.
    
    boolean nextValueIsNegated = false; // Keep track of whether next value should be negated.

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
          OperatorToken currentOperator = new OperatorToken(ch);
          currentOperator.incrementParenPriority(parenthesesCount);
          // We found an operator token

          if (!lastCharWasValue && ch == '-') {
            // If the last char was not a value and the current char is a minus sign,
            // then this - is a unary operator and the next value should be negated.
            nextValueIsNegated = true;
            index++;
          } else if (!lastCharWasValue) {
            // If the last char was not a value and the current char is not a minus sign,
            // then this is an invalid formula.
            error = true;
            break;
          } else {
            lastCharWasValue = false;
            switch (ch) {
                case OperatorToken.Plus:
                case OperatorToken.Minus:
                case OperatorToken.Mult:
                case OperatorToken.Div:
                case OperatorToken.Pow:
                    // push operatorTokens onto the output stack until
                    // we reach an operator on the operator stack that has
                    // lower priority than the current one.
                    OperatorToken stackOperator;
                    while (!operatorStack.isEmpty()) {
                        stackOperator = (OperatorToken) operatorStack.peek();
                        if ((stackOperator.getParenPriority() > currentOperator.getParenPriority()) || (
                            (stackOperator.getParenPriority() == currentOperator.getParenPriority()) &&
                            (stackOperator.getPriority() >= currentOperator.getPriority()))) {

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
            operatorStack.push(currentOperator);

            index++;
          }
        } else if (ch == '(') {
            // We found a left parenthesis
            parenthesesCount++;
            index++;
        }
        else if (ch == ')') {
            // We found a right parenthesis
            parenthesesCount--;
            if (parenthesesCount < 0) {
                error = true;
                break;
            }
            index++;
        } else if (Character.isDigit(ch)) {
            // We found a literal token
            if (lastCharWasValue) {
              error = true;
              break;
            }
            lastCharWasValue = true;
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
            if (nextValueIsNegated) {
              literalValue = -literalValue;
              nextValueIsNegated = false;
            }
            // place the literal on the output stack
            returnStack.push(new LiteralToken(literalValue));

        } else if (Character.isUpperCase(ch)) {
            // We found a cell reference token
            if (lastCharWasValue) {
              error = true;
              break;
            }
            lastCharWasValue = true;
            CellToken cellToken = new CellToken();
            index = CellToken.getCellToken(formula, index, cellToken);
            if (cellToken.getRow() == CellToken.BADCELL) {
                error = true;
                break;
            } else {
                // place the cell reference on the output stack
                if (nextValueIsNegated) {
                  // special case for negating a cell reference
                  cellToken.negate();
                  nextValueIsNegated = false;
                }
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
        // a parse error; throw an InvalidParameterException
        throw new InvalidParameterException("Invalid formula: " + formula);
    }
    Stack<Token> temp = new Stack<Token>();
   
    // Reverse the stack so that the first token is the first token in the formula.
    while (returnStack.empty() == false)
    {
      temp.push(returnStack.peek());
      returnStack.pop();
    }  
   

    return temp;
  }
  
}
