package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Game.Game.Clicks;
import Pieces.*;

public class ChessPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Game game = new Game();
	private ArrayList<Piece> pieces = game.getPieces();
	private ArrayList<Square> squares = game.getSquares();
	
	private static ArrayList<Square> legalMoves;
	public void setLegalMoves(ArrayList<Square> l) {legalMoves = l;}
//	public ArrayList<Square> getLegalMoves() {return legalMoves;}
//	
//	public void setIndex(int i) {pieceIndex = i;}
//	public int getIndex() {return pieceIndex;}
//	public void setSquare(Square s) {pieceSquare = s;}
	
	@Override	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		try {drawBoard(g);} catch (InterruptedException e) {e.printStackTrace();}
		try {lightUp(g);} catch (InterruptedException e) {e.printStackTrace();}
		try {drawPieces(g);} catch (InterruptedException e) {e.printStackTrace();}
		try {drawPath(g);} catch (InterruptedException e) {e.printStackTrace();}
//		try {drawOptions(g);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void drawBoard(Graphics g) throws InterruptedException
	{
		for(Square sqr: squares)
		{
			g.setColor(Color.WHITE);
			if((sqr.getRow()+sqr.getColumn()) % 2 ==1)
				g.setColor(Color.GREEN);
			g.fillRect(sqr.getX(), sqr.getY(), 80, 80);
		}
	}
	
	public void drawPieces(Graphics g) throws InterruptedException
	{
		for(Piece p : pieces)
				g.drawImage(p.getImage(), p.getX(), p.getY(), 77, 77, this);
	}
	
	public void drawPath(Graphics g) throws InterruptedException
	{
		g.setColor(Color.BLUE);
		if(!(legalMoves == null))
		{	
			for(Square sqr: legalMoves)
			{
				g.drawRect(sqr.getX(), sqr.getY(), 80, 80);
				g.drawRect(sqr.getX()+1, sqr.getY()+1, 78, 78);
			}
		}
	}
	
	public void lightUp(Graphics g) throws InterruptedException
	{
		g.setColor(Color.yellow);
		if(((King) (pieces.get(1))).getCheck())
		{
			g.fillRect(pieces.get(1).getX(), pieces.get(1).getY(), 80, 80);
		}
		if(((King) (pieces.get(0))).getCheck())
		{
			g.fillRect(pieces.get(0).getX(), pieces.get(0).getY(), 80, 80);
		}
	}
	
//	public void drawOptions(Graphics g) throws InterruptedException
//	{
////		if(promotion)
////		{
////			for(Square sqr : options)
////				g.drawImage(sqr.getPiece().getImage(), sqr.getPiece().getX(), sqr.getPiece().getY(), 77, 77, this);
////		}
//		if(promotion)
//			System.out.print("Test");
////			g.setColor(Color.red);
////			for(Square sqr : squares)
////				g.fillRect(sqr.getX(), sqr.getY(), 60, 60);
//	}
}
