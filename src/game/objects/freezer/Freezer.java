package game.objects.freezer;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import game.Assets;
import game.GameEngine;
import game.objects.GameObject;

public class Freezer extends GameObject {
	private static final long serialVersionUID = -858302691978566773L;
	private boolean freeze;
	private boolean coolDown;
	private long time;

	public Freezer() {
		super(Assets.bulletPea);
	}

	public Freezer(int x, int y) {
		super(Assets.bulletPea, x, y, 0);
		setWidth(40);
		setHeight(40);
	}

	public void mousePressed(MouseEvent e) {
		if (!isAnyCoolDown() && getBoundry().contains(e.getX(), e.getY()) && SwingUtilities.isLeftMouseButton(e)
				&& GameEngine.getGameWorld().getSeedBank().getSun() >= 100) {
			setImage(Assets.bulletSnowPea);
			freeze = true;
			coolDown = true;
			GameEngine.getGameWorld().getSeedBank().addSun(-100);
			time = GameEngine.getGameWorld().getTimer().getGameTime();
		}
	}

	private boolean isAnyCoolDown() {
		for (Freezer f : GameEngine.getGameWorld().getFreezers()) {
			if (f.coolDown) {
				return true;
			}
		}
		return false;
	}

	public boolean isFreeze() {
		return freeze;
	}

	@Override
	public void update() {
		if (freeze) {
			if (GameEngine.getGameWorld().getTimer().getGameTime() - time > 4000) {
				setImage(Assets.bulletPea);
				freeze = false;
			}
		}
		if (coolDown) {
			if (GameEngine.getGameWorld().getTimer().getGameTime() - time > 12000) {
				coolDown = false;
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(getImage(), (int) getX(), (int) getY() + 20, getWidth(), getHeight(), null);
	}

}
