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
import org.junit.jupiter.api.*;
import escape.*;
import escape.board.coordinate.Coordinate;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * Description
 * @version Apr 27, 2020
 */
class BetaEscapeGame_SquareTests
{

	@Test
	void testingInvalidGameConfig() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SampleEscapeGameInvalid.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			egb.makeGameManager();
			}
		);
	}
	@Test
	void testingGetPieces() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SampleEscapeGameWithPieces.xml"));
		EscapeGameManager emg = egb.makeGameManager();

		EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);

		Coordinate testCoordinate = emg.makeCoordinate(15, 15);

		assertTrue(test.equals(emg.getPieceAt(testCoordinate)));

	}

}
