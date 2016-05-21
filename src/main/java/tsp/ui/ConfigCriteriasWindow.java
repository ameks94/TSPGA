package tsp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithm;
import tsp.utils.FileHelper;
import tsp.utils.SerializationHelper;

public class ConfigCriteriasWindow extends JFrame {

	private JPanel contentPane;
	private GAMainWindow mainWindow;
	private List<City> cities;
	private JTextArea textArea;
	private double[][] distances;
	DecimalFormat df = new DecimalFormat("#");
	JButton generateCosts;
	JCheckBox considerCostChb;

	/**
	 * Create the frame.
	 */
	public ConfigCriteriasWindow(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		cities = mainWindow.getCities();
		distances = TSAlgorithm.calculateDistances(cities);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		considerCostChb = new JCheckBox("Допустима різниця вартості");
		considerCostChb.setSelected(InitialData.considerCostCriteria);
		considerCostChb.setBounds(6, 38, 207, 23);
		considerCostChb.addActionListener((ActionEvent e) -> {
			generateCosts.setEnabled(((JCheckBox) e.getSource()).isSelected());
			costWeightTf.setEnabled(((JCheckBox) e.getSource()).isSelected());
		});
		contentPane.add(considerCostChb);

		generateCosts = new JButton("Генерувати вартість");
		generateCosts.setEnabled(InitialData.considerCostCriteria);
		generateCosts.addActionListener(generateCostListener);
		generateCosts.setBounds(66, 103, 181, 23);
		contentPane.add(generateCosts);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 305, 205);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		costWeightTf = new JTextField();
		costWeightTf.setEnabled(InitialData.considerCostCriteria);
		costWeightTf.setText(String.valueOf(InitialData.costAllowRange));
		costWeightTf.setBounds(219, 39, 86, 20);
		contentPane.add(costWeightTf);
		costWeightTf.setColumns(10);

		distanceWeightTf = new JTextField();
		distanceWeightTf.setText(String.valueOf(InitialData.distanceAllowRange));
		distanceWeightTf.setColumns(10);
		distanceWeightTf.setBounds(219, 11, 86, 20);
		contentPane.add(distanceWeightTf);

		JLabel label = new JLabel("Допустима різниця відстані");
		label.setBounds(10, 14, 201, 14);
		contentPane.add(label);

		saveBtn = new JButton("Зберегти у файл");
		saveBtn.setBounds(182, 69, 135, 23);
		saveBtn.addActionListener(applyAction);
		contentPane.add(saveBtn);

		JButton button = new JButton("Загрузити з файлу");
		button.setBounds(6, 69, 166, 23);
		contentPane.add(button);
	}

	private ActionListener applyAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			InitialData.distanceAllowRange = Double.valueOf(distanceWeightTf.getText());
			InitialData.costAllowRange = Double.valueOf(costWeightTf.getText());
			// InitialData.timeAllowRange =
			// Double.valueOf(timeWeightTf.getText());
			InitialData.considerCostCriteria = considerCostChb.isSelected();
			// InitialData.considerTimeCriteria = considerTimeChb.isSelected();

			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(FileHelper.defDirForCosts));
			int ret = fileopen.showSaveDialog(contentPane);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileopen.getSelectedFile().getAbsolutePath());
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
					int size = InitialData.costs.length;
					for (int i = 0; i < size; i++) {
						for (int j = 0; j < size; j++) {
							writer.write(df.format(InitialData.costs[i][j]) + " ");
						}
						writer.newLine();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};

	private ActionListener generateTimeListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			textArea.setText("");
			int size = cities.size();
			double[][] times = new double[size][size];
			
			for (int i = 0; i < size; i++)// заполняю верхнюю триугольную матрицу
			{
				String row = "";
				for (int j = i + 1; j < size; j++) {
					double distance = distances[i][j];
					int sign = i % 2 == 0 ? -1 : 1;
					times[i][j] = distance + sign * 0.1 * distance;
					times[j][i] = times[i][j];
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
			for (int i = 0; i < size; i++)// заполняю верхнюю триугольную матрицу
			{
				
				for (int j = i + 1; j < size; j++) {
					double distance = distances[i][j];
					int sign = i % 2 == 0 ? -1 : 1;
					costs[i][j] = distance + sign * 0.1 * distance;
					costs[j][i] = costs[i][j];
				}
			}
			
			for (int i = 0; i < size; i++) {
				String row = "";
				for (int j = 0; j < size; j++) {
					row += df.format(costs[i][j]) + "  ";
				}
				textArea.append(row + "\n");
			}

			InitialData.costs = costs;
		}
	};
	private JScrollPane scrollPane;
	private JTextField costWeightTf;
	private JTextField distanceWeightTf;
	private JButton saveBtn;
}
