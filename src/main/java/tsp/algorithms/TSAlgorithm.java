package tsp.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import tsp.ui.CitiesPanel;
import tsp.ui.GAMainWindow;
import tsp.utils.CurrentResultForShowing;

public abstract class TSAlgorithm implements Runnable {
	private static final Logger logger = Logger.getLogger(TSAlgorithm.class);
	protected List<City> cities;
	protected double[][] distances;

	private CitiesPanel outputPanel;
	private JLabel outputLabel;
	// private Tour currentResult;
	private double lastDistance;
	private GAMainWindow mainWindow;
	private DefaultListModel textArea;
	
	private int iterationCount = 1;

	public TSAlgorithm(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		this.cities = mainWindow.getCities();
		initializeDistances();
		this.outputPanel = mainWindow.getOutputPanel();
		this.outputLabel = mainWindow.getOutputLable();
		this.lastDistance = new Tour(cities).getDistance();
		this.textArea = mainWindow.getTextArea();
	}

	// for GA
	protected void drawCurrentResult(Tour currTour) {
		double currDist = currTour.getFitness();
		if (currDist != lastDistance) {
			Tour copyTour = currTour.copyTour();
			CurrentResultForShowing currResult = new CurrentResultForShowing();
			currResult.setTour(copyTour);
			currResult.setDistance(currDist);
			currResult.setIteration(iterationCount);
			logger.debug(currResult);
			textArea.addElement(currResult);
			outputPanel.setRoutine(currResult.getTour());
			outputLabel.setText(String.valueOf(currResult.getDistance()));
			outputPanel.repaint();
		}
		lastDistance = currDist;
		iterationCount++;
	}
	
	protected void showElitGenome(Tour elite) {
		
	}

	// for algorithms that can finish calculation
	protected void drawFinalResult(Tour currTour) {
		outputLabel.setText(String.valueOf(currTour.getDistance()));
		outputPanel.setRoutine(currTour);
		outputPanel.repaint();
		setCalculated();
	}

	public void setCalculated() {
		mainWindow.setCalculated(true);
	}

	// public Tour getCurrentResult() {
	// return currentResult;
	// }

	private void initializeDistances() {
		int size = cities.size();
		this.distances = new double[size][size];

		for (int i = 0; i < size; i++)// заполняю верхнюю триугольную матрицу
		{
			cities.get(i).setId(i);
			for (int j = i + 1; j < size; j++) {
				distances[i][j] = cities.get(i).distanceTo(cities.get(j));
				distances[j][i] = distances[i][j];// копируем элементы верхней
			}
		}
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

		public void addCityToEnd(City city) {
			this.tourCities.add(city);
			// If the tours been altered we need to reset the fitness and
			// distance
			fitness = 0;
			distance = 0;
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
			// if (distance == 0) {
			// int tourDistance = 0;
			// // Loop through our tour's cities
			// for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
			// // Get city we're travelling from
			// City fromCity = getCity(cityIndex);
			// // City we're travelling to
			// City destinationCity;
			// // Check we're not on our tour's last city, if we are set our
			// // tour's final destination city to our starting city
			// if (cityIndex + 1 < tourSize()) {
			// destinationCity = getCity(cityIndex + 1);
			// } else {
			// destinationCity = getCity(0);
			// }
			// // Get the distance between the two cities
			// tourDistance += fromCity.distanceTo(destinationCity);
			// }
			// distance = tourDistance;
			// }
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
