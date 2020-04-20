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

package escape.board;

import java.util.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;

/**
 * The implementation of a Hex Board
 * @version Apr 12, 2020
 */
public class HexBoard extends GenericBoard implements Board<Coordinate>
{
	Map<HexCoordinate, LocationType> hexes;
	Map<HexCoordinate, EscapePiece> pieces;
	 
	public HexBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
		pieces = new HashMap<HexCoordinate, EscapePiece>();
		hexes = new HashMap<HexCoordinate, LocationType>();
		type = BoardType.HEX;
	}
	
	/*
	 * @see escape.board.GenericBoard#sameCoordinate(escape.board.coordinate.Coordinate)
	 */
	@Override
	protected boolean sameCoordinate(Coordinate c)
	{
		if (c.getClass().equals(HexCoordinate.class))
		{
			return true; 
		}
		
		return false;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		// verify if the coordinate has the correct id
		if (!sameCoordinate(coord))
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
	
	/**
	 * This method is used to determine if the given coordinate is within the 
	 * boundary (if any) of the board
	 * 
	 * @param coord 
	 * 			the coordinate to check for if it's inside the board's boundary
	 * @return whether the coordinate is within the board:
	 * 			true -> in the board, false -> not in the board
	 */
	private boolean insideBoard(HexCoordinate coord)
	{
		boolean validXBoundary = inXBoundary(coord);
		boolean validYBoundary = inYBoundary(coord);
		
		return (validXBoundary && validYBoundary);
	}
	
	/**
	 * This method is used to determine if the given coordinate's x-value is
	 * within the board's boundary (if any)
	 * 
	 * @param coord 
	 * 			the coordinate to check for if it's inside the board's boundary
	 * @return whether the coordinate is within the board:
	 * 			true -> in the board, false -> not in the board
	 */
	private boolean inXBoundary(HexCoordinate coord) 
	{
		// just return true if x-axis is infinite
		if (xMax == 0)
		{
			return true;
		}
		
		// axis is not infinite
		else
		{
			if (coord.getX() >= 0 && coord.getX() < getXMax())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method is used to determine if the given coordinate's y-value is within
	 * the board's boundary (if any)
	 * 
	 * @param coord 
	 * 			the coordinate to check for if it's inside the board's boundary
	 * @return whether the coordinate is within the board:
	 * 			true -> in the board, false -> not in the board
	 */
	private boolean inYBoundary(HexCoordinate coord)
	{
		// just return true if this y-axis is infinite
		if (yMax == 0)
		{
			return true;
		}
		
		// axis is not infinite
		else
		{
			if (coord.getY() >= 0 && coord.getY() < getYMax())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	@Override
	public void putPieceAt(EscapePiece p, Coordinate coord)
	{
		LocationType CLEAR = LocationType.CLEAR;
		LocationType BLOCK = LocationType.BLOCK;
		LocationType EXIT = LocationType.EXIT;
		
		if (!sameCoordinate(coord))
		{
			throw new EscapeException("Invalid Coordinate Type");
		} 
		
		HexCoordinate hc = (HexCoordinate) coord;
		
		if (p == null && pieces.containsKey(hc))
		{
			pieces.remove(coord);
		}
		
		// handling the special cases of the coordinate's location type
		if (!getLocationType(hc).equals(CLEAR))
		{
			if (getLocationType(hc).equals(EXIT))
			{ 
				return;
			}

			else if (getLocationType(hc).equals(BLOCK))
			{
				throw new EscapeException("Cannot put piece on a Block");
			}
		}
		if (insideBoard(hc))
		{
			pieces.put(hc, p);
			return;
		}
		
		throw new EscapeException("Unable to place piece on board");
	}
	
	/**
	 * This method is called to set a location type on the hex board
	 * 
	 * @param c 
	 * 			the coordinate to add the location type to on the board
	 * @param lt
	 * 			the location type to add to the board
	 */
	public void setLocationType(HexCoordinate c, LocationType lt)
	{
		if (insideBoard(c))
		{
			hexes.put(c, lt);
		}
		
		else
		{ 
			throw new EscapeException("Coordinate not in board");
		}
	}
	
	/**
	 * This method is called to get the location type at a specific coordinate
	 * on the board
	 * 
	 * @param c
	 * 			the coordinate to get the location type
	 * @return the locationType if it exists, else null
	 */
	public LocationType getLocationType(HexCoordinate c)
	{
		if (hexes.get(c) == null) 
		{
			return LocationType.CLEAR;
		}
		
		else
		{
			return hexes.get(c);
		} 
	}

}
