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

package escape.board.initializer;

import static escape.board.LocationType.CLEAR;
import java.util.Map;
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;
import escape.util.LocationInitializer;

/**
 * This class provides the implementations to initialize a square board 
 * with Square Coordinates
 * @version Apr 14, 2020
 */
public class SquareBoardInitializer extends InitializeBoard
{
	public SquareBoardInitializer(GenericBoard b)
	{
		super(b);
	}
	/*
	 * @see escape.board.initializer.InitializeBoard#initializeBoard(escape.board.Board, escape.util.LocationInitializer[])
	 */
	@Override
	public void initializeBoard(Map<PieceName, PieceDescriptor> pieceTypes, LocationInitializer... initializers)
	{  
		setBoardType();
		
		if (initializers == null)
		{
			return;
		}
		
		for (LocationInitializer li : initializers) {
			SquareCoordinate c = SquareCoordinate.makeCoordinate(li.x, li.y);
 
			if (li.pieceName != null) {
				if (!pieceTypes.containsKey(li.pieceName))
				{
					throw new EscapeException("Invalid Piece Type");
					
				}
				
				PieceDescriptor descriptor = pieceTypes.get(li.pieceName);
				
				EscapePiece piece = new EscapePiece(li.player, li.pieceName, descriptor);
				b.putPieceAt(piece, c);
			}
			
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
}
