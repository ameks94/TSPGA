package ga2;
/**
 * This class represents the whole GA and contains all recombination 
 * and mutation methods.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class Algorithm {
/**
 * contains the actual population
 */
	private Population pop1;
/**
 * contains the recombinated individuals
 */
	private Population pop2;	
/**
 * contains the unsorted mutated individuals, these individuals contains 
 * also the information about the mutation operator to visualize the mutation
 * in the Generation frame
 */
	private Population pop3;
/**
 * contains the sorted mutated individuals, it becomes the next actual 
 * population
 */
	private Population pop4;
/**
 * help pointer for exchange (pop1 - pop4)
 */
	private Population popres;
/**
 * contains the selected individuals (only the pointers to the individual
 * objects in the actual population)
 */
	private Individual[] select;
/**
 * number of individuals to select
 */
	private int selectsize;	
/**
 * object which stores the information about the GA Parameter frame
 */
	private DialogData dialog;
/**
 * Object which holds the information about the towns
 */
	private TravelData travel;
/**
 * simulate the following crossover or not
 * if this flag is set (true) then necessary information about each
 * recombination is stored in each individuals VisRecomb object
 */
	private boolean sim=false;
	
/**
 * This constructor creates the populations and the array to 
 * store the selected individuals, evaluates the selection, initializes the
 * start population and sorts it.
 * @param	a DialogData
 * @param	b TravelData
 */
public Algorithm(DialogData a, TravelData b) {
	int i;

	this.dialog = a;
	this.travel = b;
	pop1 = new Population(travel, dialog.pop, dialog.towns, 1);
	pop2 = new Population(travel, dialog.pop, dialog.towns, 0);
	pop3 = new Population(travel, dialog.pop, dialog.towns, 0);
	pop4 = new Population(travel, dialog.pop, dialog.towns, 0);
	pop1.evaluate();
	pop1.qsort();
	if(dialog.elitism) { selectsize=2*(dialog.pop-1);}
	else { selectsize=2*dialog.pop;}
	
	select = new Individual[selectsize];

}

/**
 * sets the simulation flag
 */
public void setSim() {
	sim=true;
}

/**
 * unsets the simulation flag
 */
public void unsetSim() {
	sim=false;
}

/**
 * gets the value of the selection size
 * @return	selection size
 */
public int getSelectionSize() {
	return selectsize;
}

/**
 * gets the array with the selected individuals
 * @return	individual array
 */
public Individual[] getSelection() {
	return select;
}

/**
 * gets the spezified population
 * @param	a number of the needed population
 * @return	Population
 */
public Population getPop(int a) {
	if(a == 1) return pop1;
	if(a == 2) return pop2;
	if(a == 3) return pop3;
	else return pop4;
}

/**
 * does the selection out of the actual population
 * always using tournament selection with the selected group size
 */
public void selection() {
	int i,j;
	int k=dialog.group;			//group size from the options dialog
	int rand;
	double bestfit=-1;			//invalid best fitness
	Individual bestindiv=null;	//invalid best individual
	Individual indiv;

	for(j=0;j<selectsize;j++) {
		for(i=0;i<k;i++) {
			rand = MyRandom.intRandom(0, dialog.pop-1);
			indiv = pop1.getIndiv(rand);
			if(bestfit == -1 || indiv.getFitness()<bestfit) {
				bestfit=indiv.getFitness();	
				bestindiv = indiv;
			}
		}
		select[j]=bestindiv;	//need only to know the pointer of individual
		bestfit = -1;
	}
}

/**
 * This mutator selects a part of the individual per random, inverts this 
 * part and inserts it into the new individual.
 */
