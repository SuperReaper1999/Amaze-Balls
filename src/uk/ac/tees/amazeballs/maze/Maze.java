package uk.ac.tees.amazeballs.maze;

import java.io.Serializable;
import java.util.List;

import android.graphics.Point;


/**
 * A class for representing a maze.
 * 
 * The grid coordinates for a maze start at (0,0) in the top left corner
 * with the X axis going across and the Y axis down like this.
 * 
 *       0  1  2  3  4 -------> X 	(width)
 * 		+--------------+
 * 	  0 |  |  |  |  |  |
 * 		+--+--+--+--+--+
 * 	  1 |  |  |  |  |  |
 * 		+--+--+--+--+--+
 * 	  2 |  |  |  |  |  |
 * 		+--+--+--+--+--+
 *    3 |  |  |  |  |  |
 * 		+--+--+--+--+--+
 *    4 |  |  |  |  |  |
 *      +--+--+--+--+--+
 *    |
 *    |
 *    |
 *    |
 *    Y
 *    
 * (height)
 *    
 * The above example represent a maze of width 5 and height 5 (5x5)
 * with each axis beginning at 0.
 * 
 * 
 * @author Alex Mullen (J9858839)
 *
 */
public class Maze implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final int width;
	public final int height;
	
	protected final TileType[][] grid;

	/**
	 * Instantiates a new empty maze of the specified dimensions.
	 * 
	 * @param width  the width of the m15aze
	 * @param height the height of the maze
	 */
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new TileType[width][height];
	}
	
	/**
	 * Returns the width of this maze (The number of tiles across).
	 * 
	 * @return the width
	 */
	//@Deprecated
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of this maze (The number of tiles down).
	 * 
	 * @return the height
	 */
	//@Deprecated
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns the tile instance at the specified coordinate in this
	 * maze.
	 * 
	 * @param x the x position (across)
	 * @param y the y position (down)
	 * @return the tile at that location or null if there is no tile
	 */
	public TileType getTileAt(int x, int y) {
		return grid[x][y];
	}
	
	/**
	 * Sets the tile instance for the specified coordinates in this
	 * maze.
	 * 
	 * @param x the x position (across)
	 * @param y the y position (down)
	 * @param tile the tile to place at the specified coordinates
	 */
	public void setTileAt(int x, int y, TileType tile) {
		grid[x][y] = tile;
	}
	
	/**
	 * Checks if the specified tile position is located at the border of 
	 * this maze.
	 * 
	 * @param x the x position (across)
	 * @param y the y position (down)
	 * @return true if the specified position is located at the border of
	 * this maze, otherwise false is returned
	 */
	public boolean isTileAtAnEdge(int x, int y) {
		return (x == 0 || x == (width - 1) || y == 0 || y == (height - 1));
	}
	
	
//	/**
//	 * Replaces all occurrences of tiles of type oldTile with tiles of
//	 * type newTile.
//	 * 
//	 * @param oldTile the tiles to replace
//	 * @param newTile the tiles to replace with
//	 */
//	public void replaceAll(TileType oldTile, TileType newTile) {
//		for (int x = 0; x < width; x++) {
//			for (int y = 0; y < height; y++) {
//				if (grid[x][y] == oldTile) {
//					grid[x][y] = newTile;
//				}
//			}
//		}
//	}
	
	/**
	 * Replaces each tile from a list of positions with the specified tile.
	 * 
	 * @param positions the list of positions to replace
	 * @param newTile the tile type to replace each position with
	 */
	public void replaceAllAt(List<Point> positions, TileType newTile) {
		for (Point p : positions) {
			grid[p.x][p.y] = newTile;
		}
	}
	
}
	