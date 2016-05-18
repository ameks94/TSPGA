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
import tsp.utils.DialogHelper;

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

		considerCostChb = new JCheckBox("Допустима різниця вартості");
		considerCostChb.setSelected(InitialData.considerCostCriteria);
		considerCostChb.setBounds(10, 38, 233, 23);
		considerCostChb.addActionListener((ActionEvent e) -> {
			generateCosts.setEnabled(((JCheckBox) e.getSource()).isSelected());
			costWeightTf.setEnabled(((JCheckBox) e.getSource()).isSelected());
		});
		contentPane.add(considerCostChb);

		generateCosts = new JButton("Генерувати вартість");
		generateCosts.setEnabled(InitialData.considerCostCriteria);
		generateCosts.addActionListener(generateCostListener);
		generateCosts.setBounds(345, 38, 181, 23);
		contentPane.add(generateCosts);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 690, 469);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		costWeightTf = new JTextField();
		costWeightTf.setEnabled(InitialData.considerCostCriteria);
		costWeightTf.setText(String.valueOf(InitialData.costAllowRange));
		costWeightTf.setBounds(249, 38, 86, 20);
		contentPane.add(costWeightTf);
		costWeightTf.setColumns(10);

		distanceWeightTf = new JTextField();
		distanceWeightTf.setText(String.valueOf(InitialData.distanceAllowRange));
		distanceWeightTf.setColumns(10);
		distanceWeightTf.setBounds(249, 11, 86, 20);
		contentPane.add(distanceWeightTf);

		JLabel label = new JLabel("Допустима різниця відстані");
		label.setBounds(29, 15, 214, 14);
		contentPane.add(label);

		saveBtn = new JButton("Зберегти");
		saveBtn.setBounds(565, 38, 135, 23);
		saveBtn.addActionListener(applyAction);
		contentPane.add(saveBtn);

		panel = new JPanel();
		panel.setBounds(20, 98, 516, 24);
		contentPane.add(panel);
		panel.setLayout(null);

		generateTimes = new JButton("Генерувати час");
		generateTimes.setBounds(335, 0, 181, 23);
		panel.add(generateTimes);
		generateTimes.setEnabled(InitialData.considerTimeCriteria);

		timeWeightTf = new JTextField();
		timeWeightTf.setBounds(239, 1, 86, 20);
		panel.add(timeWeightTf);
		timeWeightTf.setEnabled(InitialData.considerTimeCriteria);
		timeWeightTf.setText(String.valueOf(InitialData.timeAllowRange));
		timeWeightTf.setColumns(10);

		considerTimeChb = new JCheckBox("Допустима різниця часу");
		considerTimeChb.setBounds(0, 1, 233, 23);
		panel.add(considerTimeChb);
		considerTimeChb.setSelected(InitialData.considerTimeCriteria);
		considerTimeChb.addActionListener((ActionEvent e) -> {
			generateTimes.setEnabled(((JCheckBox) e.getSource()).isSelected());
			timeWeightTf.setEnabled(((JCheckBox) e.getSource()).isSelected());
		});
		generateTimes.addActionListener(generateTimeListener);
	}

	private ActionListener applyAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			InitialData.distanceAllowRange = Double.valueOf(distanceWeightTf.getText());
			InitialData.costAllowRange = Double.valueOf(costWeightTf.getText());
			InitialData.timeAllowRange = Double.valueOf(timeWeightTf.getText());
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
					double distance = distances[i][j];
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
					double distance = distances[i][j];
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
	private JPanel panel;
}
