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

package escape.piece;

/**
 * Description
 * @version Apr 26, 2020
 */
public class PieceDescriptor
{
	private final MovementPatternID movementPattern;
	private final PieceAttributeID attributeID;
	private PieceAttributeType attributeType;
	private final PieceName name;
	
	public PieceDescriptor(PieceName name, MovementPatternID pattern, PieceAttributeID attributeID, PieceAttributeType attributeType)
	{
		this.name = name;
		this.movementPattern = pattern;
		this.attributeID = attributeID;
		this.attributeType = attributeType;
	}
	
	public PieceName getName()
	{
		return this.name;
	}
	public MovementPatternID getMovementPattern()
	{
		return this.movementPattern;
	}
	
	public PieceAttributeID getPieceAttributeID()
	{
		return this.attributeID;
	}
	
	public PieceAttributeType getAttributeType()
	{
		return this.attributeType;
	}
	
	
}
