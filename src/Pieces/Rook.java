package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.Game;
import Game.Square;
import Game.PieceType;

public class Rook extends Piece
{
	private PieceType myPieceType;
	private boolean haveMoved=false;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	private King blackKing = (King) (pieces.get(0));
	private King whiteKing = (King) (pieces.get(1));

	public boolean getMoved() {return haveMoved;}
	
	public Rook(Square s, BufferedImage img, Color col) {
		super(s, img, col);
		myPieceType = PieceType.ROOK;
	}

	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
		int r = loc.getRow();
		int c = loc.getColumn();
		int i;
if(this.getColor()==Color.black && !((King) (pieces.get(0))).getCheck() || this.getColor()==Color.white && !((King) (pieces.get(1))).getCheck()){

//Positive Row
		for(i=r+1; i<8; i++)
		{	
			if(!squares.get(8*i+c).getEmpty())
			{
				legalMoves.add(squares.get(8*i+c));
				break;
			}
			legalMoves.add(squares.get(8*i+c));
		}
		
//Positive Column
		for(i=c-1; i>=0; i--)
		{
			if(!squares.get(8*r+i).getEmpty())
			{
				legalMoves.add(squares.get(8*r+i));
				break;
			}
			legalMoves.add(squares.get(8*r+i));
		}

//Negative Row
		for(i=r-1; i>=0; i--)
		{
			if(!squares.get(8*i+c).getEmpty())
			{
				legalMoves.add(squares.get(8*i+c));	
				break;
			}
			legalMoves.add(squares.get(8*i+c));	
		}
		
//Negative Column
		for(i=c+1; i<8; i++)
		{
			if(!squares.get(8*r+i).getEmpty())
			{
				legalMoves.add(squares.get(8*r+i));
				break;
			}
		legalMoves.add(squares.get(8*r+i));
		}
		
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
	public void moveTo(Square s) {
		this.setSquare(s);
		haveMoved = true;
		for(Piece p : pieces)
			if(p.getPieceType()==PieceType.PAWN)
				((Pawn) p).setDoubleMove(false);
	}

	@Override
	public void getCaptured() {
		pieces.remove(this);
	}

	@Override
	public PieceType getPieceType() {
		// TODO Auto-generated method stub
		return myPieceType;
	}

}
