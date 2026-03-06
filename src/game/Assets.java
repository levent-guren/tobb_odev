package game;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Assets {
	public static BufferedImage background;
	public static BufferedImage appIcon;

	public static void load() {
		try {
			background = ImageIO.read(Assets.class.getResource("/resource/frontyard.jpg"));
			appIcon = ImageIO.read(Assets.class.getResource("/resource/icon.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
