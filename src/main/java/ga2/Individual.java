package ga2;
/**
 * This class defines an individual for the GA. Any individual may contain
 * an object (VisRecomb.java) which stores information about its parents.
 * It contains also two mutation markers to make a visualisation possible.
 * This class has also some functions to visualize the data of an 
 * individual in different ways. A random initializer is implemented.
 * 
 * coding:   h.sarg
 * modified: 01/98
 */
public class Individual {
/**
 * fitness of the individual
 */
	private double fitness;
/**
 * array which representates the genstring
 */
	private int[] indiv;
/**
 * length of the genstring
 */
	private int size;
/**
 * marker for the best individual in the last population (elitism: true)
 */
	private boolean best;
/**
 * mutation marker
 */
	private int mut1;
/**
 * mutation marker
 */
	private int mut2;
/**
 * number of the individual in its population
 */
	private int nr;
/**
 * information about the recombinated parents
 */
	private VisRecomb visrecomb;

/**
 * This constructor creates an individual and initializes its fields.
 * @param	size length of the genstring (also: number of towns)
 */
public Individual(int size) {
	this.size = size;
	indiv = new int[size];
	fitness = -1;	
	best = false;
	mut1 = -1;
	mut2 = -1;
	nr = -1;
	visrecomb = null;

	//all gens becomes an impossible value
	for(int i=0;i<size;i++) {
		indiv[i] = -1;
	}
}

/**
 * stores all information about the recombination which is only used
 * when the user wants to simulate the functionality of the
 * crossover method, creates this object
 */
public void setVisRecomb() {
	visrecomb = new VisRecomb(size);
}

/**
 * gets the object which contains the information about the parents
 * @return	the object
 */
public VisRecomb getVisRecomb() {
	return visrecomb;
}

/**
 * deletes the information about the parents of one individual
 */
public void delVisRecomb() {
	visrecomb=null;
}

/**
 * random initialisation of the individual
 */
public void initialize() {
	int rand;
	
	//random position of the gen and not random gen!
	for(int i=0;i<size;i++) {
		rand = MyRandom.intRandom(0, size-1);
		while(indiv[rand] > -1) {
			rand = MyRandom.intRandom(0, size-1);
		}
		indiv[rand] = i;
	}
}

/**
 * sets the values for the mutation markers
 * @param a first position of mutation
 * @param b second position of mutation
 */
public void setMut(int a, int b) {
	mut1 = a;
	mut2 = b;
}

/**
 * gets the mutation marker
 * @return	position of mutation
 */
public int getMut1() {
	return mut1;
}

/**
 * gets the  mutation marker
 * @return	position of mutation
 */
public int getMut2() {
	return mut2;
}

/**
 * gets the number of this individual
 * @return	the number of individual
 */
public int getNr() {
	return this.nr;
}

/**
 * sets the number of individual
 * @param	a number of individual
 */
public void setNr(int a) {
	this.nr = a;
}

/**
 * gets the specified gen
 * @param	gen number of gen 
 * @return	value of gen
 */
public int getGen(int gen) {
	return indiv[gen];
}

/**
 * sets the specified gen
 * @param	gen which gen
 * @param	value value to set
 */
public void setGen(int gen, int value) {
	indiv[gen] = value;
}

/**
 * tests this individual if it is the best of the last generation 
 * (elitism: true)
 * @return	true if it is the best, otherwise false
 */
public boolean isBest() {
	return best;
}

/**
 * sets the best value
 * @param	a true if it is the best, otherwise false
 */
public void setBest(boolean a) {
	best=a;
}

/**
 * sets the fitness of this individual
 * @param	fit fitness
 */
public void setFitness(double fit) {
	this.fitness = fit;
}

/**
 * gets the fitness of this individual
 * @return	fitness
 */
public double getFitness() {
	return this.fitness;
}

/**
 * makes a copy of the necessary values of one individual
 * @param	a individual to copy
 */
public void copyIndiv(Individual a) {
	for(int i=0;i<size;i++) {
		this.indiv[i] = a.indiv[i];
	}
}

/**
 * creates a string which represents the gen array
 * @param	fit string should contain fitness or not
 * @return	String
 */
public String toPopString(boolean fit) {
	String s = "";
	String sh ="";
	int i=0;
	
	//can not show the trip if size is greater than 26
	if(size>26) {
		s="too long";
	}
	else {
		//can not show number of individual
		if(nr!=-1) {
			s= s + nr +" ";
			if(nr<10) {
				s = "0"+s;
			}
		}
		String helpstring="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(i=0;i<size;i++) {
       		sh = sh + helpstring.substring(indiv[i],indiv[i]+1);
    	}

		//guarantee a minimum size of the genstring
		while(i<13) {
			sh= sh+" ";
			i++;
		}
		s=s+sh;

		//concatenate the fitness to this individual string
		if(fit) {
			s = s + " "+ trimFit();
		}
	}
	return s;
}

/**
 * create a string which represents the gen array without the number
 * @return  String
 */
public String toLabelString() {
    String s = "";
    String sh ="";
    int i=0;

	//can not show the trip if size is greater than 26
    if(size>26) {
        s="too long";
    }
    else {
        String helpstring="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for(i=0;i<size;i++) {
            sh = sh + helpstring.substring(indiv[i],indiv[i]+1);
        }

		//guarantee a minimum size of the genstring
        while(i<13) {
            sh= sh+" ";
            i++;
        }
        s=s+sh;
    }
    return s;
}


/**
 * creates a string which represents the individual data (testtool)
 * @return	String
 */
public String toString() {
	String s1 = "";
	String s= "size= " + size +"\n";
	for(int i=0;i<size;i++) {
        s1 = s1 + indiv[i] +" ";
    }
	s = s + s1 +"\n" + "fitness= " +fitness + "\n";
	return s;
}

/**
 * adapts the fitness to a fixed (XXXX.XXX) formatted size
 * @return	String which represents the fitness
 */
public String trimFit() {
	int help;
	int post=3;
	String s="";
	int length =0;
	int diff=0;
	
	if(this.fitness == -1) {
		s="unknown";
	}
	else {
		//double to string
		s="" + this.fitness;
		length=s.length();

		//find the "."
		help=s.indexOf(".");

		//fix the area before the "."
		while(help<4) {
			s=" "+s;
			help++;
		}
		help++;

		//fix the area after the "."
		diff=length-help-post;
		while(diff<1) {
			s=s+"0";
			diff++;
		}
		s=s.substring(0,help+post);
	}
	return s;
}

}
