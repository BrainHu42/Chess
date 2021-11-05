package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.Game;
import Game.Square;
import Game.PieceType;

public class Knight extends Piece
{
	private PieceType myPieceType;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	private King blackKing = (King) (pieces.get(0));
	private King whiteKing = (King) (pieces.get(1));
	
	public Knight(Square s, BufferedImage img, Color col) 
	{
		super(s, img, col);
		myPieceType = PieceType.KNIGHT;
	}

	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
		int r=loc.getRow();
		int c=loc.getColumn();
if(this.getColor()==Color.black && !((King) (pieces.get(0))).getCheck() || this.getColor()==Color.white && !((King) (pieces.get(1))).getCheck()){
		
//1st Quadrant
		if(r+2<8 && c-1>=0)
			legalMoves.add(squares.get(8*(r+2)+c-1));
		if(r+1<8 && c-2>=0)
			legalMoves.add(squares.get(8*(r+1)+c-2));
		
//2nd Quadrant
		if(r-1>=0 && c-2>=0)
			legalMoves.add(squares.get(8*(r-1)+c-2));
		if(r-2>=0 && c-1>=0)
			legalMoves.add(squares.get(8*(r-2)+c-1));
		
//3rd Quadrant
		if(r-2>=0 && c+1<8)
			legalMoves.add(squares.get(8*(r-2)+c+1));
		if(r-1>=0 && c+2<8)
			legalMoves.add(squares.get(8*(r-1)+c+2));

//4th Quadrant
		if(r+1<8 && c+2<8)
			legalMoves.add(squares.get(8*(r+1)+c+2));
		if(r+2<8 && c+1<8)
			legalMoves.add(squares.get(8*(r+2)+c+1));
		
		Iterator<Square> iter = legalMoves.iterator();
		while (iter.hasNext()) 
		{
			Square sqr = iter.next();
			for(Piece p : pieces)
				if (sqr.getX()==p.getX() && sqr.getY()==p.getY() && this.getColor()==p.getColor())
					iter.remove();
		}
		
		return legalMoves;
}

	if(this.getColor()==Color.black && blackKing.getCheck())
	{
	ArrayList<Square> heroic = new ArrayList<Square>();
		Square initial;
		
		for(Piece p : blackKing.getCheckPiece())
			{
				ArrayList<Square> temp = new ArrayList<Square>();
				for(Square q : p.getLegalMoves(p.getSquare()))
					temp.add(q);
			
				if(p.getPieceType()!=PieceType.KNIGHT)
				{
					for(Square s : temp)
					{
						blackKing.setCheck(false);
						if(this.getLegalMoves(this.getSquare()).contains(s))
						{
							initial = this.getSquare();
							this.moveTo(s);
							s.setPiece(this);
							initial.setPiece(null);
							if(!p.getLegalMoves(p.getSquare()).contains(blackKing.getSquare()))
								heroic.add(s);
							this.moveTo(initial);
							s.setPiece(null);
							initial.setPiece(this);
						}
						blackKing.setCheck(true);
					}
				}
				blackKing.setCheck(false);
				if(this.getLegalMoves(this.getSquare()).contains(p.getSquare()))
					heroic.add(p.getSquare());
				blackKing.setCheck(true);
				temp.clear();
			}
		return heroic;
	}
	
	if(this.getColor()==Color.WHITE && whiteKing.getCheck())
	{
		ArrayList<Square> heroic = new ArrayList<Square>();
		Square initial;
		for(Piece p : whiteKing.getCheckPiece())
			{
				ArrayList<Square> temp = new ArrayList<Square>();
				for(Square q : p.getLegalMoves(p.getSquare()))
					temp.add(q);
				
				if(p.getPieceType()!=PieceType.KNIGHT)
				{
					for(Square s : temp)
					{
						whiteKing.setCheck(false);
						if(this.getLegalMoves(this.getSquare()).contains(s))
						{
							initial = this.getSquare();
							this.moveTo(s);
							s.setPiece(this);
							initial.setPiece(null);
							if(!p.getLegalMoves(p.getSquare()).contains(whiteKing.getSquare()))
								{heroic.add(s);}
							this.moveTo(initial);
							s.setPiece(null);
							initial.setPiece(this);
						}
						whiteKing.setCheck(true);
					}
				}
				whiteKing.setCheck(false);
				if(this.getLegalMoves(this.getSquare()).contains(p.getSquare()))
					heroic.add(p.getSquare());
				whiteKing.setCheck(true);
				temp.clear();
			}
		return heroic;
	}
	return legalMoves;
}
	
	@Override
	public void capture(Piece p) {
		this.moveTo(p.getSquare());
		p.getCaptured();
	}

	@Override
	public void getCaptured() {
		pieces.remove(this);
	}

	@Override
	public PieceType getPieceType() {
		return myPieceType;
	}

}
