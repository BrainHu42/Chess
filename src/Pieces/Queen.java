package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.Game;
import Game.Square;
import Game.PieceType;

public class Queen extends Piece
{
	private PieceType myPieceType;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	private King blackKing = (King) (pieces.get(0));
	private King whiteKing = (King) (pieces.get(1));
	
	public Queen(Square s, BufferedImage img, Color col) {
		super(s, img, col);
		myPieceType = PieceType.QUEEN;
	}

	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
		int i;
		int r1 = loc.getRow();
		int c1 = loc.getColumn();
if(this.getColor()==Color.black && !((King) (pieces.get(0))).getCheck() || this.getColor()==Color.white && !((King) (pieces.get(1))).getCheck()){

//1st Quadrant
		for(i=r1+1; i<8; i++)
		{	
			if(!squares.get(8*i+c1).getEmpty())
			{
				legalMoves.add(squares.get(8*i+c1));
				break;
			}
			legalMoves.add(squares.get(8*i+c1));
		}
		
		int r = loc.getRow()+1;
		int c = loc.getColumn()-1;
		while(r<8 && c>=0)
		{
			if(!squares.get(8*r+c).getEmpty())
			{
				legalMoves.add(squares.get(8*r+c));
				break;
			}
			legalMoves.add(squares.get(8*r+c));
			r++;
			c--;
		}

//2nd Quadrant
		for(i=c1-1; i>=0; i--)
		{
			if(!squares.get(8*r1+i).getEmpty())
			{
				legalMoves.add(squares.get(8*r1+i));
				break;
			}
			legalMoves.add(squares.get(8*r1+i));
		}
		
		r = loc.getRow()-1;
		c = loc.getColumn()-1;
		while(r>=0 && c>=0)
		{
			if(!squares.get(8*r+c).getEmpty())
			{
				legalMoves.add(squares.get(8*r+c));
				break;
			}
			legalMoves.add(squares.get(8*r+c));
			r--;
			c--;
		}
		
//3rd Quadrant
		for(i=r1-1; i>=0; i--)
		{
			if(!squares.get(8*i+c1).getEmpty())
			{
				legalMoves.add(squares.get(8*i+c1));	
				break;
			}
			legalMoves.add(squares.get(8*i+c1));	
		}
		
		r = loc.getRow()-1;
		c = loc.getColumn()+1;
		while(r>=0 && c<8)
		{
			if(!squares.get(8*r+c).getEmpty())
			{
				legalMoves.add(squares.get(8*r+c));
				break;
			}
			legalMoves.add(squares.get(8*r+c));
			r--;
			c++;
		}
		
//4th Quadrant
		for(i=c1+1; i<8; i++)
		{
			if(!squares.get(8*r1+i).getEmpty())
			{
				legalMoves.add(squares.get(8*r1+i));
				break;
			}
		legalMoves.add(squares.get(8*r1+i));
		}
		
		r = loc.getRow()+1;
		c = loc.getColumn()+1;
		while(r<8 && c<8)
		{
			if(!squares.get(8*r+c).getEmpty())
			{
				legalMoves.add(squares.get(8*r+c));
				break;
			}
			legalMoves.add(squares.get(8*r+c));
			r++;
			c++;
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
				blackKing.setCheck(false);
				for(Square q : p.getLegalMoves(p.getSquare()))
					temp.add(q);
				blackKing.setCheck(true);
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
		// TODO Auto-generated method stub
		return myPieceType;
	}

}
