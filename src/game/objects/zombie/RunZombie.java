package game.objects.zombie;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;

public class RunZombie extends GameZombie {
	private static final long serialVersionUID = -659570737460034094L;

	public RunZombie() {
		super(Assets.zombieSpringter);
		setHealth(100);
		setSpeed(-0.6);
		setDamage(0.5);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.zombieSpringter);
	}
}
