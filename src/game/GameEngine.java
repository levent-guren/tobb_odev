package game;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameEngine {
	private GamePanel gamePanel;
	private GameWorld gameWorld;

	public GameEngine() {
		gameWorld = new GameWorld();
		gamePanel = new GamePanel(gameWorld);
	}

	public void start() {
		Assets.load();
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Plants vs Zombies");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setContentPane(gamePanel);
			frame.pack();

			// Pencereyi ekrana ortala
			frame.setLocationRelativeTo(null);

			frame.setIconImage(Assets.appIcon);

			frame.setResizable(false);

			frame.setVisible(true);

			gamePanel.requestFocusInWindow();

			new Timer(1, this::mainLoop).start();
		});
	}

	private void mainLoop(ActionEvent e) {
		// Oyun ana döngüsü
		gamePanel.repaint();
	}

}
