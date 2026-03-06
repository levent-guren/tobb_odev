package game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -3782658723628146L;
	private GameWorld gameWorld;

	public GamePanel(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		setPreferredSize(new Dimension(800, 500));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// oyun arka planı
		g.drawImage(Assets.background, 0, 0, getWidth(), getHeight(), null);
		// objeleri çiz
		gameWorld.draw(g);
	}
}
