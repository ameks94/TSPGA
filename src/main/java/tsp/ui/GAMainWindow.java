package tsp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.TSAlgorithmFactory;
import tsp.algorithms.TSAlgorithmFactory.AlgorithmType;
import tsp.ui.DrawerFactory.DrawerType;
import tsp.utils.DialogHelper;
import tsp.utils.GeneratorHelper;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.DropMode;

public class GAMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private CitiesPanel panel;
	private JComboBox<AlgorithmType> cbAlgorithm;
	private JLabel lblOptimalTour;
	private JButton btnCalculate;
	private JTextArea textArea;
	//	private TextArea logArea;
	//	private JLabel lblLog;
	private final String startCalculationText = "Розрахувати";
	private final String stopCalculationText = "Зупинити обрахунок";
	private Thread runningAlgorythm;


	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 903, 724);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel for drawing
		panel = new CitiesPanel();
		panel.setBounds(12, 41, 855, 417);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridLayout(1, 2));
		contentPane.add(panel);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBackground(Color.GREEN);
		btnCalculate.setBounds(166, 7, 123, 23);
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnCalculate.getText().equals(startCalculationText)) {
					setCalculated(false);
					panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));
					panel.setShowCalculation(true);
					AlgorithmType selectedType = (AlgorithmType)cbAlgorithm.getSelectedItem();

					TSAlgorithm algorithm = TSAlgorithmFactory.getTSAlgorithm(selectedType, getMainWindow());
					runningAlgorythm = new Thread(algorithm);
					runningAlgorythm.start();
					
					
				} else {
					setCalculated(true);
					if (runningAlgorythm != null) {
						runningAlgorythm.stop();

					}
					
				}
			}
		});
		contentPane.add(btnCalculate);

		JButton btnClear = new JButton("Очистити");
		btnClear.setBounds(299, 7, 95, 23);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.clearAll();
				panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
				textArea.setText("");
				panel.setShowCalculation(false);
				panel.repaint();
			}
		});
		contentPane.add(btnClear);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(73, 8, 83, 21);
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}
		contentPane.add(cbAlgorithm);

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(0, 11, 81, 14);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAlgorithm);

		JButton btnTestchart = new JButton("Тестувати");
		btnTestchart.setBounds(529, 7, 100, 23);
		btnTestchart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				int citiesCount;
//				JFrame citiesCountFrame = new JFrame("Скільки міст вам потрібно?");
//				citiesCount = DialogHelper.getIntegerValueDialog(citiesCountFrame, "Кількість міст для генерації(не більше 20):");
//
//				int experimentCount;
//				JFrame experimentCountFrame = new JFrame("Скільки необхідно зробити експерементів?");
//				experimentCount = DialogHelper.getIntegerValueDialog(experimentCountFrame, "Кількість експерементів:");
//
//				Tour testTour = new Tour(GeneratorHelper.generateCities(citiesCount, 500, 500));
//				Result averageGAResult = getAverageResult(AlgorithmType.GA, testTour, experimentCount);
//				Result averageGreedyResult = getAverageResult(AlgorithmType.GREEDY, testTour, experimentCount);
//				Result averageBranchBoundResult = getAverageResult(AlgorithmType.BRANCH_BOUND, testTour, experimentCount);
//
//				System.out.println("GA час:" + averageGAResult.averageTimeSec);
//				System.out.println("Greedy час:" + averageGreedyResult.averageTimeSec);
//				System.out.println("BranchBound час:" + averageBranchBoundResult.averageTimeSec);
//				AccuracyChart chart1 = new AccuracyChart("Графік № 1", "Точність (Кількість знаходжень оптимального рішення)", averageGAResult, averageGreedyResult, averageBranchBoundResult);
//				TimeChart chart2 = new TimeChart("Графік № 2", "Час", averageGAResult, averageGreedyResult, averageBranchBoundResult);
//
//				chart1.pack( );   
//				RefineryUtilities.centerFrameOnScreen( chart1 ); 
//				chart1.setVisible( true );
//
//				chart2.pack( );   
//				RefineryUtilities.centerFrameOnScreen( chart2 ); 
//				chart2.setVisible( true );
			}
		});
		contentPane.add(btnTestchart);

		JButton btnGenerate = new JButton("Генерувати");
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Кількість міст?");
				int citiesCount = DialogHelper.getIntegerValueDialog(frame, "Кількість міст для генерації:");
				if (citiesCount == 0) {
					return;
				}
				List<City> cities = GeneratorHelper.generateCities(citiesCount, panel.getWidth() - 10, panel.getHeight() - 10);
				panel.setCities(cities);
				panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
				panel.setShowCalculation(false);
				panel.repaint();
			}
		});
		btnGenerate.setBounds(404, 7, 115, 23);
		contentPane.add(btnGenerate);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(632, 11, 115, 14);
		contentPane.add(labelConstOptTour);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOptimalTour.setBounds(752, 7, 115, 23);
		contentPane.add(lblOptimalTour);
		
		JLabel label = new JLabel("Вивід результатів");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(1031, 11, 155, 14);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 469, 855, 214);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setSelectionColor(Color.GRAY);
	}
	
	public void setCalculated(boolean calculated) {
		if (calculated) {
			btnCalculate.setText(startCalculationText);
			btnCalculate.setBackground(Color.GREEN);
		} else {
			btnCalculate.setText(stopCalculationText);
			btnCalculate.setBackground(Color.RED);
		}
	}
	
	public List<City> getCities() {
		return panel.getCities();
	}
	
	public JLabel getOutputLable() {
		return lblOptimalTour;
	}
	
	public CitiesPanel getOutputPanel() {
		return panel;
	}
	
	public JButton getCalculateBtn() {
		return btnCalculate;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public GAMainWindow getMainWindow() {
		return this;
	}
}
