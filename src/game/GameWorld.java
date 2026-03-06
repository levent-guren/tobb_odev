package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.objects.GameObject;

public class GameWorld {
	private List<GameObject> plants = new ArrayList<>();
	private List<GameObject> bullets = new ArrayList<>();
	private List<GameObject> zombies = new ArrayList<>();

	public void draw(Graphics g) {
		// arkadan öne sırası ile
		for (int i = 0; i < plants.size(); i++) {
			plants.get(i).draw(g);
		}
		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(g);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
	}
}
