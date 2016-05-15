package tsp.algorithms;

public class InitialData {
	public static double mutationRate;
	public static int tournamentSize;	
	public static int populationCount;
	
	public static boolean greedyInitialization;
	
	public static int maxIterationCount;
	public static int maxIterationCountWithoutImproving;
	public static double minPathImproving;
	
	public static boolean showDistances;
	
	static {
		tournamentSize = 10;
		populationCount = 100;
		mutationRate = 1. / populationCount;
		
		maxIterationCount = 500_000;
		maxIterationCountWithoutImproving = 100_000;
		minPathImproving = 0;
		
		greedyInitialization = false;
		
		showDistances = false;
	}
}