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

import java.util.*;
import escape.board.*;
import escape.board.coordinate.*;
import escape.piece.*;
import escape.util.PieceTypeInitializer.PieceAttribute;

/**
 * This class is soley meant for the design of the shortest path algorithm that 
 * is used by the Game Manager (technically the Game Controller)
 * @version May 2, 2020
 */
public class PathFindingAlgorithm
{
	private final GenericBoard board;
	private int[][] DIRECTIONS;
	private HashMap<PathFinderCoordinate, Boolean> visitedCoordinate;
	private boolean jumpFlag = false;
	private boolean unBlockFlag = false;
	private boolean flyFlag = false;
	private String falseMessage;
	
	/**
	 * The constructor for the path finding algorithm class
	 *
	 * @param board - the board to find the path on
	 */
	public PathFindingAlgorithm(GenericBoard board)
	{
		this.board = board;
	}
	
	public String getFalseMessage()
	{
		return falseMessage;
	}
	/**
	 * This method is used to determine if the specific coordinate has been 
	 * visitied yet
	 * 
	 * @param x - the x value of the coordinate to check for
	 * @param y - the y value of the coordiante to check for
	 * @return
	 * 		whether the coordinate has been visited or not
	 * 		true -> has been visited; false -> has not been visited yet
	 */
	private boolean hasVisited(int x, int y) 
	{
		PathFinderCoordinate tempC = createCoordinateWithParent(x, y, null);
		
		if (visitedCoordinate.containsKey(tempC))
		{
			return visitedCoordinate.get(tempC);
		} 
		
		return false;
	}
	
	/**
	 * This is the main method that the game manager would call to see if the piece can make the move or
	 * not. This method would call the path finding algorithm and return the result back to the game manager
	 * 
	 * @param src - the starting coordinate
	 * @param dest - the ending coordinate
	 * @param availableDirections - the directions that the piece can move to
	 * @param limit - the distance or fly value limit
	 * @return
	 * 		the results if the piece can make the move or not
	 * 		true -> can make the move; false -> cannot make the move
	 */
	public boolean canMakeMove(Coordinate src, Coordinate dest, int[][] availableDirections, int limit)
	{
		PathFinderCoordinate start = (PathFinderCoordinate) src;
		PathFinderCoordinate end = (PathFinderCoordinate) dest;
		
		EscapePiece movingPiece = board.getPieceAt(src);
		
		PieceDescriptor pd = movingPiece.getDescriptor();
		
		PieceAttribute[] attributes = pd.getPieceAttributes();
		
		// check if the moving piece contains a JUMP
		if (pd.getAttributeIDs().contains(PieceAttributeID.JUMP))
		{
			for (PieceAttribute a : attributes)
			{
				if (a.getId().equals(PieceAttributeID.JUMP))
				{
					if (a.getAttrType().equals(PieceAttributeType.BOOLEAN))
					{
						this.jumpFlag = a.isBooleanValue();
					}
				}
			}
		}
		
		// check if the moving piece contains a UNBLOCK
		if (pd.getAttributeIDs().contains(PieceAttributeID.UNBLOCK))
		{
			for (PieceAttribute a : attributes)
			{
				if (a.getId().equals(PieceAttributeID.UNBLOCK))
				{
					if (a.getAttrType().equals(PieceAttributeType.BOOLEAN))
					{
						this.unBlockFlag = a.isBooleanValue();
					}
					
				}
			}
			
		}
		
		// check if the moving piece contains a FLY
		if (pd.getAttributeIDs().contains(PieceAttributeID.FLY))
		{
			this.flyFlag = true;
		}
		
		this.DIRECTIONS = availableDirections;
		
		
		
		// call the shortest path algorithm
		ArrayList<PathFinderCoordinate> path = findShortestPath(start, end, movingPiece.getPlayer(), limit);
		
		// check to see if the path meets distance or fly value limit
		if (path.size() <= limit && path.size() > 0)
		{
			return true;
		}
		
		else if (path.size() > limit)
		{
			if (start.distanceTo(end) > limit) 
			{
				falseMessage = "> limit";
			}
			
			else 
			{
				falseMessage = "cannot make move";
			}
		}
		return false;
	}
 
