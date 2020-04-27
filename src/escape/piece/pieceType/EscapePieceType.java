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

package escape.piece.pieceType;

import escape.piece.*;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * Description
 * @version Apr 26, 2020
 */
public abstract class EscapePieceType
{
	protected PieceTypeInitializer initializer;
	protected MovementPatternID patternID;
	protected PieceAttribute attributes[];
	
	public EscapePieceType(PieceTypeInitializer initializer)
	{
		this.initializer = initializer;
	}
	
	protected void initialize()
	{
		this.patternID = initializer.getMovementPattern();
		
		this.attributes = initializer.getAttributes();
		
	}
	
	protected MovementPatternID getMovementPattern()
	{
		return this.patternID;
	}
	
	protected PieceAttribute[] getAttributes()
	{
		return this.attributes;
	}
}
