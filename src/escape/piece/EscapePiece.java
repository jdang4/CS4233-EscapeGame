/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.piece;

import escape.board.coordinate.HexCoordinate;

/**
 * This is a class for Pieces.
 * 
 * You may change this class except for the signature of the static factory 
 * method makePiece() and the getter methods for the name and player.
 * 
 * @version Mar 28, 2020
 */
public class EscapePiece
{
    private final PieceName name;
    private final Player player;
    private PieceDescriptor descriptor;
    private int value;
    
    /**
     * Constructor that takes the player and piece name.
     * @param player
     * @param name
     */
    public EscapePiece(Player player, PieceName name) 
    {
    	this.player = player;
    	this.name = name;
    }
    
    public EscapePiece(Player player, PieceName name, PieceDescriptor descriptor) 
    {
    	this.player = player;
    	this.name = name;
    	this.descriptor = descriptor;
    	this.value = descriptor.getValue();
    }
	
	/**
	 * Static factory member. 
	 * DO NOT CHANGE THE SIGNATURE.
	 * @param player the player the piece belongs to
	 * @param name the piee name
	 * @return the piece
	 */
	public static EscapePiece makePiece(Player player, PieceName name)
	{
		return new EscapePiece(player, name);
	}
	
	public PieceDescriptor getDescriptor()
	{
		return this.descriptor;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int val)
	{
		this.value = val;
	}
	

	/**
	 * @return the name
	 */
	public PieceName getName()
	{
		return name;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof EscapePiece)) {
			return false;
		}
		
		EscapePiece other = (EscapePiece) obj;
		return player.equals(other.player) && name.equals(other.name);
	}
}