public void inversion() {
	Individual ires;
	Individual indiv;
	int rand1;
	int rand2;
	int randh;
	int i,j,k,l;
	int gen;

	for(i=0;i<dialog.pop;i++) {
		ires=pop3.getIndiv(i);
		ires.setFitness(-1.0);
		ires.setMut(-1,-1);
		indiv=pop2.getIndiv(i);
		indiv.setMut(-1,-1);
		ires.copyIndiv(indiv);
		if(doit(dialog.mp)) {
        	rand1=MyRandom.intRandom(0, dialog.towns-1);
			do {
        		rand2=MyRandom.intRandom(0, dialog.towns-1);
			}
			while(rand1==rand2);
        	if(rand1>rand2) {
           	 	randh=rand1;
            	rand1=rand2;
            	rand2=randh;
        	}
			ires.setMut(rand1,rand2);
			indiv.setMut(rand1,rand2);
			k=rand2;
			for(j=rand1;j<=rand2;j++) {
				gen=indiv.getGen(j);
				ires.setGen(k, gen);
				k--;
			}
		}
	}
}

/**
 * This mutator selects a gen of the individual per random and inserts 
 * this gen at a random place in the new individual.
 */
public void insertion() {
    Individual ires;
    Individual indiv;
    int rand1;
    int rand2;
    int i,j;
    int gen;
	int genpos=0;

    for(i=0;i<dialog.pop;i++) {
        ires=pop3.getIndiv(i);
        ires.setFitness(-1.0);
		ires.setMut(-1,-1);
        indiv=pop2.getIndiv(i);
		indiv.setMut(-1,-1);
        ires.copyIndiv(indiv);
        if(doit(dialog.mp)) {
            rand1=MyRandom.intRandom(0, dialog.towns-1);     //gen
            rand2=MyRandom.intRandom(0, dialog.towns-1);     //position
            gen=rand1;

			ires.setMut(rand2, rand2);
            for(j=0;j<dialog.towns;j++) {
                if(gen==indiv.getGen(j)) {
					genpos=j;
                }
            }
			indiv.setMut(genpos, genpos);
			if(genpos<=rand2) {
				ires.setGen(rand2, gen);
        	   	for(j=genpos;j<rand2;j++) {
					ires.setGen(j, indiv.getGen(j+1));
				}
        	}
			else {
				ires.setGen(rand2, gen);
            	for(j=rand2;j<genpos;j++) {
					ires.setGen(j+1, indiv.getGen(j));
				}
			}
    	}
	}
}

/**
 * This mutator selects a part of the individual per random and inserts this
 * part at a random place in the new individual.
 */
public void displacement() {
    Individual ires;
    Individual indiv;
    int rand1;
    int rand2;
	int rand3;
    int i,j,k;
    int gen;
    int genpos;

    for(i=0;i<dialog.pop;i++) {
        ires=pop3.getIndiv(i);
        ires.setFitness(-1.0);
		ires.setMut(-1,-1);
        indiv=pop2.getIndiv(i);
		indiv.setMut(-1,-1);
        ires.copyIndiv(indiv);
        if(doit(dialog.mp)) {
            rand1=MyRandom.intRandom(0, dialog.towns-1);     
            rand2=MyRandom.intRandom(0, dialog.towns-1);   
			if(rand1>rand2) {
				rand3=rand1;
				rand1=rand2;
				rand2=rand3;
			}
			indiv.setMut(rand1, rand2);
			if(rand1!=0 || rand2!=dialog.towns-1) {
            	rand3=MyRandom.intRandom(0, dialog.towns-1);  
				while(rand3>=rand1 && rand3<=rand2){
            		rand3=MyRandom.intRandom(0, dialog.towns-1);  
				}	 
				if(rand3<rand1) {
					ires.setMut(rand3, rand3+rand2-rand1);
					k=rand3;
					for(j=rand1; j<=rand2;j++) {
						ires.setGen(k, indiv.getGen(j));
						k++;
					}
					for(j=rand3;j<rand1;j++) {
						ires.setGen(k, indiv.getGen(j));
						k++;
					}
				}
				else {
					ires.setMut(rand3-(rand2-rand1), rand3);
					k=rand3;
					for(j=rand2; j>=rand1;j--) {
						ires.setGen(k, indiv.getGen(j));
						k--;
					}
					for(j=rand3;j>rand2;j--) {
						ires.setGen(k, indiv.getGen(j));
						k--;
					}
				}
			}
		}
	}
}

