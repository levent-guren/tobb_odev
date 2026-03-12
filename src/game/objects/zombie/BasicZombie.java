package game.objects.zombie;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;

public class BasicZombie extends GameZombie {
	private static final long serialVersionUID = -3067417619682011355L;

	public BasicZombie() {
		super(Assets.zombie);
		setHealth(100);
		setSpeed(-0.2);
		setDamage(0.5);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.zombie);
	}
}
