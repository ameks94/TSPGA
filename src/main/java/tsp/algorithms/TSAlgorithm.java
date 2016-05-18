package tsp.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import tsp.ui.CitiesPanel;
import tsp.ui.GAMainWindow;
import tsp.utils.CurrentResultForShowing;

public abstract class TSAlgorithm implements Runnable {
	
	private static final Logger logger = Logger.getLogger(TSAlgorithm.class);
	
	public static double[][] distances;
	protected List<City> cities;
	protected int iterationCount = 1;
	protected boolean needCalculate = true;
	protected GAMainWindow mainWindow;
	protected double currDistance;
	protected double lastDistance;
	protected int iterWithLastImprooving;

	private CitiesPanel outputPanel;
	private JLabel outputLabel;
	private Tour currentResult;	
	private Map<Integer, Double> distancesAtIterations;
	private DefaultListModel iterationResultArea;

	private boolean needShowResult = false;

	public TSAlgorithm(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.cities = mainWindow.getCities();
		this.outputPanel = mainWindow.getOutputPanel();
		this.outputLabel = mainWindow.getOutputLable();
		this.iterationResultArea = mainWindow.getTextArea();
		this.needShowResult = mainWindow.isDrawingNeeded();
		this.distancesAtIterations = new HashMap<>();
		initializeDistances();
	}

	protected void setResult(Tour currentResult) {
		this.currentResult = currentResult;
		this.currDistance = currentResult.getFitness();
		mainWindow.setCurrentIteration(iterationCount);
		outputLabel.setText(String.valueOf(currDistance));
		if (currDistance != lastDistance) {
			iterWithLastImprooving = iterationCount;
			this.distancesAtIterations.put(iterationCount, currDistance);
			if (needShowResult) {
				drawCurrentResult();
			}
			lastDistance = currDistance;
		}
	}
	
	public static double[][] calculateDistances(List<City> cities) {
		int size = cities.size();
		double[][] distances = new double[size][size];

		for (int i = 0; i < size; i++)// заполняю верхнюю триугольную матрицу
		{
			cities.get(i).setId(i);
			for (int j = i + 1; j < size; j++) {
				distances[i][j] = cities.get(i).distanceTo(cities.get(j));
				distances[j][i] = distances[i][j];// копируем элементы верхней
			}
		}
		
		return distances;
	}

	public void stopCalculation() {
		needCalculate = false;
		mainWindow.setCalculated(true);
	}
	
	public boolean isCalculationProcessed() {
		return needCalculate;
	}
	
	public Map<Integer, Double> getIterationDistances() {
		return distancesAtIterations;
	}

	// for GA
	protected void drawCurrentResult() {
		CurrentResultForShowing currResult = new CurrentResultForShowing();
		currResult.setTour(currentResult.copyTour());
		currResult.setDistance(currDistance);
		currResult.setIteration(iterationCount);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				outputPanel.setCurrentTour(currResult.getTour());
				outputPanel.repaint();
				logger.debug(currResult);
				iterationResultArea.addElement(currResult);
			}
		});

	}

	// for algorithms that can finish calculation
	public void drawFinalResult() {
		outputLabel.setText(String.valueOf(currentResult.getDistance()));
		outputPanel.setCurrentTour(currentResult);
		outputPanel.repaint();
	}

	// public Tour getCurrentResult() {
	// return currentResult;
	// }

	private void initializeDistances() {
		distances = calculateDistances(cities);
	}

	public class Tour {

		// Holds our tour of cities
		private List<City> tourCities = null;
		// Cache
		private double fitness = 0;
		private int distance = 0;

		// Constructs a blank tour
		public Tour(int tourSize) {
			tourCities = new ArrayList<City>();
			for (int i = 0; i < tourSize; i++) {
				tourCities.add(null);
			}

		}

		public Tour(List<City> tour) {
			this.tourCities = tour;
		}

		public List<City> getCitiesPath() {
			return tourCities;
		}

		// Creates a random individual
		public void generateIndividual(List<City> allCities) {
			// Loop through all our destination cities and add them to our tour
			for (int cityIndex = 0; cityIndex < allCities.size(); cityIndex++) {
				setCity(cityIndex, allCities.get(cityIndex));
			}
			// Randomly reorder the tour
			Collections.shuffle(this.tourCities);
		}

		// Gets a city from the tour
		public City getCity(int tourPosition) {
			return tourCities.get(tourPosition);
		}

		// Sets a city in a certain position within a tour
		public void setCity(int tourPosition, City city) {
			tourCities.set(tourPosition, city);
			// If the tours been altered we need to reset the fitness and
			// distance
			fitness = 0;
			distance = 0;
		}

		// Gets the tours fitness
		public double getFitness() {
			if (fitness == 0) {
				fitness = getDistance();
			}
			return fitness;
		}

		// Gets the total distance of the tour
		public int getDistance() {
			if (distance == 0) {
				for (int i = 0; i < tourSize() - 1; i++) {
					distance += distances[getCity(i).id][getCity(i + 1).id];
				}
				distance += distances[getCity(tourSize() - 1).id][getCity(0).id];
			}
			return distance;
		}

		// Get number of cities on our tour
		public int tourSize() {
			return tourCities.size();
		}

		// Check if the tour contains a city
		public boolean containsCity(City city) {
			return tourCities.contains(city);
		}

		public Tour copyTour() {
			List<City> newCities = new ArrayList<>();
			for (City city : this.tourCities) {
				newCities.add(new City(city.x, city.y, city.id));
			}
			return new Tour(newCities);
		}

		@Override
		public String toString() {
			String geneString = "";
			for (int i = 0; i < tourSize(); i++) {
				geneString += getCity(i).id + " -> ";
			}
			geneString += getCity(0).id;
			return geneString;
		}
	}
}
