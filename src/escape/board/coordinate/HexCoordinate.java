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

/**
 * Description
 * @version Apr 12, 2020
 */
public class HexCoordinate implements Coordinate
{
	private final int x;
	private final int y;
	
	private HexCoordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	} 
	
	public static HexCoordinate makeCoordinate(int x, int y)
	{
		return new HexCoordinate(x, y);
	}
	
	/*
	 * @see escape.board.coordinate.Coordinate#distanceTo(escape.board.coordinate.Coordinate)
	 * 
	 * Title: Manhattan Distance between tiles in a hexagonal grid
	 * Author: Glorfindel and aaz
	 * Date: 04/12/2020
	 * Code Version: ??
	 * Availability: https://stackoverflow.com/questions/5084801/manhattan-distance-between-tiles-in-a-hexagonal-grid
	 */
	@Override
	public int distanceTo(Coordinate c)
	{
		HexCoordinate hc = (HexCoordinate) c;
		
		int dx = hc.getX() - x;
		int dy = hc.getY() - y;
		
		int xSign = (int) Math.signum(dx);
		int ySign = (int) Math.signum(dy);
		
		int distance;
		
		if (xSign == ySign)
		{
			distance = Math.abs(dx + dy);
		}
		
		else
		{
			distance = Math.max(Math.abs(dx), Math.abs(dy));
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
		if (!(obj instanceof HexCoordinate)) {
			return false;
		}
		HexCoordinate other = (HexCoordinate) obj;
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
