package tsp.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.apache.log4j.Logger;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.TSAlgorithm.Tour;
import tsp.algorithms.ga.GA;
import tsp.ui.drawers.DrawerFactory;
import tsp.ui.drawers.DrawerStrategy;
import tsp.ui.drawers.DrawerFactory.DrawerType;

public class CitiesPanel extends JPanel implements MouseInputListener {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CitiesPanel.class);

	Dimension preferredSize = new Dimension(400, 75);
	
	/** List of points, that user was set by mouse */
	private List<City> cities = new ArrayList<City>();

	private List<City> currentTour;
	/***/
	private DrawerStrategy drawerStrategy;
	
//	private boolean imageWasDrawn = false;
	private BufferedImage image;
	
	private GAMainWindow mainWindow;

	public CitiesPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
//		setBackground(Color.WHITE);
		setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		try
        {
            image = javax.imageio.ImageIO.read(this.getClass().getResource("/tsp/resources/osnovp_5v_Olya.jpg"));
        }
        catch (Exception e) { 
        	logger.error(e);
        }
	}

	public void setMainWindow(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void setCurrentTour(Tour currentTour) {
		this.currentTour = currentTour.getCitiesPath();
	}

	@Override
	public Dimension getPreferredSize() {
		return preferredSize;
	}

	/** It rewrites panel according to cities list */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
//		g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
		
		if (currentTour == null) {
			currentTour = new ArrayList<>(cities);
		}
		g.setColor(Color.BLACK);
		drawerStrategy.paintComponent(g, currentTour);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		cities.add(new City(e.getX(), e.getY(), cities.size()));
		mainWindow.setCityCount(cities.size());
		mainWindow.enableCalculation();
		currentTour = null;
		repaint();
	}

	//// Cities functionality
	public void clearAll() {
		cities = new ArrayList<City>();
		currentTour = null;
		mainWindow.setCityCount(0);
		mainWindow.disableCalculation();
		repaint();
	}

	// Methods required by the MouseInputListener interface.
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// 

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// 

	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
		mainWindow.enableCalculation();
		currentTour = null;
	}

	public void setDrawerStrategy(DrawerStrategy drawerStrategy) {
		this.drawerStrategy = drawerStrategy;
	}
}
