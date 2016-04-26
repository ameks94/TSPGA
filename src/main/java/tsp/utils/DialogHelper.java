package tsp.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class DialogHelper {
	public static int getIntegerValueDialog(Component parentComponent, String message) {
		String stringResult = JOptionPane.showInputDialog(parentComponent, message);
		int result = 0;
		try {
			result = Integer.parseInt(stringResult);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parentComponent, "Ops, It's not number.");
		}
		return result;
	}
}
