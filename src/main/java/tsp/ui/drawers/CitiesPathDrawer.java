package tsp.ui.drawers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
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
//		g.drawRect(fromCity.getX() - pointWidth / 2, fromCity.getY() - pointWidth / 2, pointWidth, pointWidth);
//		g.drawLine(fromCity.getX(), fromCity.getY(), toCity.getX(), toCity.getY());
		drawLine(g, fromCity, toCity);
	}
	
	private void drawLine(Graphics g1, City c1, City c2) {
		 Graphics2D g = (Graphics2D) g1.create();

		 int ARR_SIZE = 5;
         double dx = c2.getX() - c1.getX(), dy = c2.getY() - c1.getY();
         double angle = Math.atan2(dy, dx);
         int len = (int) Math.sqrt(dx*dx + dy*dy);
         AffineTransform at = AffineTransform.getTranslateInstance(c1.getX(), c1.getY());
         at.concatenate(AffineTransform.getRotateInstance(angle));
         g.transform(at);

         // Draw horizontal arrow starting in (0, 0)
         g.drawLine(0, 0, len, 0);
         g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
                       new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
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
