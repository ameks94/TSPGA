package tsp.algorithms;

import tsp.algorithms.branchbound.Solver;
import tsp.algorithms.ga.GA;
import tsp.algorithms.greedy.GreedyAlgorithm;
import tsp.controllers.MainController;
import tsp.ui.GAMainWindow;

public class TSAlgorithmFactory {
	public enum AlgorithmType {
		GA {
			@Override
			public String toString() {
				return "ГА";
			}
		}, 
		GREEDY {
			@Override
			public String toString() {
				return "Жадібний";
			}
		}

	}

	//use getShape method to get object of type shape 
	public static TSAlgorithm getTSAlgorithm(AlgorithmType algorithmType, MainController mainController){
		switch (algorithmType) {
		case GA:
			return new GA(mainController);
		case GREEDY:
			return new GreedyAlgorithm(mainController);
		}	
		return null;

	}
}
