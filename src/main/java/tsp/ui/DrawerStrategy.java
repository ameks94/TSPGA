package tsp.ui;

import java.awt.Graphics;
import java.util.List;

import tsp.algorithms.City;

public abstract class DrawerStrategy {
	protected int pointWidth = 3;

	public DrawerStrategy() {
	}

	public abstract void paintComponent(Graphics g, List<City> cities);

	protected boolean areCitiesValid(List<City> cities) {
		if (cities == null || cities.isEmpty()) {
			return false;
		}
		return true;
	}

}
