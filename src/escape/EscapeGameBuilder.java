/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape;

import java.io.*;
import javax.xml.bind.*;
import escape.board.*;
import escape.board.coordinate.CoordinateID;
import escape.board.initializer.*;
import escape.exception.EscapeException;
import escape.piece.EscapePiece;
import escape.util.*;

/**
 * This class is what a client will use to creat an instance of a game, given
 * an Escape game configuration file. The configuration file contains the 
 * information needed to create an instance of the Escape game.
 * @version Apr 22, 2020
 */
public class EscapeGameBuilder
{
    private EscapeGameInitializer gameInitializer;
    
    /**
     * The constructor takes a file that points to the Escape game
     * configuration file. It should get the necessary information 
     * to be ready to create the game manager specified by the configuration
     * file and other configuration files that it links to.
     * @param fileName the file for the Escape game configuration file.
     * @throws Exception 
     */
    public EscapeGameBuilder(File fileName) throws Exception
    {
        JAXBContext contextObj = JAXBContext.newInstance(EscapeGameInitializer.class);
        Unmarshaller mub = contextObj.createUnmarshaller();
        gameInitializer = 
            (EscapeGameInitializer)mub.unmarshal(new FileReader(fileName));
    }
    
    /**
     * Once the builder is constructed, this method creates the
     * EscapeGameManager instance.
     * @return
     */
    public EscapeGameManager makeGameManager()
    {
    	/*
    	 * TODO
    	 * (2) I need to be able to add information to the pieces (under PiceType field in XML) [Look at PieceTypeInitializer]
    	 * 	- Need to add some functions to store information about the piece 
    	 * 		- probably use some prviate variables and some getter functions
    	 */
        // To be implemented
    	
    	if (gameInitializer.getCoordinateType() == null || gameInitializer.getPieceTypes() == null)
    	{
    		throw new EscapeException("Detected Invalid Config file");
    	}
    	
    	
    	
    	CoordinateID gameCoordinateID = gameInitializer.getCoordinateType();
    	
    	GenericBoard gameBoard = makeBoard();
    	
    	/*
    	 * TODO
    	 * - need to do something about the piece types
    	 */
    	
    	EscapeGameController controller = new EscapeGameController(gameBoard, gameCoordinateID);
    	
    	
        return controller;
    }
    
    /**
	 * This method is responsible for the creation of the board and initializing it
	 * based on the given setup file
	 * 
	 * @return a board that is initialized
	 */
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
