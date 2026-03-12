package game.objects.seed;

import java.awt.image.BufferedImage;

import game.plants.GamePlant;
import game.plants.PlantSnowPeaShooter;

public class SeedSnowPeaShooter extends SeedObject {
	public SeedSnowPeaShooter(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantSnowPeaShooter(this);
	}
}
