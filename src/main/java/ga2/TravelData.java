package ga2;import java.lang.*;

/**
 * This class contains the information about the towns and their distances.
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class TravelData
{
/**
 * hold the towns information
 */
	private int[][] towns;
/**
 * holds the coordinates of the towns
 */
	private int[][] coordinates;
/**
 * holds the distances between the towns
 */
	private double[][] distance;
/**
 * number of towns
 */
	private int size;
/**
 * grid size
 */
	private int grid;


/**
 * This constructor creates the towns by random or uses the user-selected 
 * towns. Also the distances between the towns are calculated.
 * @param 	data object DialogData
 */
public TravelData(DialogData data) {
	size = data.towns;
	towns = data.townarray;
	grid = data.grid;
	coordinates=new int[size][2];
	distance=new double[size][size];

	int i,j;
	int count=0;
	boolean valid=true;

	//no towns set from the user, set towns randomly
	if(towns==null) valid=false;
	if(!valid) {
		towns=new int[grid][grid];
		initArray();
		while(count<size) {
			i=MyRandom.intRandom(0, grid-1);
			j=MyRandom.intRandom(0, grid-1);
			if(towns[i][j]==0) {
				count++;
				towns[i][j]=1;
			}
		}
	data.townarray=towns;
	}

	//set the coordinates of the towns
	count=0;
	for(i=0;i<grid;i++) {
		for(j=0;j<grid;j++) {
			if(towns[i][j]>0) {
				coordinates[count][0]=i;
				coordinates[count][1]=j;
				count++;
			}
		}
	}
	if (!valid) {
		towns=null;
	}

	//compute the distance between the towns
	checkdistance();
}

/**
 * evaluates the distance between all towns
 */
private void checkdistance() {
	int i;
	int j;
	double xdiff;
	double ydiff;

	for(i=0;i<size;i++) {
		for(j=i+1;j<size;j++) {
			xdiff=coordinates[i][0]-coordinates[j][0];
			ydiff=coordinates[i][1]-coordinates[j][1];
			distance[i][j]=Math.sqrt(xdiff*xdiff+ydiff*ydiff);
			distance[j][i]=distance[i][j];
			distance[j][i]=distance[i][j];
		}
	}
}

/**
 * initializes an array with 0
 */
public void initArray() {
   	for(int i=0;i<grid;i++) {
   		for(int j=0;j<grid;j++) {
   	    	towns[i][j]=0;
   	  	}
   	}
}

/**
 * gets the grid
 * @return grid size
 */
public int getGrid() {
	return(grid);
}

/**
 * gets the number of towns
 * @return number of towns
 */
public int getTownSize() {
	return(size);
}

/**
 * gets the coordinates of the towns
 * @return coordinates of the towns
 */
public int[][] getCoord() {
	return(coordinates);
}

/**
 * gets the distance between two towns
 * @param a first town
 * @param b second town
 * @return distance
 */
public double distance(int a, int b) {
	return(distance[a][b]);
}


/**
 * creates a string which represents the information of this class
 * @return String of this class
 */
public String toString() {
	String s1 ="";
	String s2 ="";
	String s= "size= "+ size +"\n";
	for(int i=0;i<size;i++) {
		s1 = s1 + coordinates[i][0]+" ";
		s2 = s2 + coordinates[i][1]+" ";
	}
	s=s + s1 +"\n" + s2 +"\n";
		
	return s;
}
				
}
