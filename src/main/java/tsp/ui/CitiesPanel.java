package tsp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.apache.log4j.Logger;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm.Tour;
import tsp.ui.DrawerFactory.DrawerType;

public class CitiesPanel extends JPanel implements MouseInputListener {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CitiesPanel.class);

	Dimension preferredSize = new Dimension(400, 75);

	private boolean showCalculation = false;

	/**List of points, that user was set by mouse*/
	private List<City> cities = new ArrayList<City>();
	
	private Tour currentTour;
	private Tour lastTour;
	/***/
	private DrawerStrategy drawerStrategy;

	public CitiesPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setBackground(Color.WHITE);
		setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
	}

	  public void setRoutine(Tour currentTour) {
	        this.currentTour = currentTour;
	    }
	  
	@Override
	public Dimension getPreferredSize() {
		return preferredSize;
	}

	/**It rewrites panel according to cities list*/
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (showCalculation) {
//			if (lastTour != null) {
//				g.setColor(Color.red);
//				drawerStrategy.paintComponent(g, lastTour.getCitiesPath());
//			}
			if (currentTour != null) {
				g.setColor(Color.black);
				drawerStrategy.paintComponent(g, currentTour.getCitiesPath());
				lastTour = currentTour;
			}
		} else {
			if (isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			g.setColor(getForeground());
			drawerStrategy.paintComponent(g, cities);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		cities.add(new City(e.getX(), e.getY()));
		logger.debug(cities.size() + "  " + e.getPoint());
		repaint();
	}


	////Cities functionality
	public void clearAll() {
		cities = new ArrayList<City>();
		repaint();
	}

	// Methods required by the MouseInputListener interface.
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setDrawerStrategy(DrawerStrategy drawerStrategy) {
		this.drawerStrategy = drawerStrategy;
		lastTour = null;
	}

	public boolean isShowCalculation() {
		return showCalculation;
	}

	public void setShowCalculation(boolean showCalculation) {
		this.showCalculation = showCalculation;
	}



	//	public void setLogger(LoggerUI logger) {
	//		this.logger = logger;
	//	}

}
