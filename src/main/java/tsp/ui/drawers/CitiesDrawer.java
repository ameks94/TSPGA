package tsp.ui.drawers;

import java.awt.Graphics;
import java.util.List;

import tsp.algorithms.City;
import tsp.ui.DrawerStrategy;

public class CitiesDrawer extends DrawerStrategy{

	public CitiesDrawer() {
	}

	@Override
	public void paintComponent(Graphics g, List<City> cities) {
		// If user has chosen a point, paint a small dot on top.
		if (areCitiesValid(cities)) {
			for (City c : cities) {
				g.drawRect(c.getX() - pointWidth / 2, c.getY() - pointWidth / 2, pointWidth, pointWidth);;
			}
		}
	}
}
