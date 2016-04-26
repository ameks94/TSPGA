package ga2;
import java.awt.*;
import java.applet.*;

/**
 * This class is to set generations and the update function. This dialog
 * gets activiated from the TSPGA frame. 
 *
 * coding:   h.sarg
 * modified: 01/98
 */
public class RunDialog extends java.awt.Dialog implements java.awt.event.ActionListener, java.awt.event.WindowListener {
	private java.awt.Panel ivjRunPanel2 = null;
	private java.awt.Button ivjRunClose = null;
	private java.awt.Label ivjRunGenLabel = null;
	private java.awt.Button ivjRunOk = null;
	private java.awt.Panel ivjRunPanel1 = null;
	private IntTextField ivjRunText = null;
	private Applet ivjTSPGA = null;
	private Checkbox ivjOptionsCheckbox = null;

/**
 * This Constructor initialzes the values in this dialog and stores the caller.
 * @param 	parent is the applet TSPGA
 * @param 	u information object
 */
public RunDialog(Applet parent, ConnectInfo u) {
	super(new Frame(), "Connect", true);
	ivjTSPGA=parent;
	initialize();
	ivjRunText.setText(""+ u.value);
	ivjOptionsCheckbox.setState(u.ok);
}

/**
 * RunDialog constructor comment.
 * @param parent Symbol
 */
public RunDialog(java.awt.Frame parent) {
	super(parent);
	initialize();
}

/**
 * Method to handle events for the ActionListener interface.
 * @param e java.awt.event.ActionEvent
 */
public void actionPerformed(java.awt.event.ActionEvent e) {
	if ((e.getSource() == getRunClose()) ) {
		dispose();
	}
	if ((e.getSource() == getRunOk()) ) {
		if(ivjRunText.isValid()) {
			dispose();
			((ResultProcessor)ivjTSPGA).processResult(this,
		new ConnectInfo(ivjOptionsCheckbox.getState(),ivjRunText.getValue()));
			//conn2(e);
		}
	}
}

/**
 * conn0:  (RunDialog.window.windowClosing(java.awt.event.WindowEvent) --> RunDialog.dispose())
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
 * conn1:  (RunClose.action.actionPerformed(java.awt.event.ActionEvent) --> RunDialog.dispose())
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
 * conn2:  (RunOk.action.actionPerformed(java.awt.event.ActionEvent) --> RunDialog.dispose())
 * @param arg1 java.awt.event.ActionEvent
 */
private void conn2(java.awt.event.ActionEvent arg1) {
	try {
		this.dispose();
	} catch (java.lang.Throwable ivjExc) {
		handleException(ivjExc);
	}
}

/**
 * Return the OptionsCheckbox property value.
 * @return java.awt.Checkbox
 */
private Checkbox getOptionsCheckbox() {
    if (ivjOptionsCheckbox == null) {
        try {
            ivjOptionsCheckbox = new java.awt.Checkbox();
            ivjOptionsCheckbox.setName("OptionsCheckbox");
            ivjOptionsCheckbox.setLabel("Update every generation");
            ivjOptionsCheckbox.setState(true);
        } catch (java.lang.Throwable ivjExc) {
            handleException(ivjExc);
        }
    };
    return ivjOptionsCheckbox;
}


/**
 * Return the Panel1 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getRunPanel2() {
	if (ivjRunPanel2 == null) {
		try {
			ivjRunPanel2 = new java.awt.Panel();
			ivjRunPanel2.setName("Panel1");
			ivjRunPanel2.setLayout(new java.awt.FlowLayout());
			ivjRunPanel2.add(getRunGenLabel(), getRunGenLabel().getName());
			ivjRunPanel2.add(getRunText(), getRunText().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunPanel2;
}

/**
 * Return the RunClose property value.
 * @return java.awt.Button
 */
private java.awt.Button getRunClose() {
	if (ivjRunClose == null) {
		try {
			ivjRunClose = new java.awt.Button();
			ivjRunClose.setName("RunClose");
			ivjRunClose.setLabel("Cancel");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunClose;
}

/**
 * Return the RunGenLabel property value.
 * @return java.awt.Label
 */
private java.awt.Label getRunGenLabel() {
	if (ivjRunGenLabel == null) {
		try {
			ivjRunGenLabel = new java.awt.Label();
			ivjRunGenLabel.setName("RunGenLabel");
			ivjRunGenLabel.setText("Generations:");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunGenLabel;
}

/**
 * Return the RunOk property value.
 * @return java.awt.Button
 */
private java.awt.Button getRunOk() {
	if (ivjRunOk == null) {
		try {
			ivjRunOk = new java.awt.Button();
			ivjRunOk.setName("RunOk");
			ivjRunOk.setLabel("OK");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunOk;
}

/**
 * Return the RunPanel1 property value.
 * @return java.awt.Panel
 */
private java.awt.Panel getRunPanel1() {
	if (ivjRunPanel1 == null) {
		try {
			ivjRunPanel1 = new java.awt.Panel();
			ivjRunPanel1.setName("RunPanel1");
			ivjRunPanel1.setLayout(new java.awt.FlowLayout());
			ivjRunPanel1.add(getRunOk(), getRunOk().getName());
			ivjRunPanel1.add(getRunClose(), getRunClose().getName());
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunPanel1;
}

/**
 * Return the RunText property value.
 * @return java.awt.TextField
 */
private java.awt.TextField getRunText() {
	if (ivjRunText == null) {
		try {
			ivjRunText = new IntTextField(100,1,100000,7);
			ivjRunText.setName("RunText");
			//ivjRunText.setText("2000");
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	};
	return ivjRunText;
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
	getRunClose().addActionListener(this);
	getRunOk().addActionListener(this);
}

/**
 * Initialize class
 */
private void initialize() {
	setName("RunDialog");
	setName("RunDialog");
	setLayout(new java.awt.BorderLayout());
	setSize(250, 120);
	setModal(true);
	setTitle("Run");
	this.add("North", getOptionsCheckbox());
	this.add("South", getRunPanel1());
	this.add("Center", getRunPanel2());
	initConnections();
}

/**
 * main entrypoint - starts here when it is run as an application
 * @param args java.lang.String[]
 */
public static void main(java.lang.String[] args) {
	try {
		RunDialog aRunDialog = new RunDialog(new java.awt.Frame());
		aRunDialog.setModal(true);
		try {
			Class aCloserClass = Class.forName("uvm.abt.edit.WindowCloser");
			Class parmTypes[] = { java.awt.Window.class };
			Object parms[] = { aRunDialog };
			java.lang.reflect.Constructor aCtor = aCloserClass.getConstructor(parmTypes);
			aCtor.newInstance(parms);
		} catch (java.lang.Throwable exc) {};
		aRunDialog.setVisible(true);
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

