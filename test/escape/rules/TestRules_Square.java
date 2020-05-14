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

package escape.rules;

import static escape.piece.PieceName.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import escape.*;
import escape.board.coordinate.Coordinate;
import escape.piece.EscapePiece;

/**
 * Description
 * @version May 11, 2020
 */
class TestRules_Square
{
	EscapeGameBuilder egb_pointConflict;
	EscapeGameBuilder egb_noRules;
	EscapeGameBuilder egb_remove;
	EscapeGameBuilder egb_turns;
	EscapeGameBuilder egb_game;
	TestObserver obs;
	
	@BeforeEach
	public void setup() throws Exception 
	{
		egb_pointConflict = new EscapeGameBuilder(new File("config/game/PointConflict_Square.xml"));
		egb_noRules = new EscapeGameBuilder(new File("config/game/NoRules_Square.xml"));
		egb_remove = new EscapeGameBuilder(new File("config/game/Remove_Square.xml"));
		egb_turns = new EscapeGameBuilder(new File("config/game/TurnLimit.xml"));
		egb_game = new EscapeGameBuilder(new File("config/game/Game_Square.xml"));
		obs = new TestObserver();
	}
	

	@Test
	public void testingPlayer1MovesFirst()
	{
		EscapeGameManager emg = egb_pointConflict.makeGameManager();
		
		Coordinate player2c1 = emg.makeCoordinate(15, 6);
        Coordinate player2c2 = emg.makeCoordinate(14, 6);
        
        emg.addObserver(obs);
        
        assertEquals(obs, emg.removeObserver(obs));
        
        emg.addObserver(obs);
        
        assertFalse(emg.move(player2c1, player2c2));
        assertEquals("PLAYER1 turn", obs.getMessage());
        assertNotNull(emg.getPieceAt(player2c1));
        
        
        Coordinate player1c1 = emg.makeCoordinate(15, 4);
        Coordinate player1c2 = emg.makeCoordinate(14, 4);
        
        assertTrue(emg.move(player1c1, player1c2));
        assertNull(emg.getPieceAt(player1c1));
        
        assertTrue(emg.move(player2c1, player2c2));
	}
	
	@Test
	public void cannotBattle()
	{
		EscapeGameManager emg = egb_noRules.makeGameManager();
		emg.addObserver(obs);
		
		Coordinate c1 = emg.makeCoordinate(17, 4);
        Coordinate c2 = emg.makeCoordinate(16, 4);
        
        assertFalse(emg.move(c1, c2));
        assertEquals("piece cannot battle", obs.getMessage());
	}
	
	@Test
	public void testingRemove()
	{
		EscapeGameManager emg = egb_remove.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(17, 4);
        Coordinate c2 = emg.makeCoordinate(17, 6);
        
        EscapePiece movingPiece = emg.getPieceAt(c1);
        assertTrue(emg.move(c1, c2));
        assertEquals(movingPiece, emg.getPieceAt(c2));
        assertEquals(3, movingPiece.getDescriptor().getValue());
        
	}
	
	@Test
	public void testingBattle_equalValues()
	{
		EscapeGameManager emg = egb_pointConflict.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(17, 4);
        Coordinate c2 = emg.makeCoordinate(16, 4);
        
        assertTrue(emg.move(c1, c2));
        assertNull(emg.getPieceAt(c2));
	}
	
	@Test
	public void testingBattle_pointConflictLost()
	{
		EscapeGameManager emg = egb_pointConflict.makeGameManager();
		
		Coordinate c1 = emg.makeCoordinate(17, 4);
        Coordinate c2 = emg.makeCoordinate(17, 6);
        
        assertEquals(10, emg.getPieceAt(c2).getDescriptor().getValue());
        assertTrue(emg.move(c1, c2));
        assertNotNull(emg.getPieceAt(c2));
        assertEquals(7, emg.getPieceAt(c2).getValue());
	}
	
	@Test
	public void testingBattle_pointConflictWon()
	{
		EscapeGameManager emg = egb_pointConflict.makeGameManager();

		Coordinate c1 = emg.makeCoordinate(15, 4);
		Coordinate c2 = emg.makeCoordinate(15, 6);

		assertEquals(2, emg.getPieceAt(c1).getDescriptor().getValue());
		assertEquals(1, emg.getPieceAt(c2).getDescriptor().getValue());
		assertTrue(emg.move(c1, c2));
		assertNotNull(emg.getPieceAt(c2));
		
		assertEquals(1, emg.getPieceAt(c2).getValue());
	}
	
	@Test
	public void testingTiedGame()
	{
		EscapeGameManager emg = egb_turns.makeGameManager();
		
		Coordinate player2c1 = emg.makeCoordinate(15, 6);
        Coordinate player2c2 = emg.makeCoordinate(14, 6);
        
        emg.addObserver(obs);
        
        
        assertFalse(emg.move(player2c1, player2c2));
        assertEquals("PLAYER1 turn", obs.getMessage());
        assertNotNull(emg.getPieceAt(player2c1));
        
        
        Coordinate player1c1 = emg.makeCoordinate(15, 4);
        Coordinate player1c2 = emg.makeCoordinate(14, 4);
        
        assertTrue(emg.move(player1c1, player1c2));
        assertNull(emg.getPieceAt(player1c1));
        
        assertTrue(emg.move(player2c1, player2c2));
        
        // end of turn 1
        
        Coordinate player1c3 = emg.makeCoordinate(13, 4);
        
        assertFalse(emg.move(player1c2, player1c3));
        
        assertEquals("Game is over and there were no winners", obs.getMessage());
        assertNull(obs.getCause());
	}
	
