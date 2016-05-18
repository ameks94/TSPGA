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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ConfigCriteriasWindow extends JFrame {

	private JPanel contentPane;
	private GAMainWindow mainWindow;
	private List<City> cities;
	private JTextArea textArea;
	private double[][] distances;
	DecimalFormat df = new DecimalFormat("#");
	
	JButton generateTimes;
	JButton generateCosts;
	
	JCheckBox considerTimeChb;
	JCheckBox considerCostChb;

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

		considerTimeChb = new JCheckBox("Допустима різниця часу");
		considerTimeChb.setSelected(InitialData.considerTimeCriteria);
		considerTimeChb.addActionListener((ActionEvent e) -> {
			generateTimes.setEnabled(((JCheckBox) e.getSource()).isSelected());
			timeWeightTf.setEnabled(((JCheckBox) e.getSource()).isSelected());
		});
		considerTimeChb.setBounds(10, 42, 233, 23);
		contentPane.add(considerTimeChb);

		considerCostChb = new JCheckBox("Допустима різниця вартості");
		considerCostChb.setSelected(InitialData.considerCostCriteria);
		considerCostChb.setBounds(10, 68, 233, 23);
		considerCostChb.addActionListener((ActionEvent e) -> {
			generateCosts.setEnabled(((JCheckBox) e.getSource()).isSelected());
			costWeightTf.setEnabled(((JCheckBox) e.getSource()).isSelected());
		});
		contentPane.add(considerCostChb);

		generateTimes = new JButton("Генерувати час");
		generateTimes.setEnabled(InitialData.considerTimeCriteria);
		generateTimes.addActionListener(generateTimeListener);
		generateTimes.setBounds(345, 41, 181, 23);
		contentPane.add(generateTimes);

		generateCosts = new JButton("Генерувати вартість");
		generateCosts.setEnabled(InitialData.considerCostCriteria);
		generateCosts.addActionListener(generateCostListener);
		generateCosts.setBounds(345, 68, 181, 23);
		contentPane.add(generateCosts);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 98, 690, 439);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		timeWeightTf = new JTextField();
		timeWeightTf.setEnabled(InitialData.considerTimeCriteria);
		timeWeightTf.setText(String.valueOf(InitialData.timeWeight));
		timeWeightTf.setBounds(249, 42, 86, 20);
		contentPane.add(timeWeightTf);
		timeWeightTf.setColumns(10);
		
		costWeightTf = new JTextField();
		costWeightTf.setEnabled(InitialData.considerCostCriteria);
		costWeightTf.setText(String.valueOf(InitialData.costWeight));
		costWeightTf.setBounds(249, 68, 86, 20);
		contentPane.add(costWeightTf);
		costWeightTf.setColumns(10);
		
		distanceWeightTf = new JTextField();
		distanceWeightTf.setText(String.valueOf(InitialData.distanceWeight));
		distanceWeightTf.setColumns(10);
		distanceWeightTf.setBounds(249, 11, 86, 20);
		contentPane.add(distanceWeightTf);
		
		JLabel label = new JLabel("Допустима різниця відстані");
		label.setBounds(29, 15, 214, 14);
		contentPane.add(label);
		
		saveBtn = new JButton("Зберегти");
		saveBtn.setBounds(565, 68, 135, 23);
		saveBtn.addActionListener(applyAction);
		contentPane.add(saveBtn);
	}
	
	private ActionListener applyAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			InitialData.distanceWeight = Double.valueOf(distanceWeightTf.getText());
			InitialData.costWeight = Double.valueOf(costWeightTf.getText());
			InitialData.timeWeight = Double.valueOf(timeWeightTf.getText());
			InitialData.considerCostCriteria = considerCostChb.isSelected();
			InitialData.considerTimeCriteria = considerTimeChb.isSelected();
		}
	};
	
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
	private JScrollPane scrollPane;
	private JTextField timeWeightTf;
	private JTextField costWeightTf;
	private JTextField distanceWeightTf;
	private JButton saveBtn;
}