/**
 * This mutator selects a gen and a position per random. Then the
 * selected gen and the gen at the selected position are swapped.
 */
public void swap() {
    Individual ires;
    Individual indiv;
    int rand1;
    int rand2;
    int i,j;
    int gen;

    for(i=0;i<dialog.pop;i++) {
        ires=pop3.getIndiv(i);
        ires.setFitness(-1.0);
		ires.setMut(-1,-1);
        indiv=pop2.getIndiv(i);
		indiv.setMut(-1,-1);
        ires.copyIndiv(indiv);
        if(doit(dialog.mp)) {
            rand1=MyRandom.intRandom(0, dialog.towns-1);	 //gen
            rand2=MyRandom.intRandom(0, dialog.towns-1);     //position
            gen=rand1;

            for(j=0;j<dialog.towns;j++) {
				if(gen==indiv.getGen(j)) {
                	ires.setGen(rand2, gen);
                	ires.setGen(j, indiv.getGen(rand2));

					if(rand2<j) {
						ires.setMut(rand2,j);
						indiv.setMut(rand2,j);
					}
					else {
						if(rand2==j) {
	                        ires.setMut(j,-1);
        		            indiv.setMut(j,-1);
						}
						else {	
                        	ires.setMut(j,rand2);
                        	indiv.setMut(j,rand2);
						}
                    }
				}
            }
        }
    }
}


/**
 * This mutator copies the recombinated population to the mutated population.
 */
public void nonemut() {
    Individual ires;
    Individual indiv;
    int i;

    for(i=0;i<dialog.pop;i++) {
        ires=pop3.getIndiv(i);
        ires.setFitness(-1.0);
		ires.setMut(-1,-1);
        indiv=pop2.getIndiv(i);
		indiv.setMut(-1,-1);
        ires.copyIndiv(indiv);
    }
}

/**
 * This recombination method selects a part of the first parent per random 
 * as a crossover area. The child contains this area unchanged.
 * The other gens are set in order of the second parent in a determined way
 * following the PMX method.
 */
public void pmx() {
    Individual ires;
    int rand1;
    int rand2;
    int randh;
    int i,j,k,l;
    int gen, genres;
	VisRecomb vis=null;

    l=0;
    for(i=0;i<selectsize;i=i+2) {
        ires=pop2.getIndiv(l);
        l++;
        if(!doit(dialog.rp)) {
            ires.copyIndiv(select[i+1]);
        }
        else {
            rand1=MyRandom.intRandom(0, dialog.towns-1);
            rand2=MyRandom.intRandom(0, dialog.towns-1);
            if(rand1>rand2) {
                randh=rand1;
                rand1=rand2;
                rand2=randh;
            }
			if(sim) {
				//area to keep information about the recombination
				ires.setVisRecomb();
				vis=ires.getVisRecomb();
				vis.setCoArea(rand1,rand2);
				vis.setMaxStep(1+rand2-rand1);
			}
            ires.copyIndiv(select[i+1]);
			//go through the crossover area
            for(k=rand1;k<=rand2;k++) {
                gen=select[i].getGen(k);
				genres=ires.getGen(k);
				//search for the gen
            	for(j=0;j<dialog.towns;j++) {
					if(ires.getGen(j)==gen) break;
				}
				//swap genres and gen in the child
				if(sim) {
					//hold the steps of the recomb method
					vis.setStep(k, j);
				}
				ires.setGen(j, genres);
				ires.setGen(k, gen);
			}
		}
	}
}

/**
 * This recombination method selects a part of the first parent per random 
 * as a crossover area. The child contains this area unchanged.
 * The other gens are set in order of the second parent in a determined way
 * following the OX method.
 */
