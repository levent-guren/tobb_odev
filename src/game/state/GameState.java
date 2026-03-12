package game.state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState {
	public abstract void loop();

	public abstract void draw(Graphics2D g);

	public abstract void mousePressed(MouseEvent e);

	public abstract void mouseReleased(MouseEvent e);

	public abstract void mouseMoved(MouseEvent e);

	public abstract void mouseDragged(MouseEvent e);

	public abstract void keyPressed(KeyEvent e);
}
