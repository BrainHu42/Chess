package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.Game;
import Game.Square;
import Game.PieceType;

public class King extends Piece
{
	private PieceType myPieceType;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	private boolean haveMoved, inCheck;
	private ArrayList<Square> castleMoves = new ArrayList<Square>();
	
	public ArrayList<Square> getCastleMoves() {return castleMoves;}
	public void setCheck(boolean b) {inCheck = b;}
	public boolean getCheck() {return inCheck;}
	
	public ArrayList<Piece> checkPiece = new ArrayList<Piece>();
	public ArrayList<Piece> getCheckPiece() {return checkPiece;}
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	
	public King(Square s, BufferedImage img, Color col) 
	{
		super(s, img, col);
		myPieceType = PieceType.KING;
	}

	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
		castleMoves.clear();
		int r = loc.getRow();
		int c = loc.getColumn();
//Positive Row
		if(r<7){
			legalMoves.add(squares.get(8*(r+1)+c));
			if(c>0)
				legalMoves.add(squares.get(8*(r+1)+c-1));}
		
//Positive Column
		if(c>0){
			legalMoves.add(squares.get(8*r+c-1));
			if(r>0)
				legalMoves.add(squares.get(8*(r-1)+c-1));}

//Negative Row
		if(r>0){
			legalMoves.add(squares.get(8*(r-1)+c));
			if(c<7)
				legalMoves.add(squares.get(8*(r-1)+c+1));}
		
//Negative Column
		if(c<7){
			legalMoves.add(squares.get(8*r+c+1));
			if(r<7)
				legalMoves.add(squares.get(8*(r+1)+c+1));}
		
//Castle
		if(this.getColor()==Color.black && !this.inCheck && !haveMoved && !squares.get(56).getEmpty() && squares.get(56).getPiece().getPieceType()==PieceType.ROOK && !((Rook) (squares.get(56).getPiece())).getMoved() && squares.get(8*(r+1)+c).getEmpty() && squares.get(8*(r+2)+c).getEmpty()
			|| this.getColor()==Color.white && !this.inCheck && !haveMoved && !squares.get(63).getEmpty() && !((Rook) (squares.get(63).getPiece())).getMoved() && squares.get(8*(r+1)+c).getEmpty() && squares.get(8*(r+2)+c).getEmpty())
			{legalMoves.add(squares.get(8*(r+2)+c));
			castleMoves.add(squares.get(8*(r+2)+c));
			}
		if(this.getColor()==Color.black && !this.getCheck() && !haveMoved && !squares.get(0).getEmpty() && squares.get(56).getPiece().getPieceType()==PieceType.ROOK && !((Rook) (squares.get(0).getPiece())).getMoved() && squares.get(8*(r-1)+c).getEmpty() && squares.get(8*(r-2)+c).getEmpty()
			|| this.getColor()==Color.white && !this.inCheck && !haveMoved && !squares.get(7).getEmpty() && !((Rook) (squares.get(7).getPiece())).getMoved() && squares.get(8*(r-1)+c).getEmpty() && squares.get(8*(r-2)+c).getEmpty())
			{legalMoves.add(squares.get(8*(r-2)+c));
			castleMoves.add(squares.get(8*(r-2)+c));
			}
	
		Iterator<Square> iter = legalMoves.iterator();
		while (iter.hasNext()) 
		{
			Square sqr = iter.next();
			
			for(Piece p : pieces)
			{
				if(p.getColor()!=this.getColor() && p.getPieceType()!=PieceType.KING && p.getPieceType()!=PieceType.PAWN && p.getLegalMoves(p.getSquare()).contains(sqr) && !legalMoves.isEmpty()
					|| sqr==p.getSquare() && this.getColor()==p.getColor() && !legalMoves.isEmpty() 
					|| p.getPieceType()==PieceType.PAWN && this.getColor()!=p.getColor() && ((Pawn)(p)).getCaptureMoves(p.getSquare()).contains(sqr) && !legalMoves.isEmpty())
					{iter.remove();
					break;
					}
			}
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
	
	public void castle(Square s) {
		if(s.getRow()==6 && this.getColor()==Color.WHITE)
			squares.get(63).getPiece().moveTo(squares.get(47));
		else if(s.getRow()==6 && this.getColor()==Color.BLACK)
			squares.get(56).getPiece().moveTo(squares.get(40));
		else if(s.getRow()==2 && this.getColor()==Color.WHITE)
			squares.get(7).getPiece().moveTo(squares.get(31));
		else if(s.getRow()==2 && this.getColor()==Color.BLACK)
			squares.get(0).getPiece().moveTo(squares.get(24));
		this.moveTo(s);
	}

	@Override
	public void getCaptured() {
		System.out.println("King Got Captured");
	}

	@Override
	public PieceType getPieceType() {
		// TODO Auto-generated method stub
		return myPieceType;
	}

}
