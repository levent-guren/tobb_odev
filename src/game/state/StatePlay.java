package game.state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.GameEngine;
import game.plants.GamePlant;

public class StatePlay extends GameState {

	@Override
	public void loop() {
		GameEngine.getGameWorld().tick();
		GameEngine.getInstance().getGamePanel().repaint();
	}

	@Override
	public void draw(Graphics2D g) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (GameEngine.getGameWorld().getSeedBank().getBoundries().contains(e.getX(), e.getY())) {
			// seed bank clicked
			GameEngine.getGameWorld().getSeedBank().mousePressed(e);
		}
		for (GamePlant plant : GameEngine.getGameWorld().getPlants()) {
			plant.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		GameEngine.getGameWorld().getSeedBank().mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GameEngine.getGameWorld().getSeedBank().mouseMoved(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		GameEngine.getGameWorld().getSeedBank().mouseDragged(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			// esc
			GameEngine.getInstance().setState(new StateMenu());
			// System.exit(0);
		}
	}

}
