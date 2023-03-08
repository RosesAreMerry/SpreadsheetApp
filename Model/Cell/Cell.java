package Cell;

import java.util.Stack;
import java.util.function.Function;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import Cell.Tokens.CellToken;
import Cell.Tokens.LiteralToken;
import Cell.Tokens.OperatorToken;
import Cell.Tokens.Token;


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
  
  public void setFormula(String formula) throws InvalidParameterException {
    this.formula = formula;
    postfixFormula = getFormula(formula);
  }
  
  public String getFormula() {
    return formula;
  }
  
  public int getValue() {
    return value;
  }


  /** Function that returns a list of all dependencies in the order they need to be calculated.
   * 
   * @return ArrayList<Cell> list of all dependencies in the order they need to be calculated
   */
  public ArrayList<Cell> getDependencies() {
    List<Cell> dependencies = new ArrayList<>();
      for (Token token : postfixFormula) {
          if (token instanceof CellToken) {
              dependencies.add(getCellValue.apply((CellToken) token));
          }
      }
      return (ArrayList<Cell>) dependencies;
  }
  
//  public List<Cell> getDependencies() {
//    return postfixFormula.stream().filter(token -> token instanceof CellToken)
//    		.map(token -> getCellValue.apply((CellToken) token))
//    		.collect(Collectors.toList());
//  }

  public void recalculate() {
    Stack<Integer> currentValues = new Stack<Integer>();
    int result = 0;

    Stack<Token> formula = new Stack<Token>();

    formula.addAll(postfixFormula);

    while (!formula.isEmpty()) {
      Token token = formula.pop();
      Integer value = null;
      if (token instanceof LiteralToken) {
        currentValues.push(((LiteralToken) token).getValue());
      } else if (token instanceof CellToken) {
        
        Cell cell = getCellValue.apply((CellToken) token);

        if (cell != null) {
          if (((CellToken) token).isNegated()) {
            currentValues.push(-getCellValue.apply((CellToken) token).getValue());
          } else {
            currentValues.push(getCellValue.apply((CellToken) token).getValue());
          }
        } else {
          currentValues.push(0);
        }
      } else if (token instanceof OperatorToken) {
        if (currentValues.size() >= 2) {
          value = ((OperatorToken) token).evaluate(currentValues.pop(), currentValues.pop());
          formula.push(new LiteralToken(value));
        }
      }
      if (formula.size() == 1 && formula.peek() instanceof LiteralToken) {
        if (currentValues.size() > 0) {
          throw new RuntimeException("Bad Formula: " + this.formula);
        }
        result = ((LiteralToken) formula.pop()).getValue();
      }
      if (formula.isEmpty() && currentValues.size() == 1) {
        result = currentValues.pop();
      }
    }
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
    
    boolean nextValueIsNegated = false;

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
            nextValueIsNegated = true;
            index++;
          } else {
            lastCharWasValue = false;
            switch (ch) {
                case OperatorToken.Plus:
                case OperatorToken.Minus:
                case OperatorToken.Mult:
                case OperatorToken.Div:
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
            lastCharWasValue = false;
            parenthesesCount++;
            index++;
        }
        else if (ch == ')') {
            // We found a right parenthesis
            lastCharWasValue = false;
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
   
    while (returnStack.empty() == false)
    {
      temp.push(returnStack.peek());
      returnStack.pop();
    }  
   

    return temp;
  }
  
}
