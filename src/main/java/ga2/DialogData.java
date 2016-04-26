package ga2;import java.lang.*;

/**
 * This class includes all parameters for the GA and the TSP, and it contains
 * all adjustable values out of the menu "Options".
 *
 * coding:	hannes sarg
 * modified: 01/98
 */
public class DialogData {

/**
 * default value for the recombination method
 *		0	PMX,
 *		1	OX,	
 *		2	CX,
 *		3	UOBX,
 *		4	ERX,
 *		5	ERX nach Grefenstette,
 *		6	None.
 **/
	public final static int DEF_RM = 0;

/**
 * default value for the recombination probability (valid range: 0 - 100)
 **/
	public final static int DEF_RP = 70;

/**
 * default value for the mutation method
 *		0	Inversion,
 *		1	Insertion,	
 *		2	Displacement,
 *		3	Swap,
 *		4	None.
 **/
	public final static int DEF_MM = 0;

/**
 * default value for the mutation probability (valid range: 0 - 100)
 **/
	public final static int DEF_MP = 30;

/**
 * default value for the poulation size (valid range: 5 - 100)
 **/
	public final static int DEF_POP = 20;

/**
 * default value for the tournament selection (valid range: 1 - 10)
 **/
	public final static int DEF_GROUP = 2;

/**
 * default value for the elitism, if true the best individual is kept
 **/
	public final static boolean DEF_ELITISM = true;

/**
 * default value for number of towns (valid range: 4 - 100)
 **/
	public final static int DEF_TOWNS = 10;

/**
 * default value for the grid (valid range: 10 - 100)
 **/
	public final static int DEF_GRID = 15;


/**
 * actual recombination method, adjustable between 0 and 6
 **/
	public int rm;
/**
 * actual recombination probability, adjustable between 0 and 100
 **/
	public int rp;
/**
 * actual mutation method, adjustable between 0 and 4
 **/
	public int mm;
/**
 * actual mutation probability, adjustable between 0 and 100
 **/
	public int mp;
/**
 * actual population size, adjustable between 5 and 100
 **/
	public int pop;
/**
 * actual group size, adjustable between 1 and 10
 **/
	public int group;
/**
 * actual elitism
 **/
	public boolean elitism;
/**
 * actual number of towns, adjustable between 4 and 100
 **/
	public int towns;
/**
 * actual grid, adjustable between 10 and 100
 **/
	public int grid;
/**
 * actual position of towns
 **/
	public int[][] townarray;
/**
 * indicate towns with letters (A-Z) or show only their position
 **/
	public boolean showtowns=true;
/**
 * actual fonts: 0=small, 1=medium, 2=large
 **/
	public int largefonts=1;
/**
 * actual generationstep in RunDialog
 **/
	public int genstep=100;
/**
 * actual update in StatisticCanvas
 **/
	public boolean update=true;

/**
 * Constructor  
 * set default values, initialize townarray
 */
public DialogData() {
	this.rm = DEF_RM;
	this.rp = DEF_RP;
	this.mm = DEF_MM;
	this.mp = DEF_MP;
	this.pop = DEF_POP;
	this.group = DEF_GROUP;
	this.elitism = DEF_ELITISM;
	this.towns = DEF_TOWNS;
	this.grid = DEF_GRID;
	this.townarray = null;
}

/**
 * saving the actual values in the GA Parameters dialog  
 */
public void setDialogData(int rm, int rp, int mm, int mp, int pop, int group,
						boolean elitism) {
	this.rm = rm;
	this.rp = rp;
	this.mm = mm;
	this.mp = mp;
	this.pop = pop;
	this.group = group;
	this.elitism = elitism;
}

/** 
 * create the titel for the finalframe
 */
public String toTitle() {
	String s1="";
	String s2="";

	switch(rm) {
		case 0:	s1="PMX";
				break;
		case 1: s1="OX";
				break;
		case 2: s1="CX";
				break;
		case 3: s1="UOBX";
				break;
		case 4: s1="ERX";
				break;
		case 5: s1="ERX+";
				break;
		case 6: s1="None";
				break;
	    default:System.out.println("bad value of recombination-method :" +rm);
                break;
    }

    switch(mm) {
        case 0: s2="Inversion";
                break;
        case 1: s2="Insertion";
                break;
        case 2: s2="Displacement";
                break;
        case 3: s2="Swap";
                break;
        case 4: s2="None";
                break;
        default:System.out.println("bad value of mutation-method :" +mm);
                break;
    }


    String s = s1 + ":" +
               rp + "%, " +
               s2 + ":" +
               mp + "%, " +
               "Pop:" + pop + ", " +
               "Group size:" + group + ", " +
               "Elitism:" + elitism; 
    return s;
}


/**
 *  make values of this object printable 
 *	@return	String represent the data of this object
 */
public String toString() {
	String s = "Recombination method: " + rm + "\n" +
			   "Recombination probability: " + rp + "\n" +
			   "Mutation method: " + mm + "\n" +
			   "Mutation probability: " + mp + "\n" +
			   "Population size: " + pop + "\n" +
			   "Group size: " + group + "\n" +
			   "Elitism: " + elitism + "\n" +
			   "Towns: " + towns + "\n"+
			   "Grid: " + grid + "\n"+
				"Show towns: "+showtowns +"\n" +
				"Large fonts: "+largefonts +"\n" +
				"update: "+update +"\n" +
				"Generation step: "+genstep +"\n";
	return s;
}

} 


