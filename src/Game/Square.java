package Game;

import Pieces.Piece;

public class Square 
{
	private int myX, myY, myRow, myColumn;
	private Piece myPiece;
	
	public Square (int x, int y, int r, int c)
	{
		myX = x;
		myY = y;
		myRow = r;
		myColumn = c;
	}
	
	public Square (int x, int y, Piece p)
	{
		myX = x;
		myY = y;
		myPiece = p;
	}
	
	public int getRow(){return myRow;}
	public int getColumn(){return myColumn;}
	public int getX(){return myX;}
	public int getY(){return myY;}
	public Piece getPiece(){return myPiece;}
	public void setPiece(Piece p) {myPiece = p;}
	
	public boolean getEmpty() 
	{
		if(myPiece==null)
			return true;
		return false;
	}
}