public void ordercrossover() {
	Individual ires;
	int rand1;
	int rand2;
	int randh;
	int i,j,k,l;
	int gen;
	boolean found;
	int pos;
	int step;
	VisRecomb vis=null;

	l=0;
	for(i=0;i<selectsize;i=i+2) {
		ires=pop2.getIndiv(l);
		//ires.setNr(l);
		l++;
		if(!doit(dialog.rp)) {
			ires.copyIndiv(select[i+1]);
		}
		else {
			pos=0;
			rand1=MyRandom.intRandom(0, dialog.towns-1);
			rand2=MyRandom.intRandom(0, dialog.towns-1);
			if(rand1>rand2) {
				randh=rand1;
				rand1=rand2;
				rand2=randh;
			}
			step=2;
            if(sim) {
                //area to keep information about the recombination
                ires.setVisRecomb();
                vis=ires.getVisRecomb();
                vis.setCoArea(rand1,rand2);
            }
			for(j=rand1;j<=rand2;j++) {
				gen=select[i+1].getGen(j);
				found=false;
				for(k=rand1;k<=rand2;k++) {
					if(gen==select[i].getGen(k)) {
						found=true;
						//System.out.println("habe gen: "+gen);
						break;
					}
				}
				if(!found) {
	                if(sim) {
    	                //hold the steps of the recomb method
        	            vis.setStep(pos, step);
            	        vis.setInfo(pos, 4);
                	}
					ires.setGen(pos,gen);
					pos++;
				}
			}
			//set the crossover area in child
			step++;
			for(k=rand1;k<=rand2;k++) {
	            if(sim) {
    	            //hold the steps of the recomb method
        	        vis.setStep(pos, step);
            	    vis.setInfo(pos, 3);
               	}
				gen=select[i].getGen(k);
				ires.setGen(pos,gen);
				pos++;
			}
			for(j=rand2+1;j<dialog.towns;j++) {
           	 	gen=select[i+1].getGen(j);
           	 	found=false;
           	 	for(k=rand1;k<=rand2;k++) {
           	   		if(gen==select[i].getGen(k)) {
               	    	found=true;
                    	break;
              		}
            	}
            	if(!found) {
	            	if(sim) {
    	           		//hold the steps of the recomb method
        	     	  	vis.setStep(pos, 2);
            	   		vis.setInfo(pos, 2);
               		}
                	ires.setGen(pos,gen);
                	pos++;
            	}
        	}
			for(j=0;j<rand1;j++) {
           	 	gen=select[i+1].getGen(j);
            	found=false;
            	for(k=rand1;k<=rand2;k++) {
               	 	if(gen==select[i].getGen(k)) {
                    	found=true;
                    	break;
                	}
            	}
            	if(!found) {
	            	if(sim) {
    	           		//hold the steps of the recomb method
        	     	  	vis.setStep(pos, 2);
            	   		vis.setInfo(pos, 2);
               		}
                	ires.setGen(pos,gen);
                	pos++;
            	}
        	}
			if(sim) vis.setMaxStep(step);
		}
	}
}

/**
 * This recombination method selects a gen of the first parent per random.
 * A cycle of gens caused by that gen is then contents of the child. 
 * The other gens are set in order of the second parent.
 */
