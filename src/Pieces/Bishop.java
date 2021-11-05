package Pieces;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import Game.*;

public class Bishop extends Piece
{
	private PieceType myPieceType;
	private ArrayList<Square> legalMoves = new ArrayList<Square>();
	
	Game obj = new Game();
	private ArrayList<Square> squares = obj.getSquares();
	private ArrayList<Piece> pieces = obj.getPieces();
	private King blackKing = (King) (pieces.get(0));
	private King whiteKing = (King) (pieces.get(1));
	
	public Bishop(Square s, BufferedImage img, Color col) 
	{
		super(s, img, col);
		myPieceType = PieceType.BISHOP;
	}

	public ArrayList<Square> getLegalMoves(Square loc)
	{
		legalMoves.clear();
if(this.getColor()==Color.black && !blackKing.getCheck() || this.getColor()==Color.white && !whiteKing.getCheck()){

//1st Quadrant
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
		
//		if(obj.getCurrentPlayer().getColor()==Color.WHITE && this.getColor()==Color.WHITE && click.getThisPiece()!=null)
//		{
//			System.out.println("HI");
//			for(int i=0; i<legalMoves.size(); i++)
//			{
//				Square sqr = legalMoves.get(i);
//				Square initial = this.getSquare();
//				for(Piece p : pieces)
//				{
//					if(p.getColor()==Color.BLACK)
//					{
//						this.moveTo(sqr);
//						sqr.setPiece(this);
//						initial.setPiece(null);
//						if(p.getLegalMoves(p.getSquare()).contains(whiteKing))
//						{
//							legalMoves.remove(i);
//							i--;
//						}
//						this.moveTo(initial);
//						sqr.setPiece(null);
//						initial.setPiece(this);
//					}
//				}
//			}
//		}
//			
//			if(obj.getCurrentPlayer().getColor()==Color.BLACK && this.getColor()==Color.BLACK && click.getThisPiece()!=null)
//			{
//				System.out.println("HI");
//				for(int i=0; i<legalMoves.size(); i++)
//				{
//					Square sqr = legalMoves.get(i);
//					Square initial = this.getSquare();
//					for(Piece p : pieces)
//					{
//						if(p.getColor()==Color.WHITE)
//						{
//							this.moveTo(sqr);
//							sqr.setPiece(this);
//							initial.setPiece(null);
//							if(p.getLegalMoves(p.getSquare()).contains(blackKing))
//							{
//								legalMoves.remove(i);
//								i--;
//							}
//							this.moveTo(initial);
//							sqr.setPiece(null);
//							initial.setPiece(this);
//						}
//					}
//				}
//			}
//		for(int i=0; i<legalMoves.size(); i++)
//		{
//			Square sqr = legalMoves.get(i);
//			Square initial = this.getSquare();
//			for(Piece p : pieces)
//			{
//			//	System.out.println("Hi");
//				if(this.getColor()==Color.BLACK)
//					blackKing.setCheck(false);
//				if(this.getColor()==Color.WHITE)
//					whiteKing.setCheck(false);
//				this.moveTo(sqr);
//				sqr.setPiece(this);
//				initial.setPiece(null);
//				if(p.getColor()==Color.BLACK && this.getColor()==Color.WHITE && p==this && p.getLegalMoves(p.getSquare()).contains(whiteKing.getSquare())
//						|| p.getColor()==Color.WHITE && this.getColor()==Color.BLACK && p==this && p.getLegalMoves(p.getSquare()).contains(blackKing.getSquare()))
//						{legalMoves.remove(i);
//						i--;
//						}
//				this.moveTo(initial);
//				sqr.setPiece(null);
//				initial.setPiece(this);
//			}
//		}
//		while(iter.hasNext())
//		{
//			Square sqr = iter.next();
//			Square initial = this.getSquare();
//			for(Piece p : pieces)
//			{	
//					System.out.println("HI");
//					this.moveTo(sqr);
//					sqr.setPiece(this);
//					initial.setPiece(null);
//					if(p.getColor()==Color.BLACK && this.getColor()==Color.WHITE && p.getPieceType()!=PieceType.BISHOP && p.getLegalMoves(p.getSquare()).contains(whiteKing.getSquare())
//						|| p.getColor()==Color.WHITE && this.getColor()==Color.BLACK && p.getPieceType()!=PieceType.BISHOP && p.getLegalMoves(p.getSquare()).contains(blackKing.getSquare()))
//						iter.remove();
//					this.moveTo(initial);
//					sqr.setPiece(null);
//					initial.setPiece(this);
//			}
//		}
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

	if(this.getColor()==Color.white && whiteKing.getCheck())
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
								heroic.add(s);
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
