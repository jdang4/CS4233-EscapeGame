/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020 Gary F. Pollice
 *******************************************************************************/

package escape.board.coordinate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


/**
 * Tests for various coordinates
 * @version Mar 28, 2020
 */
class CoordinateTest
{
    
	/**
	 * This type of test gets its arguments via dependency injection from moveTestProvider();
	 * @param initializers set up the board
	 * @param from move from this coordinate
	 * @param to move to this coordinate
	 * @param expected the expected result calling canMove() on the piece at "from"
	 */
    @ParameterizedTest
    @MethodSource("squareDistanceTestProvider")
    void testingSquareDistanceTo(List<SquareCoordinate> initializers, int expected)
    {
    	SquareCoordinate from = initializers.get(0);
    	SquareCoordinate to = initializers.get(1);
    	
    	assertEquals(expected, from.distanceTo(to));
    }
	
	static Stream<Arguments> squareDistanceTestProvider()
    {
		CoordinateID id = CoordinateID.SQUARE;
		return Stream.of(
				Arguments.of(
						makeInitializers(id, 1, 1, 2, 2), 1),
				Arguments.of(
						makeInitializers(id, 4, 4, 4, 8), 4),
				Arguments.of(
						makeInitializers(id, 4, 4, 2, 4), 2),
				Arguments.of(
						makeInitializers(id, 1, 1, 8, 8), 9),
				Arguments.of(
						makeInitializers(id, 1, 1, 7, 3), 6),
				Arguments.of(
						makeInitializers(id, 1, 1, 1, 1), 0),
				Arguments.of(
						makeInitializers(id, 7, 5, 2, 1), 6)
				);
    }
	
	/**
	 * This type of test gets its arguments via dependency injection from moveTestProvider();
	 * @param initializers set up the board
	 * @param from move from this coordinate
	 * @param to move to this coordinate
	 * @param expected the expected result calling canMove() on the piece at "from"
	 */
    @ParameterizedTest
    @MethodSource("hexDistanceTestProvider")
    void testingHexDistanceTo(List<HexCoordinate> initializers, int expected)
    {
    	HexCoordinate from = initializers.get(0);
    	HexCoordinate to = initializers.get(1);
    	
    	assertEquals(expected, from.distanceTo(to));
    }
	
	static Stream<Arguments> hexDistanceTestProvider()
    {
		CoordinateID id = CoordinateID.HEX;
		return Stream.of(
				Arguments.of(
						makeInitializers(id, 1, 1, 2, 2), 2),
				Arguments.of(
						makeInitializers(id, 1, 2, 3, 5), 5),
				Arguments.of(
						makeInitializers(id, 0, 0, 0, -2), 2),
				Arguments.of(
						makeInitializers(id, 0, 2, -2, 0), 4),
				Arguments.of(
						makeInitializers(id, -1, 2, 3, 0), 4)
				);
    }
	
	// helper method
	private static List<Coordinate> makeInitializers(CoordinateID id, Object... params)
    {
    	List<Coordinate> initializers = new ArrayList<Coordinate>();
    	int ix = 0;
    	while (ix < params.length) 
    	{
    		Coordinate c;
    		if (id.equals(CoordinateID.SQUARE))
    		{
    			c = SquareCoordinate.makeCoordinate((int)params[ix++], (int)params[ix++]);
    		}
    		
    		else if (id.equals(CoordinateID.ORTHOSQUARE))
    		{
    			c = OrthoSquareCoordinate.makeCoordinate((int)params[ix++], (int)params[ix++]);
    		}
    		
    		else
    		{
    			c = HexCoordinate.makeCoordinate((int)params[ix++], (int)params[ix++]);
    		}
    		
    		initializers.add(c);
    	}

    	return initializers;
    }
    
}
