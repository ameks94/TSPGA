package ga2;import java.awt.*;

/**
 * This class shows the trip representated by an individual and its fitness.
 *
 * coding:   h.sarg
 * modified: 01/98
 */

public class TripFrame extends Frame implements java.awt.event.ComponentListener, java.awt.event.WindowListener {
	private Label ivjTripFitLabel = null;
    private Label ivjTripIndivLabel = null;
    private TravelCanvas ivjTripTravelCanvas = null;
/**
 * the grid
 */
	private int grid;
/**
 * the individual to draw
 */
	private Individual indiv;

/**
 * TripFrame constructor comment.
 * @param parent Symbol
 */
public TripFrame() {
    super();
    initialize();
}


/**
 * sets the individual to draw and the necessary data for the TravelCanvas
 * @param 	indiv the individual
 * @param 	a data to show the trip
 * @param 	b indicates if the towns should be labeled or not
 * @param 	c indicates the font size
 * @param 	grid grid
 */
public void setData(Individual indiv, TravelData a, boolean b, int c, int grid) {
	this.grid = grid;
	this.indiv = indiv;
	ivjTripTravelCanvas.setTravelData(a,b,c);
	ivjTripTravelCanvas.setTravelIndiv(indiv);
	ivjTripFitLabel.setText("Fitness: " + indiv.trimFit() );
    ivjTripIndivLabel.setText(indiv.toPopString(false) );

}

/**
 * Return the TripFitLabel property value.
 * @return java.awt.Label
 */
private Label getTripFitLabel() {
    if (ivjTripFitLabel == null) {
        try {
            ivjTripFitLabel = new java.awt.Label();
            ivjTripFitLabel.setName("TripFitLabel");
            ivjTripFitLabel.setAlignment(java.awt.Label.CENTER);
            ivjTripFitLabel.setText("Fitness=10099");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTripFitLabel;
}

/**
 * Return the TripIndivLabel property value.
 * @return java.awt.Label
 */
private Label getTripIndivLabel() {
    if (ivjTripIndivLabel == null) {
        try {
            ivjTripIndivLabel = new java.awt.Label();
            ivjTripIndivLabel.setName("TripIndivLabel");
            ivjTripIndivLabel.setAlignment(java.awt.Label.CENTER);
            ivjTripIndivLabel.setText("ADFHJKIUFGH");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTripIndivLabel;
}

/**
 * Return the TripTravelCanvas property value.
 * @return TravelCanvas
 */
private TravelCanvas getTripTravelCanvas() {
    if (ivjTripTravelCanvas == null) {
        try {
            ivjTripTravelCanvas = new TravelCanvas();
            ivjTripTravelCanvas.setName("TripTravelCanvas");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjTripTravelCanvas;
}


/**
 * Called whenever the part throws an exception.
 * @param exception java.lang.Throwable
 */
private void handleException(Throwable exception) {

    /* Uncomment the following lines to print uncaught exceptions to stdout */
    // System.out.println("--------- UNCAUGHT EXCEPTION ---------");
    // exception.printStackTrace(System.out);
}


/**
 * Initializes connections
 */
private void initConnections() {
    this.addWindowListener(this);
    this.addComponentListener(this);
}


/**
 * Initialize class
 */
private void initialize() {
	setName("TripFrame");
	setLayout(new java.awt.BorderLayout());
	setSize(180, 240);
	setTitle("Trip");
	this.add("North", getTripIndivLabel());
	this.add("Center", getTripTravelCanvas());
	this.add("South", getTripFitLabel());
    initConnections();
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowActivated(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowClosed(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowClosing(java.awt.event.WindowEvent e) {
	Object source = e.getSource();
    if ((source == this) ) {
		this.dispose();
    }
}
/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowDeactivated(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowDeiconified(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowIconified(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the WindowListener interface.
 * @param e java.awt.event.WindowEvent
 */
public void windowOpened(java.awt.event.WindowEvent e) {
}

/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentHidden(java.awt.event.ComponentEvent e) {
}
/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentMoved(java.awt.event.ComponentEvent e) {
}
/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentResized(java.awt.event.ComponentEvent e) {
    Object source = e.getSource();

    if ((source == this) ) {
        //System.out.println("Resize TripFrame \n");
        int stepwidth;
        int stepheight;
        Dimension canvassize;

        canvassize=ivjTripTravelCanvas.getSize();
        stepwidth=(int)(canvassize.width/grid);
        stepheight=(int)(canvassize.height/grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjTripTravelCanvas.setTravelCanvas(stepwidth,stepheight);
    }
}

/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentShown(java.awt.event.ComponentEvent e) {
}


}
