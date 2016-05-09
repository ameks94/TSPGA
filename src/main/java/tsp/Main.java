package tsp;


import java.awt.EventQueue;

import tsp.ui.GAMainWindow;

public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GAMainWindow frame = new GAMainWindow();
					frame.setLocation(0, 0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
