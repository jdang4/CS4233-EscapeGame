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

import escape.board.coordinate.CoordinateID;

/**
 * Description
 * @version Apr 15, 2020
 */
public interface SquareBoardInfo
{
	BoardType getBoardType();
    void setCoordinateID(CoordinateID id);
}
