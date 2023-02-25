/*
 * Utility functions.
 * You should place these methods in the appropriate classes.
 * @author Donald Chinn
 */

/**
 * Return a string associated with a token
 * @param expTreeToken  an ExpressionTreeToken
 * @return a String associated with expTreeToken
 */
String printExpressionTreeToken (Token expTreeToken) {
    String returnString = "";

    if (expTreeToken instanceof OperatorToken) {
        returnString = ((OperatorToken) expTreeToken).getOperatorToken() + " ";
    } else if (expTreeToken instanceof CellToken) {
        returnString = printCellToken((CellToken) expTreeToken) + " ";
    } else if (expTreeToken instanceof LiteralToken) {
        returnString = ((LiteralToken) expTreeToken).getValue() + " ";
    } else {
        // This case should NEVER happen
        System.out.println("Error in printExpressionTreeToken.");
        System.exit(0);
    }
    return returnString;
}

/**
 * Return true if the char ch is an operator of a formula.
 * Current operators are: +, -, *, /, (.
 * @param ch  a char
 * @return  whether ch is an operator
 */
boolean isOperator (char ch) {
    return ((ch == Plus) ||
            (ch == Minus) ||
            (ch == Mult) ||
            (ch == Div) ||
            (ch == LeftParen) );
}

/**
 * Given an operator, return its priority.
 *
 * priorities:
 *   +, - : 0
 *   *, / : 1
 *   (    : 2
 *
 * @param ch  a char
 * @return  the priority of the operator
 */
int operatorPriority (char ch) {
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
        case LeftParen:
            return 2;

        default:
            // This case should NEVER happen
            System.out.println("Error in operatorPriority.");
            System.exit(0);
            break;
    }
}

/*
 * Return the priority of this OperatorToken.
 *
 * priorities:
 *   +, - : 0
 *   *, / : 1
 *   (    : 2
 *
 * @return  the priority of operatorToken
 */
int priority () {
    switch (this.operatorToken) {
        case Plus:
            return 0;
        case Minus:
            return 0;
        case Mult:
            return 1;
        case Div:
            return 1;
        case LeftParen:
            return 2;

        default:
            // This case should NEVER happen
            System.out.println("Error in priority.");
            System.exit(0);
            break;
    }
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
int getCellToken (String inputString, int startIndex, CellToken cellToken) {
    char ch;
    int column = 0;
    int row = 0;
    int index = startIndex;

    // handle a bad startIndex
    if ((startIndex < 0) || (startIndex >= inputString.length() )) {
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
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
    if (index == inputString.length) {
        // reached the end of the string before finding a capital letter
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    }

    // ASSERT: index now points to the first non-whitespace character

    ch = inputString.charAt(index);            
    // process CAPITAL alphabetic characters to calculate the column
    if (!Character.isUpperCase(ch)) {
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
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
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
        return index;
    }

    // ASSERT: We have processed leading whitespace and the
    // capital letters of the cell reference

    // read numeric characters to calculate the row
    if (Character.isDigit(ch)) {
        row = ch - '0';
        index++;
    } else {
        cellToken.setColumn(BadCell);
        cellToken.setRow(BadCell);
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

/**
 *  Given a CellToken, print it out as it appears on the
 *  spreadsheet (e.g., "A3")
 *  @param cellToken  a CellToken
 *  @return  the cellToken's coordinates
 */
String printCellToken (CellToken cellToken) {
    char ch;
    String returnString = "";
    int col;
    int largest = 26;  // minimum col number with number_of_digits digits
    int number_of_digits = 2;

    col = cellToken.getColumn();

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
        ch = (char) ((col / largest) - 1) + 'A';
        returnString += ch;
        col = col % largest;
        largest = largest  / 26;
        number_of_digits--;
    }

    // handle last digit
    ch = col + 'A';
    returnString += ch;

    // append the row as an integer
    returnString += cellToken.getRow();

    return returnString;
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
Stack getFormula(String formula) {
    Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
    bool error = false;
    char ch = ' ';

    int literalValue = 0;

    CellToken cellToken;
    int column = 0;
    int row = 0;

    int index = 0;  // index into formula
    Stack operatorStack = new Stack();  // stack of operators

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
        if (isOperator(ch)) {
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
                        stackOperator = (OperatorToken) operatorStack.top();
                        if ( (stackOperator.priority() >= operatorPriority(ch)) &&
                            (stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

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
            stackOperator = (OperatorToken) operatorStack.topAndPop();
            // This code does not handle operatorStack underflow.
            while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
                // pop operators off the stack until a LeftParen appears and
                // place the operators on the output stack
                returnStack.push(stackOperator);
                stackOperator = (OperatorToken) operatorStack.topAndPop();
            }

            index++;
        } else if (Character.isDigit(ch)) {
            // We found a literal token
            literalValue = ch - '0';
            index++;
            while (index < formula.length) {
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
            index = getCellToken(formula, index, cellToken);
            if (cellToken.getRow() == BadCell) {
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
        returnStack.push(operatorStack.topAndPop());
    }

    if (error) {
        // a parse error; return the empty stack
        returnStack.makeEmpty();
    }

    return returnStack;
}


