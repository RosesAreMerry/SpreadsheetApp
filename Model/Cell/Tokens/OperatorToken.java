package Cell.Tokens;

/**
 * This class represents an operator token.
 * It has values for the operator and its priority and the number of parentheses it is in.
 * It also has methods to evaluate the operator on two values.
 * 
 * @author Rosemary
 */
public class OperatorToken extends Token {

  public static final char Plus = '+';
  public static final char Minus = '-';
  public static final char Mult = '*';
  public static final char Div = '/';
  public static final char Pow = '^';

  private int parenPriority;
  private int priority;

  public OperatorToken(char ch) {
    if (!isOperator(ch)) {
        // This case should NEVER happen
        System.out.println("Error in OperatorToken constructor.");
        System.exit(0);
    }
    operator = ch;
    priority = operatorPriority(ch);
    parenPriority = 0;
  }

  
  private char operator;

  /**
   * Return the operator of this OperatorToken.
   * 
   * @return The operator of this OperatorToken.
   */
  public char getOperator() {
    return operator;
  }

  /**
   * Evaluate the operator on the two values.
   * 
   * @param val1 The first value.
   * @param val2 The second value.
   * @return The result of the operation.
   */
  public int evaluate(int val1, int val2) {
    int returnVal = 0;

    switch (operator) {
        case Plus:
            returnVal = val1 + val2;
            break;
        case Minus:
            returnVal = val2 - val1; // I reverse the stack for ease of processing. 
            //This works well, but will reverse the order of non-commutative operators.
            //I could have done something else, but this is quite easy.
            break;
        case Mult:
            returnVal = val1 * val2;
            break;
        case Div:
            returnVal = val2 / val1;
            break;
        case Pow:
            returnVal = (int) Math.pow(val2, val1);
            break;
        default:
            // This case should NEVER happen
            System.out.println("Error in OperatorToken.evaluate.");
            System.exit(0);
            break;
    }
    return returnVal;
  }

  /**
   * Return true if the char ch is an operator of a formula.
   * Current operators are: +, -, *, /, ^.
   * 
   * @param ch  a char
   * @return  whether ch is an operator
   */
  public static boolean isOperator (char ch) {
    return ((ch == '+') ||
            (ch == '-') ||
            (ch == '*') ||
            (ch == '/') ||
            (ch == '^'));
  }

  /**
   * Given an operator, return its default priority.
   *
   * priorities:
   *   +, - : 0
   *   *, / : 1
   *   ^    : 2
   *
   * @param ch  a char
   * @return  the priority of the operator
   */
  public static int operatorPriority (char ch) {
    if (!isOperator(ch)) {
        // This case should NEVER happen
        System.out.println("Error in operatorPriority.");
        System.exit(0);
    }
    switch (ch) {
        case Plus:
            return 0;
        case Minus:
            return 0;
        case Mult:
            return 1;
        case Div:
            return 1;
        case Pow:
            return 2;
        default:
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
            return -1;
    }
  }


  public int getParenPriority() {
    return parenPriority;
  }

  /**
  * Return the priority of this OperatorToken.
  *
  * default priorities:
  *   +, - : 0
  *   *, / : 1
  *   ^    : 2
  *
  * @return The priority of operatorToken
  */
  public int getPriority() {
    return priority;
  }
  
  /**
   * Increment the parenPriority of this OperatorToken by the given amount.
   * 
   * parenPriority is priority that supercedes the default priority.
   * 
   * @param parenthesesCount The number of parentheses that this operator is in.
   */
  public void incrementParenPriority(int parenthesesCount) {
    parenPriority += parenthesesCount;
  }
  
}
