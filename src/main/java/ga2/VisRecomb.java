package ga2;
/**
 * This class contains information about the recombination of two
 * individuals.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class VisRecomb {
/*
 * cointains information about the gen (which parent, crossoverarea ...)
 */
	private int[] info;
/*
 * cointains in which step the gen was set in the crossover
 */
	private int[] step;
/*
 * start of the crossoverarea 
 */
	private int startco;
/*
 * end of the crossoverarea
 */
	private int endco;
/*
 * number of towns
 */
	private int towns;
/*
 * how much steps was used in the crossover
 */
	private int maxstep;
/*
 * used when ERX and ERX with Heuristic to store the edge list
 */
	private int[][] edges;

/**
 * This constructor creates an object and initializes its fields
 * @param   towns number of towns
 */
public VisRecomb(int towns) {
	int i;
	startco = -1;
	this.towns=towns;
	endco = -1;
	info = new int[towns];
	step = new int[towns];
	maxstep = -1;
	edges = null;
	for(i=0;i<towns;i++) {
		//gen from second parent in the first step, used for pmx
		info[i]=2;
		step[i]=0;
	}
}

/**
 * makes a copy from the parameter edge list and stores it only for the
 * visualisation of the ERX and ERX with heuristic
 * @param   a edge list
 */
public void setEdges(int[][] a) {
	int i,j;
	int[] help;

	edges= new int[towns][6];
	for(i=0;i<towns;i++) {
		help= a[i];
			j=0;
			while(help[j]!=-1) {
				edges[i][j]=help[j];
				j++;
			}
		edges[i][j]=help[j];
	}	
}

/**
 * gets the edge list for the visualisation of the Erx and Erx with Heuristic
 * @return	edge list
 */
public int[][] getEdges() {
	return edges;
}

/**
 * sets the maximum step value of the crossover
 * @param   a max step value
 */
public void setMaxStep(int a) {
	maxstep=a;
}

/**
 * gets the maximum step value of the crossover
 * @return	max step value
 */
public int  getMaxStep() {
	return maxstep;
}

/**
 * stores the border of the crossover area
 * @param   a begin
 * @param   b end
 */
public void setCoArea(int a, int b) {
	startco = a;
	endco = b;
}

/**
 * stores the step value when this gen is set in the crossover
 * @param   a position in the list
 * @param   b step value
 */
public void setStep(int a, int b) {
	step[a] = b;
}

/**
 * stores information about a gen
 * @param   a position in the list
 * @param   b information about the gen
 */
public void setInfo(int a, int b) {
	info[a] = b;
}

/**
 * gets the start of the crossover area
 * @return	start of the crossover area
 */
public int getCoStart() {
	return startco;
}

/**
 * gets the end of the crossover area
 * @return	end of the crossover area
 */
public int getCoEnd() {
	return endco;
}

/**
 * gets the step value when this gen was set in the crossover
 * @param   a position of the gen
 * @return	the step when the gen was set in the crossover
 */
public int getStep(int a) {
	return step[a];
}

/**
 * gets the stored information about this gen
 * @param   a position of the gen
 * @return	the information about the gen set in the crossover
 */
public int getInfo(int a) {
	return info[a];
}
	
/**
 * creates a string which represents this object
 * @return  String represent the data of this object
 */
public String toString() {
	int i;
    String s = "step: ";
    for(i=0;i<towns;i++) {
        s = s + step[i];
    }
    s = s + "info: ";
    for(i=0;i<towns;i++) {
        s = s + info[i];
    }
    s = s + "maxstep: " + maxstep+ "\n";
    return s;
}


}

