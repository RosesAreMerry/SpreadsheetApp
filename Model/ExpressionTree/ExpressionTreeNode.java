package ExpressionTree;

import Cell.Tokens.Token;

public class ExpressionTreeNode {
	private Token token;
	ExpressionTreeNode left;
	ExpressionTreeNode right;
	
	public ExpressionTreeNode(Token theToken)
	{
		this(theToken, null, null);
	}
	
	public ExpressionTreeNode(Token theToken,ExpressionTreeNode lt, ExpressionTreeNode rt){
		token=theToken;
		left=lt;
		right=rt;
	}
	
}