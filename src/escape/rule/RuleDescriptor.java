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

import java.util.HashMap;

/**
 * This class is used to store all the rules defined in the game 
 * config file
 * @version May 11, 2020
 */
public class RuleDescriptor
{
	private boolean point_conflict;
	private boolean remove;
	private int score;
	private int turn_limit;
	
	/**
	 * The constructor of the RuleDescriptor object
	 */
	public RuleDescriptor() 
	{
		// default values
		this.point_conflict = false;
		this.remove = false;
		this.score = -1;
		this.turn_limit = -1;
	}
	
	/**
	 * This method is called to initialize this object to store the rules that were defined
	 * in the config file
	 * 
	 * @param rules - the rules of the config file
	 */
	public void initialize(Rule[] rules)
	{
		if (rules != null)
		{
			for (Rule rule : rules)
			{
				if (rule.getId().equals(RuleID.POINT_CONFLICT))
				{
					this.point_conflict = true;
				}
				
				else if (rule.getId().equals(RuleID.REMOVE))
				{
					this.remove = true;
				}
				
				else if (rule.getId().equals(RuleID.SCORE))
				{
					this.score = rule.getIntValue();
				}
				
				else if (rule.getId().equals(RuleID.TURN_LIMIT))
				{
					this.turn_limit = rule.getIntValue();
				}
			}
		}
	}
	
	/**
	 * This method is called to see if the config file has any errors. The errors
	 * are if it contains both Point_Conflict and Remove, and if it contains 2 of the same
	 * RuleIDs
	 * 
	 * @param rules - the rules in the config file
	 * @return
	 * 		returns true if it found an error in the config file, else it returns false
	 */
	public boolean detectedInvalidConfig(Rule[] rules)
	{
		HashMap<RuleID, Integer> foundRules = new HashMap<>();
		
		boolean foundOne = false;
		if (rules != null)
		{
			for (Rule rule : rules)
			{
				if (rule.getId().equals(RuleID.POINT_CONFLICT))
				{
					if (foundOne == true)
					{
						return true;
					}
					
					foundOne = true;
				}
				
				else if (rule.getId().equals(RuleID.REMOVE))
				{
					if (foundOne == true)
					{
						return true;
					}
					
					foundOne = true;
				}
				
				// checks to see if I have already encountered this rule
				else if (foundRules.containsKey(rule.getId()))
				{
					return true;
				}
				
				foundRules.put(rule.getId(), 1);
			}
		}
		
		return false;
	}
	
	/**
	 * This method gets the correct battle id
	 *
	 * @return the battle id to use during battles
	 */
	public RuleID getBattleID()
	{
		if (point_conflict)
		{
			return RuleID.POINT_CONFLICT;
		}
		
		return RuleID.REMOVE;
	}
	
	/**
	 * Gets the value of the point_conflict variable
	 * 
	 * @return the value of the point_conflict variable
	 */
	public boolean getPointConflict()
	{
		return this.point_conflict;
	}
	
	/**
	 * Gets the value of the remove variable
	 * 
	 * @return the value of the remove variable
	 */
	public boolean getRemove()
	{
		return this.remove;
	}
	
	/**
	 * Gets the value of the turn_limit variable
	 * 
	 * @return the value of the turn_limit variable
	 */
	public int getTurnLimit()
	{
		return this.turn_limit;
	}
	
	/**
	 * Gets the value of the score variable
	 * 
	 * @return the value of the score variable
	 */
	public int getScore()
	{
		return this.score;
	}
}
