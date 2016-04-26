package tsp.utils;

import java.util.List;

public class Result {
	private double averageTimeSec;
	/**Distances for experiments*/
	private List<Integer> distances;
	public Result(double averageTimeSec, List<Integer> distances) {
		super();
		this.averageTimeSec = averageTimeSec;
		this.distances = distances;
	}
	public double getAverageTimeSec() {
		return averageTimeSec;
	}
	public void setAverageTimeSec(double averageTimeSec) {
		this.averageTimeSec = averageTimeSec;
	}
	public List<Integer> getDistances() {
		return distances;
	}
	public void setDistances(List<Integer> distances) {
		this.distances = distances;
	}

}
