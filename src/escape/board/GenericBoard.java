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

package escape.board;

import escape.board.coordinate.*;

/**
 * This is an abstract class that represents a generic board that 
 * is used in the escape game
 * @version Apr 16, 2020
 */
public abstract class GenericBoard implements Board<Coordinate>
{
	protected final int xMax, yMax;
	protected BoardType type;
	protected CoordinateID id;
	
	/**
	 * This is the constructor for a Generic Board
	 * 
	 * @param xMax
	 * 			the maximum x value for the board
	 * @param yMax
	 * 			the maximum y value for the board
	 */
	public GenericBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
	} 
	
	/**
	 * This method is used to get the type of the board
	 * 
	 * @return the board type
	 */
	public BoardType getBoardType()
	{
		return type;
	}
	
	/**
	 * This method is used to set the coordinate ID of the board. 
	 * This coordinateID will be used to determine what coordinate type
	 * the board is using
	 * 
	 * @param id the coordinate ID to set the board's id to
	 */
	public void setCoordinateID(CoordinateID id)
	{
		// do this to only allow this to be set once
		if (this.id == null && id != null)
		{
			this.id = id;
		}
	}
	
	/**
	 * This method is called to determine if the given coordinate is of
	 * the same type
	 * 
	 * @param c
	 * 			the coordinate to be tested
	 * @return whether the given coordinate is of the same coordinate type
	 * 			true -> same type; false -> not same type
	 */
	protected abstract boolean sameCoordinate(Coordinate c);
	
	/**
	 * This method is used to get the xMax value
	 * 
	 * @return the xMax value of the board
	 */
	public int getXMax()
	{
		return xMax;
	}
	
	/**
	 * This method is used to get the yMax value
	 * 
	 * @return the yMax value of the board
	 */
	public int getYMax()
	{
		return yMax;
	}
}
