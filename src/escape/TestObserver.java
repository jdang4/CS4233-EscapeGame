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

package escape;

/**
 * An observer to use for testing purposes
 * @version May 10, 2020
 */
public class TestObserver implements GameObserver
{
	private String message;
	private Throwable cause;
	
	public TestObserver() {}
	
	/*
	 * @see escape.GameObserver#notify(java.lang.String)
	 */
	@Override
	public void notify(String message)
	{
		this.message = message;
	}
	
	/*
	 * @see escape.GameObserver#notify(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void notify(String message, Throwable cause)
	{
		this.message = message;
		this.cause = cause;
	}
	
	/**
	 * Used to get the false message
	 * 
	 * @return the false message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Gets the error cause
	 * 
	 * @return the error cause
	 */
	public Throwable getCause()
	{
		return cause;
	}
}
