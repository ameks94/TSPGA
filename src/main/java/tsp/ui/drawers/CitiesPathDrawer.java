package tsp.ui.drawers;

import java.awt.Graphics;
import java.util.List;

import tsp.algorithms.City;
import tsp.ui.DrawerStrategy;

public class CitiesPathDrawer extends DrawerStrategy{

	public CitiesPathDrawer() {
	}

	@Override
	public void paintComponent(Graphics g, List<City> cities) {
		if (areCitiesValid(cities) && cities.size() > 1) {
			drawCitiesPath(g, cities);
		}
	}

	/**Draws path*/
	private void drawCitiesPath(Graphics g, List<City> cities) {
		City curCity = null, nextCity = null;
		int size = cities.size();
		for (int i = 0; i < size - 1; i++) {
			curCity = cities.get(i);
			nextCity = cities.get(i + 1);
			g.drawRect(curCity.getX() - pointWidth / 2, curCity.getY() - pointWidth / 2, pointWidth, pointWidth);
			g.drawLine(curCity.getX(), curCity.getY(), nextCity.getX(), nextCity.getY());
		}
		//draw from end to start
		if (nextCity != null) {
			g.drawRect(nextCity.getX() - pointWidth / 2, nextCity.getY() - pointWidth / 2, pointWidth, pointWidth);
			g.drawLine(nextCity.getX(), nextCity.getY(), cities.get(0).getX(), cities.get(0).getY());
		}
	}
}
