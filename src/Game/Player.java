package Game;

import java.awt.Color;
import java.util.ArrayList;

public class Player 
{
	private String name;
	private int points;
	private Color color;
	private ArrayList<PieceType> capturedPieces = new ArrayList<PieceType>();
	
	public int getPoints() {return points;}
	public Color getColor() {return color;}
	public ArrayList<PieceType> getCapturedPieces() {return capturedPieces;}
	
	public void addPoints(int p) {points += p;}
	public void addPiece(PieceType t) {capturedPieces.add(t);}
	
	public Player(Color c)
	{
		color = c;
		points = 0;
		if(c==Color.WHITE)
			name="White Player";
		if(c==Color.BLACK)
			name="Black Player";
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
