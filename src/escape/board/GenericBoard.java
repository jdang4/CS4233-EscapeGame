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
 * Description
 * @version Apr 16, 2020
 */
public abstract class GenericBoard implements Board<Coordinate>
{
	protected final int xMax, yMax;
	protected BoardType type;
	protected CoordinateID id;
	
	public GenericBoard(int xMax, int yMax)
	{
		this.xMax = xMax;
		this.yMax = yMax;
	} 
	
	public BoardType getBoardType()
	{
		return type;
	}
	 
	public void setCoordinateID(CoordinateID id)
	{
		// do this to only allow this to be set once
		if (this.id == null && id != null)
		{
			this.id = id;
		}
	}
	
	public int getXMax()
	{
		return xMax;
	}
	
	public int getYMax()
	{
		return yMax;
	}
}
