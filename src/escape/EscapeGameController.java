/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package escape;

import java.util.ArrayList;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;
import escape.rule.*;

/**
 * This class is the instantiation of a game manager. This is the centralizing
 * of where most of the logic is happened for the beta iteration
 * @version Apr 26, 2020
 */
public class EscapeGameController implements EscapeGameManager<Coordinate>
{
	private final GenericBoard board;
	private final PathFindingAlgorithm findPath;
	private final MovementValidator movementValidator;
	private final RuleValidator ruleValidator;
	
	private ArrayList<GameObserver> observers = new ArrayList<GameObserver>();
	private final RuleDescriptor ruleDescriptor;
	
	private Player playerTurn;
	private boolean winCondition;
	private Player winner;
	private int numOfTurns;

	
	/**
	 * The constructor for a game controller
	 * 
	 * @param board 
	 * 		the board that is used by the controller
	 */
	public EscapeGameController(GenericBoard board, RuleDescriptor descriptor) 
	{
		this.board = board;
		this.findPath = new PathFindingAlgorithm(board);
		this.movementValidator = new MovementValidator(board);
		this.ruleValidator = new RuleValidator(descriptor);
		this.playerTurn = Player.PLAYER1;
		this.winCondition = false;
		this.ruleDescriptor = descriptor;
		this.numOfTurns = 0;
	}
	
	/**
	 * A getter function for the board
	 * @return 
	 * 		the board instance
	 */
	public GenericBoard getBoard()
	{
		return this.board;
	}
	
	/*
	 * @see escape.EscapeGameManager#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate c)
	{
		return board.getPieceAt(c);
	}
	  
	/*
	 * @see escape.EscapeGameManager#makeCoordinate(int, int)
	 */
	@Override
	public Coordinate makeCoordinate(int x, int y)
	{
		Coordinate result;
		if (board.getBoardType().equals(BoardType.SQUARE))
		{
			
			result = SquareCoordinate.makeCoordinate(x, y);
			
		}
		
		else if (board.getBoardType().equals(BoardType.ORTHOSQUARE))
		{
			result = OrthoSquareCoordinate.makeCoordinate(x, y);
		}
		
		else
		{
			result = HexCoordinate.makeCoordinate(x, y);
		}
		
		if (!board.insideBoard(result)) 
		{
			result = null;
		}
		
		return result;
	}
	
	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		if (winCondition)
		{
			String message = "Game is over and there were no winners";
			
			if (winner != null)
			{
				message = "Game is over and " + winner.name() + " has won";
			}
			
			notifyObservers(message, null);
			return false;
		}
		
		// do some early checks for a false case
		if (!movementValidator.passFalseCheck(from, to)) 
		{
			String falseMessage = movementValidator.getFalseMessage();
			
			notifyObservers(falseMessage, null);
			return false;
		}
		
		// verify the movement is a valid movement for board
		if (!movementValidator.passErrorCheck(from, to)) 
		{
			String errorMessage = movementValidator.getErrorMessage();
			EscapeException cause = new EscapeException(errorMessage);
			notifyObservers(errorMessage, cause);
			
			return false;
			
		}
		
		EscapePiece movingPiece = getPieceAt(from);
		
		if (ruleValidator.notPlayerTurn(movingPiece, playerTurn))
		{
			notifyObservers(playerTurn.name() + " turn", null);
			return false;
		}
		
 		// essentially the distance limit of the moving piece
		int limit = movingPiece.getDescriptor().getFlyOrDistanceValue(); 
		
		if (limit < 0)
		{
			notifyObservers("No Negative Values", null);
			return false;
		}
		
		PathFinderCoordinate start = (PathFinderCoordinate) from;
		PathFinderCoordinate end = (PathFinderCoordinate) to;
		
		int[][] availableDirections = movementValidator.getMovementDirections(start, end);
		
		// another early false check
		if (availableDirections == null)
		{
			String falseMessage = movementValidator.getFalseMessage();
			notifyObservers(falseMessage, null);
			
			return false; 
		}
		
		boolean moveResult = findPath.canMakeMove(start, end, availableDirections, limit);
		
		if (moveResult)
		{
			if (getPieceAt(to) != null)
			{
				if (ruleValidator.canBattle())
				{
					movingPiece = battle(movingPiece, to);
				}
				 
				else
				{
					notifyObservers("piece cannot battle", null);
					return false;
				}
			}
			
			else if (board.getLocationType(to).equals(LocationType.EXIT))
			{
				ruleValidator.updateScore(movingPiece);
			}
			
			board.putPieceAt(movingPiece, to);
			board.putPieceAt(null, from);
			
			if (ruleValidator.checkIfGameEnded(numOfTurns))
			{
				winner = ruleValidator.getGameWinner();
				
				String gameEndMessage = "Tied Game";
				
				if (winner != null)
				{
					gameEndMessage = winner.name() + " wins";
				}
				
				notifyObservers(gameEndMessage, null);
				winCondition = true;
			}
			
			this.playerTurn = (playerTurn.equals(Player.PLAYER1)) ? Player.PLAYER2 : Player.PLAYER1;
			
			if (playerTurn.equals(Player.PLAYER2))
			{	
				numOfTurns++;
			}
		}
		
		else
		{
			String falseMessage = findPath.getFalseMessage();
			notifyObservers(falseMessage, null);
			
		}
		
		return moveResult;
	}
	
	
	
	private EscapePiece battle(EscapePiece movingPiece, Coordinate dest)
	{
		RuleID battleID = ruleDescriptor.getBattleID();
		
		if (battleID.equals(RuleID.REMOVE))
		{
			return movingPiece;
		}
		
		EscapePiece pieceAtDest = board.getPieceAt(dest);
		
		int movingPiece_val = movingPiece.getValue();
		int pieceAtDest_val = pieceAtDest.getValue();
		
		// movingPiece won the battle
		if (movingPiece_val > pieceAtDest_val)
		{
			movingPiece.setValue(movingPiece_val - pieceAtDest_val);
			return movingPiece;
		}
		
		// movingPiece lost the battle
		else if (pieceAtDest_val > movingPiece_val)
		{
			pieceAtDest.setValue(pieceAtDest_val - movingPiece_val);
			return pieceAtDest;
		}
		
		// pieces "killed" each other
		else
		{
			board.putPieceAt(null, dest);
			return null;
		}
	}
	
	
	/*
	 * @see escape.EscapeGameManager#addObserver(escape.GameObserver)
	 */
	@Override
	public GameObserver addObserver(GameObserver observer)
	{
		observers.add(observer);
		
		return observer;
	}
	
	/*
	 * @see escape.EscapeGameManager#removeObserver(escape.GameObserver)
	 */
	@Override
	public GameObserver removeObserver(GameObserver observer)
	{
		observers.remove(observers.indexOf(observer));
		
		return observer;
	}
	
	/**
	 * This method is called to notify the observers who are observing the game
	 * 
	 * @param message - the message to notify each observers
	 * @param error - the cause of the error (if any) 
	 */
	private void notifyObservers(String message, Throwable error)
	{
		for (GameObserver obs : observers)
		{
			if (error == null)
			{
				obs.notify(message);
			}
			
			else
			{
				obs.notify(message, error);
			}
		}
	}
	
}
