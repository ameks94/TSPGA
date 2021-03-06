package tsp.algorithms.ga;

import java.util.List;
import java.util.Random;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.greedy.GreedyAlgorithm;
import tsp.controllers.MainController;
import tsp.ui.GAMainWindow;

public class GA extends TSAlgorithm {

	/* GA parameters */
	private final double mutationRate = InitialData.mutationRate;
	private final int tournamentSize = InitialData.tournamentSize;
	private final boolean elitism = true;
	private final int populationCount = InitialData.populationCount;
	private final boolean greedyInitialization = InitialData.greedyInitialization;
	private final int maxIterationCount = InitialData.maxIterationCount;
	private final int maxIterationCountWithoutImproving = InitialData.maxIterationCountWithoutImproving;

	// TODO - добавить мутацию другую + настрока их

	// +add rout direction... rows
	// +add activated- deactivated checkbox for restrictions
	// + обавить начальный город.... или рандом
	// TODO add cool windows placement -> cascade... или сохранить позиции
	// TODO добавить отдельное <i> для настройки всех параметров
	// TODO добавить мутацию вторую

	// TODO подписать матрицу стоимости... что бі біло видно
	// TODO .. добавить проверки, что и когда можно включать...
	// TODO добавить вівод стоимости
	// TODO добавить тултипі...

	public GA(MainController mainController) {
		super(mainController);
	}

	@Override
	public void run() {
		Population pop = new Population(populationCount, cities, true);
		if (greedyInitialization) {
			List<Tour> greedyTours;
			if (populationCount > cities.size()) {
				greedyTours = new GreedyAlgorithm(mainController).generateTours(cities.size());
				for (int i = 0; i < greedyTours.size(); i++) {
					pop.saveTour(i, greedyTours.get(i));
				}
			} else {
				greedyTours = new GreedyAlgorithm(mainController).generateTours(populationCount);
				for (int i = 0; i < greedyTours.size(); i++) {
					pop.saveTour(i, greedyTours.get(i));
				}
			}
		}
		// setResult(pop.getFittest());
		while (isContinueCalculation()) {
			pop = evolvePopulation(pop);
			setResult(pop.getFittest());
			iterationCount++;
		}
		stopCalculation();
		drawFinalResult();
	}

	private boolean isContinueCalculation() {
		return needCalculate && iterationCount <= maxIterationCount
				&& iterationCount - iterWithLastImprooving <= maxIterationCountWithoutImproving;
	}

