package tsp.algorithms;

public class TSAlgorithmStrategy {

	private TSAlgorithm tsAlgorithm;

	public TSAlgorithmStrategy(TSAlgorithm tsAlgorithm) {
		this.tsAlgorithm = tsAlgorithm;
	}

	public TSAlgorithm getTSAlgorithm() {
		return tsAlgorithm;
	}

	public void setTSAlgorithm(TSAlgorithm tsAlgorithm) {
		this.tsAlgorithm = tsAlgorithm;
	}
}
