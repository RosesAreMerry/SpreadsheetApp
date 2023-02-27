package Model.Cell;

import java.util.Stack;

import Model.Cell.Tokens.CellToken;
import Model.Cell.Tokens.LiteralToken;
import Model.Cell.Tokens.OperatorToken;
import Model.Cell.Tokens.Token;

public class Cell {
	
  Cell(String row, int column, String formula) {
    setRow(row);
    setColumn(column);
    setFormula(formula);
  }

  public int getRow() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public int getColumn() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void setRow(String row) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void setColumn(int column) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void setFormula(String formula) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public String getFormula() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public int getValue() {
    throw new UnsupportedOperationException("Not supported yet.");
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

    return returnStack;
  }
  
}