public void cyclecrossover() {
    Individual ires;
    int i,j,k,l;
    int gen;
	boolean[] help= new boolean[dialog.towns];
    boolean found;
    int pos;
	VisRecomb vis=null;
	int step;

    l=0;
    for(i=0;i<selectsize;i=i+2) {
        ires=pop2.getIndiv(l);
        l++;
        if(!doit(dialog.rp)) {
            ires.copyIndiv(select[i+1]);
        }
        else {
			found=false;
    		for(j=0;j<dialog.towns;j++) {
				help[j]=false;
			}
            if(sim) {
                //area to keep information about the recombination
                ires.setVisRecomb();
                vis=ires.getVisRecomb();
                vis.setCoArea(-1,-1);
            }
			step=0;
			gen=select[i].getGen(0);  //erstes Gen f�r Zyklus
            if(sim) {
                //hold the steps of the recomb method
                vis.setStep(0, step);
                vis.setInfo(0, 1);
				step++;
            }
			ires.setGen(0, gen);
			help[0]=true;
			pos=0;
			do {
				gen=select[i+1].getGen(pos);
    			for(j=0;j<dialog.towns;j++) {
					if(gen==select[i].getGen(j)) break;
				}
				if(!help[j]) {
                    if(sim) {
                        //hold the steps of the recomb method
                        vis.setStep(j, step);
                        vis.setInfo(j, 1);
						step++;
                    }
					//gen=select[i].getGen(j);
					ires.setGen(j, gen);
					pos=j;
					help[j]=true;
				}
				else found=true;
			}
			while(!found);	
			for(j=0;j<dialog.towns;j++) {
				if(!help[j]) {
					ires.setGen(j, select[i+1].getGen(j));
                    if(sim) {
                        //hold the steps of the recomb method
                        vis.setStep(j, step);
                        vis.setInfo(j, 2);
						step++;
                    }
				}
			}
			if(sim) vis.setMaxStep(step-1);
		}
	}
}

/**
 * This recombination method uses a randomly generated array filled with 
 * ones and zeros. Gens from the first parent including a one in the array 
 * come into the child. The other gens are set in order of the
 * second parent.
 */
public void uobx() {
	Individual ires;
	boolean[] zufall = new boolean[dialog.towns];
	boolean[] help =new boolean[dialog.towns];
	int i,j,l;
	int h;
	int index_a;
	int index_h;
	VisRecomb vis = null;
	int step;

	l=0;
	for(i=0;i<selectsize;i=i+2) {
		ires=pop2.getIndiv(l);
		l++;
		if(!doit(dialog.rp)) {
			ires.copyIndiv(select[i+1]);
		}
		else {
			for(j=0;j<dialog.towns;j++) {
				zufall[j] = MyRandom.boolRandom();
			}

            if(sim) {
                //area to keep information about the recombination
                ires.setVisRecomb();
                vis=ires.getVisRecomb();
                vis.setCoArea(-1,-1);
            }
			step=1;
			for(j=0;j<dialog.towns;j++) {
				h=select[i].getGen(j);
				if(zufall[j]){
                    if(sim) {
                        //hold the steps of the recomb method
                        vis.setStep(j, step);
                        vis.setInfo(j, 1);
                    }
					ires.setGen(j, h);
					help[h]=true;
				}
				else help[h]=false;
			}
			index_a=0;
            step++;
			for(j=0;j<dialog.towns;j++) {
				if(!zufall[j]) {
					do {
						index_h=select[i+1].getGen(index_a);
						index_a++;
					}
					while(help[index_h]);
					ires.setGen(j,index_h);
                    if(sim) {
                        //hold the steps of the recomb method
                        vis.setStep(j, step);
                        vis.setInfo(j, 2);
						step++;
                    }
				}
			}
			if(sim) vis.setMaxStep(step-1);
		}
	}	
}
							
/**
 * This recombination method (improved erx)
 * creates an edge list for each town. Double edges get marked.
 * Starting at a random town the edge list is worked out.
 * If the parameter heuristic is true always the shortest connection in the 
 * list is taken, otherwise the town with the shortest list. 
 * Double entries are favoured.
 * @param heuristic: with or without heuristic
 */
