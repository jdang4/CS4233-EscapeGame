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

import escape.exception.EscapeException;
import escape.util.PieceTypeInitializer;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * Description
 * @version Apr 26, 2020
 */
public class PieceFactoryBuilder
{
	private PieceTypeInitializer initializers[];
	
	public PieceFactoryBuilder(PieceTypeInitializer... initializers)
	{
		this.initializers = initializers;
	}
	
	private boolean errorDetection(PieceTypeInitializer init)
	{
		if (init.getMovementPattern() == null || init.getPieceName() == null
				|| init.getAttributes() == null)
		{
			return false;
		}
		
		else if (init.getAttributes() != null)
		{
			PieceAttribute[] attributes = init.getAttributes();
			
			for (PieceAttribute attribute : attributes)
			{
				if (attribute.getId() == null)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public PieceDescriptor makePiece()
	{
		return null;
	}
}
