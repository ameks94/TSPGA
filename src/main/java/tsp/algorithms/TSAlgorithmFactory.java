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
				return "GA";
			}
		}, 
		GREEDY {
			@Override
			public String toString() {
				return "GREEDY";
			}
		},
		BRANCH_BOUND {
			@Override
			public String toString() {
				return "BRANCH_BOUND";
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
		case BRANCH_BOUND:
			return new Solver(mainController);
		}	
		return null;

	}
}