public void erx(boolean heuristic) {
    Individual ires;
    int i,j,k,l;
	int[][] edges=new int[dialog.towns][6];
    int gen, rand;
    int pre;
	int post;
    int pos;
	int shortest;
	int shortestlist;
	boolean found;
	double sum, randdbl;
	int[] heur =new int[6];
	VisRecomb vis = null;
	boolean edgefault=false;

    l=0;
    for(i=0;i<selectsize;i=i+2) {
        ires=pop2.getIndiv(l);
        l++;
        if(!doit(dialog.rp)) {
            ires.copyIndiv(select[i+1]);
        }

		//produce an edge - list from eatch town to its neighbours
		edges[select[i].getGen(0)][1]=select[i].getGen(dialog.towns-1);
		edges[select[i].getGen(0)][2]=select[i].getGen(1);
		for(j=1;j<(dialog.towns-1);j++) {
			edges[select[i].getGen(j)][1]=select[i].getGen(j-1);
			edges[select[i].getGen(j)][2]=select[i].getGen(j+1);
		}
		edges[select[i].getGen(j)][1]=select[i].getGen(dialog.towns-2);
		edges[select[i].getGen(j)][2]=select[i].getGen(0);

		pre=select[i+1].getGen(dialog.towns-1);
		post=select[i+1].getGen(1);
		create(edges[select[i+1].getGen(0)], pre, post);
		for(j=1;j<(dialog.towns-1);j++) {
			pre=select[i+1].getGen(j-1);
			post=select[i+1].getGen(j+1);
			create(edges[select[i+1].getGen(j)], pre, post);
		}
		pre=select[i+1].getGen(dialog.towns-2);
		post=select[i+1].getGen(0);
		create(edges[select[i+1].getGen(dialog.towns-1)], pre, post);

        if(sim) {
            //area to keep information about the recombination
            ires.setVisRecomb();
            vis=ires.getVisRecomb();
			vis.setEdges(edges);
            vis.setMaxStep(dialog.towns);
        }

		//town to start the trip is the first town of the first parent
		pos=0;
		gen=select[i].getGen(0);
		ires.setGen(pos, gen);
		edges[gen][0]=-1;
		pos++;

		//while trip not ready
		while(pos<dialog.towns) {
			
		//eleminate the town from the edge - list
			for(j=0;j<dialog.towns;j++) {
				k=0;
				while(edges[j][k] != -1) {
					k++;
					if(edges[j][k] ==gen) {
						edges[j][k]=-3;
						edges[j][0]--;
					}
				}
			}
		
		//take next town (shortest List)
			sum = 0.0;
			shortest=5;
			shortestlist=-1;
			k=0;
			j=1;
			found=false;
			while(edges[gen][j]!=-1) {
				if(edges[gen][j]>-1 ) {
					if(heuristic) {
						if(edges[ edges[gen][j] ][0] !=-1) {
							heur[k]=edges[gen][j];
							sum+=1/(travel.distance(gen, heur[k]));
							found=true;	
							k++;
						}
					}
					else {
						if(edges[ edges[gen][j] ][0] !=-1 && 
								edges[ edges[gen][j] ][0] <= shortest) {
							shortest=edges[ edges[gen][j] ][0];
							shortestlist=edges[gen][j];	
							found=true;
						}
					}
				}
				j++;
			}
			if(heuristic && found) {
				heur[k]=-1;
				randdbl=MyRandom.dblRandom(sum);
				j=0;
				sum=0.0;
				while(heur[j]!=-1) {
					sum+=1/(travel.distance(gen, heur[j]));
					if(randdbl<sum) {
						shortestlist= heur[j];
						break;
					}
					j++;
				}
			}
			if(!found) edgefault=true;
			while(!found) {
				rand = MyRandom.intRandom(0, dialog.towns-1);
				if(edges[rand][0]!= -1) {
					//System.out.println("Edge fault" + "\n");
					found=true;
					shortestlist=rand;
				}
			}	
			gen=shortestlist;
        	if(sim) {
           		//area to keep information about the recombination
				if(edgefault) vis.setInfo(pos,1);
				else vis.setInfo(pos,2);
				edgefault=false;
        	}
			ires.setGen(pos, gen);
			edges[gen][0]=-1;
			pos++;
		}
	}	
}

/**
 * creates a list without double entries, double values gets marked and
 * inserted at the end of the list only once
 * @param list: edge list of spezial town
 * @param pre:	predecessor of second individual
 * @param post: descendant of second individual
 */
