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
import org.junit.Rule;
import org.junit.jupiter.api.*;
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
	void buildBoard1() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		Board b = bb.makeBoard();
		assertNotNull(b);
		// Now I will do some tests on this board and its contents.
		
		// placing piece to proper location
		EscapePiece newPiece = new EscapePiece(Player.PLAYER1, PieceName.FOX);
		SquareCoordinate pieceCoordinate = SquareCoordinate.makeCoordinate(1, 1);
		b.putPieceAt(newPiece, pieceCoordinate);
		
		assertEquals(newPiece, b.getPieceAt(pieceCoordinate));
		
		assertEquals(pieceCoordinate.distanceTo(SquareCoordinate.makeCoordinate(2, 2)), 1);
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
	
	@Test
	void buildBoard3() throws Exception
	{
		BoardBuilder bb = new BoardBuilder(new File("config/board/BoardConfig1.xml"));
		Board test = bb.makeBoard();
		assertNotNull(test);
		
		assertEquals(LocationType.BLOCK, ((SquareBoard)test).getLocationType(SquareCoordinate.makeCoordinate(3, 5)));
		// Now I will do some tests on this board and its contents.
	}
	
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
	
}
