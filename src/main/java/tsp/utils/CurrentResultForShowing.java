package tsp.utils;

import tsp.algorithms.TSAlgorithm.Tour;

public class CurrentResultForShowing {
	private int iteration;
	private Tour tour;
	private double distance;
	
	public CurrentResultForShowing() {
		super();
	}
	public CurrentResultForShowing(int iteration, Tour tour, double distance) {
		super();
		this.iteration = iteration;
		this.tour = tour;
		this.distance = distance;
	}
	public int getIteration() {
		return iteration;
	}
	public void setIteration(int iteration) {
		this.iteration = iteration;
	}
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "It: " + iteration + " | " + distance + " | " + tour;
	}
	
}
