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

/**
 * Description
 * @version Apr 14, 2020
 */
public interface SquaredShapeCoordinate extends Coordinate
{
	
	/**
	 * This method is called to get the y value of the coordinate
	 * @return the y value of the coordinate
	 */
	public int getX();
	
	/**
	 * This method is called to get the y value of the coordinate
	 * @return the y value of the coordinate
	 */
	public int getY();
	
	/**
	 * This method is called to get the coordinate id of the calling Coordinate
	 * class
	 * 
	 * @return the coordinateID
	 */
	public CoordinateID getID();
}
