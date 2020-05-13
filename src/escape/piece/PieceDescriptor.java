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

import java.util.ArrayList;
import escape.exception.EscapeException;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * This class is used to store the piece type information for each of the piece types
 * @version Apr 26, 2020
 */
public class PieceDescriptor
{
	private final MovementPatternID movementPattern;
	private final PieceAttribute attributes[];
	private final PieceName name;
	private final String identity;
	
	/**
	 * This is the constructor of the piece descriptor
	 * 
	 * @param init - the initializer to read from and store the info of
	 */
	public PieceDescriptor(PieceTypeInitializer init)
	{ 
		this.movementPattern = init.getMovementPattern();
		this.name = init.getPieceName();
		this.attributes = init.getAttributes();
		this.identity = init.toString();
	}
	
	/**
	 * This method gets the piece's name
	 * 
	 * @return the name of the piece
	 */
	public PieceName getName()
	{
		return this.name;
	}
	
	/**
	 * This method gets the movement pattern
	 * 
	 * @return the movement pattern id
	 */
	public MovementPatternID getMovementPattern()
	{
		return this.movementPattern;
	}
	
	/**
	 * This method gets the list of the piece attributes
	 * @return the list of the piece attributes
	 */
	public PieceAttribute[] getPieceAttributes()
	{
		return this.attributes;
	}
	
	public int getValue()
	{
		int value = 1;
		for (PieceAttribute attribute : attributes)
		{
			if (attribute.getId().equals(PieceAttributeID.VALUE))
			{
				value = attribute.getIntValue();
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * This method gets the piece descriptor identity
	 * 
	 * @return the piece identity
	 */
	public String getIdentity()
	{
		return this.identity;
	}
	
	/**
	 * This method is used to get all the piece attribute ids of the 
	 * piece type
	 * 
	 * @return a list of the piece attribute id
	 */
	public ArrayList<PieceAttributeID> getAttributeIDs()
	{
		ArrayList<PieceAttributeID> attributeIDs = new ArrayList<>();
		
		for (PieceAttribute attribute : attributes)
		{
			attributeIDs.add(attribute.getId());
		}
		
		return attributeIDs;
	}
	
	/**
	 * This method is used to check for the fly and distance condition. It makes
	 * sure that the fly and distance are an exclusive or and that one of them must
	 * exist and also both cannot exist at the same time
	 * 
	 * @return whether it has the appropriate fly xor distance
	 * 		true -> if meets the requirement; false -> it doesn't meet requirement
	 */
	public boolean checkingFlyAndDistance()
	{
		ArrayList<PieceAttributeID> attributeIDs = getAttributeIDs();
		int count = 0;
		for (PieceAttributeID id : attributeIDs)
		{
			if (id.equals(PieceAttributeID.FLY) || 
					id.equals(PieceAttributeID.DISTANCE))
			{
				count++;
			}
		}
		
		return (count == 1);
	}
	
	/**
	 * This method is used to get the value of the fly or distance value
	 * @return the int value of the fly or distance
	 */
	public int getFlyOrDistanceValue()
	{
		int result = -1;
		for (PieceAttribute pa : attributes)
		{
			if (pa.getId().equals(PieceAttributeID.FLY) ||
					pa.getId().equals(PieceAttributeID.DISTANCE))
			{
				result = pa.getIntValue();
			} 
		}
		
		return result; 
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
		if (!(obj instanceof PieceDescriptor)) {
			return false;
		}
		
		PieceDescriptor other = (PieceDescriptor) obj;
		
		return this.identity.equals(other.getIdentity());
	}
	
}
