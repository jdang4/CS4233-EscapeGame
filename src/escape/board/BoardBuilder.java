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

import escape.board.coordinate.CoordinateID;
import escape.board.initializer.*;
import escape.exception.EscapeException;
import escape.util.*;

/**
 * Description
 * @version Apr 27, 2020
 */
public class BoardBuilder
{
	private final EscapeGameInitializer gameInitializer;
	
	public BoardBuilder(EscapeGameInitializer init)
	{
		this.gameInitializer = init;
	}
	
	public GenericBoard makeBoard()
	{
		GenericBoard board = null;
		
		return initializeBoard(board, gameInitializer.getLocationInitializers());
	}
	
	/** 
	 * This method is called when trying to initialize the board. It determines the kind of 
	 * board to create by looking at the provided coordinate id to intialize the proper board
	 * 
	 * @param board 
	 * 			the one to be initialized
	 * @param initializers
	 * 			the information that is used to initialized the board
	 * @return the initialized board
	 */ 
	private GenericBoard initializeBoard(GenericBoard board, LocationInitializer... initializers)
	{
		InitializeBoard initBoard = null;
		
		// initializing a hex board
		if (gameInitializer.getCoordinateType().equals(CoordinateID.HEX)) 
		{
			board = new HexBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
			initBoard = new HexBoardInitializer();
		}
		 
		// initializing a square board
		else
		{
			// verify that the setup of the potential square board is finite
			if (gameInitializer.getxMax() == 0 || gameInitializer.getyMax() == 0)
			{
				throw new EscapeException("Cannot make an infinte square board");
			}
			
			// initializing a square board with orthosquare coordinates
			if (gameInitializer.getCoordinateType().equals(CoordinateID.ORTHOSQUARE))
			{
				board = new OrthoSquareBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
				initBoard = new OrthoSquareBoardInitializer();
			} 
			
			// initializing a square board with square coordinates
			else
			{
				board = new SquareBoard(gameInitializer.getxMax(), gameInitializer.getyMax());
				initBoard = new SquareBoardInitializer();
			}
		} 
		
		// initializes the board with the proper configurations
		initBoard.initializeBoard(board, gameInitializer.getLocationInitializers());
		
		// returns the initialized board
		return board;	
	}
}
