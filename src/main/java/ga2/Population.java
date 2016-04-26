package ga2;/**
 * This class represents a population for the GA.
 * It also contains the fitness function and
 * a quicksort algorithm to sort the individuals.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class Population {
/**
 * array which holds the individuals of the population
 */
	private Individual[] pop;
/**
 * best fitness in the population
 */
	private double bestfit;
/**
 * number of the best individual
 */
	private int bestindex;
/**
 * mean fitness of the population
 */
	private double meanfit;
/**
 * population size
 */
	private int popsize;
/**
 * length of the genstring
 */
	private int size;
/**
 * Object which holds the information about the towns
 */
	private TravelData travel;

/**
 * This constructor creates a new population.
 * @param   travel object which contains the values of the towns
 * @param   popsize population size
 * @param   size number of towns
 * @param   init indicates if the population should be initialized
 */
public Population(TravelData travel, int popsize, int size, int init) {
	int i;

	this.travel = travel;
	this.popsize = popsize;
	this.size = size;
	bestfit = -1;
	bestindex = -1;
	meanfit = -1;

	pop = new Individual[popsize];
	if(init == 1) {
		for(i=0; i<popsize;i++) {
			pop[i] = new Individual(size);
			pop[i].initialize();
		}
	}
	else {
		for(i=0; i<popsize;i++) {
            pop[i] = new Individual(size);
        }
	}
}

/**
 * gets the specified individual
 * @param   a number of individual
 * @return  specified individual
 */
public Individual getIndiv(int a) {
	return pop[a];
}

/**
 * gets the best individual in this population
 * @return  best individual
 */
public Individual getBest() {
		return pop[bestindex];
}

/**
 * gets the best fitness in this population
 * @return  bestfit
 */
public double getBestFit() {
		return bestfit;
}

/**
 * gets the mean fitness in this population
 * @return  meanfit
 */
public double getMeanFit() {
		return meanfit;
}

/**
 * evaluates this population, evaluates the best fitness
 */
public void evaluate() {
	int i,j;
	int a,b;
	double fit=0;
	double best=-1.0;

	for(i=0; i<popsize;i++) {
		fit=0;
		j=0;

		//remove the best flag
		pop[i].setBest(false);

		a = pop[i].getGen(j);
	 	for(j=1; j<size;j++) {
			b = pop[i].getGen(j);	
			fit+= travel.distance(a,b);
			a = b;
		}
		b = pop[i].getGen(0);
		fit+= travel.distance(a,b);
		pop[i].setFitness(fit);
		if(best<0.0 || best>=fit) {
			best=fit;
			bestindex=i;
		}
    }
	bestfit=best;
}

/**
 * interface to the quicksort algorithm to determine the number of
 * the individuals and to get the mean value 
 */
public void qsort() {
	int i;
	double mean=0.0;
	
	quicksort(0, popsize-1);

	for(i=0; i<popsize;i++) {
		pop[i].setNr(i);
		mean+=pop[i].getFitness();
	}

	bestindex=0;
	bestfit=pop[0].getFitness();
	meanfit=mean/popsize;
}
	
/** This  quicksort algorithm will handle arrays that are already
 * sorted and arrays with duplicate keys.
 *
 * If you think of a one dimensional array as going from
 * the lowest index on the left to the highest index on the right
 * then the parameters to this function are lowest index or
 * left and highest index or right.  The first time you call
 * this function it will be with the parameters 0, a.length - 1.
 *
 * @param 	lo0 left boundary of array partition
 * @param 	hi0 right boundary of array partition
 */
public void quicksort(int lo0, int hi0) 
{
   int lo = lo0;
   int hi = hi0;
   double mid;

   if ( hi0 > lo0)
   {

      /* Arbitrarily establishing partition element as the midpoint of
       * the array.
       */
      mid = pop[ ( lo0 + hi0 ) / 2 ].getFitness();

      // loop through the array until indices cross
      while( lo <= hi )
      {
         /* find the first element that is greater than or equal to
          * the partition element starting from the left Index.
          */
          while( ( lo < hi0 ) && ( pop[lo].getFitness() < mid ))
              ++lo;

         /* find an element that is smaller than or equal to
          * the partition element starting from the right Index.
          */
          while( ( hi > lo0 ) && ( pop[hi].getFitness() > mid ))
              --hi;

         // if the indexes have not crossed, swap
         if( lo <= hi )
         {
            swap(lo, hi);
            ++lo;
            --hi;
         }
      }

      /* If the right index has not reached the left side of array
       * must now sort the left partition.
       */
      if( lo0 < hi )
         quicksort(lo0, hi );

      /* If the left index has not reached the right side of array
       * must now sort the right partition.
       */
      if( lo < hi0 )
         quicksort(lo, hi0 );

   }
}

/**
 * swap the individual i withe the individual j
 * @param	i position i
 * @param	j position j
 */
private void swap(int i, int j)
{
   Individual T;
   T = pop[i];
   pop[i] = pop[j];
   pop[j] = T;

 }


}

