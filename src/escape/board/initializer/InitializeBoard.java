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

import java.util.Map;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.*;
import escape.util.LocationInitializer;

/**
 * Description
 * @version Apr 14, 2020
 */
public abstract class InitializeBoard
{
	protected GenericBoard b;
	
	public InitializeBoard(GenericBoard b) 
	{
		this.b = b;
	}
	
	public abstract void initializeBoard(Map<PieceName, PieceDescriptor> pieceTypes, LocationInitializer...initializers);
	
	public void setBoardType()
	{
		BoardType boardType = b.getBoardType();
		
		if (boardType.equals(BoardType.HEX)) 
		{
			b.setCoordinateID(CoordinateID.HEX);
		}
		
		else if (boardType.equals(BoardType.SQUARE))
		{
			b.setCoordinateID(CoordinateID.SQUARE);
		}
		
		else 
		{
			b.setCoordinateID(CoordinateID.ORTHOSQUARE);
		}
	}
}