private void create(int[] list, int pre, int post) {
	boolean pre_in=false;
	boolean post_in=false;

	if(list[1]==pre) pre_in=true;
	if(list[2]==pre) post_in=true;
	if(list[1]==post) pre_in=true;
	if(list[2]==post) post_in=true;

	if(pre_in==false) {
		if(post_in==false) {
			list[3]=pre;
			list[4]=post;
			list[5]=-1;
			list[0]=4;
		}
		else {
			if(list[2]==pre) {
				list[2]=post;
				list[3]=-2;
				list[4]=pre;
				list[5]=-1;
				list[0]=3;
			}
			else {
				list[2]=pre;
				list[3]=-2;
				list[4]=post;
				list[5]=-1;
				list[0]=3;
			}
		}
	}
	else {
        if(post_in==true) {
            list[1]=-2;
            list[2]=pre;
            list[3]=post;
			list[4]=-1;
            list[0]=2;
        }
        else {
            if(list[1]==pre) {
                list[1]=post;
                list[3]=-2;
                list[4]=pre;
                list[5]=-1;
                list[0]=3;
            }
            else {
                list[1]=pre;
                list[3]=-2;
                list[4]=post;
                list[5]=-1;
                list[0]=3;
            }
        }
	}
}

/**
 * This recombination method always copies the first parent to the 
 * recombinated population.
 */
public void nonerecomb() {
    Individual ires;
    int i,j,l;

	l=0;
    for(i=0;i<selectsize;i=i+2) {
        ires=pop2.getIndiv(l);
		l++;
        ires.copyIndiv(select[i+1]);
    }
}

/**
 * calls the selected crossover method
 */
public void crossover() {
	int choice=dialog.rm;
	switch(choice) {
		case 0: pmx();
				break;
		case 1: ordercrossover();
				break;
		case 2: cyclecrossover();
				break;
		case 3: uobx();
				break;
		case 4: erx(false);
				break;
		case 5: erx(true);
				break;
		case 6: nonerecomb();
				break;
		default:System.out.println("bad value of mutation-method :" +choice); 
				break;
	}
}

/**
 * calls the selected mutation method
 */
public void mutation() {
	int choice=dialog.mm;
	switch(choice) {
		case 0: inversion();
				break;
		case 1: insertion();
				break;
		case 2: displacement();
				break;
		case 3: swap();
				break;
		case 4: nonemut();
				break;
		default:System.out.println("bad value of mutation-method :" +choice); 
				break;
	}
}

/**
 * Evaluates the mutated population, creates and sorts a copy.
 * If the option elitism is set then the best individual of the last 
 * generation is added to the mutated population.
 */
public void evaluate() {
	if(dialog.elitism) {
		pop3.getIndiv(dialog.pop-1).copyIndiv(pop1.getBest() );
	}
	pop3.evaluate();
	holdPop();
	pop4.qsort();
}

/**
 * copies the unsorted mutated population to the sorted population and marks 
 * the added individual if elitism is true
 */
public void holdPop() {
    int i;

    for(i=0; i<dialog.pop;i++) {
        pop4.getIndiv(i).copyIndiv(pop3.getIndiv(i));
        pop4.getIndiv(i).setFitness(pop3.getIndiv(i).getFitness() );
		pop4.getIndiv(i).setBest(false);
    }
	if(dialog.elitism) {
		pop4.getIndiv(dialog.pop-1).setBest(true);	
	}
}


/**
 * selects a random number and compares it with the parameter bound
 * @param	bound: probability to use the operator (recombination or mutation)
 * @return	do the recombination / mutation or not
 */
public boolean doit(int bound) {
	double b = (double) bound;
	if(MyRandom.dblRandom(100.0) < bound) return true;
	else return false;
}

/**
 * the sorted mutation population becomes the new actual population
 */
public void populate() {
	popres=pop1;
	pop1=pop4;
	pop4=popres;
}

}

