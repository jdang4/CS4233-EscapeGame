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

import java.util.Map;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * @version Apr 26, 2020
 */
public class EscapeGameController implements EscapeGameManager<Coordinate>
{
	private final GenericBoard board;
	private final CoordinateID id;
	private Map<PieceName, PieceDescriptor> pieceTypes;
	 
	public EscapeGameController(GenericBoard board, CoordinateID id) 
	{
		this.board = board;
		this.id = id;
	}
	
	public GenericBoard getBoard()
	{
		return this.board;
	}
	
	public void setPieceTypes(Map<PieceName, PieceDescriptor> piecetypes)
	{
		this.pieceTypes = piecetypes;
	}
	
	public Map<PieceName, PieceDescriptor> getPieceTypes()
	{
		return this.pieceTypes;
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
		if (id.equals(CoordinateID.SQUARE))
		{
			return SquareCoordinate.makeCoordinate(x, y);
		}
		
		else if (id.equals(CoordinateID.ORTHOSQUARE))
		{
			return OrthoSquareCoordinate.makeCoordinate(x, y);
		}
		
		else
		{
			return HexCoordinate.makeCoordinate(x, y);
		}
	}
	
	
	/*
	 * @see escape.EscapeGameManager#move(escape.board.coordinate.Coordinate, escape.board.coordinate.Coordinate)
	 */
	@Override
	public boolean move(Coordinate from, Coordinate to)
	{
		EscapePiece movingPiece = getPieceAt(from);
		
		if (!pieceTypes.containsKey(movingPiece.getName()))
		{
			throw new EscapeException("Cannot move invalid piece");
		}
		
		/**
		 * TODO
		 * (1) extract the piece's pieceType info
		 * (2) do some error checking based on pieceType info
		 * 		(2.1) Hex cannot have Orthothogonal or Diagonal
		 * 		(2.2) OMNI needs distance value
		 * 		(2.3) FLY and JUMP cannot co-exist ????
		 * 		(2.4) FLY and DISTANCE cannot co-exist 
		 */
		
		PieceDescriptor movingPieceDescriptor = pieceTypes.get(movingPiece.getName());
		
		
		return false;
	}
	
}
