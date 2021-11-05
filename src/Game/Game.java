
package Game;
import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JOptionPane;

//import java.util.concurrent.TimeUnit;

import Pieces.*;

public class Game extends JFrame
{
	/**
	 * Started February 15, 2019
	 * Updated March 8, 2019
	 */
	private static final long serialVersionUID = 1L;
	Player p1 = new Player(Color.WHITE);
	Player p2 = new Player(Color.BLACK);
	private Player currentPlayer = p1;
	private King blackKing, whiteKing;
	
	public Player getCurrentPlayer() {return currentPlayer;}
	public King getBlackKing() {return blackKing;}
	public King getWhiteKing() {return whiteKing;}
	
	private static ArrayList<Piece> pieces = new ArrayList<Piece>();
	private static ArrayList<Square> squares = new ArrayList<Square>();
	
	public ArrayList<Piece> getPieces() {return pieces;}
	public ArrayList<Square> getSquares() {return squares;}
	
	private boolean promotion = false;
	private final int width = 800;
	private final int height = 800;
	private int turn=0;
	
	
	public static void main(String[] args)
	{ 
		Game obj = new Game();
		obj.makeEnvironment();
	}
	
	public void makeEnvironment()
	{
		setTitle("Chess");
		loadSquares();
		loadPieces();
		blackKing = (King) (pieces.get(0));
		whiteKing = (King) (pieces.get(1));
		updateGame();
		setBounds(0, 0, width, height);//frame.setBounds(upperLeftHandX, upperLeftHandY, width, height)
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ChessPanel pan = new ChessPanel();
		pan.setBackground(Color.white);
		getContentPane().add(pan);// attaches the panel to the frame
		setResizable(true);
		setVisible(true);
		addMouseListener(new Clicks());
		repaint();
	}
	
	public void updateGame()
	{
		whiteKing.setCheck(false);
		blackKing.setCheck(false);
		
		if(!blackKing.getCheck())
			blackKing.getCheckPiece().clear();
		if(!whiteKing.getCheck())
			whiteKing.getCheckPiece().clear();
		for(Square sqr: squares)
			sqr.setPiece(null);
		
		for(Piece p : pieces)
		{
			for(Square sqr : squares)
			{
				if(p.getSquare() == sqr)
				{
					sqr.setPiece(p);
					break;
				}
			}
		}
		for(Piece p : pieces)
		{
			if(p.getColor()==Color.BLACK && p.getPieceType()!=PieceType.KING && p.getLegalMoves(p.getSquare()).contains(whiteKing.getSquare()))
				whiteKing.getCheckPiece().add(p);
			if(p.getColor()==Color.WHITE && p.getPieceType()!=PieceType.KING && p.getLegalMoves(p.getSquare()).contains(blackKing.getSquare()))
				blackKing.getCheckPiece().add(p);
		}
		if(!whiteKing.getCheckPiece().isEmpty())
			whiteKing.setCheck(true);
		if(!blackKing.getCheckPiece().isEmpty())
			blackKing.setCheck(true);
		
		if(turn%2==0) {
			currentPlayer=p1;
			return;}
		if(turn%2==1) {
			currentPlayer=p2;
			return;}
	}
	
	public void resetGame()
	{
		pieces.clear();
		squares.clear();
		turn = 0;
		loadSquares();
		loadPieces();
		currentPlayer=p1;
		repaint();
	}
		
	private void loadSquares()
	{
		int width, height;
		for(int r = 0; r<8; r++)
		{
			for(int c = 0; c<8; c++)
			{
				width = r*80+78;
				height = c*80+78;
				squares.add(new Square(width,height,r,c));
			}
		}
	}
	
