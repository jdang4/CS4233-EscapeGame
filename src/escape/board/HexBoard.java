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
import escape.piece.EscapePiece;

/**
 * Description
 * @version Apr 12, 2020
 */
public class HexBoard implements Board<HexCoordinate>
{
	Map<HexCoordinate, LocationType> hexes;
	Map<HexCoordinate, EscapePiece> pieces;
	
	private final int xMax, yMax;
	public HexBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
		pieces = new HashMap<HexCoordinate, EscapePiece>();
		hexes = new HashMap<HexCoordinate, LocationType>();
	}
	
	public HexBoard()
	{
		this.xMax = Integer.MAX_VALUE;
		this.yMax = Integer.MAX_VALUE;
		pieces = new HashMap<HexCoordinate, EscapePiece>();
		hexes = new HashMap<HexCoordinate, LocationType>();
	}
	
	/*
	 * @see escape.board.Board#getPieceAt(escape.board.coordinate.Coordinate)
	 */
	//TODO
	// implement this
	@Override
	public EscapePiece getPieceAt(HexCoordinate coord)
	{
		// TODO Auto-generated method stub
		return pieces.get(coord);
	}

	/*
	 * @see escape.board.Board#putPieceAt(escape.piece.EscapePiece, escape.board.coordinate.Coordinate)
	 */
	// TODO
	// implement this
	@Override
	public void putPieceAt(EscapePiece p, HexCoordinate coord)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void setLocationType(HexCoordinate c, LocationType lt)
	{
		hexes.put(c, lt);
	}
}
