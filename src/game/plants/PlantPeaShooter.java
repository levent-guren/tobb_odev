package game.plants;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;
import game.GameEngine;
import game.objects.GameObject;
import game.objects.seed.SeedObject;

public class PlantPeaShooter extends GamePlant {
	private static final long serialVersionUID = 6856721315036097595L;
	private long lastShootTime;
	private long shootInterval = 2000;

	public PlantPeaShooter(SeedObject creator) {
		super(Assets.plantPeaShooter, creator);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.plantPeaShooter);
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastShootTime > shootInterval) {
			GameObject bullet = new BulletPea();
			bullet.setX(getX());
			bullet.setY(getY() + 10);
			bullet.setWidth(bullet.getImage().getWidth());
			bullet.setHeight(bullet.getImage().getHeight());
			bullet.setSpeed(4.0);
			GameEngine.getGameWorld().addBullet(bullet);
			lastShootTime = System.currentTimeMillis();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

}
