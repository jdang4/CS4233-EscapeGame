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

import static escape.board.LocationType.CLEAR;
import java.io.*;
import javax.xml.bind.*;
import escape.board.coordinate.*;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * A Builder class for creating Boards. It is only an example and builds
 * just Square boards. If you choose to use this
 * @version Apr 2, 2020
 */
public class BoardBuilder
{
	private BoardInitializer bi;
	/**
	 * The constructor for this takes a file name. It is either an absolute path
	 * or a path relative to the beginning of this project.
	 * @param fileName
	 * @throws Exception 
	 */
	public BoardBuilder(File fileName) throws Exception
	{
		JAXBContext contextObj = JAXBContext.newInstance(BoardInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        bi = (BoardInitializer)mub.unmarshal(new FileReader(fileName));
	}
	
	public Board makeBoard()
	{
		if (bi.getCoordinateId().equals(CoordinateID.SQUARE))
		{
			SquareBoard board = new SquareBoard(bi.getxMax(), bi.getyMax());
	        initializeBoard(board, bi.getLocationInitializers());
	        return board;
		}
		
		else if (bi.getCoordinateId().equals(CoordinateID.HEX))
		{
			
			HexBoard board = new HexBoard(bi.getxMax(), bi.getyMax());
			
		}
		// Change next when we have Hex boards too.
		HexBoard board = new HexBoard(bi.getxMax(), bi.getyMax());
		return board;
	}
	
	/*
	private void test(Board b, BoardType type, LocationInitializer...initializers)
	{
		for (LocationInitializer li : initializers) {
			Coordinate c = null;
			
			if (type.equals(BoardType.SQUARE))
			{
				c = SquareCoordinate.makeCoordinate(li.x, li.y);
				b = ((SquareBoard) b);
			}
			
			else
			{
				c = HexCoordinate.makeCoordinate(li.x, li.y);
				b = ((HexBoard) b);
			}
			
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
	*/
	private void initializeBoard(SquareBoard b, LocationInitializer... initializers)
	{
		for (LocationInitializer li : initializers) {
			SquareCoordinate c = SquareCoordinate.makeCoordinate(li.x, li.y);
			
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
