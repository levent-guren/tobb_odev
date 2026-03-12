package game.objects.zombie;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;

public class TankZombie extends GameZombie {
	private static final long serialVersionUID = 2526044552203591368L;

	public TankZombie() {
		super(Assets.zombieFootball);
		setHealth(200);
		setSpeed(-0.6);
		setDamage(0.5);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.zombieFootball);
	}
}
