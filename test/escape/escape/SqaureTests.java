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
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.*;
import escape.board.GenericBoard;
import escape.board.coordinate.*;
import escape.piece.*;

/**
 * Description
 * @version Apr 27, 2020
 */
class SqaureTests
{
	
	@Test
	void testingWithNoInitializers() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/NoLocationInitializers_square.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		assertNotNull(emg);
	}
	
	@Test
	void testingMakeCoordinate() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SquareBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(15, 15);
		Coordinate c2 = SquareCoordinate.makeCoordinate(15, 15); 
		
		assertNotNull(c1);
		
		assertTrue(c1.equals(c2));
		
		assertNull(emg.makeCoordinate(100, 100));
	}
	
	@Test
	void testingGetPieces() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SquareBoardWithPieces.xml"));
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
		= new EscapeGameBuilder(new File("config/SquareBoardWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();
		
		Coordinate NoPieces = emg.makeCoordinate(19, 19);
		Coordinate invalidCoordinateType = HexCoordinate.makeCoordinate(15, 15);
		Coordinate OutOfBounds = SquareCoordinate.makeCoordinate(100, 100);
		
		assertNull(emg.getPieceAt(NoPieces));
		assertNull(emg.getPieceAt(invalidCoordinateType));
		assertNull(emg.getPieceAt(OutOfBounds));
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
						"config/SquareBoardWithPieces.xml", 15, 15, 16, 16),
				// handling if EXIT in route but can move around it
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 15, 15, 16, 19),
				// making basic move
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 15, 15, 12, 15),
				// verifying if piece can make the best choice jump
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 6, 5, 5, 8),
				// verifying that algorithm gets the best shortest path
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 6, 5, 4, 6),
				// testing a fly diagonal move
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 4, 3, 6, 5),
				// test move algorithm against Block and Exit
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 7, 11, 9, 13),
				// testing fly diagonal move over block and piece
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 4, 3, 7, 6),
				// testing with jump and unblock set to true
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 15, 7, 15, 4)
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
				// can't make short of distance because EXIT is in the way of possible path
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 15, 7, 15, 10),
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 4, 3, 5, 4),
				Arguments.of(
						"config/SquareBoardWithPieces.xml", 4, 3, 1, 0)
				);
	}
	

}
