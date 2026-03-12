package game.plants;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import game.Assets;
import game.GameEngine;
import game.objects.seed.SeedObject;

public class PlantSunFlower extends GamePlant {
	private static final long serialVersionUID = 1003011907797165360L;
	private long lastShootTime;
	private long shootInterval = 2000;
	private boolean flower;

	public PlantSunFlower(SeedObject creator) {
		super(Assets.plantSunFlower, creator);
		setHealth(100);
		lastShootTime = System.currentTimeMillis();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.plantSunFlower);
	}

	@Override
	public void update() {
		if (System.currentTimeMillis() - lastShootTime > shootInterval) {
			flower = true;
			lastShootTime = System.currentTimeMillis();
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (flower) {
			g.drawImage(Assets.sun, (int) getX() + 30, (int) getY() + 10, 20, 20, null);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (flower && getBoundry().contains(e.getX(), e.getY())) {
			flower = false;
			lastShootTime = System.currentTimeMillis();
			GameEngine.getGameWorld().getSeedBank().addSun(getCreator().getSunValue());
		}
	}
}
