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

package escape.rule;

/**
 * This class is to store the values under the rule tag in the config file
 * @version May 11, 2020
 */
public class Rule
{
	private RuleID id;
	private int intValue;
	
	/**
	 * the constructor
	 */
	public Rule() {}
	
	/**
	 * gets the ruleID
	 * @return the ruleID
	 */
	public RuleID getId() 
	{ 
		return id; 
		
	}
	
	/**
	 * sets the ruleID
	 * @param id - the ruleID to set to
	 */
	public void setId(RuleID id) 
	{ 
		this.id = id; 
		
	}
	
	/**
	 * gets the int value
	 * @return - the int value
	 */
	public int getIntValue() 
	{ 
		return intValue; 
		
	}
	
	/**
	 * sets the intValue 
	 * @param intValue - the intValue to set to
	 */
	public void setIntValue(int intValue) 
	{ 
		this.intValue = intValue; 
		
	}

}
