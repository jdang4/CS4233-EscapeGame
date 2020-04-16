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
	@Test
	void compareSameValueCoordinates()
	{
		SquareCoordinate sc1 = SquareCoordinate.makeCoordinate(1, 1);
		SquareCoordinate sc2 = SquareCoordinate.makeCoordinate(1, 1);
		
		OrthoSquareCoordinate osc1 = OrthoSquareCoordinate.makeCoordinate(1, 1);
		OrthoSquareCoordinate osc2 = OrthoSquareCoordinate.makeCoordinate(1, 1);
		
		HexCoordinate hc1 = HexCoordinate.makeCoordinate(0, 0);
		HexCoordinate hc2 = HexCoordinate.makeCoordinate(0, 0);
		
		assertTrue(sc1.equals(sc2));
		assertTrue(osc1.equals(osc2));
		assertTrue(hc1.equals(hc2));
	}
	
	@Test
	void compareSameCoordinate()
	{
		SquareCoordinate sc = SquareCoordinate.makeCoordinate(1, 1);
		OrthoSquareCoordinate osc = OrthoSquareCoordinate.makeCoordinate(1, 1);
		HexCoordinate hc = HexCoordinate.makeCoordinate(0, 0);
		
		assertTrue(sc.equals(sc));
		assertTrue(osc.equals(osc));
		assertTrue(hc.equals(hc));
	}
	
	@Test
	void compareDifferentTypeCoordinates()
	{
		SquareCoordinate sc = SquareCoordinate.makeCoordinate(1, 1);
		OrthoSquareCoordinate osc = OrthoSquareCoordinate.makeCoordinate(1, 1);
		HexCoordinate hc = HexCoordinate.makeCoordinate(1, 1);
		
		assertFalse(sc.equals(hc));
		assertFalse(hc.equals(osc));
		assertFalse(osc.equals(sc));
	}
	
	@Test
	void compareDifferentValueCoordinates()
	{
		SquareCoordinate sc1 = SquareCoordinate.makeCoordinate(1, 1);
		SquareCoordinate sc2 = SquareCoordinate.makeCoordinate(1, 2);
		
		OrthoSquareCoordinate osc1 = OrthoSquareCoordinate.makeCoordinate(1, 1);
		OrthoSquareCoordinate osc2 = OrthoSquareCoordinate.makeCoordinate(1, 2);
		
		HexCoordinate hc1 = HexCoordinate.makeCoordinate(0, 0);
		HexCoordinate hc2 = HexCoordinate.makeCoordinate(0, 1);
		
		assertFalse(sc1.equals(sc2));
		assertFalse(osc1.equals(osc2));
		assertFalse(hc1.equals(hc2));

		
	}
	
	@Test
	void makingSquareCoordinate()
	{
		SquareCoordinate c = SquareCoordinate.makeCoordinate(1, 1);
		
		assertNotNull(c);
		assertEquals(1, c.getX());
		assertEquals(1, c.getY());
	}
	
	@Test
	void makingOrthoSquareCoordinate()
	{
		OrthoSquareCoordinate c = OrthoSquareCoordinate.makeCoordinate(1, 1);
		
		assertNotNull(c);
		assertEquals(1, c.getX());
		assertEquals(1, c.getY());
	}
	
	@Test
	void makingHexCoordinate()
	{
		HexCoordinate c = HexCoordinate.makeCoordinate(1, 1);
		
		assertNotNull(c);
		assertEquals(1, c.getX());
		assertEquals(1, c.getY());
	}
    
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
						makeInitializers(id, 1, 2, 3, 5), 3),
				Arguments.of(
						makeInitializers(id, 1, 1, 1, 8), 7),
				Arguments.of(
						makeInitializers(id, 1, 1, 6, 1), 5),
				Arguments.of(
						makeInitializers(id, 1, 1, 8, 8), 7)
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
						makeInitializers(id, 0, 0, -1, 2), 2),
				Arguments.of(
						makeInitializers(id, -1, 2, 2, -2), 4),
				Arguments.of(
						makeInitializers(id, 0, 0, 0, -2), 2),
				Arguments.of(
						makeInitializers(id, 0, 2, -2, 0), 4),
				Arguments.of(
						makeInitializers(id, -1, 2, 3, 0), 4)
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
    @MethodSource("orthosquareDistanceTestProvider")
    void testingOrthoSquareDistanceTo(List<OrthoSquareCoordinate> initializers, int expected)
    {
    	OrthoSquareCoordinate from = initializers.get(0);
    	OrthoSquareCoordinate to = initializers.get(1);
    	
    	assertEquals(expected, from.distanceTo(to));
    }
    
    static Stream<Arguments> orthosquareDistanceTestProvider()
    {
		CoordinateID id = CoordinateID.ORTHOSQUARE;
		return Stream.of(
				Arguments.of(
						makeInitializers(id, 1, 2, 3, 5), 5),
				Arguments.of(
						makeInitializers(id, 1, 1, 2, 2), 2),
				Arguments.of(
						makeInitializers(id, 1, 1, 8, 8), 14),
				Arguments.of(
						makeInitializers(id, 5, 5, 3, 1), 6)
				);
    }
    
    @Test
    void testingCoordinateID()
    {
    	SquareCoordinate sc = SquareCoordinate.makeCoordinate(1, 1);
    	OrthoSquareCoordinate osc = OrthoSquareCoordinate.makeCoordinate(2, 2);
    	HexCoordinate hc = HexCoordinate.makeCoordinate(0, 0);
    	
    	assertEquals(CoordinateID.SQUARE, sc.getID());
    	assertEquals(CoordinateID.ORTHOSQUARE, osc.getID());
    	//assertEquals(CoordinateID.HEX, hc.g)
    }
    
    @Test
    void testingHashCode()
    {
    	SquareCoordinate sc = SquareCoordinate.makeCoordinate(1, 1);
    	OrthoSquareCoordinate osc = OrthoSquareCoordinate.makeCoordinate(2, 2);
    	HexCoordinate hc = HexCoordinate.makeCoordinate(0, 0);
    	
    	assertNotNull(sc.hashCode());
    	assertNotNull(osc.hashCode());
    	assertNotNull(hc.hashCode());
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
