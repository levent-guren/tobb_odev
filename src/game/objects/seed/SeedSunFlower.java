package game.objects.seed;

import java.awt.image.BufferedImage;

import game.plants.GamePlant;
import game.plants.PlantSunFlower;

public class SeedSunFlower extends SeedObject {
	private static final long serialVersionUID = -804611674185642213L;

	public SeedSunFlower(BufferedImage image, int x, int y, int sunValue, SeedBank seedBank) {
		super(image, x, y, sunValue, seedBank);
	}

	@Override
	public GamePlant getObject() {
		return new PlantSunFlower(this);
	}
}
