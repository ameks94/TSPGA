package tsp.ui.drawers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithm;

public class CitiesPathDrawer extends DrawerStrategy {

	public CitiesPathDrawer() {
	}

	@Override
	public void paintComponent(Graphics g, List<City> cities) {
		if (areCitiesValid(cities) && cities.size() > 1) {
			drawCitiesPath(g, cities);
		}
	}

	/** Draws path */
	private void drawCitiesPath(Graphics g, List<City> cities) {
		City curCity = null, nextCity = null;
		int size = cities.size();
		for (int i = 0; i < size - 1; i++) {
			curCity = cities.get(i);
			nextCity = cities.get(i + 1);
			if (InitialData.showDistances) {
				drawDistanceValue(g, curCity, nextCity);
			}
			g.drawString(String.valueOf(curCity.getId()), curCity.getX(), curCity.getY() - 10);
			g.drawRect(curCity.getX() - pointWidth / 2, curCity.getY() - pointWidth / 2, pointWidth, pointWidth);
			g.drawLine(curCity.getX(), curCity.getY(), nextCity.getX(), nextCity.getY());
		}
		// draw from end to start
		if (nextCity != null) {
			if (InitialData.showDistances) {
				drawDistanceValue(g, nextCity, cities.get(0));
			}
			g.drawString(String.valueOf(nextCity.getId()), nextCity.getX(), nextCity.getY() - 10);
			g.drawRect(nextCity.getX() - pointWidth / 2, nextCity.getY() - pointWidth / 2, pointWidth, pointWidth);
			g.drawLine(nextCity.getX(), nextCity.getY(), cities.get(0).getX(), cities.get(0).getY());
		}
	}
	
	private void drawDistanceValue(Graphics g, City fromCity, City toCity) {
		g.setColor(Color.BLUE);
		Point centrePoint = getCentrePoint(fromCity, toCity);
		g.drawString(String.valueOf((int) TSAlgorithm.distances[fromCity.getId()][toCity.getId()]),
				centrePoint.x, centrePoint.y);
		g.setColor(Color.black);
	}

	private class Point {
		int x;
		int y;

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

	}

	private Point getCentrePoint(City first, City second) {
		return new Point((first.getX() + second.getX()) / 2, (first.getY() + second.getY()) / 2);
	}
}
