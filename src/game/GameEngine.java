package game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import game.state.GameState;
import game.state.StateLoose;
import game.state.StateMenu;
import game.state.StateWin;

public class GameEngine {
	private static GameEngine instance;
	private GamePanel gamePanel;
	private GameWorld gameWorld;
	private GameState state = new StateMenu();
	private int frames = 0;
	private long lastTime = System.currentTimeMillis();
	private int fps;

	public GameEngine() {
		instance = this;
	}

	public static GameEngine getInstance() {
		return instance;
	}

	public static GameWorld getGameWorld() {
		return instance.gameWorld;
	}

	public void setGameWorld(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void init() {
		gameWorld = new GameWorld();
		gamePanel = new GamePanel(this, gameWorld);
	}

	public int getGameAreaWidth() {
		return gamePanel.getWidth();
	}

	public void start() {
		Assets.load();
		init();
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Plants vs Zombies");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(gamePanel);
			frame.pack();

			// Pencereyi ekrana ortala
			frame.setLocationRelativeTo(null);
			try {
				Image icon = ImageIO.read(getClass().getResource("/resources/icon.png"));
				frame.setIconImage(icon);
			} catch (Exception e) {
				// e.printStackTrace();
			}

			frame.setResizable(false);

			frame.setVisible(true);
			gamePanel.requestFocusInWindow();
			new Timer(1, this::mainLoop).start();
		});
	}

	void mainLoop(ActionEvent actionEvent) {
		state.loop();
	}

	public void keyPressed(KeyEvent e) {
		// System.out.println("Pressed keycode:" + e.getKeyCode());
		state.keyPressed(e);
		// e.getKeyCode():
		// 38 up
		// 40 down
		// 39 right
		// 37 left
		// 10 enter
	}

	public void addFrameCount() {
		frames++;
		long now = System.currentTimeMillis();
		if (now - lastTime >= 1000) {
			fps = frames;
			frames = 0;
			lastTime = now;
		}
	}

	public int getFps() {
		return fps;
	}

	public void mouseReleased(MouseEvent e) {
		state.mouseReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		state.mousePressed(e);
	}

	public void mouseMoved(MouseEvent e) {
		state.mouseMoved(e);
	}

	public void mouseDragged(MouseEvent e) {
		state.mouseDragged(e);
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void gameOver(boolean win) {
		state = win ? new StateWin() : new StateLoose();
	}

}
