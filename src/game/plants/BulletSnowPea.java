package game.plants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import game.Assets;
import game.GameEngine;
import game.objects.GameObject;
import game.objects.zombie.GameZombie;

public class BulletSnowPea extends GameObject {
	private static final long serialVersionUID = 3309524586144703491L;

	public BulletSnowPea() {
		super(Assets.bulletSnowPea);
		setDamage(25);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.bulletSnowPea);
	}

	@Override
	public void update() {
		List<GameZombie> zombies = GameEngine.getGameWorld().getZombies();
		for (int i = 0; i < zombies.size(); i++) {
			if (zombies.get(i).getBoundry().contains(getX() + getWidth(), getY())) {
				// hit zombie
				zombies.get(i).setSlow(true);
				setAlive(false);
				zombies.get(i).doDamage(getDamage());
			}
		}
		super.update();
		if (getX() > GameEngine.getInstance().getGameAreaWidth()) {
			setAlive(false);
		}
	}

}
