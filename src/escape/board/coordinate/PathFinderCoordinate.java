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
 * @version May 2, 2020
 */
public interface PathFinderCoordinate extends Coordinate
{
	/**
	 * This method is used to set the parent coordinate to the coordinate
	 * @param c the parent coordinate to set as the parent
	 */
	public void setParent(PathFinderCoordinate c);
	
	/**
	 * This method is used to get the parent coordinate of the calling coordinate
	 * 
	 * @return the parent coordinate
	 */
	public PathFinderCoordinate getParent();
	
	/**
	 * This method is used to get the x value of the coordinate
	 * 
	 * @return the x value of the coordinate
	 */
	public int getX();
	
	/**
	 * This method is used to get the y value of the coordinate
	 * 
	 * @return the y value of the coordinate
	 */
	public int getY();
	
	/**
	 * This method is used to determine if the coordinates are equal to each other
	 * 
	 * @param obj - the Object to test if it is equal to the calling coordinate
	 * @return 
	 * 		whether the coordinates are equaled to each other
	 * 		true -> yes; false -> no
	 */
	public boolean equals(Object obj);
}
