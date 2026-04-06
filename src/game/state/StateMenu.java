package game.state;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import game.Assets;
import game.FileOperations;
import game.GameEngine;
import game.GamePanel;
import game.Timer2;

public class StateMenu extends GameState {
	private String menuStart = "Start";
	private String menuSave = "Save";
	private String menuLoad = "Load";
	private String menuExit = "Exit";
	private Rectangle rectStart;
	private Rectangle rectSave;
	private Rectangle rectLoad;
	private Rectangle rectExit;
	private String selectedMenu;

	public StateMenu() {
		if (GameEngine.getInstance() != null) {
			Timer2 timer2 = GameEngine.getGameWorld().getTimer2();
			if (timer2 != null) {
				timer2.pause();
			}
		}
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
		int boxW = 200;
		// int fontHeight = fm.getHeight()
		int boxH = 320;

		// ortala
		int boxX = (panelW - boxW) / 2;
		int boxY = (panelH - boxH) / 2;

		// yarı saydam arka plan
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRoundRect(boxX, boxY, boxW, boxH, 32, 32);

		// menü seçenekleri
		// koda yazılan string objeleri her seferinde yaratılmaz. java derleyicisinin
		// optimizasyonu ile sadece tek instance olarak kullanılırlar.
		if (GameEngine.getGameWorld().isPlaying()) {
			menuStart = "Continue";
		} else {
			menuStart = "Start";
		}

		// X koordinatı: Panel merkezinden yazının genişliğinin yarısını çıkarıyoruz
		int startX = (panelW - fm.stringWidth(menuStart)) / 2;
		int saveX = (panelW - fm.stringWidth(menuSave)) / 2;
		int loadX = (panelW - fm.stringWidth(menuLoad)) / 2;
		int exitX = (panelW - fm.stringWidth(menuExit)) / 2;
		int startY = boxY + 70;
		int saveY = startY + 70;
		int loadY = saveY + 70;
		int exitY = loadY + 70;

		g.setColor(menuStart.equals(selectedMenu) ? Color.white : new Color(1, 1, 1, 0.3f));
		g.drawString(menuStart, startX, startY);

		g.setColor(menuSave.equals(selectedMenu) ? Color.white : new Color(1, 1, 1, 0.3f));
		g.drawString(menuSave, saveX, saveY);

		g.setColor(menuLoad.equals(selectedMenu) ? Color.white : new Color(1, 1, 1, 0.3f));
		g.drawString(menuLoad, loadX, loadY);

		g.setColor(menuExit.equals(selectedMenu) ? Color.white : new Color(1, 1, 1, 0.3f));
		g.drawString(menuExit, exitX, exitY);

		// menü seçeneklerinin rectangle objelerinin hesaplanması
		if (rectStart == null) {
			int startWidth = fm.stringWidth(menuStart);
			int saveWidth = fm.stringWidth(menuSave);
			int loadWidth = fm.stringWidth(menuLoad);
			int exitWidth = fm.stringWidth(menuExit);
			// -6 font yüksekliğinde alta kayabilen karakterler de oluğu için ona göre
			// hesaplıyor. Bizde öyle karakter olmadığından tam ortalaması için bu değeri
			// çıkarıyoruz.
			int fontHeight = fm.getHeight() - 6;
			int fontAscent = fm.getAscent();

			int marginX = 10;
			int marginY = 10;
			rectStart = new Rectangle(startX - marginX, startY - fontAscent - marginY, startWidth + marginX * 2,
					fontHeight + marginY * 2);
			rectSave = new Rectangle(saveX - marginX, saveY - fontAscent - marginY, saveWidth + marginX * 2,
					fontHeight + marginY * 2);
			rectLoad = new Rectangle(loadX - marginX, loadY - fontAscent - marginY, loadWidth + marginX * 2,
					fontHeight + marginY * 2);
			rectExit = new Rectangle(exitX - marginX, exitY - fontAscent - marginY, exitWidth + marginX * 2,
					fontHeight + marginY * 2);

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (menuStart.equals(selectedMenu)) {
			// start seçildi
			GameEngine.getInstance().setState(new StatePlay());
			GameEngine.getGameWorld().setPlaying(true);
			GameEngine.getGameWorld().start();
		} else if (menuSave.equals(selectedMenu)) {
			// save seçildi
			new FileOperations().save();
			GameEngine.getGameWorld().setPlaying(true);
			GameEngine.getInstance().setState(new StatePlay());
		} else if (menuLoad.equals(selectedMenu)) {
			// load seçildi
			new FileOperations().load();
			GameEngine.getGameWorld().setPlaying(true);
			// GameEngine.getInstance().setState(new StatePlay());
		} else if (menuExit.equals(selectedMenu)) {
			// exit seçildi
			System.exit(0);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (rectStart != null && rectStart.contains(e.getPoint())) {
			selectedMenu = menuStart;
		} else if (rectSave != null && rectSave.contains(e.getPoint())) {
			selectedMenu = menuSave;
		} else if (rectLoad != null && rectLoad.contains(e.getPoint())) {
			selectedMenu = menuLoad;
		} else if (rectExit != null && rectExit.contains(e.getPoint())) {
			selectedMenu = menuExit;
		} else {
			selectedMenu = null;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27 && GameEngine.getGameWorld().isPlaying()) {
			GameEngine.getInstance().setState(new StatePlay());
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

}
