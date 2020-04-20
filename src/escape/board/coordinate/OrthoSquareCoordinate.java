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

package escape.board.coordinate;

import java.util.Objects;
import escape.exception.EscapeException;

/**
 * Description
 * @version Apr 12, 2020
 */
public class OrthoSquareCoordinate implements Coordinate, SquaredShapeCoordinate
{
	private final int x;
	private final int y;
	
	private OrthoSquareCoordinate(int x, int y)
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
	public static OrthoSquareCoordinate makeCoordinate(int x, int y)
	{
		return new OrthoSquareCoordinate(x, y);
	}
	
	/*
	 * TODO
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		// verifying if valid coordinate type
		if (!c.getClass().equals(OrthoSquareCoordinate.class))
		{
			throw new EscapeException("Invalid Coordinate Type");
		}
		
		OrthoSquareCoordinate osc = (OrthoSquareCoordinate) c;
		int xDistance = Math.abs(osc.getX() - x);
		int yDistance = Math.abs(osc.getY() - y);
		
		return (xDistance + yDistance);
	}
	
	/*
	 * @see escape.board.coordinate.SquaredShapeCoordinate#getX()
	 */
	@Override
	public int getX()
	{
		return x;
	}

	
	/*
	 * @see escape.board.coordinate.SquaredShapeCoordinate#getY()
	 */
	@Override
	public int getY()
	{
		return y;
	}
	
	/*
	 * @see escape.board.coordinate.SquaredShapeCoordinate#getID()
	 */
	@Override
	public CoordinateID getID()
	{
		return CoordinateID.ORTHOSQUARE;
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
		if (!(obj instanceof OrthoSquareCoordinate)) {
			return false;
		}
		OrthoSquareCoordinate other = (OrthoSquareCoordinate) obj;
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
