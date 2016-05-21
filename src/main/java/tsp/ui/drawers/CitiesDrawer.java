package tsp.ui.drawers;

import java.awt.Graphics;
import java.util.List;

import tsp.algorithms.City;

public class CitiesDrawer extends DrawerStrategy{

	public CitiesDrawer() {
	}

	@Override
	public void paintComponent(Graphics g, List<City> cities) {
		// If user has chosen a point, paint a small dot on top.
		if (areCitiesValid(cities)) {
			for (City c : cities) {
				g.drawString(String.valueOf(c.getId() + 1), c.getX(), c.getY() - 10);
				g.drawRect(c.getX() - pointWidth / 2, c.getY() - pointWidth / 2, pointWidth, pointWidth);
			}
		}
	}
}
