package ga2;import java.awt.*;

/**
 * Interface for the Run dialog.
 *
 * coding:  hannes sarg
 * modified: 10.97
 */
public interface ResultProcessor
{
/**
 * this function is to implement in TSPGA.java
 * @param	source dialog
 * @param	obj	object which holds the information
 */
    public void processResult(Dialog source, Object obj);
}