	private void loadPieces()
	{
		try{
			pieces.add(new King(squares.get(4*8), ImageIO.read(new File("Assets/BlackKing.png")),Color.BLACK));
			pieces.add(new King(squares.get(4*8+7), ImageIO.read(new File("Assets/WhiteKing.png")), Color.WHITE));
			
		for(int i=0; i<8; i++)
				pieces.add(new Pawn(squares.get(i*8+1), ImageIO.read(new File("Assets/BlackPawn.png")),Color.BLACK));
	
		pieces.add(new Queen(squares.get(3*8), ImageIO.read(new File("Assets/BlackQueen.png")), Color.BLACK));
		pieces.add(new Bishop(squares.get(2*8), ImageIO.read(new File("Assets/BlackBishop.png")), Color.BLACK));
		pieces.add(new Bishop(squares.get(5*8), ImageIO.read(new File("Assets/BlackBishop.png")), Color.BLACK));
		pieces.add(new Knight(squares.get(1*8), ImageIO.read(new File("Assets/BlackKnight.png")), Color.BLACK));		
		pieces.add(new Knight(squares.get(6*8), ImageIO.read(new File("Assets/BlackKnight.png")), Color.BLACK));
		pieces.add(new Rook(squares.get(0*8), ImageIO.read(new File("Assets/BlackRook.png")), Color.BLACK));
		pieces.add(new Rook(squares.get(7*8), ImageIO.read(new File("Assets/BlackRook.png")), Color.BLACK));
		
		for( int i=1; i<=8; i++)
			pieces.add(new Pawn(squares.get(8*i-2), ImageIO.read(new File("Assets/WhitePawn.png")), Color.WHITE));
		
		pieces.add(new Queen(squares.get(3*8+7), ImageIO.read(new File("Assets/WhiteQueen.png")), Color.WHITE));
		pieces.add(new Bishop(squares.get(2*8+7), ImageIO.read(new File("Assets/WhiteBishop.png")), Color.WHITE));
		pieces.add(new Bishop(squares.get(5*8+7), ImageIO.read(new File("Assets/WhiteBishop.png")), Color.WHITE));
		pieces.add(new Knight(squares.get(1*8+7), ImageIO.read(new File("Assets/WhiteKnight.png")), Color.WHITE));
		pieces.add(new Knight(squares.get(6*8+7), ImageIO.read(new File("Assets/WhiteKnight.png")), Color.WHITE));
		pieces.add(new Rook(squares.get(0*8+7), ImageIO.read(new File("Assets/WhiteRook.png")), Color.WHITE));
		pieces.add(new Rook(squares.get(7*8+7), ImageIO.read(new File("Assets/WhiteRook.png")), Color.WHITE));
		} catch(IOException e) {e.printStackTrace();}
	}
	
//	public void loadOptions(Piece p)
//	{
//		options.clear();
//		if(p.getColor()==Color.white)
//		{
//			options.add(new Square(p.getX()-90, p.getY()-60, 0, 0));
//			options.add(new Square(p.getX()-30, 0, 0, 0));
//			options.add(new Square(p.getX()+30, 0, 0, 0));
//			options.add(new Square(p.getX()+90, 0, 0, 0));
//			try {
//				options.get(0).setPiece(new Queen(options.get(0), ImageIO.read(new File("Assets/WhiteQueen.png")), Color.white));
//				options.get(1).setPiece(new Bishop(options.get(1), ImageIO.read(new File("Assets/WhiteBishop.png")), Color.white));
//				options.get(2).setPiece(new Knight(options.get(2), ImageIO.read(new File("Assets/WhiteKnight.png")), Color.white));
//				options.get(3).setPiece(new Rook(options.get(3), ImageIO.read(new File("Assets/WhiteRook.png")), Color.white));
//			} catch (IOException e) {e.printStackTrace();}
//		}
//		
//		if(p.getColor()==Color.black)
//		{
//			options.add(new Square(p.getX()-90, p.getY(), 0, 0));
//			options.add(new Square(p.getX()-30, p.getY(), 0, 0));
//			options.add(new Square(p.getX()+30, p.getY(), 0, 0));
//			options.add(new Square(p.getX()+90, p.getY(), 0, 0));
//			try {
//				options.get(0).setPiece(new Queen(options.get(0), ImageIO.read(new File("Assets/BlackQueen.png")), Color.black));
//				options.get(1).setPiece(new Bishop(options.get(1), ImageIO.read(new File("Assets/BlackBishop.png")), Color.black));
//				options.get(2).setPiece(new Knight(options.get(2), ImageIO.read(new File("Assets/BlackKnight.png")), Color.black));
//				options.get(3).setPiece(new Rook(options.get(3), ImageIO.read(new File("Assets/BlackRook.png")), Color.black));
//			} catch (IOException e) {e.printStackTrace();}
//		}
//	}
	
	public ArrayList<Square> saveKing(Piece piece, ArrayList<Square> moves)
	{
		ArrayList<Square> temp = new ArrayList<Square>();
		Square initial = piece.getSquare();
		if(piece.getPieceType()==PieceType.KING)
		{
			for(int i=0; i<moves.size(); i++)
			{
				Square sqr = moves.get(i);
				if(sqr.getEmpty())
				{
					piece.setSquare(sqr);
					updateGame();
					if(!((King)(piece)).getCheck())
						temp.add(sqr);
					piece.setSquare(initial);
					updateGame();
				}
				else if(!sqr.getEmpty())
				{
					Piece unicorn = sqr.getPiece();
					int index = pieces.indexOf(unicorn);
					piece.capture(unicorn);
					updateGame();
					if(!((King)(piece)).getCheck())
						temp.add(sqr);
					piece.setSquare(initial);
					pieces.add(index, unicorn);
					updateGame();
				}
			}
		}
		else if(piece.getColor()==Color.WHITE)
		{
			for(int i=0; i<moves.size(); i++)
			{
				Square sqr = moves.get(i);
				if(sqr.getPiece()!=null && sqr.getPiece().getColor()==Color.black)
					temp.add(sqr);
				piece.setSquare(sqr);
				updateGame();
				if(!whiteKing.getCheck())
					temp.add(sqr);
				piece.setSquare(initial);
				updateGame();
			}
		}
		else if(piece.getColor()==Color.black)
		{
			for(int i=0; i<moves.size(); i++)
			{
				Square sqr = moves.get(i);
				if(sqr.getPiece()!=null && sqr.getPiece().getColor()==Color.white)
					temp.add(sqr);
				piece.setSquare(sqr);
				updateGame();
				if(!blackKing.getCheck())
					temp.add(sqr);
				piece.setSquare(initial);
				updateGame();
			}
		}
		return temp;
	}
	