	/**
	 * This method is the main logic of this class, where it implements the BFS algorithm in order to calculate 
	 * the shortest path from the starting coordinate to the ending coordinate
	 * 
	 * 
	 * @param src - the starting coordinate
	 * @param dest - the ending coordinate
	 * @param player - the escape piece player type
	 * @return
	 * 		an array of the PathFinderCoordinates of the shortest path to the destingation or an empty list
	 * 		if it is not possible
	 * 
	 * Code Citation:
	 * Title: Shortest path in a Binary Maze
	 * Author: Aditya Goel
	 * Date: 05/1/2020
	 * Availability: https://www.geeksforgeeks.org/shortest-path-in-a-binary-maze/
	 */
	private ArrayList<PathFinderCoordinate> findShortestPath(PathFinderCoordinate src, PathFinderCoordinate dest, Player player, int limit)
	{
		// acts as a reference to check which coordinates I have visited
		visitedCoordinate = new HashMap<>(); 
		
		// stores the all the coordinates to visit next
		LinkedList<PathFinderCoordinate> nextToVisit = new LinkedList<>();
        
		visitedCoordinate.put(src, true);

        src.setParent(null);
        nextToVisit.add(src);
        
        // loop until either found the path or it could not find the path
        while (!nextToVisit.isEmpty()) {
            PathFinderCoordinate cur = nextToVisit.remove();
            
            // found the path to the destination
            if (cur.equals(dest)) {
                return getShortestPath(cur);
            }
            
            // check for all the directions
            for (int[] direction : DIRECTIONS) { 

            	int curX = cur.getX() + direction[0];
            	int curY = cur.getY() + direction[1];
            	
            	if (isValidInBoard(curX, curY) && !hasVisited(curX, curY)) {	
            		if (flyFlag)
                	{
                		// can skip over any obstacles in the way
                	}
                	
                	else 
                	{
                		// checking against block or exit
                		if (!checkBlock(curX, curY) || foundExitInPath(curX, curY, dest))
                		{
                			continue;
                		}
               		
                		// handling PIECE in PATH (not at final destination)
                		if (foundPieceInPath(curX, curY) && !(dest.getX() == curX && dest.getY() == curY))
                		{ 
                			if (jumpFlag)
                			{
                				int newX = curX + direction[0];
                				int newY = curY + direction[1];
                				
                				// verify new coordinate is in board and has not been visited 
                				if (isValidInBoard(newX, newY) && !hasVisited(newX, newY)) 
                				{
                					if (!checkBlock(newX, newY) || foundExitInPath(newX, newY, dest))
                            		{
                            			// go right to continue
                            		}
                					
                					else if (foundPieceInPath(newX, newY))
                					{	
                						// go right to continue
                					}
                					
                					else
                					{
                						// simulating a jumping move
                						PathFinderCoordinate jumpedOver = createCoordinateWithParent(curX, curY, cur);
                						
                						PathFinderCoordinate jumpedTo = createCoordinateWithParent(newX, newY, jumpedOver);
                						
                						visitedCoordinate.put(cur, true);
                						nextToVisit.add(jumpedTo);
                						
                					}
                				}
                			}
                			
                			// move on to the nexToVisit Coordinate
                			continue;
                		}
                	}
            		
            		// if get to here, add to nextNode
            		PathFinderCoordinate coordinate = createCoordinateWithParent(cur.getX() + direction[0], cur.getY() + direction[1], cur);
            		visitedCoordinate.put(cur, true);	
            		nextToVisit.add(coordinate);
                    
            	} 	
            }
        }
        
        // can't make the move so return an empty list
        return new ArrayList<>();
	}
	
