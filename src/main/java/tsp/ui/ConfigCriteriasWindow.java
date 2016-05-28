package tsp.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
		setTitle("Налаштування критеріїв");
		this.mainWindow = mainWindow;
		cities = mainWindow.getCities();
		distances = TSAlgorithm.calculateDistances(cities);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 389, 381);
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
		generateCosts.setBounds(6, 103, 166, 23);
		contentPane.add(generateCosts);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 353, 205);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		costWeightTf = new JTextField();
		costWeightTf.setEnabled(InitialData.considerCostCriteria);
		costWeightTf.setText(String.valueOf(InitialData.costAllowRange));
		costWeightTf.setBounds(218, 39, 86, 20);
		contentPane.add(costWeightTf);
		costWeightTf.setColumns(10);

		distanceWeightTf = new JTextField();
		distanceWeightTf.setText(String.valueOf(InitialData.distanceAllowRange));
		distanceWeightTf.setColumns(10);
		distanceWeightTf.setBounds(218, 11, 86, 20);
		contentPane.add(distanceWeightTf);

		JLabel label = new JLabel("Допустима різниця відстані");
		label.setBounds(10, 14, 201, 14);
		contentPane.add(label);

		saveBtn = new JButton("Зберегти у файл");
		saveBtn.setBounds(182, 70, 181, 23);
		saveBtn.addActionListener(saveAction);
		contentPane.add(saveBtn);

		JButton loadBtn = new JButton("Завантажити з файлу");
		loadBtn.setBounds(6, 69, 166, 23);
		loadBtn.addActionListener(loadDataAction);
		contentPane.add(loadBtn);
		
		button = new JButton("Прийняти налаштування");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InitialData.distanceAllowRange = Double.valueOf(distanceWeightTf.getText());
				InitialData.costAllowRange = Double.valueOf(costWeightTf.getText());
				// InitialData.timeAllowRange =
				// Double.valueOf(timeWeightTf.getText());
				InitialData.considerCostCriteria = considerCostChb.isSelected();
				// InitialData.considerTimeCriteria = considerTimeChb.isSelected();
			}
		});
		button.setBounds(182, 103, 181, 23);
		contentPane.add(button);
	}

	private ActionListener saveAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(FileHelper.defDirForCosts));
			int ret = fileopen.showSaveDialog(contentPane);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileopen.getSelectedFile().getAbsolutePath());
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
					int size = InitialData.costs.length;
					writer.write(String.valueOf(size));
					writer.newLine();
					
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
	
	private ActionListener loadDataAction = new ActionListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(FileHelper.defDirForCosts));
			int ret = fileopen.showOpenDialog(contentPane);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileopen.getSelectedFile().getAbsolutePath());
				try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
					String line = reader.readLine();
					int size = Integer.valueOf(line);
					double[][] costs = new double[size][size];
					
					for (int i = 0; i < size; i++) {
						line = reader.readLine();
						String[] values = line.split(" ");
						String row = "";
						for (int j = 0; j < size; j++) {
							costs[i][j] = Double.valueOf(values[j]);
							row += df.format(costs[i][j]) + "  ";
						}
						textArea.append(row + "\n");
					}
					
					InitialData.costs = costs;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
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
	private JButton button;
}
