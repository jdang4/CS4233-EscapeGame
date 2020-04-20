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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.File;
import java.util.*;
import java.util.stream.Stream;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;
/**
 * Description
 * @version Apr 2, 2020
 */
class BoardTest
{	
	
	@Test
	void buildSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-square.xml"));
		Board b = bb.makeBoard();
		assertNotNull(b);
		
		assertTrue(((SquareBoard)b).getBoardType().equals(BoardType.SQUARE));
	}
	
	@Test
	void buildOrthoSquareBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-ortho.xml"));
		Board b = bb.makeBoard();
		assertNotNull(b);
		
		assertTrue(((OrthoSquareBoard)b).getBoardType().equals(BoardType.ORTHOSQUARE));
	}
	
	@Test
	void buildHexBoard() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-hex.xml"));
		Board b = bb.makeBoard();
		assertNotNull(b);
		
		assertTrue(((HexBoard)b).getBoardType().equals(BoardType.HEX));
	}
	
	@Test
	void buildingNoCoordinateIDBoard() throws Exception
	{	
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-no-coordinateID.xml"));
		Assertions.assertThrows(EscapeException.class, () -> {
			bb.makeBoard();
			}
		);
	}
	
	@Test
	void buildingInvalidCoordinateIDBoard() throws Exception
	{	
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-infinite-square.xml"));
		Assertions.assertThrows(EscapeException.class, () -> {
			bb.makeBoard();
			}
		);
	}

	@ParameterizedTest
	@MethodSource("nullCoordinatesProvider")
	void gettingNull(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();

		assertNull(b.getPieceAt(coord));
	}

	static Stream<Arguments> nullCoordinatesProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(8, 8)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(8, 8)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(-23, -29))
				);

	}
	
	@ParameterizedTest
	@MethodSource("wrongCoordinateTypeProvider")
	void attemptingToGetPieceAtInvalidCoordinateType(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();

		Assertions.assertThrows(EscapeException.class, () -> {
			b.getPieceAt(coord);
			}
		);
	}

	static Stream<Arguments> wrongCoordinateTypeProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square.xml", OrthoSquareCoordinate.makeCoordinate(2, 2)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", HexCoordinate.makeCoordinate(2, 2)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", SquareCoordinate.makeCoordinate(-2, -2))
				);

	}
	
	@ParameterizedTest
    @MethodSource("invalidCoordinatesProvider")
	void addingLocationTypeToInvalidCoordinate(String fileName) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			bb.makeBoard();
			}
		);
	}
	
	static Stream<Arguments> invalidCoordinatesProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square-invalid-LocationTypeCoordinate.xml"),
				Arguments.of(
						"config/board/BoardConfig-ortho-invalid-LocationTypeCoordinate.xml"),
				Arguments.of(
						"config/board/BoardConfig-hex-invalid-LocationTypeCoordinate.xml")
				);

	}
	
	@ParameterizedTest
    @MethodSource("coordinatesProvider")
	void addingPieceToBoard(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();
		
		// placing piece to proper location
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		b.putPieceAt(newPiece, coord);

		// checking put piece
		assertNotNull(b.getPieceAt(coord));
		assertEquals(newPiece, b.getPieceAt(coord));
	}
	
	static Stream<Arguments> coordinatesProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(1, 1)),
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(2, 2)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(2, 2)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(0, 0)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(-500, 300)),
				Arguments.of(
						"config/board/BoardConfig-hex-X-infinite.xml", HexCoordinate.makeCoordinate(-400, 5)),
				Arguments.of(
						"config/board/BoardConfig-hex-Y-infinite.xml", HexCoordinate.makeCoordinate(0, 100))
				);
	}
	 
	@ParameterizedTest
    @MethodSource("errorCoordinatesProvider")
	void attemptingToAddPieceToBoard(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();
		
		// placing piece to proper location
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		
		Assertions.assertThrows(EscapeException.class, () -> {
			b.putPieceAt(newPiece, coord);
			}
		);
	}
	
	static Stream<Arguments> errorCoordinatesProvider()
	{
		return Stream.of(
				// square board
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(0, 0)),
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(3, 5)),
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(9, 15)),
				Arguments.of(
						"config/board/BoardConfig-square.xml", OrthoSquareCoordinate.makeCoordinate(3, 3)),
				Arguments.of(
						"config/board/BoardConfig-square.xml", HexCoordinate.makeCoordinate(3, 3)),
				
				// orthosquare board
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(0, 2)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(3, 5)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", SquareCoordinate.makeCoordinate(3, 3)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", HexCoordinate.makeCoordinate(3, 3)),
				
				// hex board
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(3, 5)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", SquareCoordinate.makeCoordinate(8, 8)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", OrthoSquareCoordinate.makeCoordinate(8, 8)),
				Arguments.of(
						"config/board/BoardConfig-hex-X-infinite.xml", HexCoordinate.makeCoordinate(-400, 9)),
				Arguments.of(
						"config/board/BoardConfig-hex-Y-infinite.xml", HexCoordinate.makeCoordinate(-1, 25))
				);
				 
	}
	
	@ParameterizedTest
    @MethodSource("addingNullCoordinatesProvider")
	void addingNullToExistingPiece(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		
		b.putPieceAt(newPiece, coord);
		
		assertNotNull(b.getPieceAt(coord));
		
		b.putPieceAt(null, coord);
		
		assertNull(b.getPieceAt(coord));
		
	}
	
	static Stream<Arguments> addingNullCoordinatesProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(6, 5)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(1, 5)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(-2, -9))
				);
				 
	}
	
	@ParameterizedTest
    @MethodSource("exitCoordinatesProvider")
	void addingPieceToExit(String fileName, Coordinate coord) throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File(fileName));
		Board b = bb.makeBoard();
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		
		b.putPieceAt(newPiece, coord);
		
		assertNull(b.getPieceAt(coord));
		
	}
	
	static Stream<Arguments> exitCoordinatesProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/board/BoardConfig-square.xml", SquareCoordinate.makeCoordinate(5, 8)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(5, 8)),
				Arguments.of(
						"config/board/BoardConfig-hex.xml", HexCoordinate.makeCoordinate(-5, -5))
				);
				 
	}
	
	
}
