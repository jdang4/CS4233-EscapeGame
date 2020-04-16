/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Copyright ©2016-2020 Gary F. Pollice
 *******************************************************************************/
package escape.board;

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * An example of how a Board might be implemented. This board has
 * square coordinates and finite bounds, represented by xMax and yMax.
 * All methods required by the Board interface have been implemented. Students
 * would naturally add methods based upon theire design.
 * @version Apr 2, 2020
 */
public class SquareBoard implements Board<SquaredShapeCoordinate>, SquareBoardInfo
{
	Map<SquaredShapeCoordinate, LocationType> squares;
	Map<SquaredShapeCoordinate, EscapePiece> pieces;
	private final BoardType type;
	private CoordinateID id;
	
	private final int xMax, yMax;
	public SquareBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<SquaredShapeCoordinate, EscapePiece>();
		squares = new HashMap<SquaredShapeCoordinate, LocationType>();
		type = BoardType.SQUARE;
	}
	
	private boolean insideBoard(SquaredShapeCoordinate coord)
	{
		if (coord.getX() >= 0 && coord.getX() <= xMax)
		{
			if (coord.getY() >= 0 && coord.getY() <= yMax)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public BoardType getBoardType()
	{
		return type;
	}
	
	public void setCoordinateID(CoordinateID id)
	{
		this.id = id;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(SquaredShapeCoordinate coord)
	{
		if (pieces.containsKey(coord))
		{
			return pieces.get(coord);
		}
		
		// no pieces at coordinate
		return null;
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, SquaredShapeCoordinate coord)
	{
		
		// verifying if attempting to put piece on a Block
		if (getLocationType(coord).equals(LocationType.BLOCK))
		{
			throw new EscapeException("Cannot place piece on a blocked coordinate");
		}
		
		// desired coordinate is not of a block type
		else
		{
			// verify coordinate id is not different
			if (coord.getID().equals(this.id))
			{
				if (p == null && pieces.containsKey(coord))
				{
					pieces.remove(coord);
					return;
				}
				
				else
				{
					if (insideBoard(coord))
					{
						if (!getLocationType(coord).equals(LocationType.EXIT))
						{
							pieces.put(coord, p);
						}
						
						return;
					}
				}
			}
			
			// coordinate id was different
			else
			{
				throw new EscapeException("Cannot put a different coordinate type on board");
			}
		}
	}
	
	public void setLocationType(SquaredShapeCoordinate c, LocationType lt)
	{
		squares.put(c, lt);
	}
	
	public LocationType getLocationType(SquaredShapeCoordinate c)
	{
		if (squares.containsKey(c))
		{
			return squares.get(c);
		}
		
		return null;
	}
	
	public int getXMax()
	{
		return xMax;
	}
	
	public int getYMax()
	{
		return yMax;
	}
	
}
