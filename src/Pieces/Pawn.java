package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.PieceType;
import Game.Game;
import Game.Square;

public class Pawn extends Piece
{
	private PieceType myPieceType;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	private boolean doubleMove = false;
	
	public boolean getDoubleMove() {return doubleMove;}
	public void setDoubleMove(boolean b) {doubleMove=b;}
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	private King blackKing = (King) (pieces.get(0));
	private King whiteKing = (King) (pieces.get(1));
	
	public Pawn(Square s, BufferedImage img, Color col) 
	{
		super(s, img, col);
		myPieceType = PieceType.PAWN;
	}
	
	public ArrayList<Square> getCaptureMoves(Square loc)
	{
		ArrayList<Square> captureMoves = new ArrayList<Square>();
		
		if(this.getColor()==Color.WHITE && loc.getRow()!=7 && loc.getColumn()!=0 /* && !squares.get((squares.indexOf(loc)+7)).getEmpty()*/)
			captureMoves.add(squares.get(squares.indexOf(loc)+7));
		else if(this.getColor()==Color.BLACK && loc.getRow()!=0 && loc.getColumn()!=7 /*&& !squares.get((squares.indexOf(loc)-7)).getEmpty()*/)
			captureMoves.add(squares.get(squares.indexOf(loc)-7));
		
		if(this.getColor()==Color.WHITE && loc.getRow()!=0 && loc.getColumn()!=0 /*&& !squares.get((squares.indexOf(loc)-9)).getEmpty()*/)
			captureMoves.add(squares.get(squares.indexOf(loc)-9));
		else if(this.getColor()==Color.BLACK && loc.getRow()!=7 && loc.getColumn()!=7 /*&& !squares.get((squares.indexOf(loc)+9)).getEmpty()*/)
			captureMoves.add(squares.get(squares.indexOf(loc)+9));
		
		return captureMoves;
	}
	
	public ArrayList<Square> getEnPassant(Square loc)
	{
		ArrayList<Square> enPassant = new ArrayList<Square>();
		
		int r = loc.getRow();
		int c = loc.getColumn();
		if(this.getColor()==Color.white && this.getSquare().getColumn()==3 && r>0 && squares.get(8*(r-1)+c).getPiece()!=null && squares.get(8*(r-1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r-1)+c).getPiece())).getDoubleMove())
			enPassant.add(squares.get(8*(r-1)+c-1));
		else if(this.getColor()==Color.black && this.getSquare().getColumn()==4 && r>0 && squares.get(8*(r-1)+c).getPiece()!=null && squares.get(8*(r-1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r-1)+c).getPiece())).getDoubleMove())
			legalMoves.add(squares.get(8*(r-1)+c+1));
		
		if(this.getColor()==Color.white && this.getSquare().getColumn()==3 && r<7 && squares.get(8*(r+1)+c).getPiece()!=null && squares.get(8*(r+1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r+1)+c).getPiece())).getDoubleMove())
			enPassant.add(squares.get(8*(r+1)+c-1));
		else if(this.getColor()==Color.black && this.getSquare().getColumn()==4 && r<7 && squares.get(8*(r+1)+c).getPiece()!=null && squares.get(8*(r+1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r+1)+c).getPiece())).getDoubleMove())
			enPassant.add(squares.get(8*(r+1)+c+1));
		
		return enPassant;
	}
	
	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
		int r = loc.getRow();
		int c = loc.getColumn();
