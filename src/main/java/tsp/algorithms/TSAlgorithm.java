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

import com.google.common.collect.Iterables;

import tsp.controllers.MainController;
import tsp.ui.CitiesPanel;
import tsp.ui.GAMainWindow;
import tsp.utils.CurrentResultForShowing;

public abstract class TSAlgorithm implements Runnable {

	private static final Logger logger = Logger.getLogger(TSAlgorithm.class);

	public static double[][] distances;
	protected List<City> cities;
	protected int iterationCount = 1;
	protected boolean needCalculate = true;
	protected MainController mainController;
	protected GAMainWindow mainWindow;
	protected double currDistance;
	protected double lastDistance;
	protected int iterWithLastImprooving;

	private CitiesPanel outputPanel;
	private JLabel outputLabel;
	private Tour currentResult;
	private Map<Integer, Double> distancesAtIterations;
	private DefaultListModel iterationResultArea;
	protected int startCityIndex = 0;

	private boolean needShowResult = false;

	public TSAlgorithm(MainController mainController) {
		this.mainController = mainController;
		this.mainWindow = mainController.getMainWindow();
		this.startCityIndex = mainWindow.getStartCityIndex();
		this.cities = mainWindow.getCities();
		this.outputPanel = mainWindow.getOutputPanel();
		this.outputLabel = mainWindow.getOutputLable();
		this.iterationResultArea = mainController.resultIterationArea();
		this.needShowResult = mainWindow.isDrawingNeeded();
		this.distancesAtIterations = new HashMap<>();
		initializeDistances();
	}

	protected void setResult(Tour currentResult) {
		this.currentResult = currentResult;
		this.currDistance = currentResult.getFitnessDistance();
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
		currResult.setTour(reorderTourDependingStartCity(currentResult.copyTour()));
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
	
	private Tour reorderTourDependingStartCity(Tour tour) {
		List<City> cities = tour.tourCities;
		int size = cities.size();
		int indexOfCity = Iterables.indexOf(cities, city -> city.id == startCityIndex);
		Tour reorderedTour = new Tour(size);
		for (int i = 0; i < size; i++) {
			reorderedTour.setCity(i, cities.get((indexOfCity++) % size));
		}
		return reorderedTour;
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
		private double fitnessDistance = 0;
		private int distance = 0;

		private double fitnessTime = 0;
		private int time = 0;

		private double fitnessCost = 0;
		private int cost = 0;

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
			fitnessDistance = 0;
			distance = 0;
			fitnessCost = 0;
			cost = 0;
			fitnessTime = 0;
			time = 0;
		}

		// Gets the tours fitness
		public double getFitnessDistance() {
			if (fitnessDistance == 0) {
				fitnessDistance = getDistance();
			}
			return fitnessDistance;
		}

		// Gets the tours fitness
		public double getFitnessTimes() {
			if (fitnessTime == 0) {
				fitnessTime = getTimes();
			}
			return fitnessTime;
		}

		// Gets the tours fitness
		public double getFitnessCosts() {
			if (fitnessCost == 0) {
				fitnessCost = getCosts();
			}
			return fitnessCost;
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

		// Gets the total getTimes of the tour
		public int getTimes() {
			if (time == 0) {
				for (int i = 0; i < tourSize() - 1; i++) {
					time += InitialData.times[getCity(i).id][getCity(i + 1).id];
				}
				time += InitialData.times[getCity(tourSize() - 1).id][getCity(0).id];
			}
			return time;
		}

		// Gets the total getCosts of the tour
		public int getCosts() {
			if (cost == 0) {
				for (int i = 0; i < tourSize() - 1; i++) {
					cost += InitialData.costs[getCity(i).id][getCity(i + 1).id];
				}
				cost += InitialData.costs[getCity(tourSize() - 1).id][getCity(0).id];
			}
			return cost;
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
				geneString += getCity(i).id + 1 + " -> ";
			}
			geneString += getCity(0).id + 1;
			return geneString;
		}
	}
}
