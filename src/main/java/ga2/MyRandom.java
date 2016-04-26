package ga2;import java.lang.*;

/**
 * This class is an extension of the Math.random().
 *
 * coding:   h.sarg
 * modified: 10/97
 */
public class MyRandom {


/**
 * Return a random double in [0,1).
 * @return 	random double
 */
public static double dblRandom() {
	return Math.random();
}


/**
 * Return a random double in [0,high).
 * @param	high upper border
 * @return 	random double
 */
public static double dblRandom(double high) {
	return Math.random()*high;
}


/**
 * Return a random double in [low,high).
 * @param	low lower border
 * @param	high upper border
 * @return 	random double
 */
public static double dblRandom(double low, double high) {
	return Math.random()*(high - low) + low;
}


/**
 * Return a random integer from low to high (inclusive).
 * @param	low lower border
 * @param	high upper border
 * @return 	random integer
 */
public static int intRandom(int low, int high) {
	int nb = high - low + 1;
	return (int)(Math.random()*nb + low);
}


/**
 * Return a random integer from 1 to n inclusive.
 * @param	n upper border
 * @return 	random integer
 */
public static int intRandom(int n) {
   return (int)(Math.random()*n);
}


/**
 * Return a random bit (0 or 1).
 * @return 	random bit
 */
public static int bitRandom() {
   return (int)(Math.random()*2);
}


/**
 * Return a random boolean (false or true).
 * @return 	random boolean
 */
public static boolean boolRandom() {
   return Math.random() >= 0.5;
}

}

