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
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * Description
 * @version Apr 26, 2020
 */
public class PieceDescriptor
{
	private final MovementPatternID movementPattern;
	private final PieceAttribute attributes[];
	private final PieceName name;
	private final String identity;
	
	public PieceDescriptor(PieceTypeInitializer init)
	{ 
		this.movementPattern = init.getMovementPattern();
		this.name = init.getPieceName();
		this.attributes = init.getAttributes();
		this.identity = init.toString();
	}
	
	public PieceName getName()
	{
		return this.name;
	}
	public MovementPatternID getMovementPattern()
	{
		return this.movementPattern;
	}
	
	public PieceAttribute[] getPieceAttributes()
	{
		return this.attributes;
	}
	
	public String getIdentity()
	{
		return this.identity;
	}
	
	public ArrayList<PieceAttributeID> getAttributeIDs()
	{
		ArrayList<PieceAttributeID> attributeIDs = new ArrayList<>();
		
		for (PieceAttribute attribute : attributes)
		{
			attributeIDs.add(attribute.getId());
		}
		
		return attributeIDs;
	}
	
	
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
