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

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import org.junit.jupiter.api.*;
import escape.*;

/**
 * Used to the test the rules on a hex board
 * @version May 13, 2020
 */
class TestRules_Hex
{

	EscapeGameBuilder egb_game;
	TestObserver obs;
	
	@BeforeEach
	public void setup() throws Exception 
	{
		egb_game = new EscapeGameBuilder(new File("config/game/Game_Hex.xml"));
		obs = new TestObserver();
	}
	
	@Test
	public void testForError()
	{
		EscapeGameManager emg = egb_game.makeGameManager();
		assertNotNull(emg);
		emg.addObserver(obs);
		
		assertFalse(emg.move(emg.makeCoordinate(17, 18), emg.makeCoordinate(14, 15)));
		assertNotNull(obs.getCause());
		
		assertEquals("Cannot Use the ORTHOGONAL Pattern on a Hex Board", obs.getCause().getMessage());
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
		assertTrue(emg.move(emg.makeCoordinate(3, 14), emg.makeCoordinate(3, 15)));
		
		assertTrue(emg.move(emg.makeCoordinate(8, 11), emg.makeCoordinate(9, 11)));
		////////////////////// end turn 5 ///////////////////////////////////////
		
		
		////////////////////// turn 6 //////////////////////////////////////////
		assertFalse(emg.move(emg.makeCoordinate(3, 15), emg.makeCoordinate(3, 16)));
		assertEquals("Game is over and PLAYER2 has won", obs.getMessage());
		////////////////////// end turn 6 ///////////////////////////////////////
		
	}

}
