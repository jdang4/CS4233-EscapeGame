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

import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * This class is the instantiation of a game manager. This is the centralizing
 * of where most of the logic is happened for the beta iteration
 * @version Apr 26, 2020
 */
public class EscapeGameController implements EscapeGameManager<Coordinate>
{
	private final GenericBoard board;
	private final PathFindingAlgorithm findPath;
	
	/**
	 * The constructor for a game controller
	 * 
	 * @param board 
	 * 		the board that is used by the controller
	 */
	public EscapeGameController(GenericBoard board) 
	{
		this.board = board;
		this.findPath = new PathFindingAlgorithm(board);
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
	
	/**
	 * This method verifys the movement pattern against a hex board
	 * 
	 * @param movePattern - the movement pattern to test against
	 * @return
	 * 		whether the movement pattern is allowed by the hex board
	 * 		true -> is allowed; false -> is not allowed
	 */
	private boolean verifyMovementPatternForHex(MovementPatternID movePattern)
	{
		if (board.getBoardType().equals(BoardType.HEX) &&
				(movePattern.equals(MovementPatternID.ORTHOGONAL) ||
				movePattern.equals(MovementPatternID.DIAGONAL)))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method verifys the movement pattern against a ortho board
	 * 
	 * @param movePattern - the movement pattern to test against
	 * @return
	 * 		whether the movement pattern is allowed by the ortho board
	 * 		true -> is allowed; false -> is not allowed
	 */
	private boolean verifyMovementPatternForOrtho(MovementPatternID movePattern)
	{
		if (board.getBoardType().equals(BoardType.ORTHOSQUARE) &&
				movePattern.equals(MovementPatternID.DIAGONAL))
		{
			return false;
		}
		
		return true;
	} 
	
	/**
	 * This method checks for any moves that should return a false without having to 
	 * test the movement pattern or having to find the path
	 * 
	 * @param from - the starting coordinate
	 * @param to - the destingation coordinate
	 * @param movingPiece - the escape piece that is moving
	 * @return
	 * 		whether it detected a false move or not
	 * 		true -> detected a false move; false -> no detectin
	 */
	private boolean checkFalseMoves(Coordinate from, Coordinate to, EscapePiece movingPiece)
	{
		if (from.equals(to) || movingPiece == null)
		{
			return false;
		}
		
		LocationType BLOCK = LocationType.BLOCK;
		
		if (board.getLocationType(to).equals(BLOCK))
		{
			return false;
		}
		
		EscapePiece pieceAtDest = board.getPieceAt(to);
		
		if (pieceAtDest != null && movingPiece.getPlayer().equals(pieceAtDest.getPlayer())) 
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method is used to get the available movement direction for the path finding algorithm
	 * 
	 * @param pattern - the movement pattern id
	 * @param src - the starting coordinate
	 * @param dest - the ending coordinate
	 * @return
	 * 		the appropriate directions that depends against the board type used and the the movement pattern
	 * 		given
	 */
	private int[][] getMovementDirections(MovementPatternID pattern, PathFinderCoordinate src, PathFinderCoordinate dest)
	{
		int[][] ORTHOGONAL = { {0,-1}, {0,1}, {-1,0}, {1,0} };
		int[][] DIAGONAL = { {-1,-1}, {-1,1}, {1,-1}, {1,1} };
		int[][] OMNI = { 
				{0,-1}, {0,1}, {-1,0}, {1,0},
				{-1,-1}, {-1,1}, {1,-1}, {1,1}
		};
		
		if (pattern.equals(MovementPatternID.ORTHOGONAL))
		{
			return ORTHOGONAL;
		}
		
		else if (pattern.equals(MovementPatternID.DIAGONAL))
		{
			return DIAGONAL;
		}
		
		else if (pattern.equals(MovementPatternID.OMNI))
		{
			// omni on orthosquare board ==> orthogonal movement
			if (board.getBoardType().equals(BoardType.ORTHOSQUARE))
			{
				return ORTHOGONAL;
			}
			
			// handling the specific movements of a hex board
			else if (board.getBoardType().equals(BoardType.HEX))
			{
				int[][] hex_OMNI = {
						{-1, 1}, {0, 1}, {1,0},
						{1,-1}, {0,-1}, {-1,0}
				};
				
				return hex_OMNI;
			}
			
			return OMNI;
			
		}
		
		// handling a linear move case
		else
		{
			
			int srcX = src.getX();
			int srcY = src.getY();
			
			int destX = dest.getX();
			int destY = dest.getY();
			
			int xDiff = destX - srcX;
			int yDiff = destY - srcY;
			
			int absXDiff = Math.abs(destX) - Math.abs(srcX);
			int absYDiff = Math.abs(destY) - Math.abs(srcY);
			
			
			if (board.getBoardType().equals(BoardType.ORTHOSQUARE))
			{
				if (absXDiff != 0 && absYDiff != 0)
				{
					return null;
				}
				
			}
			
			// handling the zero case
			int xDirection = (xDiff == 0) ? 0 : (xDiff / Math.abs(xDiff));
			int yDirection = (yDiff == 0) ? 0 : (yDiff / Math.abs(yDiff));
			
			
			int[][] LINEAR = { {xDirection, yDirection} };
			
			return LINEAR;
		}
	}
	
	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		EscapePiece movingPiece = getPieceAt(from);
		
		/**
		 * TODO
		 * (1) extract the piece's pieceType info (DONE)
		 * (2) do some error checking based on pieceType info
		 * 		(2.1) Hex cannot have Orthothogonal or Diagonal (DONE)
		 * 		(2.2) OMNI needs distance value (DONE)
		 * 		(2.3) FLY and JUMP cannot co-exist (DONE)
		 * 		(2.4) FLY and DISTANCE cannot co-exist (DONE)
		 * 		(2.5) Must have at least a Fly or Distance (DONE)
		 * 		(2.6) Ortho cannot have Diagonal (DONE)
		 * 		(2.7) Saw 2 MovementPatterns (DONE)
		 * 		(2.8) Start or End not in board
		 * 
		 * (3) false cases
		 * 		(3.1) Block at ending location (DONE)
		 * 		(3.2) Same Piece Type At end location
		 * 		(3.3) Non Omni and exits board when in route (DONE)
		 * 		(3.4) Cannot reach location with its specifications (DONE)
		 * 		(3.5) Distance is too small to reach to destination (DONE)
		 * 		(3.6) No Pieces at Start (DONE)
		 * 		(3.7) Start == End (DONE)
		 * 	
		 */ 
		
		if (movingPiece == null)
		{
			return false;
		}
		MovementPatternID movePattern = movingPiece.getDescriptor().getMovementPattern();
		
		if (!(verifyMovementPatternForHex(movePattern) && 
				verifyMovementPatternForOrtho(movePattern)))
			
		{
			throw new EscapeException("Invalid Movement Pattern for Board");
		}
		
		if (!checkFalseMoves(from, to, movingPiece))
		{
			return false;
		} 
		
		int limit = movingPiece.getDescriptor().getFlyOrDistanceValue();
		
		PathFinderCoordinate start = (PathFinderCoordinate) from;
		PathFinderCoordinate end = (PathFinderCoordinate) to;
		
		int[][] availableDirections = getMovementDirections(movePattern, start, end);
		
		if (availableDirections == null)
		{
			return false;
		}
		boolean moveResult = findPath.canMakeMove(start, end, availableDirections, limit);
		
		if (moveResult)
		{
			board.putPieceAt(movingPiece, to);
		}
		return moveResult;
	}
	
	
	
}
