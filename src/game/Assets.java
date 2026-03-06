package game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Assets {
	public static BufferedImage background;
	public static BufferedImage appIcon;
	public static BufferedImage lawnMower;
	public static BufferedImage seedSlot;
	public static BufferedImage seedBank;
	public static BufferedImage sun;
	public static BufferedImage plantPeaShooter;
	public static BufferedImage plantSnowPea;
	public static BufferedImage plantWallNut;
	public static BufferedImage plantSunFlower;
	public static BufferedImage plantCherryBomb;
	public static BufferedImage bulletPea;
	public static BufferedImage bulletSnowPea;
	public static BufferedImage seedCherryBomb;
	public static BufferedImage seedPeaShooter;
	public static BufferedImage seedSnowPeaShooter;
	public static BufferedImage seedSunFlower;
	public static BufferedImage seedWallNut;
	public static BufferedImage zombie;
	public static BufferedImage zombieConeHead;
	public static BufferedImage zombieFootball;
	public static BufferedImage zombieSpringter;

	public static void load() {
		try {
			background = ImageIO.read(Assets.class.getResource("/resources/frontyard.jpg"));
			appIcon = ImageIO.read(Assets.class.getResource("/resources/icon.png"));
			lawnMower = ImageIO.read(Assets.class.getResource("/resources/HD_Lawn_Mower.png"));
			seedSlot = ImageIO.read(Assets.class.getResource("/resources/Seed_Slot.png"));
			seedBank = ImageIO.read(Assets.class.getResource("/resources/SeedBank.png"));
			sun = ImageIO.read(Assets.class.getResource("/resources/SunPvZH.png"));
			plantPeaShooter = ImageIO.read(Assets.class.getResource("/resources/plants/1769829-plant_peashooter_thumb.png"));
			plantSnowPea = ImageIO.read(Assets.class.getResource("/resources/plants/HD_Snow_Pea1.png"));
			plantWallNut = ImageIO.read(Assets.class.getResource("/resources/plants/HD_Wall-nut.png"));
			plantSunFlower = ImageIO.read(Assets.class.getResource("/resources/plants/Sunflower2009HD.png"));
			plantCherryBomb = ImageIO.read(Assets.class.getResource("/resources/plants/PvZ_Pictures.doc2.png"));
			bulletPea = ImageIO.read(Assets.class.getResource("/resources/bullets/ProjectilePea.png"));
			bulletSnowPea = ImageIO.read(Assets.class.getResource("/resources/bullets/ProjectileSnowPea.png"));
			seedCherryBomb = ImageIO.read(Assets.class.getResource("/resources/seed/CherryBombSeedPacket1.png"));
			seedPeaShooter = ImageIO.read(Assets.class.getResource("/resources/seed/PeashooterSeedPacket1.png"));
			seedSnowPeaShooter = ImageIO.read(Assets.class.getResource("/resources/seed/SnowPeaSeedPacket1.png"));
			seedSunFlower = ImageIO.read(Assets.class.getResource("/resources/seed/SunflowerSeedPacket1.png"));
			seedWallNut = ImageIO.read(Assets.class.getResource("/resources/seed/Wall-nutSeedPacket1.png"));
			zombie = ImageIO.read(Assets.class.getResource("/resources/zombies/ZombieHD.png"));
			zombieConeHead = ImageIO.read(Assets.class.getResource("/resources/zombies/ConeHead_Zombie.png"));
			zombieFootball = ImageIO.read(Assets.class.getResource("/resources/zombies/FootballZombieHD.png"));
			zombieSpringter = ImageIO.read(Assets.class.getResource("/resources/zombies/Sprinter.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
