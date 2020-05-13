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

package escape.escape;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.*;
import escape.board.GenericBoard;
import escape.board.coordinate.*;
import escape.piece.*;

/**
 * Test cases to test the hex board
 * @version May 1, 2020
 */
class HexTests
{
	@Test
	void testingWithNoInitializers() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/NoLocationInitializers_hex.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNotNull(emg);
	}
	@Test
	void testingMakeCoordinate() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(150, 150);
		Coordinate c2 = HexCoordinate.makeCoordinate(150, 150); 
		
		assertNotNull(c1);
		
		assertTrue(c1.equals(c2));
		
	}
	
	@Test
	void testingGetPieces() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);

		Coordinate testCoordinate = emg.makeCoordinate(15, 15);

		assertTrue(test.equals(emg.getPieceAt(testCoordinate)));
		
		GenericBoard b = ((EscapeGameController) emg).getBoard();
		
		b.putPieceAt(null, testCoordinate);
		
		assertNull(emg.getPieceAt(testCoordinate));
	}
	
	@Test
	void testingGettingNull() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate NoPieces = emg.makeCoordinate(190, 190);
		Coordinate invalidCoordinateType = SquareCoordinate.makeCoordinate(15, 15);
		
		assertNull(emg.getPieceAt(NoPieces));
		assertNull(emg.getPieceAt(invalidCoordinateType));
	}
	
	@ParameterizedTest
	@MethodSource("moveProvider")
	void testingMove(String fileName, int srcX, int srcY, int destX, int destY) throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File(fileName));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate start = emg.makeCoordinate(srcX, srcY);
		Coordinate end = emg.makeCoordinate(destX, destY);
		EscapePiece movedPiece = emg.getPieceAt(start);
		
		assertTrue(emg.move(start, end));
		assertTrue(emg.getPieceAt(end).equals(movedPiece));
	}
	
	static Stream<Arguments> moveProvider()
	{
		return Stream.of(
				// making basic move
				Arguments.of(
						"config/HexBoardWithPieces.xml", 15, 15, 16, 16),
				// making basic linear move
				Arguments.of(
						"config/HexBoard_Finite.xml", 4, 3, 7, 3),
				// making linear capture move
				Arguments.of(
						"config/HexBoard_Finite.xml", 6, 7, 6, 8),
				// verifying FLY 
				Arguments.of(
						"config/HexBoard_Finite.xml", 6, 6, 4, 6),
				// making a jump capture move
				Arguments.of(
						"config/HexBoardWithPieces.xml", 6, 5, 6, 7),
				// verifying shortest path can be made
				Arguments.of(
						"config/HexBoardWithPieces.xml", 6, 5, 3, 5),
				// testing with complex jumping to get shortest path
				Arguments.of(
						"config/HexBoardWithPieces.xml", 6, 5, 4, 7),
				Arguments.of(
						"config/HexBoardWithPieces.xml", 6, 5, 4, 7),
				Arguments.of(
						"config/HexBoardWithPieces.xml", -20, -20, -25, -22)
				);
	}
	
	@ParameterizedTest
	@MethodSource("falseMovesProvider")
	void testingFalseMoves(String fileName, int srcX, int srcY, int destX, int destY) throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File(fileName));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate start = emg.makeCoordinate(srcX, srcY);
		Coordinate end = emg.makeCoordinate(destX, destY);
		
		assertFalse(emg.move(start, end));
	}
	
	static Stream<Arguments> falseMovesProvider()
	{
		return Stream.of(
				// can't make with linear move
				Arguments.of(
					"config/HexBoard_Finite.xml", 4, 3, 3, 5),
				// handling distance of 0
				Arguments.of(
						"config/HexBoardWithPieces.xml", 15, 7, 15, 8)
				);
	}
	
	@Test
	void movingToExit() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoard_Finite.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate start = emg.makeCoordinate(4, 3);
		Coordinate end = emg.makeCoordinate(4, 5);
		
		assertTrue(emg.move(start, end));
		assertNull(emg.getPieceAt(end));
	}
	
	
}
