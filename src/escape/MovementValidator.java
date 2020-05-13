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
import escape.piece.*;

/**
 * This class is called by the concrete game manager to validate a move and also be provided
 * the appropriate directions to use
 * @version May 10, 2020
 */
public class MovementValidator
{
	private GenericBoard board;
	private BoardType type;
	private String falseMessage;
	private String errorMessage; 
	
	public MovementValidator(GenericBoard board) 
	{
		this.board = board;
		this.type = board.getBoardType();
	}
	
	public boolean passErrorCheck(Coordinate from, Coordinate to) 
	{
		EscapePiece movingPiece = board.getPieceAt(from);
		MovementPatternID pattern = movingPiece.getDescriptor().getMovementPattern();
		
		return verifyMovementPatternForHex(pattern) && verifyMovementPatternForOrtho(pattern);
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
		if (type.equals(BoardType.HEX) &&
				(movePattern.equals(MovementPatternID.ORTHOGONAL) ||
				movePattern.equals(MovementPatternID.DIAGONAL)))
		{
			if (errorMessage == null)
			{
				errorMessage = "Cannot Use the " + movePattern.name() + " Pattern on a Hex Board"; 
			}
			
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
		if (type.equals(BoardType.ORTHOSQUARE) &&
				movePattern.equals(MovementPatternID.DIAGONAL))
		{
			if (errorMessage == null)
			{
				errorMessage = "Cannot Use the " + movePattern.name() + " Pattern on a Ortho Board"; 
			}
			
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
	 * @return
	 * 		whether it detected a false move or not
	 * 		true -> detected a false move; false -> no detectin
	 */
	public boolean passFalseCheck(Coordinate from, Coordinate to)
	{
		EscapePiece movingPiece = board.getPieceAt(from);
		
		// make sure the coordinates are valid
		if (from == null || to == null)
		{
			falseMessage = "Out of Bounds Coordinate";
			return false;
		}

		// same spot
		if (from.equals(to))
		{
			falseMessage = "Moving to Same Spot";
			return false; 
		}
		
		if (movingPiece == null)
		{
			falseMessage = "No Piece To Move";
			return false;
		}

		LocationType BLOCK = LocationType.BLOCK;

		// cannot land on a block
		if (board.getLocationType(to).equals(BLOCK))
		{
			falseMessage = "land on block";
			return false;
		}

		EscapePiece pieceAtDest = board.getPieceAt(to);

		if (pieceAtDest != null && movingPiece.getPlayer().equals(pieceAtDest.getPlayer()))
		{
			falseMessage = "land on same player";
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
	public int[][] getMovementDirections(PathFinderCoordinate src, PathFinderCoordinate dest)
	{
		EscapePiece movingPiece = board.getPieceAt(src);
		MovementPatternID pattern = movingPiece.getDescriptor().getMovementPattern();
		
		int[][] ORTHOGONAL = { {0,-1}, {0,1}, {-1,0}, {1,0} };
		int[][] DIAGONAL = { {-1,-1}, {-1,1}, {1,-1}, {1,1} };
		int[][] OMNI = { 
				{0,-1}, {0,1}, {-1,0}, {1,0},
				{-1,-1}, {-1,1}, {1,-1}, {1,1}
		};
		
		int srcX = src.getX();
		int srcY = src.getY();
		
		int destX = dest.getX();
		int destY = dest.getY();
		
		int xDiff = destX - srcX;
		int yDiff = destY - srcY;
		
		int absXDiff = Math.abs(destX - srcX);
		int absYDiff = Math.abs(destY - srcY);
		
		if (pattern.equals(MovementPatternID.ORTHOGONAL))
		{
			return ORTHOGONAL;
		}
		
		else if (pattern.equals(MovementPatternID.DIAGONAL))
		{
			
			if (absXDiff != absYDiff)
			{
				falseMessage = "non-diagonal";
				return null;
			}
			
			return DIAGONAL;
		}
		
		else if (pattern.equals(MovementPatternID.OMNI))
		{
			// omni on orthosquare board ==> orthogonal movement
			if (type.equals(BoardType.ORTHOSQUARE))
			{
				return ORTHOGONAL;
			}
			
			// handling the specific movements of a hex board
			else if (type.equals(BoardType.HEX))
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
			
			if (absXDiff != 0 && absYDiff != 0)
			{
				if (type.equals(BoardType.ORTHOSQUARE))
				{
					falseMessage = "non-linear";
					return null;
				}
				
				else if (absXDiff != absYDiff && type.equals(BoardType.SQUARE))
				{
					falseMessage = "non-linear";
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
	
	public String getFalseMessage()
	{
		return this.falseMessage;
	}
	
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
	
	
}
