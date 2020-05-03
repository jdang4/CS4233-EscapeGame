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
 * My tests for testing an OrthoSquare Board
 * @version May 1, 2020
 */
class OrthoTests
{
	@Test
	void testingWithNoInitializers() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/NoLocationInitializers_ortho.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNotNull(emg);
	}
	
	@Test
	void testingMakeCoordinate() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(15, 15);
		Coordinate c2 = OrthoSquareCoordinate.makeCoordinate(15, 15); 
		
		assertNotNull(c1);
		
		assertTrue(c1.equals(c2));
		
		assertNull(emg.makeCoordinate(100, 100));
	}
	
	@Test
	void testingGetPieces() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
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
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate NoPieces = emg.makeCoordinate(19, 19);
		Coordinate invalidCoordinateType = HexCoordinate.makeCoordinate(15, 15);
		Coordinate OutOfBounds = OrthoSquareCoordinate.makeCoordinate(100, 100);
		
		assertNull(emg.getPieceAt(NoPieces));
		assertNull(emg.getPieceAt(invalidCoordinateType));
		assertNull(emg.getPieceAt(OutOfBounds));
	}
	
	@ParameterizedTest
	@MethodSource("validMovesProvider")
	void testingValidMoves(String fileName, int srcX, int srcY, int destX, int destY) throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File(fileName));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate start = emg.makeCoordinate(srcX, srcY);
		Coordinate end = emg.makeCoordinate(destX, destY);
		
		assertTrue(emg.move(start, end));
	}
	
	static Stream<Arguments> validMovesProvider()
	{
		return Stream.of(
				// just barely making an orthogonel move
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 6, 6, 4, 6),
				// testing flying over pieces and blocks
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 6, 6, 6, 3)
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
				// going to exit
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 6, 5, 4, 5),
				// went through exit
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 4, 3, 4, 6),
				// moving to same spot
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 4, 3, 4, 3),
				// unable to make move with only linear
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 4, 3, 3, 2),
				// no piece at start
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 3, 3, 3, 2),
				// ending location is of the same player
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 6, 7, 6, 6),
				// can't make move due to only having orthogonal despite being OMNI
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 15, 15, 12, 12),
				// moving to block
				Arguments.of(
						"config/OrthoSquareBoardWithPieces.xml", 6, 7, 5, 7)
				);
	}
}
