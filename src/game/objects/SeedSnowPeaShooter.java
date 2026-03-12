package game.objects;

import java.awt.image.BufferedImage;

import game.objects.seed.SeedObject;
import game.plants.GamePlant;
import game.plants.PlantSnowPeaShooter;

public class SeedSnowPeaShooter extends SeedObject {
	private static final long serialVersionUID = -1653283340485262328L;

	public SeedSnowPeaShooter(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantSnowPeaShooter(this);
	}
}
