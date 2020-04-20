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
package escape.board.coordinate;

import java.util.Objects;
import escape.exception.EscapeException;

/**
 * This is an example of how a SquareCoordinate might be organized.
 * 
 * @version Mar 27, 2020
 */
public class SquareCoordinate implements Coordinate
{
    private final int x;
    private final int y;
    
    private SquareCoordinate(int x, int y)
    {
    	this.x = x;
    	this.y = y;
    }
    
    /**
	 * This method is called to create a HexCoordinate object with the
	 * given values
	 * 
	 * @param x 
	 * 			denotes the x-axis value
	 * @param y
	 * 			denotes the y-axis value
	 * @return a new HexCoordinate object with the provided values
	 */
    public static SquareCoordinate makeCoordinate(int x, int y)
    {
    	return new SquareCoordinate(x, y);
    }
    
    /*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		// verifying if correct coordinate type
		if (!c.getClass().equals(SquareCoordinate.class))
		{
			throw new EscapeException("Invalid Coordinate Type");
		}
		
		SquareCoordinate sc = (SquareCoordinate) c;
		
		int x2 = sc.getX();
		int y2 = sc.getY(); 
		
		int distance = 0;
		
		if (x == x2 && y != y2)
		{
			distance = Math.abs(y2 - y);
		}
		
		else if (y == y2 && x != x2)
		{
			distance = Math.abs(x2 - x);
		}
		
		else
		{
			distance = Math.max((x2 - x), (y2 - y));
		}
		
		return distance;
	}
	
	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SquareCoordinate)) {
			return false;
		}
		SquareCoordinate other = (SquareCoordinate) obj;
		return x == other.x && y == other.y;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}