	// Evolves a population over one generation
	private Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.populationSize(), cities, false);

		int elitismOffset = 0;
		if (elitism) {
			newPopulation.saveTour(0, pop.getFittest());
			elitismOffset = 1;
		}
		// Crossover population
		// Loop over the new population's size and create individuals from
		// Current population
		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			// Select parents
			Tour parent1 = tournamentSelection(pop);
			Tour parent2 = tournamentSelection(pop);
			// Crossover parents
			Tour child = crossover(parent1, parent2);
			// Add child to new population
			newPopulation.saveTour(i, child);
		}

		// Mutate the new population a bit to add some new genetic material
		for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
			mutate(newPopulation.getTour(i));
		}

		return newPopulation;
	}

	// Applies crossover to a set of parents and creates offspring
	private Tour crossover(Tour parent1, Tour parent2) {
		int tourSize = cities.size();
		// Create new child tour
		Tour child = new Tour(tourSize);

		// Get start and end sub tour positions for parent1's tour
		int startPos = (int) (Math.random() * tourSize);
		int endPos = (int) (Math.random() * tourSize);

		// Loop and add the sub tour from parent1 to our child
		for (int i = 0; i < tourSize; i++) {
			// If our start position is less than the end position
			if (startPos < endPos && i > startPos && i < endPos) {
				child.setCity(i, parent1.getCity(i));
			} // If our start position is larger
			else if (startPos > endPos) {
				if (!(i > endPos && i < startPos)) {
					child.setCity(i, parent1.getCity(i));
				}
			}
		}

		// Loop through parent2's city tour
		for (int i = 0; i < tourSize; i++) {
			// If child doesn't have the city add it
			if (!child.containsCity(parent2.getCity(i))) {
				// Loop to find a spare position in the child's tour
				for (int ii = 0; ii < tourSize; ii++) {
					// Spare position found, add city
					if (child.getCity(ii) == null) {
						child.setCity(ii, parent2.getCity(i));
						break;
					}
				}
			}
		}

		return child;
	}

	// Mutate a tour using swap mutation
	private void mutate(Tour tour) {
		// Loop through tour cities
		for (int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++) {
			// Apply mutation rate
			if (Math.random() <= mutationRate) {
				// Get a second random position in the tour
				int tourPos2 = (int) (tour.tourSize() * Math.random());

				// Get the cities at target position in tour
				City city1 = tour.getCity(tourPos1);
				City city2 = tour.getCity(tourPos2);

				// Swap them around
				tour.setCity(tourPos2, city1);
				tour.setCity(tourPos1, city2);
			}
		}
	}

	// Mutate simple, one gen with set probability
	private void mutateSimple(Tour tour) {
		if (Math.random() <= mutationRate) {
			int tourPos1 = (int) (tour.tourSize() * Math.random());
			int tourPos2 = (int) (tour.tourSize() * Math.random());

			// Get the cities at target position in tour
			City city1 = tour.getCity(tourPos1);
			City city2 = tour.getCity(tourPos2);

			// Swap them around
			tour.setCity(tourPos2, city1);
			tour.setCity(tourPos1, city2);
		}
	}

	// Selects candidate tour for crossover
	private Tour tournamentSelection(Population pop) {
		// Create a tournament population
		Population tournament = new Population(tournamentSize, cities, false);
		// For each place in the tournament get a random candidate tour and
		// add it
		for (int i = 0; i < tournament.populationSize(); i++) {
			int randomId = (int) (Math.random() * pop.populationSize());
			tournament.saveTour(i, pop.getTour(randomId));
		}
		// Get the fittest tour
		Tour fittest = tournament.getFittest();
		return fittest;
	}

	public class Population {

		// Holds population of tours
		Tour[] tours;

		boolean fittestCalculated = false;
		Tour fittestCash;

		// Construct a population
		public Population(int populationSize, List<City> cities, boolean initialise) {
			tours = new Tour[populationSize];
			// If we need to initialise a population of tours do so
			if (initialise) {

				// Loop and create individuals
				for (int i = 0; i < populationSize(); i++) {
					Tour newTour = new Tour(cities.size());
					newTour.generateIndividual(cities);
					saveTour(i, newTour);
				}
			}
			fittestCalculated = false;
		}

		// Saves a tour
		public void saveTour(int index, Tour tour) {
			tours[index] = tour;
			fittestCalculated = false;
		}

		// Gets a tour from population
		public Tour getTour(int index) {
			return tours[index];
		}

		// TODO брать лучший по... сначало первый критерий... потом второй...
		// потом третий...
		// как сортировка по 3 значениям
		// Gets the best tour in the population
		public Tour getFittest() {
			if (fittestCalculated) {
				return fittestCash;
			}
			Tour fittest = tours[0];

			if (InitialData.considerCostCriteria) {
				// Loop through individuals to find fittest
				for (int i = 1; i < populationSize(); i++) {
					// real differences
					double diffDistances = fittest.getFitnessDistance() - getTour(i).getFitnessDistance();
					double diffCosts = fittest.getFitnessCosts() - getTour(i).getFitnessCosts();

					// calculate weight of every difference...
					double weightDist = Math.abs(diffDistances / InitialData.distanceAllowRange);
					double weightCosts = Math.abs(diffCosts / InitialData.costAllowRange);

					if (weightDist - weightCosts >= 0) {
						if (diffDistances > 0) {
							fittest = getTour(i);
						}
					} else {
						if (diffCosts > 0) {
							fittest = getTour(i);
						}
					}
				}
			} else {
				// Loop through individuals to find fittest
				for (int i = 1; i < populationSize(); i++) {
					double diffDistances = fittest.getFitnessDistance() - getTour(i).getFitnessDistance();
					if (diffDistances > 0) {
						fittest = getTour(i);
					}
				}
			}
			fittestCash = fittest;
			fittestCalculated = true;
			return fittest;
		}

		// Gets population size
		public int populationSize() {
			return tours.length;
		}
	}
}
