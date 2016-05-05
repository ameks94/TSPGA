package tsp.algorithms.branchbound;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.ui.forms.GAMainWindow;

/**
 * Solves the traveling salesman problem using Branch and Bound by utilizing Node's
 */
public class Solver extends TSAlgorithm{
	double best_cost;
	int[] best_path;

	/**
	 * Constructs a new Solver and initializes distances array
	 *
	 * @param cities An ArrayList of City's
	 */
	public Solver(GAMainWindow mainWindow) {
		super(mainWindow);
	}

	/**
	 * Calculates the shortest (non-repeating) path between a series of nodes
	 *
	 * @return An array with the locations of the best path
	 */
	public int[] calculate() {
		HashSet<Integer> location_set = new HashSet<Integer>(distances.length);
		for(int i = 0; i < distances.length; i++) {
			location_set.add(i);
		}

		best_cost = findGreedyCost(0, location_set, distances);

		int[] active_set = new int[distances.length];
		for(int i = 0; i < active_set.length; i++) {
			active_set[i] = i;
		}

		Node root = new Node(null, 0, distances, active_set, 0);
		traverse(root);

		return best_path;
	}

	/**
	 * Get current path cost
	 *
	 * @return The cost
	 */
	public double getCost() {
		return best_cost;
	}

	/**
	 * Find the greedy cost for a set of locations
	 *
	 * @param i The current location
	 * @param location_set Set of all remaining locations
	 * @param distances The 2D array containing point distances
	 * @return The greedy cost
	 */
	private double findGreedyCost(int i, HashSet<Integer> location_set, double[][] distances) {
		if(location_set.isEmpty()) {
			return distances[0][i];
		}

		location_set.remove(i);

		double lowest = Double.MAX_VALUE;
		int closest = 0;
		for(int location : location_set) {
			double cost = distances[i][location];
			if(cost < lowest) {
				lowest = cost;
				closest = location;
			}
		}

		return lowest + findGreedyCost(closest, location_set, distances);
	}

	/**
	 * Recursive method to go through the tree finding and pruning paths
	 *
	 * @param parent The root/parent node
	 */
	private void traverse(Node parent) {
		Node[] children = parent.generateChildren();

		for(Node child : children) {
			if(child.isTerminal()) {
				double cost = child.getPathCost();
				if(cost < best_cost) {
					best_cost = cost;
					best_path = child.getPath();
				}
			}
			else if(child.getLowerBound() <= best_cost) {
				traverse(child);
			}
		}
	}
	
	public void run() {
		int[] path = calculate();
		List<City> result = new ArrayList<City>();
		for (int i = 0; i < cities.size(); i++) {
			int currCityNumber = path[i];
			result.add(cities.get(currCityNumber));
		}
		Tour opt = new Tour(result);
		setResult(opt);
		drawFinalResult();
	}

//	@Override
//	public Tour calculateTour() {
//		int[] path = calculate();
//		List<City> result = new ArrayList<City>();
//		for (int i = 0; i < cities.size(); i++) {
//			int currCityNumber = path[i];
//			result.add(cities.get(currCityNumber));
//		}
//		return new Tour(result);
//	}
}
