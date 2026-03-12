package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -550237471039114261L;
	private GameEngine gameEngine;
	private GameWorld gameWorld;
	private BufferedImage background;

	private boolean drawFps;

	GamePanel(GameEngine gameEngine, GameWorld gameWorld) {
		this.gameEngine = gameEngine;
		this.gameWorld = gameWorld;
		// Oyun ekranı boyutu
		setPreferredSize(new Dimension(Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT));
		setFocusable(true);
		try {
			background = ImageIO.read(getClass().getResource("/resources/frontyard.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				gameEngine.keyPressed(e);
				if (e.getKeyChar() == 'f') {
					drawFps = !drawFps;
				}
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				gameEngine.mousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				gameEngine.mouseReleased(e);
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				gameEngine.mouseMoved(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				gameEngine.mouseDragged(e);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Yazılar için kenar yumuşatma
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Şekiller ve genel kenarlar için
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Resim kalitesi için
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// background
		g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
		gameWorld.draw(g);

		// varsa menu
		gameEngine.getState().draw(g2d);

		gameEngine.addFrameCount();
		if (drawFps) {
			drawFPS(g, gameEngine.getFps());
		}

	}

	void drawFPS(Graphics g, int fps) {
		String text = "FPS: " + fps;
		g.setFont(Assets.fontBebas);

		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(text);
		int h = fm.getHeight();

		int padding = 5;
		int x = getWidth() - w - 10;
		int y = h;

		// yarı saydam arka plan
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRoundRect(x - padding, y - h + padding, w + padding * 2, h, 8, 8);
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}

}
