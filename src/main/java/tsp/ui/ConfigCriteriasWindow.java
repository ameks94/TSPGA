package tsp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithm;

public class ConfigCriteriasWindow extends JFrame {

	private JPanel contentPane;
	private GAMainWindow mainWindow;
	private List<City> cities;
	private JTextArea textArea;
	private double[][] distances;
	DecimalFormat df = new DecimalFormat("#");
	
	JButton generateTimes;
	JButton generateCosts;

	/**
	 * Create the frame.
	 */
	public ConfigCriteriasWindow(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		cities = mainWindow.getCities();
		distances = TSAlgorithm.calculateDistances(cities);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 726, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JCheckBox considerTimeChb = new JCheckBox("Враховувати час");
		considerTimeChb.setSelected(InitialData.considerTimeCriteria);
		considerTimeChb.addActionListener((ActionEvent e) -> {
			InitialData.considerTimeCriteria = ((JCheckBox) e.getSource()).isSelected();
			generateTimes.setEnabled(InitialData.considerTimeCriteria);
		});
		considerTimeChb.setBounds(6, 7, 171, 23);
		contentPane.add(considerTimeChb);

		JCheckBox considerCostChb = new JCheckBox("Враховувати вартість");
		considerCostChb.setSelected(InitialData.considerCostCriteria);
		considerCostChb.setBounds(6, 33, 171, 23);
		considerCostChb.addActionListener((ActionEvent e) -> {
			InitialData.considerCostCriteria = ((JCheckBox) e.getSource()).isSelected();
			generateCosts.setEnabled(InitialData.considerCostCriteria);
		});
		contentPane.add(considerCostChb);

		generateTimes = new JButton("Генерувати час");
		generateTimes.setEnabled(InitialData.considerTimeCriteria);
		generateTimes.addActionListener(generateTimeListener);
		generateTimes.setBounds(196, 7, 183, 23);
		contentPane.add(generateTimes);

		generateCosts = new JButton("Генерувати вартість");
		generateCosts.setEnabled(InitialData.considerCostCriteria);
		generateCosts.addActionListener(generateCostListener);
		generateCosts.setBounds(198, 33, 181, 23);
		contentPane.add(generateCosts);

		textArea = new JTextArea();
		textArea.setBounds(10, 63, 690, 474);
		contentPane.add(textArea);
	}
	
	private ActionListener generateTimeListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			int size = cities.size();
			double[][] times = new double[size][size];
			for (int i = 0; i < size; i++) {
				String row = "";
				for (int j = 0; j < size; j++) {
					double distance  = distances[i][j];
					int sign = i % 2 == 0 ? -1 : 1;
					times[i][j] = distance + sign * 0.1 * distance;
					row += df.format(times[i][j]) + "  ";
				}
				textArea.append(row + "\n");
			}

			InitialData.times = times;
		}
	};
	
	private ActionListener generateCostListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			int size = cities.size();
			double[][] costs = new double[size][size];
			for (int i = 0; i < size; i++) {
				String row = "";
				for (int j = 0; j < size; j++) {
					double distance  = distances[i][j];
					int sign = i % 3 == 0 ? -1 : 1;
					costs[i][j] = distance + sign * 0.15 * distance;
					row += df.format(costs[i][j]) + "  ";
				}
				textArea.append(row + "\n");
			}

			InitialData.costs = costs;
		}
	};
}
