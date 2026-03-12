package game.state;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.Assets;
import game.GameEngine;
import game.GamePanel;

public class StateLoose extends GameState {
	private String text = "Sorry! You lost.";
	private String menu = "Menu";
	private Rectangle rectMenu;
	private String selectedMenu;

	public StateLoose() {
		GameEngine.getGameWorld().newGame();
	}

	@Override
	public void loop() {
		GameEngine.getInstance().getGamePanel().repaint();
	}

	@Override
	public void draw(Graphics2D g) {
		GamePanel gamePanel = GameEngine.getInstance().getGamePanel();
		int panelW = gamePanel.getWidth();
		int panelH = gamePanel.getHeight();

		// arka planı hafif karartma
		g.setColor(new Color(0, 0, 0, 50));
		g.fillRect(0, 0, panelW, panelH);
		//
		g.setFont(Assets.fontBebasMenu);
		// text büyüklüğü hesaplama için
		FontMetrics fm = g.getFontMetrics();

		// menü büyüklüğü
		int boxW = 300;
		// int fontHeight = fm.getHeight()
		int boxH = 200;

		// ortala
		int boxX = (panelW - boxW) / 2;
		int boxY = (panelH - boxH) / 2;

		// yarı saydam arka plan
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRoundRect(boxX, boxY, boxW, boxH, 32, 32);
		GameEngine.getGameWorld().setPlaying(false);

		// X koordinatı: Panel merkezinden yazının genişliğinin yarısını çıkarıyoruz
		int textX = (panelW - fm.stringWidth(text)) / 2;
		int menuX = (panelW - fm.stringWidth(menu)) / 2;
		int textY = boxY + 70;
		int menuY = textY + 70;

		g.setColor(Color.red);
		g.drawString(text, textX, textY);
		g.setColor(menu.equals(selectedMenu) ? Color.white : new Color(1, 1, 1, 0.3f));
		g.drawString(menu, menuX, menuY);

		if (rectMenu == null) {
			int startWidth = fm.stringWidth(text);
			int fontHeight = fm.getHeight() - 6;
			int fontAscent = fm.getAscent();

			int marginX = 10;
			int marginY = 10;
			rectMenu = new Rectangle(menuX - marginX, menuY - fontAscent - marginY, startWidth + marginX * 2,
					fontHeight + marginY * 2);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (menu.equals(selectedMenu)) {
			// menu seçildi
			GameEngine.getInstance().setState(new StateMenu());
			GameEngine.getGameWorld().setPlaying(false);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (rectMenu != null && rectMenu.contains(e.getPoint())) {
			selectedMenu = menu;
		} else {
			selectedMenu = null;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

}
