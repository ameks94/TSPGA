package ga2;
/**
 * This class contains the information for the Run dialog.
 *
 * coding:   h.sarg 
 * modified: 01\98
 */
public class ConnectInfo {
/**
 * true means update StatisticCanvas every generation, else not
 */
    public boolean ok;

/**
 * number of generations the thread should run
 */
    public int value;

/**
 * RunDialog constructor comment
 */
    ConnectInfo(boolean o, int v)
        {ok = o; value = v;}
}