	public void loadOptions(Piece p)
	{
		int option = JOptionPane.showOptionDialog(this, "Choose a Piece", "Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, Option.values(), Option.values()[0]);
		if (option == JOptionPane.CLOSED_OPTION) {
			loadOptions(p);
	      } else {
	         Option type = Option.values()[option];
	         if(p.getColor()==Color.white)
	         {
		         if(type==Option.QUEEN)
		        	 try {pieces.set(pieces.indexOf(p), new Queen(p.getSquare(), ImageIO.read(new File("Assets/WhiteQueen.png")), Color.white));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.BISHOP)
		        	 try {pieces.set(pieces.indexOf(p), new Bishop(p.getSquare(), ImageIO.read(new File("Assets/WhiteBishop.png")), Color.white));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.KNIGHT)
		        	 try {pieces.set(pieces.indexOf(p), new Knight(p.getSquare(), ImageIO.read(new File("Assets/WhiteKnight.png")), Color.white));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.ROOK)
		        	 try {pieces.set(pieces.indexOf(p), new Rook(p.getSquare(), ImageIO.read(new File("Assets/WhiteRook.png")), Color.white));} catch (IOException e1) {e1.printStackTrace();}
		         if(type!=null)
		        	 promotion=false;
	         }
	        
	         if(p.getColor()==Color.black)
	         {
	        	 if(type==Option.QUEEN)
		        	 try {pieces.set(pieces.indexOf(p), new Queen(p.getSquare(), ImageIO.read(new File("Assets/BlackQueen.png")), Color.black));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.BISHOP)
		        	 try {pieces.set(pieces.indexOf(p), new Bishop(p.getSquare(), ImageIO.read(new File("Assets/BlackBishop.png")), Color.black));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.KNIGHT)
		        	 try {pieces.set(pieces.indexOf(p), new Knight(p.getSquare(), ImageIO.read(new File("Assets/BlackKnight.png")), Color.black));} catch (IOException e1) {e1.printStackTrace();}
		         if(type==Option.ROOK)
		        	 try {pieces.set(pieces.indexOf(p), new Rook(p.getSquare(), ImageIO.read(new File("Assets/BlackRook.png")), Color.black));} catch (IOException e1) {e1.printStackTrace();}
		         if(type!=null)
		        	 promotion=false;
	         }
	    }
	}

	enum Option {
		QUEEN("Queen"), BISHOP("Bishop"), KNIGHT("Knight"), ROOK("Rook");
		private String text;
		
		private Option(String str) {
			this.text =str;
		}
		public String toString() {
			return text;
		}
	}
	class Clicks extends MouseAdapter
	{
		private int mouseX = -10, mouseY = -10;
		private boolean noMoves = false;
		private ArrayList<Square> legalMoves;
		private King enemyKing;
		private Square initial;
		private Piece thisPiece;
		private Piece choice;
		
		public Piece getThisPiece() {return thisPiece;}
		
		ChessPanel obj = new ChessPanel();
		
		public void mousePressed(MouseEvent e)
		{
			mouseX = e.getX()-3;
			mouseY = e.getY()-23;
			
			if(!promotion)
			{
				for(int i=0; i<pieces.size(); i++)
				{
					if(mouseX>pieces.get(i).getX() && mouseX<pieces.get(i).getX()+77 && mouseY>pieces.get(i).getY() && mouseY<pieces.get(i).getY()+77
							&& pieces.get(i).getColor()==currentPlayer.getColor())
					{
						thisPiece = pieces.get(i);
						legalMoves = saveKing(thisPiece, thisPiece.getLegalMoves(thisPiece.getSquare()));
						obj.setLegalMoves(legalMoves);
						if(thisPiece.getColor()==Color.BLACK)
							enemyKing = whiteKing;
						if(thisPiece.getColor()==Color.WHITE)
							enemyKing = blackKing;
						repaint();
						break;
					}
				}
			}
		}
		
		
		public void mouseReleased(MouseEvent e)
		{
			mouseX = e.getX()-3;
			mouseY = e.getY()-23;

			if(thisPiece!=null && !promotion) 
			{
				for(Square sqr : squares)
				{
					if(thisPiece.getColor()==currentPlayer.getColor() && mouseX>sqr.getX() && mouseX<sqr.getX()+77 && mouseY>sqr.getY() && mouseY<sqr.getY()+77 && legalMoves.contains(sqr))
					{
/*Castle*/				if(thisPiece.getPieceType()==PieceType.KING && ((King) (thisPiece)).getCastleMoves().contains(sqr))
							((King) (thisPiece)).castle(sqr);

/*En Passant*/			else if(sqr.getEmpty() && thisPiece.getPieceType()==PieceType.PAWN && ((Pawn) (thisPiece)).getEnPassant(thisPiece.getSquare()).contains(sqr))
						{
							if(thisPiece.getColor()==Color.white)
								{thisPiece.capture(squares.get(squares.indexOf(sqr)+1).getPiece());
								thisPiece.moveTo(sqr);
								}
							else if(thisPiece.getColor()==Color.black)
								{thisPiece.capture(squares.get(squares.indexOf(sqr)-1).getPiece());
								thisPiece.moveTo(sqr);
								}
						}

/*Promotion*/			else if(thisPiece.getPieceType()==PieceType.PAWN && (sqr.getColumn()==0 || sqr.getColumn()==7))
						{
							promotion=true;
							thisPiece.moveTo(sqr);
						//	try {Thread.currentThread().wait(1000);} catch (InterruptedException e1) {e1.printStackTrace();}
							loadOptions(thisPiece);
//							if(thisPiece.getColor()==Color.white)
//								try {pieces.set(pieces.indexOf(thisPiece), new Queen(sqr, ImageIO.read(new File("Assets/WhiteQueen.png")), Color.white));} catch (IOException e1) {e1.printStackTrace();}
//							if(thisPiece.getColor()==Color.black)
//								try {pieces.set(pieces.indexOf(thisPiece), new Queen(sqr, ImageIO.read(new File("Assets/BlackQueen.png")), Color.black));} catch (IOException e1) {e1.printStackTrace();}
						}
						
/*Capture*/				else if(!sqr.getEmpty() && !(sqr.getPiece().getColor()==thisPiece.getColor()))
						{
							if(thisPiece.getPieceType()==PieceType.PAWN && !sqr.getEmpty() && ((Pawn) (thisPiece)).getCaptureMoves(thisPiece.getSquare()).contains(sqr))
								thisPiece.capture(sqr.getPiece());
							else if(!(thisPiece.getPieceType()==PieceType.PAWN))
								thisPiece.capture(sqr.getPiece());
						}
/*Move*/				else
							{thisPiece.moveTo(sqr);}

						legalMoves=null;
						obj.setLegalMoves(legalMoves);
						turn++;
						updateGame();
						repaint();
						break;	
					}
				}
			
//			if(enemyKing.getCheck())
//			{
//				for(int i=0; i<pieces.size(); i++)
//				{
//					Piece p = pieces.get(i);
//					if(p.getColor()!=thisPiece.getColor())
//						for(Square s : saveKing(p, p.getLegalMoves(p.getSquare())))
//						{
//							System.out.println("("+s.getRow()+","+s.getColumn()+")");
//						}
//							
//				}
//			}
				
				for(int i=0; i<pieces.size(); i++) 
				{
					Piece p = pieces.get(i);
					if(p.getColor()!=thisPiece.getColor())
					{
						if(!saveKing(p,p.getLegalMoves(p.getSquare())).isEmpty())
						{
							noMoves = false;
							break;
						}
						else
							noMoves = true;
					}
				}
				
				if(noMoves && enemyKing.getCheck())	
				{
					if(currentPlayer==p1)
					{
						int elephant = JOptionPane.showConfirmDialog(null, "Play A New Game?", "CheckMate: "+p2+" Wins", JOptionPane.YES_NO_OPTION);
						if(elephant==JOptionPane.YES_OPTION)
						{
							resetGame();
						}
						else
							System.exit(0);
					}
					if(currentPlayer==p2)
					{
						int elephant = JOptionPane.showConfirmDialog(null, "Play A New Game?", "CheckMate: "+p1+" Wins", JOptionPane.YES_NO_OPTION);
						if(elephant==JOptionPane.YES_OPTION)
						{
							resetGame();
						}
						else
							System.exit(0);
					}
				}
				if(noMoves && !enemyKing.getCheck())
				{
					int elephant = JOptionPane.showConfirmDialog(null, "Play A New Game?", "StaleMate: No One Wins", JOptionPane.YES_NO_OPTION);
					if(elephant==JOptionPane.YES_OPTION)
					{
						resetGame();
					}
					else
						System.exit(0);
				}
			}
		}
	}
}
