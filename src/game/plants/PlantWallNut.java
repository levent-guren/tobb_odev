package game.plants;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.SwingUtilities;

import game.Assets;
import game.GameEngine;
import game.objects.seed.SeedObject;

public class PlantWallNut extends GamePlant {
	private static final long serialVersionUID = -2127016513965679178L;

	public PlantWallNut(SeedObject creator) {
		super(Assets.plantWallNut, creator);
		setHealth(300);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		setImage(Assets.plantWallNut);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (getBoundry().contains(e.getX(), e.getY()) && SwingUtilities.isRightMouseButton(e)) {
			setAlive(false);
			GameEngine.getGameWorld().getSeedBank().addSun(50 / 2);
		}
	}
}
