package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.*;

public abstract class Piece
{
	private Square mySquare;
	private BufferedImage myImage;
	private Color color;
	
	Game obj = new Game();
	ArrayList<Piece> pieces = obj.getPieces();
	
	public Piece(Square s, BufferedImage img, Color c)
	{
		mySquare = s;
		myImage = img;
		color = c;
	}
	
	public int getX() {return mySquare.getX();}
	public int getY() {return mySquare.getY();}
	public Square getSquare() {return mySquare;}
	public void setSquare(Square s) {mySquare = s;}
	public BufferedImage getImage() {return myImage;}
	public Color getColor() {return color;}
	
	public void moveTo(Square s) 
	{
		mySquare = s;
		for(Piece p : pieces)
			if(p.getPieceType()==PieceType.PAWN)
				((Pawn) p).setDoubleMove(false);
	}
	public abstract ArrayList<Square> getLegalMoves(Square loc);
	public abstract void capture(Piece p);
	public abstract void getCaptured();
	public abstract PieceType getPieceType();
	
}