	/**
	 * This method is used to determine if the given x and y values are within the board
	 * 
	 * @param x - the x value of the coordinate
	 * @param y - the y value of the coordinate
	 * @return
	 * 		whether the x and y values are in the board or not
	 * 		true -> are in the board; false -> are not in the board
	 */
	private boolean isValidInBoard(int x, int y)
	{
		PathFinderCoordinate tempC = createCoordinateWithParent(x, y, null);
		
		// call the function of through the board
		if (board.insideBoard(tempC))
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method is in charge of creating the appropriate coordinate type with a parent coordinate
	 * set to it
	 * 
	 * @param x - the x value of the coordinate
	 * @param y - the y value of the coordinate
	 * @param parent - the coordinate that is the parent to the coordinate that is being created
	 * @return
	 * 		the resulted coordinated based on the parameters given
	 */
	private PathFinderCoordinate createCoordinateWithParent(int x, int y, PathFinderCoordinate parent)
	{
		PathFinderCoordinate result;
		
		if (board.getBoardType().equals(BoardType.SQUARE))
		{
			
			result = SquareCoordinate.makeCoordinate(x, y);	
		}
		
		else if (board.getBoardType().equals(BoardType.ORTHOSQUARE))
		{
			result = OrthoSquareCoordinate.makeCoordinate(x, y);
		}
		
		else
		{
			result = HexCoordinate.makeCoordinate(x, y);
		}
		
		// setting the parent
		result.setParent(parent);
		
		return result;
	}
	
	/**
	 * This method is used to iterate through the current coordinate and return the 
	 * actual path used to get to the destination
	 * 
	 * @param cur - the coordinate to get the path of from the source coordinate
	 * @return
	 * 		and array list that shows the path to the provided coordinate from the
	 * 		starting coordinate
	 * 
	 * Code Citation:
	 * Title: Shortest path in a Binary Maze
	 * Author: Aditya Goel
	 * Date: 05/1/2020
	 * Availability: https://www.geeksforgeeks.org/shortest-path-in-a-binary-maze/
	 */
	private ArrayList<PathFinderCoordinate> getShortestPath(PathFinderCoordinate cur) {
        ArrayList<PathFinderCoordinate> path = new ArrayList<>();
        PathFinderCoordinate iter = cur;

        while (iter != null) {
            path.add(0, iter);
            iter = iter.getParent();
        }
        
        path.remove(0);

        return path;
    }
	
	/**
	 * This method is used to determine if the provided x and y values would land on an
	 * EXIT location type on the board
	 * 
	 * 
	 * @param x - the x value of the coordinate
	 * @param y - the y value of the coordinate
	 * @param to - the destingation coordinate 
	 * @return
	 * 		whether the coordinate is where an EXIT location type is at
	 * 		true -> it is an EXIT; false -> it is not an EXIT
	 */
	private boolean foundExitInPath(int x, int y, PathFinderCoordinate to)
	{
		PathFinderCoordinate tempC = createCoordinateWithParent(x, y, null);
		
		if (board.getLocationType(tempC).equals(LocationType.EXIT) &&
				!tempC.equals(to))
		{
			
			return true;
		}
		
		return false; 
	}
	
	/**
	 * This method is called to handle the case when the coordinate is of a block type
	 * Depending on if the moving piece has an unblock attribute it will determine if 
	 * the piece can land on the block or not
	 * 
	 * @param x - the x value of the coordinate 
	 * @param y - the y value of the coordinate
	 * @return
	 * 		whether the piece can land on the cooridnate 
	 * 		true -> it can land on the coordinate or it isn't of a block type; false -> it cannot
	 */
	private boolean checkBlock(int x, int y)
	{
		PathFinderCoordinate tempC = createCoordinateWithParent(x, y, null);
		
		if (board.getLocationType(tempC).equals(LocationType.BLOCK))
		{
			if (!unBlockFlag)
			{
				return false;
			}
		}
		
		return true;
	} 
	
	/**
	 * This method is called to check and see if there is a piece on the coordinate
	 * 
	 * @param x - the x value of the coordinate
	 * @param y - the y value of the coordinate
	 * @return
	 * 		whether the coordinate has a piece on it or not
	 * 		true -> has a piece on it; false -> no piece is on it
	 */
	private boolean foundPieceInPath(int x, int y)
	{
		PathFinderCoordinate tempC = createCoordinateWithParent(x, y, null);
		
		EscapePiece piece = board.getPieceAt(tempC);
		
		if (piece != null)
		{
			return true;
		}
		
		return false;
	}
	
}
