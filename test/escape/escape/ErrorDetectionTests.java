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
import escape.board.*;
import escape.board.coordinate.*;
import escape.exception.EscapeException;
import escape.piece.*;

/**
 * This test class is used to detect errors in the config file
 * @version May 1, 2020
 */
class ErrorDetectionTests
{

	@ParameterizedTest
	@MethodSource("gameErrorProvider")
	void attemptingToGetPieceAtInvalidCoordinateType(String fileName) throws Exception
	{
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameBuilder egb 
			= new EscapeGameBuilder(new File(fileName));
			
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate src = emg.makeCoordinate(15, 15);
			Coordinate dest = emg.makeCoordinate(12, 14);
			
			emg.move(src, dest);
			}
		);
	}
	
	static Stream<Arguments> gameErrorProvider()
	{
		return Stream.of(
				Arguments.of(
						"config/NoFlyOrDistance.xml"),
				Arguments.of(
						"config/InvalidPiecesPutOnBoard.xml"),
				Arguments.of(
						"config/BothFlyAndDistance.xml"),
				Arguments.of(
						"config/MultipleOfSamePieceType.xml"),
				Arguments.of(
						"config/Hex_WithInvalidMovement_Ortho.xml"),
				Arguments.of(
						"config/Hex_WithInvalidMovement_Diag.xml"),
				Arguments.of(
						"config/NoPieceTypes.xml"),
				Arguments.of(
						"config/NoCoordinateID.xml"),
				Arguments.of(
						"config/NoMatchPieceName.xml"),
				Arguments.of(
						"config/Ortho_WithInvalidMovement.xml"),
				Arguments.of(
						"config/MakingInfiniteSquareBoard.xml"),
				Arguments.of(
						"config/NegativeIntValues.xml")
				);
	}
	
	@Test
	void puttingPieceOfWrongCoordinateType_Square() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SquareBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = OrthoSquareCoordinate.makeCoordinate(15, 15);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void puttingPieceOfWrongCoordinateType_Hex() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = SquareCoordinate.makeCoordinate(15, 15);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void puttingPieceOfWrongCoordinateType_Ortho() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = SquareCoordinate.makeCoordinate(15, 15);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void puttingPieceOnBlock_Square() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/SquareBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = SquareCoordinate.makeCoordinate(5, 6);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void puttingPieceOnBlock_Hex() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/HexBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = HexCoordinate.makeCoordinate(5, 6);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void puttingPieceOnBlock_Ortho() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate wrongCoordinateType = OrthoSquareCoordinate.makeCoordinate(5, 6);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			EscapePiece test = new EscapePiece(Player.PLAYER1, PieceName.FROG);
			
			b.putPieceAt(test, wrongCoordinateType);
			}
		);
	}
	
	@Test
	void settingOutOfBoundsLocationType() throws Exception
	{
		EscapeGameBuilder egb 
		= new EscapeGameBuilder(new File("config/OrthoSquareBoardWithPieces.xml"));
		
		Assertions.assertThrows(EscapeException.class, () -> {
			EscapeGameManager emg = egb.makeGameManager();
			
			Coordinate OutOfBounds = OrthoSquareCoordinate.makeCoordinate(100, 106);
			
			GenericBoard b = ((EscapeGameController) emg).getBoard();
			
			b.setLocationType(OutOfBounds, LocationType.EXIT);
			}
		);
	}

}
