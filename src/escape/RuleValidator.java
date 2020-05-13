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

package escape;

import escape.piece.*;
import escape.rule.RuleDescriptor;

/**
 * This class is used to validate the move against the rules
 * @version May 11, 2020
 */
public class RuleValidator
{
	RuleDescriptor descriptor;
	
	private int player1Score;
	private int player2Score;
	
	/**
	 * The constructor of the RuleValidator object
	 * 
	 * @param descriptor - stores the rules that were defined in config file
	 */
	public RuleValidator(RuleDescriptor descriptor) 
	{
		this.descriptor = descriptor;
		
		this.player1Score = 0;
		this.player2Score = 0;
	}
	
	/**
	 * This method is to check if it the moving piece is of the correct player
	 * to make the move at the current moment
	 * 
	 * @param movingPiece - the piece that is moving
	 * @param playerTurn - the player whose turn it currently is
	 * @return
	 * 		returns true if the moving piece is not of the correct player; else false if correct player moving
	 */
	public boolean notPlayerTurn(EscapePiece movingPiece, Player playerTurn)
	{
		// this is what we want
		if (movingPiece.getPlayer().equals(playerTurn))
		{
			return false;
		}
		
		// trying to move player when not player's turn
		return true;
	}
	
	/**
	 * This method is used to determine if a battle can happen in the game
	 * 
	 * @return true if battling is allowe; else battle cannot happen
	 */
	public boolean canBattle()
	{
		if (descriptor.getRemove() || descriptor.getPointConflict())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method is used to update the score when a piece has exited the board
	 * 
	 * @param piece - the piece that has moved to the exit
	 */
	public void updateScore(EscapePiece piece)
	{
		int scoreIncrementor = 1; // default value if piece has no value
		
		if (descriptor.getScore() != -1 && piece.getValue() != -1)
		{
			scoreIncrementor = piece.getValue();
		}
		
		// update the corresponding player's score
		if (piece.getPlayer().equals(Player.PLAYER1))
		{
			player1Score += scoreIncrementor;
		}
		
		else
		{
			player2Score += scoreIncrementor;
		}
	}
	 
	/**
	 * This method is called to see if the game had ended by looking at 
	 * if it equals the turn_limit or if a player score meets the score limt
	 * 
	 * @param numOfTurns - the number of turns that happened
	 * @return
	 * 		true if the game has ended; false if not
	 */
	public boolean checkIfGameEnded(int numOfTurns)
	{
		if (descriptor.getTurnLimit() == -1 &&
				descriptor.getScore() == -1)
		{
			return false;
		}
		
		if (descriptor.getTurnLimit() != -1 &&
				numOfTurns >= descriptor.getTurnLimit())
		{
			return true;
		}
		
		// if it detected a score
		if (descriptor.getScore() != -1)
		{
			int TARGET_SCORE = descriptor.getScore();
			
			if (player1Score >= TARGET_SCORE ||
					player2Score >= TARGET_SCORE)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method is called when the game has ended and is used
	 * to get the winning player (if any)
	 * 
	 * @return
	 * 		the player that won or null if tied
	 */
	public Player getGameWinner()
	{
		if (player1Score > player2Score)
		{
			return Player.PLAYER1;
		}
		
		else if (player2Score > player1Score)
		{
			return Player.PLAYER2;
		}
		
		// no winners
		return null;
	}
	
}
