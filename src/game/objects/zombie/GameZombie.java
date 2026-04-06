package game.objects.zombie;

import java.awt.Point;
import java.awt.image.BufferedImage;

import game.GameEngine;
import game.GameWorld;
import game.objects.GameObject;
import game.objects.freezer.Freezer;
import game.plants.GamePlant;

public abstract class GameZombie extends GameObject {
	private static final long serialVersionUID = 2444668173931549429L;
	private boolean slow;

	public GameZombie(BufferedImage image) {
		super(image);
	}

	@Override
	public void update() {
		Point gridPoint = GameEngine.getGameWorld().getGrid().getGridCoord((int) getX(), (int) getY());
		if (getX() < GameEngine.getGameWorld().getGrid().getWorldRect(0, 0).x) {
			GameEngine.getInstance().gameOver(false);
		}
		if (gridPoint == null) {
			// not on grid (for safety)
			setX(getX() + getSpeed() * (isSlow() ? 0.6 : 1));
			return;
		} else {
			// grid üzerinde ise
			Freezer freezer = GameEngine.getGameWorld().getGrid().getFreeze(gridPoint.y);
			// ve ilgili satır freeze durumunda ise çık.
			// Yani alttaki komutları çalıştırma(Önündeki plant'a vurma ve hareket etme)
			if (freezer.isFreeze()) {
				return;
			}
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

	@Override
	public void doDamage(double damage) {
		super.doDamage(damage);
		if (!isAlive()) {
			// bu zombie öldüyse ve son 1 zombie kaldıysa (ölen dahil)
			// win durumunu kontrol et
			// zombie wave'ler bitti ise oyunu kazandık
			GameWorld world = GameEngine.getGameWorld();
			System.out.println("created zombie:" + world.getCreatedZombie());
			System.out.println("wave count:" + world.getZombieWaveCount());
		}
	}

}
