package Cell.Tokens;

/**
 * This class represents a literal token.
 * It has a value.
 * 
 * @author Rosemary
 */
public class LiteralToken extends Token {

  private int value;

  public LiteralToken(int value) {
    this.value = value;
    
  }

  public int getValue() {
    return value;
  }
}
