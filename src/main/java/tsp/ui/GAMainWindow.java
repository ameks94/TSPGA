package tsp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jfree.ui.RefineryUtilities;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.TSAlgorithmFactory;
import tsp.algorithms.TSAlgorithmFactory.AlgorithmType;
import tsp.ui.charts.TourChart;
import tsp.ui.drawers.DrawerFactory;
import tsp.ui.drawers.DrawerFactory.DrawerType;
import tsp.utils.GeneratorHelper;

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
	private TSAlgorithm algorithm;
	private JCheckBox showDrawingChbx;
	private JTextField citiesCountTf;
	private JTextField maxIterationCountTf;
	private JTextField maxAffectIterationCountTf;
	private JTextField minPathImprovingTf;
	private JTextField currentIterationTf;
	private JTextField mutationRateTf;
	private JTextField populationCountTf;
	private JTextField tournamentSizeTf;

	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 688);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel for drawing
		panel = new CitiesPanel();
		panel.setMainWindow(this);
		panel.setBounds(10, 11, 657, 634);
		contentPane.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
//		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//		panel.set

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(677, 48, 83, 14);
		contentPane.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.LEFT);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(793, 45, 131, 21);
		contentPane.add(cbAlgorithm);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(677, 297, 247, 23);
		btnCalculate.setEnabled(false);
		contentPane.add(btnCalculate);
		btnCalculate.setBackground(Color.GREEN);

		JButton btnClear = new JButton("Очистити");
		btnClear.setBounds(677, 331, 247, 23);
		contentPane.add(btnClear);

		JButton btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(677, 263, 247, 23);
		contentPane.add(btnGenerate);

		JButton btnTestchart = new JButton("Графік результатів");
		btnTestchart.setBounds(677, 365, 247, 23);
		contentPane.add(btnTestchart);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setHorizontalAlignment(SwingConstants.LEFT);
		labelConstOptTour.setBounds(677, 561, 115, 14);
		contentPane.add(labelConstOptTour);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(828, 554, 83, 21);
		contentPane.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));

		showDrawingChbx = new JCheckBox("Показати результати");
		showDrawingChbx.setHorizontalAlignment(SwingConstants.RIGHT);
		showDrawingChbx.setSelected(true);
		showDrawingChbx.setBounds(714, 172, 151, 23);
		contentPane.add(showDrawingChbx);
		btnTestchart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				final TourChart demo = new TourChart("Мінімальний шлях по ітераціям", algorithm.getIterationDistances());
		        demo.pack();
		        RefineryUtilities.centerFrameOnScreen(demo);
		        demo.setVisible(true);
			}
		});

		btnGenerate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				JFrame frame = new JFrame("Кількість міст?");
				int citiesCount = Integer.valueOf(citiesCountTf.getText());
				if (citiesCount == 0) {
					return;
				}
				List<City> cities = GeneratorHelper.generateCities(citiesCount, panel.getWidth() - 10,
						panel.getHeight() - 10);
				panel.setCities(cities);
				panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
				panel.repaint();
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
				panel.repaint();
			}
		});
		btnCalculate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnCalculate.getText().equals(startCalculationText)) {
					if (getCities() == null || getCities().isEmpty())
						return;
					setCalculated(false);
					panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));

					if (isDrawingNeeded()) {
						if (resultWindow != null && resultWindow.isShowing()) {
							resultWindow.hide();
						}
						resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());
						resultWindow.setLocation(850, 0);
						resultWindow.setVisible(true);
					}

					AlgorithmType selectedType = (AlgorithmType) cbAlgorithm.getSelectedItem();

					initializeData();
					algorithm = TSAlgorithmFactory.getTSAlgorithm(selectedType, getMainWindow());
					runningAlgorythm = new Thread(algorithm);
					runningAlgorythm.start();
					btnClear.setEnabled(false);
					btnGenerate.setEnabled(false);

				} else {
					setCalculated(true);
					algorithm.stopCalculation();
					algorithm.drawFinalResult();
					btnClear.setEnabled(true);
					btnGenerate.setEnabled(true);
				}
			}
		});
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}
		resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());
		
		JLabel label = new JLabel("Кількість міст:");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(702, 235, 115, 14);
		contentPane.add(label);
		
		citiesCountTf = new JTextField();
		citiesCountTf.setBounds(844, 232, 46, 20);
		contentPane.add(citiesCountTf);
		citiesCountTf.setColumns(10);
		citiesCountTf.setText("20");
		
		JLabel lblMax_1 = new JLabel("Max. кількість ітерацій");
		lblMax_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblMax_1.setBounds(677, 439, 150, 14);
		contentPane.add(lblMax_1);
		
		maxIterationCountTf = new JTextField();
		maxIterationCountTf.setText(String.valueOf(InitialData.maxIterationCount));
		maxIterationCountTf.setBounds(878, 436, 46, 20);
		contentPane.add(maxIterationCountTf);
		maxIterationCountTf.setColumns(10);
		
		JLabel lblMax = new JLabel("Max. ітерацій без покращення");
		lblMax.setHorizontalAlignment(SwingConstants.LEFT);
		lblMax.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		lblMax.setBounds(677, 467, 197, 14);
		contentPane.add(lblMax);
		
		maxAffectIterationCountTf = new JTextField();
		maxAffectIterationCountTf.setText(String.valueOf(InitialData.maxIterationCountWithoutImproving));
		maxAffectIterationCountTf.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		maxAffectIterationCountTf.setColumns(10);
		maxAffectIterationCountTf.setBounds(878, 464, 46, 20);
		contentPane.add(maxAffectIterationCountTf);
		
		JLabel lblMin = new JLabel("Min. допустиме покращення шляху");
		lblMin.setHorizontalAlignment(SwingConstants.LEFT);
		lblMin.setToolTipText("Мінімально допустиме значення на яке може бути покращенний шлях");
		lblMin.setBounds(677, 495, 197, 14);
		contentPane.add(lblMin);
		
		minPathImprovingTf = new JTextField();
		minPathImprovingTf.setText(String.valueOf(InitialData.minPathImproving));
		minPathImprovingTf.setColumns(10);
		minPathImprovingTf.setBounds(878, 492, 46, 20);
		contentPane.add(minPathImprovingTf);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(677, 420, 234, 2);
		contentPane.add(separator);
		
		JLabel label_1 = new JLabel("Критерії зупину");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(750, 399, 115, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Результати");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(750, 520, 115, 14);
		contentPane.add(label_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(677, 541, 234, 2);
		contentPane.add(separator_1);
		
		JLabel label_3 = new JLabel("Керування");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(750, 202, 100, 14);
		contentPane.add(label_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(677, 223, 234, 2);
		contentPane.add(separator_2);
		
		JLabel label_4 = new JLabel("Поточна ітерація:");
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setBounds(677, 594, 115, 14);
		contentPane.add(label_4);
		
		currentIterationTf = new JTextField();
		currentIterationTf.setEditable(false);
		currentIterationTf.setBounds(825, 588, 86, 20);
		contentPane.add(currentIterationTf);
		currentIterationTf.setColumns(10);
		
		JLabel label_5 = new JLabel("Вірогідність мутації:");
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setBounds(677, 83, 132, 14);
		contentPane.add(label_5);
		
		mutationRateTf = new JTextField();
		mutationRateTf.setText(String.valueOf(InitialData.mutationRate));
		mutationRateTf.setBounds(819, 78, 46, 20);
		contentPane.add(mutationRateTf);
		mutationRateTf.setColumns(10);
		
		populationCountTf = new JTextField();
		populationCountTf.setText(String.valueOf(InitialData.populationCount));
		populationCountTf.setColumns(10);
		populationCountTf.setBounds(819, 141, 46, 20);
		contentPane.add(populationCountTf);
		
		JLabel label_7 = new JLabel("Розмір популяції:");
		label_7.setHorizontalAlignment(SwingConstants.LEFT);
		label_7.setBounds(677, 144, 132, 14);
		contentPane.add(label_7);
		
		tournamentSizeTf = new JTextField();
		tournamentSizeTf.setText(String.valueOf(InitialData.tournamentSize));
		tournamentSizeTf.setColumns(10);
		tournamentSizeTf.setBounds(819, 108, 46, 20);
		contentPane.add(tournamentSizeTf);
		
		JLabel label_6 = new JLabel("Розмір турніру:");
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setBounds(677, 113, 132, 14);
		contentPane.add(label_6);
		
		JLabel label_8 = new JLabel("%");
		label_8.setBounds(869, 78, 55, 19);
		contentPane.add(label_8);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(677, 32, 234, 2);
		contentPane.add(separator_3);
		
		JLabel label_9 = new JLabel("Налаштування алгоритму");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(723, 11, 167, 14);
		contentPane.add(label_9);
	}
	
	private void initializeData() {
		InitialData.maxIterationCount = Integer.valueOf(maxIterationCountTf.getText());
		InitialData.maxIterationCountWithoutImproving = Integer.valueOf(maxAffectIterationCountTf.getText());
		InitialData.minPathImproving = Double.valueOf(minPathImprovingTf.getText());
		InitialData.mutationRate = Double.valueOf(mutationRateTf.getText());
		InitialData.populationCount = Integer.valueOf(populationCountTf.getText());
		InitialData.tournamentSize = Integer.valueOf(tournamentSizeTf.getText());
		
	}
	
	public void disableCalculation() {
		btnCalculate.setEnabled(false);
	}
	
	public void enableCalculation() {
		btnCalculate.setEnabled(true);
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
	
	public void setCurrentIteration(int currIteration) {
		currentIterationTf.setText(String.valueOf(currIteration));
	}
	
	public void setCityCount(int citisCount) {
		citiesCountTf.setText(String.valueOf(citisCount));
	}

	public boolean isDrawingNeeded() {
		return showDrawingChbx.isSelected();
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
