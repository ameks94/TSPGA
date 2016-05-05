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
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;

public class GAMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private CitiesPanel panel;
	private JComboBox<AlgorithmType> cbAlgorithm;
	private JLabel lblOptimalTour;
	private JButton btnCalculate;
	
	private ResultWindow resultWindow;
	// private TextArea logArea;
	// private JLabel lblLog;
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

		JPanel ButtonsPanel = new JPanel();
		ButtonsPanel.setBounds(21, 7, 845, 23);
		contentPane.add(ButtonsPanel);
		ButtonsPanel.setLayout(null);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(166, 0, 123, 23);
		ButtonsPanel.add(btnCalculate);
		btnCalculate.setBackground(Color.GREEN);

		JButton btnClear = new JButton("Очистити");
		btnClear.setBounds(299, 0, 95, 23);
		ButtonsPanel.add(btnClear);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(73, 1, 83, 21);
		ButtonsPanel.add(cbAlgorithm);

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(0, 4, 81, 14);
		ButtonsPanel.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.CENTER);

		JButton btnTestchart = new JButton("Тестувати");
		btnTestchart.setBounds(529, 0, 100, 23);
		ButtonsPanel.add(btnTestchart);

		JButton btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(404, 0, 115, 23);
		ButtonsPanel.add(btnGenerate);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(632, 4, 115, 14);
		ButtonsPanel.add(labelConstOptTour);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(762, 0, 83, 23);
		ButtonsPanel.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
				// Panel for drawing
				panel = new CitiesPanel();
				panel.setBounds(17, 41, 853, 634);
				contentPane.add(panel);
				panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
				panel.setBackground(Color.WHITE);
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Кількість міст?");
				int citiesCount = DialogHelper.getIntegerValueDialog(frame, "Кількість міст для генерації:");
				if (citiesCount == 0) {
					return;
				}
				List<City> cities = GeneratorHelper.generateCities(citiesCount, panel.getWidth() - 10,
						panel.getHeight() - 10);
				panel.setCities(cities);
				panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
				panel.setShowCalculation(false);
				panel.repaint();
			}
		});
		btnTestchart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// int citiesCount;
				// JFrame citiesCountFrame = new JFrame("Скільки міст вам
				// потрібно?");
				// citiesCount =
				// DialogHelper.getIntegerValueDialog(citiesCountFrame,
				// "Кількість міст для генерації(не більше 20):");
				//
				// int experimentCount;
				// JFrame experimentCountFrame = new JFrame("Скільки необхідно
				// зробити експерементів?");
				// experimentCount =
				// DialogHelper.getIntegerValueDialog(experimentCountFrame,
				// "Кількість експерементів:");
				//
				// Tour testTour = new
				// Tour(GeneratorHelper.generateCities(citiesCount, 500, 500));
				// Result averageGAResult = getAverageResult(AlgorithmType.GA,
				// testTour, experimentCount);
				// Result averageGreedyResult =
				// getAverageResult(AlgorithmType.GREEDY, testTour,
				// experimentCount);
				// Result averageBranchBoundResult =
				// getAverageResult(AlgorithmType.BRANCH_BOUND, testTour,
				// experimentCount);
				//
				// System.out.println("GA час:" +
				// averageGAResult.averageTimeSec);
				// System.out.println("Greedy час:" +
				// averageGreedyResult.averageTimeSec);
				// System.out.println("BranchBound час:" +
				// averageBranchBoundResult.averageTimeSec);
				// AccuracyChart chart1 = new AccuracyChart("Графік № 1",
				// "Точність (Кількість знаходжень оптимального рішення)",
				// averageGAResult, averageGreedyResult,
				// averageBranchBoundResult);
				// TimeChart chart2 = new TimeChart("Графік № 2", "Час",
				// averageGAResult, averageGreedyResult,
				// averageBranchBoundResult);
				//
				// chart1.pack( );
				// RefineryUtilities.centerFrameOnScreen( chart1 );
				// chart1.setVisible( true );
				//
				// chart2.pack( );
				// RefineryUtilities.centerFrameOnScreen( chart2 );
				// chart2.setVisible( true );
			}
		});
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.clearAll();
				panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
				if (resultWindow != null && resultWindow.isShowing()) {
					resultWindow.hide();
				}
				panel.setShowCalculation(false);
				panel.repaint();
			}
		});
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getCities() == null || getCities().isEmpty())
					return;
				if (btnCalculate.getText().equals(startCalculationText)) {
					setCalculated(false);
					panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));
					panel.setShowCalculation(true);
					
					if (resultWindow != null && resultWindow.isShowing()) {
						resultWindow.hide();
					}
					resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());
					resultWindow.setVisible(true);
					
					AlgorithmType selectedType = (AlgorithmType) cbAlgorithm.getSelectedItem();

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
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}
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

	public DefaultListModel getTextArea() {
		return resultWindow.getResultModel();
	}

	public GAMainWindow getMainWindow() {
		return this;
	}
}
