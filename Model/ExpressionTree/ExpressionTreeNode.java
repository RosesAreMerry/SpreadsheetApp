package Model.ExpressionTree;

public class ExpressionTreeNode() {
	private Token token;
	ExpressionTreeNode left;
	ExpressionTreeNode Right;
	
	public ExpressionTreeNode(Token theToken)
	{
		this(theToken, null, null);		
	}
	
	public ExpressionTreeNode(Token theToken,ExpressionTreeNode lt, ExpressionTreeNode rt){
		token=theToken 
		left=lt;
		Right=rt;
	}
	
}