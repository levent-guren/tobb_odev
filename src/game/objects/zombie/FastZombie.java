package game.objects.zombie;

import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;

public class FastZombie extends GameZombie {
	private static final long serialVersionUID = -1529874793464961260L;

	public FastZombie() {
		super(Assets.zombieConeHead);
		setHealth(100);
		setSpeed(-0.4);
		setDamage(0.5);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.zombieConeHead);
	}
}
