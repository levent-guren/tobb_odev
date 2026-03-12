package game.objects.zombie;

import java.awt.Point;
import java.awt.image.BufferedImage;

import game.GameEngine;
import game.objects.GameObject;
import game.plants.GamePlant;

public abstract class GameZombie extends GameObject {
	private static final long serialVersionUID = 2444668173931549429L;
	private boolean slow;

	public GameZombie(BufferedImage image) {
		super(image);
	}

	@Override
	public void update() {
		if (getX() < GameEngine.getGameWorld().getGrid().getWorldRect(0, 0).x) {
			GameEngine.getInstance().gameOver(false);
		}
		Point gridPoint = GameEngine.getGameWorld().getGrid().getGridCoord((int) getX(), (int) getY());
		if (gridPoint == null) {
			// not on grid (for safety)
			setX(getX() + getSpeed() * (isSlow() ? 0.6 : 1));
			return;
		}
		GamePlant plantOnSameGrid = GameEngine.getGameWorld().getGrid().getObject(gridPoint.x, gridPoint.y);
		if (plantOnSameGrid != null) {
			// damage the plant
			plantOnSameGrid.doDamage(getDamage());
		} else {
			setX(getX() + getSpeed() * (isSlow() ? 0.6 : 1));
		}
	}

	public void setSlow(boolean slow) {
		this.slow = slow;
	}

	public boolean isSlow() {
		return slow;
	}

}
