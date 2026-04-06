package game;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import game.objects.freezer.Freezer;
import game.plants.GamePlant;

public class Grid implements Serializable {
	private static final long serialVersionUID = 684076289042630833L;
	private GamePlant[][] grid = new GamePlant[Constants.GRID_COLUMN_COUNT][Constants.LANE_COUNT];

	public Point getGridCoord(int worldX, int worldY) {
		int x = (worldX - 162) / 52;
		int y = (worldY - 67) / 82;
		if (x < 0 || x >= Constants.GRID_COLUMN_COUNT || y < 0 || y >= Constants.LANE_COUNT) {
			return null;
		}
		return new Point(x, y);
	}

	public Rectangle getWorldRect(int gridX, int gridY) {
		return new Rectangle(gridX * 52 + 162, gridY * 82 + 67, 52, 82);
	}

	public void setObject(GamePlant gameObject, Point gridCoord) {
		grid[gridCoord.x][gridCoord.y] = gameObject;
		Rectangle rect = getWorldRect(gridCoord.x, gridCoord.y);
		gameObject.setX(rect.x);
		gameObject.setY(rect.y);
	}

	public GamePlant getObject(int gridX, int gridY) {
		return grid[gridX][gridY];
	}

	public Freezer getFreeze(int gridY) {
		return GameEngine.getGameWorld().getFreezers().get(gridY);
	}

	public void clearDeadPlants() {
		for (int x = 0; x < Constants.GRID_COLUMN_COUNT; x++) {
			for (int y = 0; y < Constants.LANE_COUNT; y++) {
				if (grid[x][y] != null && !grid[x][y].isAlive()) {
					grid[x][y] = null;
				}
			}
		}
	}

	public GamePlant[][] getGrid() {
		return grid;
	}

	public void setGrid(GamePlant[][] grid) {
		this.grid = grid;
	}

	public void clear() {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 5; y++) {
				grid[x][y] = null;
			}
		}
	}

}