if(this.getColor()==Color.black && !((King) (pieces.get(0))).getCheck() || this.getColor()==Color.white && !((King) (pieces.get(1))).getCheck()){

//Start
		if(this.getColor()==Color.BLACK && loc.getColumn()==1)
		{
			if(squares.get(8*r+c+1).getEmpty())
				legalMoves.add(squares.get(8*r+c+1));
			if(squares.get(8*r+c+2).getEmpty())
				legalMoves.add(squares.get(8*r+c+2));
		}
		
		else if(this.getColor()==Color.WHITE && loc.getColumn()==6)
		{
			if(squares.get(8*r+c-1).getEmpty())
				legalMoves.add(squares.get(8*r+c-1));
			if(squares.get(8*r+c-2).getEmpty())
				legalMoves.add(squares.get(8*r+c-2));
		}
//Regular
		else if(this.getColor()==Color.BLACK && loc.getColumn()!=7 && squares.get(8*r+c+1).getEmpty())
			legalMoves.add(squares.get(8*r+c+1));
		
		else if(this.getColor()==Color.WHITE && loc.getColumn()!=0 && squares.get(8*r+c-1).getEmpty())
			legalMoves.add(squares.get(8*r+c-1));
		
//Capture
		if(this.getColor()==Color.WHITE && loc.getRow()!=7 && loc.getColumn()!=0 && !squares.get((squares.indexOf(loc)+7)).getEmpty())
			legalMoves.add(squares.get(squares.indexOf(loc)+7));
		else if(this.getColor()==Color.BLACK && loc.getRow()!=0 && loc.getColumn()!=7 && !squares.get((squares.indexOf(loc)-7)).getEmpty())
			legalMoves.add(squares.get(squares.indexOf(loc)-7));
		
		if(this.getColor()==Color.WHITE && loc.getRow()!=0 && loc.getColumn()!=0 && !squares.get((squares.indexOf(loc)-9)).getEmpty())
			legalMoves.add(squares.get(squares.indexOf(loc)-9));
		else if(this.getColor()==Color.BLACK && loc.getRow()!=7 && loc.getColumn()!=7 && !squares.get((squares.indexOf(loc)+9)).getEmpty())
			legalMoves.add(squares.get(squares.indexOf(loc)+9));
		
//En Passant
		if(this.getColor()==Color.white && this.getSquare().getColumn()==3 && r>0 && squares.get(8*(r-1)+c).getPiece()!=null
				&& squares.get(8*(r-1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r-1)+c).getPiece())).getDoubleMove())
			legalMoves.add(squares.get(8*(r-1)+c-1));
		else if(this.getColor()==Color.black && this.getSquare().getColumn()==4 && r>0 && squares.get(8*(r-1)+c).getPiece()!=null
				&& squares.get(8*(r-1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r-1)+c).getPiece())).getDoubleMove())
			legalMoves.add(squares.get(8*(r-1)+c+1));
		
		if(this.getColor()==Color.white && this.getSquare().getColumn()==3 && r<7 && squares.get(8*(r+1)+c).getPiece()!=null
				&& squares.get(8*(r+1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r+1)+c).getPiece())).getDoubleMove())
			legalMoves.add(squares.get(8*(r+1)+c-1));
		else if(this.getColor()==Color.black && this.getSquare().getColumn()==4 && r<7 && squares.get(8*(r+1)+c).getPiece()!=null
				&& squares.get(8*(r+1)+c).getPiece().getPieceType()==PieceType.PAWN && ((Pawn)(squares.get(8*(r+1)+c).getPiece())).getDoubleMove())
			legalMoves.add(squares.get(8*(r+1)+c+1));
		
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
								{heroic.add(s);}
							this.moveTo(initial);
							s.setPiece(null);
							initial.setPiece(this);
						}
						blackKing.setCheck(true);
					}
				}
				whiteKing.setCheck(false);
				if(this.getCaptureMoves(this.getSquare()).contains(p.getSquare()))
					heroic.add(p.getSquare());
				whiteKing.setCheck(true);
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
				if(this.getCaptureMoves(this.getSquare()).contains(p.getSquare()))
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
		if(this.getColor()==Color.black && this.getSquare().getColumn()==1 && s.getColumn()==3
			|| this.getColor()==Color.white && this.getSquare().getColumn()==6 && s.getColumn()==4)
			doubleMove = true;
		else
			doubleMove = false;
		
		for(Piece p : pieces)
			if(p.getPieceType()==PieceType.PAWN && p!=this)
				((Pawn) p).setDoubleMove(false);
		
		this.setSquare(s);
		
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
