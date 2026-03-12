package game.plants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import game.Assets;
import game.GameEngine;
import game.objects.seed.SeedObject;
import game.objects.zombie.GameZombie;

public class PlantCherryBomb extends GamePlant {
	private static final long serialVersionUID = 7859077773650484795L;
	private long createTime;
	private long bombTime = 4000; // 4 second

	public PlantCherryBomb(SeedObject creator) {
		super(Assets.plantCherryBomb, creator);
		setHealth(300);
		createTime = System.currentTimeMillis();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.plantCherryBomb);
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - createTime > bombTime) {
			List<GameZombie> zombies = GameEngine.getGameWorld().getZombies();
			for (int i = 0; i < zombies.size(); i++) {
				if (zombies.get(i).getBoundry().contains(getX() + getWidth(), getY())) {
					// hit zombie
					zombies.get(i).setAlive(false);
				}
			}
			setAlive(false);
		}
	}
}
