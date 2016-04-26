package ga2;
/**
 * This class is about the origin of this JAVA program.  This Dialog
 * gets activated from the TSPGA frame.
 * 
 * coding:	 h.sarg
 * modified: 01/98	
 */
public class AboutDialog extends java.awt.Dialog implements java.awt.event.ActionListener, java.awt.event.WindowListener {
	private java.awt.Button ivjCloseButton = null;
	private java.awt.Label ivjHeaderLabel = null;
	private java.awt.Label ivjVersionLabel = null;
	private java.awt.Label ivjNameLabel = null;
	private java.awt.Panel ivjHeaderPanel = null;
	private java.awt.Panel ivjClosePanel = null;

/**
 * Constructor
 * @param parent Symbol
 */
public AboutDialog(java.awt.Frame parent) {
	super(parent);
	initialize();
}

/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
public void actionPerformed(java.awt.event.ActionEvent e) {
	if ((e.getSource() == getCloseButton()) ) {
		conn1(e);
	}
}

/**
 * conn0:  (AboutDialog.window.windowClosing(java.awt.event.WindowEvent) --> AboutDialog.dispose())
 * @param arg1 java.awt.event.WindowEvent
 */
private void conn0(java.awt.event.WindowEvent arg1) {
	try {
		this.dispose();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * conn1:  (CloseButton.action.actionPerformed(java.awt.event.ActionEvent) --> AboutDialog.dispose())
 * @param arg1 java.awt.event.ActionEvent
 */
private void conn1(java.awt.event.ActionEvent arg1) {
	try {
		this.dispose();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * Return the CloseButton property value.
 * @return java.awt.Button
 */
private java.awt.Button getCloseButton() {
	if (ivjCloseButton == null) {
		try {
			ivjCloseButton = new java.awt.Button();
			ivjCloseButton.setName("CloseButton");
			ivjCloseButton.setLabel("Close");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjCloseButton;
}

/**
 * Return the HeaderLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getHeaderLabel() {
	if (ivjHeaderLabel == null) {
		try {
			ivjHeaderLabel = new java.awt.Label();
			ivjHeaderLabel.setName("HeaderLabel");
			ivjHeaderLabel.setFont(new java.awt.Font("dialog", 1, 12));
			ivjHeaderLabel.setText("Traveling Salesman Problem Demonstration");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjHeaderLabel;
}

/**
 * Return the VersionLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getVersionLabel() {
	if (ivjVersionLabel == null) {
		try {
			ivjVersionLabel = new java.awt.Label();
			ivjVersionLabel.setName("VersionLabel");
			ivjVersionLabel.setText("version 1.0");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjVersionLabel;
}

/**
 * Return the NameLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getNameLabel() {
	if (ivjNameLabel == null) {
		try {
			ivjNameLabel = new java.awt.Label();
			ivjNameLabel.setName("NameLabel");
			ivjNameLabel.setText("1998 by Johannes Sarg");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjNameLabel;
}

/**
 * Return the HeaderPanel property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getHeaderPanel() {
	if (ivjHeaderPanel == null) {
		try {
			ivjHeaderPanel = new java.awt.Panel();
			ivjHeaderPanel.setName("HeaderPanel");
			ivjHeaderPanel.setLayout(new java.awt.FlowLayout());
			ivjHeaderPanel.add(getHeaderLabel(), getHeaderLabel().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjHeaderPanel;
}

/**
 * Return the ClosePanel property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getClosePanel() {
	if (ivjClosePanel == null) {
		try {
			ivjClosePanel = new java.awt.Panel();
			ivjClosePanel.setName("ClosePanel");
			ivjClosePanel.setLayout(new java.awt.FlowLayout());
			ivjClosePanel.add(getCloseButton(), getCloseButton().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjClosePanel;
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
	getCloseButton().addActionListener(this);
}

/**
 * Initialize class
 */
private void initialize() {
	setName("AboutDialog");
	setName("AboutDialog");
	setTitle("About");
	setLayout(new java.awt.BorderLayout());
	setSize(350, 130);
	setModal(true);
	setResizable(false);
	this.add("North", getHeaderPanel());
	this.add("South", getClosePanel());
	this.add("Center", getNameLabel());
	this.add("West", getVersionLabel());
	initConnections();
}

/**
 * main entrypoint - starts here when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		AboutDialog aAboutDialog = new AboutDialog(new java.awt.Frame());
		aAboutDialog.setModal(true);
		try {
			Class aCloserClass = Class.forName("uvm.abt.edit.WindowCloser");
			Class parmTypes[] = { java.awt.Window.class };
			Object parms[] = { aAboutDialog };
			java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			aCtor.newInstance(parms);
		} catch (java.lang.Throwable exc) {};
		aAboutDialog.setVisible(true);
	} catch (Throwable exception) {
		System.err.println("Exception occurred in main() of java.awt.Dialog");
	}
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
	if ((e.getSource() == this) ) {
		conn0(e);
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

}

