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
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		Board b = bb.makeBoard();
		assertNotNull(b);
		
		assertEquals(BoardType.SQUARE, ((SquareBoard) b).getBoardType());
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
						"config/board/BoardConfig1.xml", SquareCoordinate.makeCoordinate(1, 1)),
				Arguments.of(
						"config/board/BoardConfig1.xml", SquareCoordinate.makeCoordinate(2, 2)),
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
				Arguments.of(
						"config/board/BoardConfig1.xml", SquareCoordinate.makeCoordinate(0, 0)),
				Arguments.of(
						"config/board/BoardConfig1.xml", SquareCoordinate.makeCoordinate(3, 5)),
				Arguments.of(
						"config/board/BoardConfig1.xml", SquareCoordinate.makeCoordinate(9, 15)),
				Arguments.of(
						"config/board/BoardConfig1.xml", OrthoSquareCoordinate.makeCoordinate(3, 3)),
				Arguments.of(
						"config/board/BoardConfig1.xml", HexCoordinate.makeCoordinate(3, 3)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(0, 2)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", OrthoSquareCoordinate.makeCoordinate(3, 5)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", SquareCoordinate.makeCoordinate(3, 3)),
				Arguments.of(
						"config/board/BoardConfig-ortho.xml", HexCoordinate.makeCoordinate(3, 3)),
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
	
	@Test
	void buildBoard2() throws Exception
	{
		
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig2.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			bb.makeBoard();
			}
		);
	}
	
	/*
	@Test
	void buildBoard3() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		Board test = bb.makeBoard();
		assertNotNull(test);
		
		assertEquals(LocationType.BLOCK, ((SquareBoard)test).getLocationType(SquareCoordinate.makeCoordinate(3, 5)));
		// Now I will do some tests on this board and its contents.
	}
	*/
	
	/*
	@Test
	void buildBoardHex1() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-hex.xml"));
		Board test = bb.makeBoard();
		assertNotNull(test);
		
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		test.putPieceAt(newPiece, HexCoordinate.makeCoordinate(-100, -500));
		
		assertNotNull(test.getPieceAt(HexCoordinate.makeCoordinate(-100, -500)));
		// Now I will do some tests on thotheris board and its contents.
		 
	}
	
	@Test
	void buildBoardOrtho1() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-ortho.xml"));
		Board test = bb.makeBoard();
		assertNotNull(test);
		
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		test.putPieceAt(newPiece, OrthoSquareCoordinate.makeCoordinate(5, 5));
		
		assertNotNull(test.getPieceAt(OrthoSquareCoordinate.makeCoordinate(5, 5))); 
	}
	
	@Test
	void buildBoardOrtho2() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig-ortho.xml"));
		Board test = bb.makeBoard();
		assertNotNull(test);
		
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		
		
		Assertions.assertThrows(EscapeException.class, () -> {
			test.putPieceAt(newPiece, SquareCoordinate.makeCoordinate(5, 5));
			}
		);
		 
	}
	*/
}
