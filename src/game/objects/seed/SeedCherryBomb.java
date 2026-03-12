package game.objects.seed;

import java.awt.image.BufferedImage;

import game.plants.GamePlant;
import game.plants.PlantCherryBomb;

public class SeedCherryBomb extends SeedObject {
	public SeedCherryBomb(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantCherryBomb(this);
	}
}