	@Test 
	public void testingForWinner()
	{
		EscapeGameManager emg = egb_turns.makeGameManager();
		
		Coordinate player2c1 = emg.makeCoordinate(15, 6);
        Coordinate player2c2 = emg.makeCoordinate(14, 6);
        
        emg.addObserver(obs);
        
        Coordinate player1c1 = emg.makeCoordinate(5, 6);
        Coordinate player1c2 = emg.makeCoordinate(6, 6);
        
        assertTrue(emg.move(player1c1, player1c2));
        assertNull(emg.getPieceAt(player1c1));
        
        assertTrue(emg.move(player2c1, player2c2));
        
        // end of turn 1
        
        Coordinate start = emg.makeCoordinate(15, 4);
        Coordinate player1c3 = emg.makeCoordinate(16, 4);
        assertFalse(emg.move(start, player1c3));
        assertEquals("Game is over and PLAYER1 has won", obs.getMessage());
        assertNull(obs.getCause());
	}
	
	@Test
	public void testingScore()
	{
		EscapeGameManager emg = egb_game.makeGameManager();
		emg.addObserver(obs);
		
		////////////////////// turn 1 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(6, 4), emg.makeCoordinate(12, 4)));
		assertNull(emg.getPieceAt(emg.makeCoordinate(6, 4)));
		assertEquals(4, emg.getPieceAt(emg.makeCoordinate(12, 4)).getValue());
		
		assertFalse(emg.move(emg.makeCoordinate(8, 17), emg.makeCoordinate(8, 19)));
		assertEquals("land on block", obs.getMessage());
		
		assertTrue(emg.move(emg.makeCoordinate(12, 20), emg.makeCoordinate(8, 20)));
		assertNull(emg.getPieceAt(emg.makeCoordinate(8, 20)));
		////////////////////// end turn 1 ///////////////////////////////////////
		
		
		////////////////////// turn 2 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(9, 4), emg.makeCoordinate(12, 4)));
		assertEquals(1, emg.getPieceAt(emg.makeCoordinate(12, 4)).getValue());
		
		assertTrue(emg.move(emg.makeCoordinate(12, 12), emg.makeCoordinate(14, 15)));
		////////////////////// end turn 2 ///////////////////////////////////////
		
		
		////////////////////// turn 3 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(12, 4), emg.makeCoordinate(16, 6)));
		
		assertTrue(emg.move(emg.makeCoordinate(6, 11), emg.makeCoordinate(4, 11)));
		assertEquals("PLAYER2 wins", obs.getMessage());
		////////////////////// end turn 3 ///////////////////////////////////////
		
		
		////////////////////// turn 3 //////////////////////////////////////////
		assertFalse(emg.move(emg.makeCoordinate(3, 13), emg.makeCoordinate(3, 14)));
		assertEquals("Game is over and PLAYER2 has won", obs.getMessage());
		////////////////////// end turn 3 ///////////////////////////////////////
		
	}
	
	@Test
	public void endingByTurnLimit()
	{
		EscapeGameManager emg = egb_game.makeGameManager();
		emg.addObserver(obs);
		
		////////////////////// turn 1 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(6, 4), emg.makeCoordinate(12, 4)));
		assertNull(emg.getPieceAt(emg.makeCoordinate(6, 4)));
		assertEquals(4, emg.getPieceAt(emg.makeCoordinate(12, 4)).getValue());
		
		assertFalse(emg.move(emg.makeCoordinate(8, 17), emg.makeCoordinate(8, 19)));
		assertEquals("land on block", obs.getMessage());
		
		assertTrue(emg.move(emg.makeCoordinate(12, 20), emg.makeCoordinate(8, 20)));
		assertNull(emg.getPieceAt(emg.makeCoordinate(8, 20)));
		////////////////////// end turn 1 ///////////////////////////////////////
		
		
		////////////////////// turn 2 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(9, 4), emg.makeCoordinate(12, 4)));
		assertEquals(1, emg.getPieceAt(emg.makeCoordinate(12, 4)).getValue());
		
		assertTrue(emg.move(emg.makeCoordinate(12, 12), emg.makeCoordinate(14, 15)));
		////////////////////// end turn 2 ///////////////////////////////////////
		
		
		////////////////////// turn 3 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(12, 4), emg.makeCoordinate(16, 6)));
		
		assertTrue(emg.move(emg.makeCoordinate(6, 11), emg.makeCoordinate(7, 11)));
		////////////////////// end turn 3 ///////////////////////////////////////
		
		
		////////////////////// turn 4 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(3, 13), emg.makeCoordinate(3, 14)));
		
		assertTrue(emg.move(emg.makeCoordinate(7, 11), emg.makeCoordinate(8, 11)));
		////////////////////// end turn 4 ///////////////////////////////////////
		
		
		////////////////////// turn 5 //////////////////////////////////////////
		assertTrue(emg.move(emg.makeCoordinate(17, 18), emg.makeCoordinate(14, 15)));
		
		assertTrue(emg.move(emg.makeCoordinate(8, 11), emg.makeCoordinate(9, 11)));
		////////////////////// end turn 5 ///////////////////////////////////////
		
		
		////////////////////// turn 6 //////////////////////////////////////////
		assertFalse(emg.move(emg.makeCoordinate(3, 15), emg.makeCoordinate(3, 16)));
		assertEquals("Game is over and PLAYER1 has won", obs.getMessage());
		////////////////////// end turn 6 ///////////////////////////////////////
		
	}

}
