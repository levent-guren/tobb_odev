package game.plants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import game.Assets;
import game.GameEngine;
import game.objects.GameObject;
import game.objects.zombie.GameZombie;

public class BulletPea extends GameObject {
	private static final long serialVersionUID = -858302691978566773L;
	private List<GameZombie> damagedZombies = new ArrayList<GameZombie>();

	public BulletPea() {
		super(Assets.bulletPea);
		setDamage(25);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.bulletPea);
	}

	public BulletPea(int x, int y, double speed) {
		super(Assets.bulletPea, x, y, speed);
	}

	@Override
	public void update() {
		List<GameZombie> zombies = GameEngine.getGameWorld().getZombies();
		for (int i = 0; i < zombies.size(); i++) {
			if (!damagedZombies.contains(zombies.get(i))
					&& zombies.get(i).getBoundry().contains(getX() + getWidth(), getY())) {
				// hit zombie
				zombies.get(i).doDamage(getDamage());
				damagedZombies.add(zombies.get(i));
				// setAlive(false);
			}
		}
		if (damagedZombies.size() == 3) {
			setAlive(false);
		}
		super.update();
		if (getX() > GameEngine.getInstance().getGameAreaWidth()) {
			setAlive(false);
		}
	}

}
