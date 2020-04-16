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
 * Description
 * @version Apr 12, 2020
 */
public class HexBoard extends GenericBoard implements Board<Coordinate>
{
	Map<HexCoordinate, LocationType> hexes;
	Map<HexCoordinate, EscapePiece> pieces;
	 
	//private final int xMax, yMax;
	public HexBoard(int xMax, int yMax)
	{
		super(xMax, yMax);
		pieces = new HashMap<HexCoordinate, EscapePiece>();
		hexes = new HashMap<HexCoordinate, LocationType>();
		type = BoardType.HEX;
	}
	 
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	//TODO
	// implement this
	@Override
	public EscapePiece getPieceAt(Coordinate coord)
	{
		if (!coord.getClass().equals(HexCoordinate.class))
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
	
	private boolean insideBoard(HexCoordinate coord)
	{
		boolean validXBoundary = inXBoundary(coord);
		boolean validYBoundary = inYBoundary(coord);
		
		return (validXBoundary && validYBoundary);
	}
	
	private boolean inXBoundary(HexCoordinate coord) 
	{
		// infinite rows
		if (xMax == 0)
		{
			return true;
		}
		
		else
		{
			if (coord.getX() >= 0 && coord.getX() <= getXMax())
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean inYBoundary(HexCoordinate coord)
	{
		if (yMax == 0)
		{
			return true;
		}
		
		else
		{
			if (coord.getY() >= 0 && coord.getY() <= getYMax())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	// TODO
	// implement this
	@Override
	public void putPieceAt(EscapePiece p, Coordinate coord)
	{
		LocationType BLOCK = LocationType.BLOCK;
		LocationType EXIT = LocationType.EXIT;
		
		if (!coord.getClass().equals(HexCoordinate.class))
		{
			throw new EscapeException("Invalid Coordinate Type");
		} 
		
		HexCoordinate hc = (HexCoordinate) coord;
		
		if (p == null && pieces.containsKey(hc))
		{
			pieces.remove(coord);
		}
		
		// handling the special cases of the coordinate's location type
		if (getLocationType(hc) != null)
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
	
	public LocationType getLocationType(HexCoordinate c)
	{
		if (hexes.containsKey(c))
		{
			return hexes.get(c);
		}
		
		return null;
	}

}
