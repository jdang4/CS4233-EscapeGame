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
import escape.exception.EscapeException;
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
		Board board = null;
		InitializeBoard initBoard = null;
		
		if (bi.getCoordinateId().equals(CoordinateID.HEX))
		{
			board = new HexBoard(bi.getxMax(), bi.getyMax());
			initBoard = new HexBoardInitializer();
		}
		
		else
		{
			board = new SquareBoard(bi.getxMax(), bi.getyMax());
			
			if (bi.getCoordinateId().equals(CoordinateID.ORTHOSQUARE))
			{
				initBoard = new OrthoSquareBoardInitializer();
			} 
			
			else if (bi.getCoordinateId().equals(CoordinateID.SQUARE))
			{
				initBoard = new SquareBoardInitializer();
			}
			
			else
			{
				throw new EscapeException("No CoordinateID detected");
			}
			
		}
		
		
		initBoard.initializeBoard(board, bi.getLocationInitializers());
		
		return board;
		
		
	}
	
	/*
	private void initializeBoard(Board b, InitializeBoard init, LocationInitializer... initializers)
	{
		
		init.initializeBoard(b, initializers);
		
	}
	*/
	
}
