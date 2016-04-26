package ga2;import java.awt.*;

/**
 * This class is an extension of the TextField. It allows input validation.
 * (from "core JAVA", S.235 ff)
 *
 * coding:   h.sarg
 * modified: 10/97
 */
public class IntTextField extends TextField {
/**
 * low value in this IntTextField
 */
	private int low;
/**
 * high value in this IntTextField
 */
	private int high;

/**
 * This constructor sets the range of the acceptable values.
 * @param def:	default value
 * @param min:	minimal acceptable value
 * @param max:	maximal acceptable value
 * @param size:	length of integer input
 */
public IntTextField(int def, int min, int max, int size) {
    super (""+def,size);
    low = min;
    high = max;
}
 

/**
 * checks the input in the IntTextField
 * @return	true if input is ok, otherwise false
 */ 
public boolean isValid() {
    int value;
    try
    {
      value = Integer.valueOf(getText().trim()).intValue();
      if (value < low || value > high)
        throw new NumberFormatException();
    }
    catch (NumberFormatException e)
    {
      requestFocus();
      return false;
    }
    return true;
}


/**
 * gets the value of the IntTextField after checking its input
 * @return	the Integer value
 */ 
public int getValue() {
    return Integer.parseInt(getText().trim());
}

}

