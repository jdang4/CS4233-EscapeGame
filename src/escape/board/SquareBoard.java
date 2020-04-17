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
public class SquareBoard extends GenericBoard implements Board<Coordinate>, SquareBoardInfo
{
	Map<SquaredShapeCoordinate, LocationType> squares;
	Map<SquaredShapeCoordinate, EscapePiece> pieces;
	
	//private final int xMax, yMax;
	public SquareBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
		pieces = new HashMap<SquaredShapeCoordinate, EscapePiece>();
		squares = new HashMap<SquaredShapeCoordinate, LocationType>();
		type = BoardType.SQUARE;
	} 
	 
	private CoordinateID getCoordinateID(Coordinate c)
	{
		if (c.getClass().equals(SquareCoordinate.class))
		{
			return CoordinateID.SQUARE;
		}
		
		else if (c.getClass().equals(OrthoSquareCoordinate.class))
		{
			return CoordinateID.ORTHOSQUARE;
		}
		
		else
		{
			return CoordinateID.HEX;
		}
	}
	
	private boolean insideBoard(SquaredShapeCoordinate coord)
	{
		if (coord.getX() > 0 && coord.getX() <= getXMax())
		{
			if (coord.getY() > 0 && coord.getY() <= getYMax())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		if (!this.id.equals(getCoordinateID(coord)))
		{
			throw new EscapeException("Invalid Coordinate Type");
		}
		
		else if (pieces.containsKey(coord))
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
	public void putPieceAt(EscapePiece p, Coordinate coord)
	{
		LocationType BLOCK = LocationType.BLOCK;
		LocationType EXIT = LocationType.EXIT;
		
		if (!this.id.equals(getCoordinateID(coord)))
		{
			throw new EscapeException("Invalid Coordinate Type");
		}
		
		SquaredShapeCoordinate sc = (SquaredShapeCoordinate) coord;
		// putting a null where an existing piece is at
		if (p == null && pieces.containsKey(sc))
		{
			pieces.remove(sc);
			return;
		}

		// handling the special cases of the coordinate's location type
		if (getLocationType(sc) != null)
		{
			if (getLocationType(sc).equals(EXIT))
			{ 
				return;
			}

			else if (getLocationType(sc).equals(BLOCK))
			{
				throw new EscapeException("Cannot put piece on a Block");
			}
		}

		// lastly check to see if coordinate is within the board
		// before putting it in the board
		if (insideBoard(sc))
		{
			pieces.put(sc, p);
			return;
		}
		
		throw new EscapeException("Unable to place piece on board");
		
	}
	 
	public void setLocationType(SquaredShapeCoordinate c, LocationType lt)
	{
		if (c.getID().equals(this.id) && insideBoard(c))
		{
			squares.put(c, lt);
		}
		
		else
		{
			throw new EscapeException("Given invalid coordinate");
		}

	}
	 
	private LocationType getLocationType(SquaredShapeCoordinate c)
	{	
		if (squares.containsKey(c))
		{
			return squares.get(c);
		}
		
		return null;
	}
}
