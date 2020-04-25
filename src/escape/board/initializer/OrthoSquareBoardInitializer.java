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
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.util.LocationInitializer;

/**
 * This class provides the implementations to initialize a square board 
 * with OrthoSquare Coordinates
 * @version Apr 14, 2020
 */
public class OrthoSquareBoardInitializer implements InitializeBoard
{ 
	/*
	 * @see escape.board.initializer.InitializeBoard#initializeBoard(escape.board.Board, escape.util.LocationInitializer[])
	 */
	@Override
	public void initializeBoard(Board board, LocationInitializer... initializers)
	{
		OrthoSquareBoard b = (OrthoSquareBoard) board;
		b.setCoordinateID(CoordinateID.ORTHOSQUARE);
		
		if (initializers == null)
		{
			return;
		}
		
		for (LocationInitializer li : initializers) {
			OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(li.x, li.y);
			
			// i believe this means if it is CLEAR 
			if (li.pieceName != null) {
				b.putPieceAt(new EscapePiece(li.player, li.pieceName), c);
			}
			
			// this is for setting a location type on the board (either EXIT or BLOCK)
			if (li.locationType != null && li.locationType != CLEAR) {
				b.setLocationType(c, li.locationType);
			}
		}
	}
}
