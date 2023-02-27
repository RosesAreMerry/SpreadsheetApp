package Model.Cell.Tokens;

public class LiteralToken extends Token {

  private int value;

  public LiteralToken(int value) {
    this.value = value;
    
  }

  public int getValue() {
    return value;
  }
}
