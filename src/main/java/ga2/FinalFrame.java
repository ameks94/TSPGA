package ga2;import java.awt.*;
import java.lang.*;
import java.util.*;


/**
 * This class shows the result of one GA run.
 *
 * coding:   h.sarg
 * modified: 01/98
 */

public class FinalFrame extends Frame implements java.awt.event.ComponentListener, java.awt.event.WindowListener {
    private Label ivjStatisticBestfitLabel = null;
    private Label ivjStatisticBestindivLabel = null;
    private Label ivjStatisticBestLabel = null;
    private StatisticCanvas ivjStatisticCanvas = null;
    private Frame ivjStatisticFrame = null;
    private GridLayout ivjStatisticFrameGridLayout = null;
    private Label ivjStatisticGenLabel = null;
    private Panel ivjStatisticLabelPanel = null;
    private Panel ivjStatisticLabelPanel1 = null;
    private FlowLayout ivjStatisticLabelPanelFlowLayout = null;
    private FlowLayout ivjStatisticLabelPanelFlowLayout1 = null;
    private Label ivjStatisticMeanfitLabel = null;
    private Label ivjStatisticMeanLabel = null;
    private ScrollPane ivjStatisticScrollPane = null;
    private Panel ivjStatisticStatisticPanel = null;
    private Panel ivjStatisticTavelPanel = null;
    private TravelCanvas ivjStatisticTravelCanvas = null;
/**
 * hold the actual grid value
 */
	private int grid;
/**
 * hold the actual size of fonts
 */
	private int largefonts=0;
/**
 * the actual font
 */
	private Font f;

/**
 * FinalFrame constructor comment.
 */
public FinalFrame() {
    super();
    initialize();
}

/**
 * sets the necessary data of one GA run
 * @param	data Object which holds the GA Parameters
 * @param	trav Object which holds the information about the towns
 * @param	a	String best fitness
 * @param	b	String mean fitness
 * @param	c	String number of generations
 * @param	d	String best trip
 * @param	v1 Vector of best fitness entries / generation
 * @param	v2 Vector of mean fitness entries / generation
 * @param	indiv Object best individual
 */
public void setData(DialogData data ,TravelData trav, String a, String b, 
		String c, String d, Vector v1, Vector v2, Individual indiv) {

	grid=data.grid;
	largefonts=data.largefonts;
	
    if(largefonts==1)  {
        f = new Font("Monospaced",Font.PLAIN, 12);
    }
    if(largefonts==0)  {
        f = new Font("Monospaced",Font.PLAIN, 11);
    }
    if(largefonts==2)  {
        f = new Font("Monospaced",Font.PLAIN, 13);
    }

    ivjStatisticBestfitLabel.setFont(f);
    ivjStatisticBestindivLabel.setFont(f);
    ivjStatisticMeanfitLabel.setFont(f);
    ivjStatisticGenLabel.setFont(f);
    ivjStatisticMeanLabel.setFont(f);
    ivjStatisticBestLabel.setFont(f);

	ivjStatisticBestfitLabel.setText(a);
	ivjStatisticMeanfitLabel.setText(b);
	ivjStatisticGenLabel.setText(c);
	ivjStatisticBestindivLabel.setText(d);

	int l=v1.size();
    ivjStatisticCanvas.setData(ivjStatisticScrollPane);

	for(int i=0; i<l; i++) {
		ivjStatisticCanvas.setValues( ((Double) v1.elementAt(i)).doubleValue(),
						((Double) v2.elementAt(i)).doubleValue());
	}

	ivjStatisticTravelCanvas.setTravelData(trav, data.showtowns, data.largefonts);
	ivjStatisticTravelCanvas.setTravelIndiv(indiv);
}

/**
 * Return the StatisticBestfitLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestfitLabel() {
    if (ivjStatisticBestfitLabel == null) {
        try {
            ivjStatisticBestfitLabel = new java.awt.Label();
            ivjStatisticBestfitLabel.setName("StatisticBestfitLabel");
            ivjStatisticBestfitLabel.setAlignment(java.awt.Label.LEFT);
            ivjStatisticBestfitLabel.setText("Best Fitness: 0.0");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticBestfitLabel;
}

/**
 * Return the StatisticBestindivLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestindivLabel() {
    if (ivjStatisticBestindivLabel == null) {
        try {
            ivjStatisticBestindivLabel = new java.awt.Label();
            ivjStatisticBestindivLabel.setName("StatisticBestindivLabel");
            ivjStatisticBestindivLabel.setText("The best trip for now:  ");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticBestindivLabel;
}

/**
 * Return the StatisticBestLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticBestLabel() {
    if (ivjStatisticBestLabel == null) {
        try {
            ivjStatisticBestLabel = new java.awt.Label();
            ivjStatisticBestLabel.setName("StatisticBestLabel");
            ivjStatisticBestLabel.setForeground(java.awt.Color.red);
            ivjStatisticBestLabel.setText("Best Fitness");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticBestLabel;
}

/**
 * Return the StatisticCanvas property value.
 * @return StatisticCanvas
 */
private StatisticCanvas getStatisticCanvas() {
    if (ivjStatisticCanvas == null) {
        try {
            ivjStatisticCanvas = new StatisticCanvas();
            ivjStatisticCanvas.setName("StatisticCanvas");
            ivjStatisticCanvas.setBounds(0, 0, 600, 200);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticCanvas;
}

/**
 * Return the StatisticFrameGridLayout property value.
 * @return java.awt.GridLayout
 */
private GridLayout getStatisticFrameGridLayout() {
    java.awt.GridLayout ivjStatisticFrameGridLayout = null;
    try {
        /* Create part */
        ivjStatisticFrameGridLayout = new java.awt.GridLayout();
        ivjStatisticFrameGridLayout.setColumns(2);
    } catch (java.lang.Throwable ivjExc) {
        handleException(ivjExc);
    };
    return ivjStatisticFrameGridLayout;
}

/**
 * Return the StatisticGenLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticGenLabel() {
    if (ivjStatisticGenLabel == null) {
        try {
            ivjStatisticGenLabel = new java.awt.Label();
            ivjStatisticGenLabel.setName("StatisticGenLabel");
            ivjStatisticGenLabel.setText("Generations: 0     ");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticGenLabel;
}

/**
 * Return the StatisticLabelPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticLabelPanel() {
    if (ivjStatisticLabelPanel == null) {
        try {
            ivjStatisticLabelPanel = new java.awt.Panel();
            ivjStatisticLabelPanel.setName("StatisticLabelPanel");
            ivjStatisticLabelPanel.setLayout(getStatisticLabelPanelFlowLayout());
            ivjStatisticLabelPanel.add(getStatisticMeanfitLabel(), getStatisticMeanfitLabel().getName());
            ivjStatisticLabelPanel.add(getStatisticGenLabel(), getStatisticGenLabel().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticLabelPanel;
}

/**
 * Return the StatisticLabelPanel1 property value.
 * @return java.awt.Panel
 */
private Panel getStatisticLabelPanel1() {
    if (ivjStatisticLabelPanel1 == null) {
        try {
            ivjStatisticLabelPanel1 = new java.awt.Panel();
            ivjStatisticLabelPanel1.setName("StatisticLabelPanel");
            ivjStatisticLabelPanel1.setLayout(getStatisticLabelPanelFlowLayout1());
            ivjStatisticLabelPanel1.add(getStatisticMeanLabel(), getStatisticMeanLabel().getName());
            ivjStatisticLabelPanel1.add(getStatisticBestLabel(), getStatisticBestLabel().getName());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticLabelPanel1;
}


/**
 * Return the StatisticLabelPanelFlowLayout property value.
 * @return java.awt.FlowLayout
 */
private FlowLayout getStatisticLabelPanelFlowLayout() {
    java.awt.FlowLayout ivjStatisticLabelPanelFlowLayout = null;
    try {
        /* Create part */
        ivjStatisticLabelPanelFlowLayout = new java.awt.FlowLayout();
        ivjStatisticLabelPanelFlowLayout.setVgap(5);
        ivjStatisticLabelPanelFlowLayout.setHgap(5);
    } catch (java.lang.Throwable ivjExc) {
        handleException(ivjExc);
    };
    return ivjStatisticLabelPanelFlowLayout;
}

/**
 * Return the StatisticLabelPanelFlowLayout1 property value.
 * @return java.awt.FlowLayout
 */
private FlowLayout getStatisticLabelPanelFlowLayout1() {
    java.awt.FlowLayout ivjStatisticLabelPanelFlowLayout1 = null;
    try {
        /* Create part */
        ivjStatisticLabelPanelFlowLayout1 = new java.awt.FlowLayout();
        ivjStatisticLabelPanelFlowLayout1.setVgap(5);
        ivjStatisticLabelPanelFlowLayout1.setHgap(5);
    } catch (java.lang.Throwable ivjExc) {
        handleException(ivjExc);
    };
    return ivjStatisticLabelPanelFlowLayout1;
}


/**
 * Return the StatisticMeanfitLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticMeanfitLabel() {
    if (ivjStatisticMeanfitLabel == null) {
        try {
            ivjStatisticMeanfitLabel = new java.awt.Label();
            ivjStatisticMeanfitLabel.setName("StatisticMeanfitLabel");
            ivjStatisticMeanfitLabel.setText("Mean Fitness: 0.0         ");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticMeanfitLabel;
}

/**
 * Return the StatisticMeanLabel property value.
 * @return java.awt.Label
 */
private Label getStatisticMeanLabel() {
    if (ivjStatisticMeanLabel == null) {
        try {
            ivjStatisticMeanLabel = new java.awt.Label();
            ivjStatisticMeanLabel.setName("StatisticMeanLabel");
            //ivjStatisticMeanLabel.setFont(new java.awt.Font("Dialog", 0, 12));
            ivjStatisticMeanLabel.setForeground(java.awt.Color.green);
            ivjStatisticMeanLabel.setText("Mean Fitness");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticMeanLabel;
}

/**
 * Return the StatisticScrollPane property value.
 * @return java.awt.ScrollPane
 */
private ScrollPane getStatisticScrollPane() {
    if (ivjStatisticScrollPane == null) {
        try {
            ivjStatisticScrollPane = new java.awt.ScrollPane();
            ivjStatisticScrollPane.setName("StatisticScrollPane");
            ivjStatisticScrollPane.add(getStatisticCanvas(), getStatisticCanvas().getName());
            Adjustable vadjust = ivjStatisticScrollPane.getVAdjustable();
            Adjustable hadjust = ivjStatisticScrollPane.getHAdjustable();
            hadjust.setUnitIncrement(10);
            vadjust.setUnitIncrement(10);

            ivjStatisticScrollPane.setSize(300, 300);

        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticScrollPane;
}

/**
 * Return the StatisticStatisticPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticStatisticPanel() {
    if (ivjStatisticStatisticPanel == null) {
        try {
            ivjStatisticStatisticPanel = new java.awt.Panel();
            ivjStatisticStatisticPanel.setName("StatisticStatisticPanel");
            ivjStatisticStatisticPanel.setLayout(new java.awt.BorderLayout());
            getStatisticStatisticPanel().add("North", getStatisticLabelPanel1());
            getStatisticStatisticPanel().add("South", getStatisticLabelPanel());
            getStatisticStatisticPanel().add("Center", getStatisticScrollPane());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticStatisticPanel;
}

/**
 * Return the StatisticTavelPanel property value.
 * @return java.awt.Panel
 */
private Panel getStatisticTavelPanel() {
    if (ivjStatisticTavelPanel == null) {
        try {
            ivjStatisticTavelPanel = new java.awt.Panel();
            ivjStatisticTavelPanel.setName("StatisticTavelPanel");
            ivjStatisticTavelPanel.setLayout(new java.awt.BorderLayout());
            getStatisticTavelPanel().add("South", getStatisticBestfitLabel());
            getStatisticTavelPanel().add("North", getStatisticBestindivLabel());
            getStatisticTavelPanel().add("Center", getStatisticTravelCanvas());
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticTavelPanel;
}

/**
 * Return the StatisticTravelCanvas property value.
 * @return TravelCanvas
 */
private TravelCanvas getStatisticTravelCanvas() {
    if (ivjStatisticTravelCanvas == null) {
        try {
            ivjStatisticTravelCanvas = new TravelCanvas();
            ivjStatisticTravelCanvas.setName("StatisticTravelCanvas");
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjStatisticTravelCanvas;
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
            setName("StatisticFrame");
            setLayout(getStatisticFrameGridLayout());
            setSize(640, 250);
            setTitle("Statistic");
            this.add(getStatisticTavelPanel(), getStatisticTavelPanel().getName());
            this.add(getStatisticStatisticPanel(), getStatisticStatisticPanel().getName());
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
        int stepwidth;
        int stepheight;
        Dimension canvassize;

        canvassize=ivjStatisticTravelCanvas.getSize();
        stepwidth=(int)(canvassize.width/grid);
        stepheight=(int)(canvassize.height/grid);

        if(stepwidth>stepheight) stepwidth=stepheight;
        else stepheight= stepwidth;
        ivjStatisticTravelCanvas.setTravelCanvas(stepwidth,stepheight);
        ivjStatisticCanvas.setChanged();

    }

}
/**
 * Method to handle events for the ComponentListener interface.
 * @param e java.awt.event.ComponentEvent
 */
public void componentShown(java.awt.event.ComponentEvent e) {
}


}
