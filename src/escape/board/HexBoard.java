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
public class HexBoard implements Board<HexCoordinate>
{
	Map<HexCoordinate, LocationType> hexes;
	Map<HexCoordinate, EscapePiece> pieces;
	private final BoardType type; 
	private CoordinateID id;
	
	private final int xMax, yMax;
	public HexBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<HexCoordinate, EscapePiece>();
		hexes = new HashMap<HexCoordinate, LocationType>();
		type = BoardType.HEX;
		id = CoordinateID.HEX;
	}
	
	public BoardType getBoardType()
	{
		return type;
	}
	
	/*
	 * @see escape.board.Board#setCoordinateID()
	 */
	public void setCoordinateID(CoordinateID id)
	{
		this.id = id;
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	//TODO
	// implement this
	@Override
	public EscapePiece getPieceAt(HexCoordinate coord)
	{
		if (pieces.containsKey(coord))
		{
			return pieces.get(coord);
		}
		
		return null;
	}
	
	private boolean insideBoard(HexCoordinate coord)
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

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	// TODO
	// implement this
	@Override
	public void putPieceAt(EscapePiece p, HexCoordinate coord)
	{
		boolean is_finite = (xMax == 0 && yMax == 0) ? false : true;
		
		if (p == null && pieces.containsKey(coord))
		{
			pieces.remove(coord);
		}
		
		else
		{
			// checking if it is a finite hex board
			if (is_finite)
			{
				if (!insideBoard(coord))
				{
					throw new EscapeException("Not a Valid Hex Coordinate for this HexBoard");
				}
			}
			
			pieces.put(coord, p);
		}
	}
	
	public void setLocationType(HexCoordinate c, LocationType lt)
	{
		hexes.put(c, lt);
	}

	

}
