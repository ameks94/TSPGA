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
			drawElements(g, curCity, nextCity);
		}
		// draw from end to start
		if (nextCity != null) {
			drawElements(g, nextCity, cities.get(0));
		}
	}
	
	private void drawElements(Graphics g, City fromCity, City toCity) {
		if (InitialData.showDistances) {
			g.setColor(Color.BLUE);
			Point centrePoint = getCentrePoint(fromCity, toCity);
			g.drawString(String.valueOf((int) TSAlgorithm.distances[fromCity.getId()][toCity.getId()]),
					centrePoint.x, centrePoint.y - 10);
			g.setColor(Color.black);
		}
		if (InitialData.showTimeCriteria && InitialData.considerTimeCriteria) {
			g.setColor(Color.DARK_GRAY);
			Point centrePoint = getCentrePoint(fromCity, toCity);
			g.drawString(String.valueOf((int) InitialData.times[fromCity.getId()][toCity.getId()]),
					centrePoint.x, centrePoint.y);
			g.setColor(Color.black);
		}
		if (InitialData.showCostCriteria && InitialData.considerCostCriteria) {
			g.setColor(Color.RED);
			Point centrePoint = getCentrePoint(fromCity, toCity);
			g.drawString(String.valueOf((int) InitialData.costs[fromCity.getId()][toCity.getId()]),
					centrePoint.x, centrePoint.y + 10);
			g.setColor(Color.black);
		}
		
		g.drawString(String.valueOf(fromCity.getId()), fromCity.getX(), fromCity.getY() - 10);
		g.drawRect(fromCity.getX() - pointWidth / 2, fromCity.getY() - pointWidth / 2, pointWidth, pointWidth);
		g.drawLine(fromCity.getX(), fromCity.getY(), toCity.getX(), toCity.getY());
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
