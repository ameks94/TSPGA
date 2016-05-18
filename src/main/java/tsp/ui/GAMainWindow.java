package tsp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;
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
import tsp.utils.SerializationHelper;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class GAMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(getClass().getName());
	private JPanel contentPane;
	private CitiesPanel panel;
	private JComboBox<AlgorithmType> cbAlgorithm;
	private JLabel lblOptimalTour;
	private JButton btnCalculate;
	private JButton btnClear;
	private JButton btnGenerate;
	private JButton addCriteriasBtn;

	private JLabel currentIterationLbl;
	private JLabel currentTimeLbl;

	private Timer timer;
	private long startCalculationTime;
	private static final String defDirForData = "inputFiles/.";

	private ResultWindow resultWindow;
	// private TextArea logArea;
	// private JLabel lblLog;
	private final String startCalculationText = "Розрахувати";
	private final String stopCalculationText = "Зупинити обрахунок";
	private Thread runningAlgorythm;
	private TSAlgorithm algorithm;
	private JTextField citiesCountTf;
	private JTextField maxIterationCountTf;
	private JTextField maxAffectIterationCountTf;
	private JTextField maxWorkTimeTf;
	private JTextField mutationRateTf;
	private JTextField populationCountTf;
	private JTextField tournamentSizeTf;
	private JCheckBox greedyInitializationChb;
	
	JCheckBox showDistancesChb;
	JCheckBox showTimesChb;
	JCheckBox showCostsChb;
	JCheckBox showDrawingChbx;

	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel for drawing
		panel = new CitiesPanel();
		panel.setMainWindow(this);
		panel.setBounds(10, 25, 601, 664);
		contentPane.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		// panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(610, 25, 268, 664);
		contentPane.add(panel_5);
		panel_5.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 9, 270, 135);
		panel_5.add(panel_2);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u041D\u0410\u041B\u0410\u0428\u0422\u0423\u0412\u0410\u041D\u041D\u042F \u0410\u041B\u0413\u041E\u0420\u0418\u0422\u041C\u0423",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setLayout(null);
		// panel.set

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(10, 19, 83, 14);
		panel_2.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.LEFT);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(126, 16, 131, 21);
		panel_2.add(cbAlgorithm);

		JLabel label_5 = new JLabel("Вірогідність мутації:");
		label_5.setBounds(6, 44, 132, 14);
		panel_2.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);

		mutationRateTf = new JTextField();
		mutationRateTf.setBounds(152, 43, 46, 20);
		panel_2.add(mutationRateTf);
		mutationRateTf.setText(String.valueOf(InitialData.mutationRate));
		mutationRateTf.setColumns(10);

		populationCountTf = new JTextField();
		populationCountTf.setBounds(152, 88, 46, 20);
		panel_2.add(populationCountTf);
		populationCountTf.setText(String.valueOf(InitialData.populationCount));
		populationCountTf.setColumns(10);

		JLabel label_7 = new JLabel("Розмір популяції:");
		label_7.setBounds(6, 91, 132, 14);
		panel_2.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.LEFT);

		tournamentSizeTf = new JTextField();
		tournamentSizeTf.setBounds(152, 66, 46, 20);
		panel_2.add(tournamentSizeTf);
		tournamentSizeTf.setText(String.valueOf(InitialData.tournamentSize));
		tournamentSizeTf.setColumns(10);

		JLabel label_6 = new JLabel("Розмір турніру:");
		label_6.setBounds(6, 69, 132, 14);
		panel_2.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel label_8 = new JLabel("%");
		label_8.setBounds(202, 43, 55, 19);
		panel_2.add(label_8);

		greedyInitializationChb = new JCheckBox("Ініціалізація жадібним алгоритмом");
		greedyInitializationChb.setBounds(6, 110, 251, 23);
		panel_2.add(greedyInitializationChb);
		greedyInitializationChb.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 141, 270, 327);
		panel_5.add(panel_1);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u041A\u0415\u0420\u0423\u0412\u0410\u041D\u041D\u042F", TitledBorder.LEFT, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.setLayout(null);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(10, 118, 247, 23);
		panel_1.add(btnCalculate);
		btnCalculate.setEnabled(false);
		btnCalculate.setBackground(Color.GREEN);

		btnClear = new JButton("Очистити");
		btnClear.setBounds(10, 152, 247, 23);
		panel_1.add(btnClear);

		btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(10, 50, 247, 23);
		panel_1.add(btnGenerate);

		JButton btnTestchart = new JButton("Графік результатів");
		btnTestchart.setBounds(10, 187, 247, 23);
		panel_1.add(btnTestchart);

		JLabel label = new JLabel("Кількість міст:");
		label.setBounds(10, 25, 115, 14);
		panel_1.add(label);
		label.setHorizontalAlignment(SwingConstants.LEFT);

		citiesCountTf = new JTextField();
		citiesCountTf.setBounds(127, 22, 46, 20);
		panel_1.add(citiesCountTf);
		citiesCountTf.setColumns(10);
		citiesCountTf.setText("20");

		addCriteriasBtn = new JButton("Додати критерії");
		addCriteriasBtn.addActionListener(addCriteriasBtnAction);
		addCriteriasBtn.setBounds(10, 84, 247, 23);
		panel_1.add(addCriteriasBtn);
		
		showDistancesChb = new JCheckBox("Показати відстані");
		showDistancesChb.setForeground(Color.BLUE);
		showDistancesChb.setBackground(UIManager.getColor("Label.background"));
		showDistancesChb.setSelected(InitialData.showDistances);
		showDistancesChb.setHorizontalAlignment(SwingConstants.LEFT);
		showDistancesChb.setBounds(10, 217, 140, 23);
		showDistancesChb.addActionListener((ActionEvent e) -> {InitialData.showDistances = ((JCheckBox) e.getSource()).isSelected();});
		panel_1.add(showDistancesChb);

		showTimesChb = new JCheckBox("Показати час");
		showTimesChb.setForeground(Color.DARK_GRAY);
		showTimesChb.setSelected(InitialData.showTimeCriteria);
		showTimesChb.setHorizontalAlignment(SwingConstants.LEFT);
		showTimesChb.setBounds(10, 243, 140, 23);
		showTimesChb.addActionListener((ActionEvent e) -> {InitialData.showTimeCriteria = ((JCheckBox) e.getSource()).isSelected();});
		panel_1.add(showTimesChb);
		
		showCostsChb = new JCheckBox("Показати вартість");
		showCostsChb.setForeground(Color.RED);
		showCostsChb.setSelected(InitialData.showCostCriteria);
		showCostsChb.setHorizontalAlignment(SwingConstants.LEFT);
		showCostsChb.setBounds(10, 269, 140, 23);
		showCostsChb.addActionListener((ActionEvent e) -> {InitialData.showCostCriteria = ((JCheckBox) e.getSource()).isSelected();});
		panel_1.add(showCostsChb);
		
		showDrawingChbx = new JCheckBox("Показати результати");
		showDrawingChbx.setSelected(true);
		showDrawingChbx.setHorizontalAlignment(SwingConstants.LEFT);
		showDrawingChbx.setBounds(10, 295, 151, 23);
		panel_1.add(showDrawingChbx);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 559, 270, 105);
		panel_5.add(panel_4);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u0420\u0415\u0417\u0423\u041B\u042C\u0422\u0410\u0422\u0418", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		panel_4.setLayout(null);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(6, 23, 115, 14);
		panel_4.add(labelConstOptTour);
		labelConstOptTour.setHorizontalAlignment(SwingConstants.LEFT);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(131, 16, 83, 21);
		panel_4.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));
		resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());

		JLabel label_4 = new JLabel("Поточна ітерація:");
		label_4.setBounds(6, 48, 115, 14);
		panel_4.add(label_4);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);

		currentIterationLbl = new JLabel("0");
		currentIterationLbl.setHorizontalAlignment(SwingConstants.LEFT);
		currentIterationLbl.setBounds(131, 48, 73, 14);
		panel_4.add(currentIterationLbl);

		JLabel lblNewLabel = new JLabel("Час роботи:");
		lblNewLabel.setBounds(6, 73, 115, 14);
		panel_4.add(lblNewLabel);

		currentTimeLbl = new JLabel("0");
		currentTimeLbl.setHorizontalAlignment(SwingConstants.LEFT);
		currentTimeLbl.setBounds(131, 73, 72, 14);
		panel_4.add(currentTimeLbl);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 468, 270, 94);
		panel_5.add(panel_3);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u041A\u0420\u0418\u0422\u0415\u0420\u0406\u0407 \u0417\u0423\u041F\u0418\u041D\u0423",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setLayout(null);

		JLabel lblMax_1 = new JLabel("Max. кількість ітерацій");
		lblMax_1.setBounds(6, 19, 150, 14);
		panel_3.add(lblMax_1);
		lblMax_1.setHorizontalAlignment(SwingConstants.LEFT);

		maxIterationCountTf = new JTextField();
		maxIterationCountTf.setBounds(207, 16, 53, 20);
		panel_3.add(maxIterationCountTf);
		maxIterationCountTf.setText(String.valueOf(InitialData.maxIterationCount));
		maxIterationCountTf.setColumns(10);

		JLabel lblMax = new JLabel("Max. ітерацій без покращення");
		lblMax.setBounds(6, 44, 197, 14);
		panel_3.add(lblMax);
		lblMax.setHorizontalAlignment(SwingConstants.LEFT);
		lblMax.setToolTipText("Максимальна кількість ітерацій без покращення результату");

		maxAffectIterationCountTf = new JTextField();
		maxAffectIterationCountTf.setBounds(207, 41, 53, 20);
		panel_3.add(maxAffectIterationCountTf);
		maxAffectIterationCountTf.setText(String.valueOf(InitialData.maxIterationCountWithoutImproving));
		maxAffectIterationCountTf.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		maxAffectIterationCountTf.setColumns(10);

		JLabel label_1 = new JLabel("Максимальний час роботи (с.)");
		label_1.setBounds(6, 69, 197, 14);
		panel_3.add(label_1);

		maxWorkTimeTf = new JTextField();
		maxWorkTimeTf.setBounds(207, 66, 53, 20);
		panel_3.add(maxWorkTimeTf);
		maxWorkTimeTf.setText("60");
		maxWorkTimeTf.setColumns(10);
		btnTestchart.addActionListener(btnTestChartAction);

		btnGenerate.addActionListener(btnGenerateAction);
		btnClear.addActionListener(btnClearAction);
		btnCalculate.addActionListener(btnCalculateAction);
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(10, 0, 941, 21);
		contentPane.add(menuBar);

		JMenu mnNewMenu = new JMenu("Файл");
		menuBar.add(mnNewMenu);

		JMenuItem saveDataToFileItem = new JMenuItem("Зберегти данні");
		saveDataToFileItem.addActionListener(saveDataAction);
		mnNewMenu.add(saveDataToFileItem);

		JMenuItem loadDataFromFileItem = new JMenuItem("Загрузити данні");
		loadDataFromFileItem.addActionListener(loadDataAction);
		mnNewMenu.add(loadDataFromFileItem);
	}

	private ActionListener addCriteriasBtnAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ConfigCriteriasWindow confWind = new ConfigCriteriasWindow(getMainWindow());
			confWind.setVisible(true);
		}
	};

	private ActionListener btnGenerateAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// JFrame frame = new JFrame("Кількість міст?");
			int citiesCount = Integer.valueOf(citiesCountTf.getText());
			if (citiesCount == 0) {
				return;
			}
			List<City> cities = GeneratorHelper.generateCities(citiesCount, panel.getWidth() - 10,
					panel.getHeight() - 10);
			setCities(cities);
		}
	};

	private ActionListener btnCalculateAction = new ActionListener() {
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
				timer = new Timer(10, updateTimeListener);
				startCalculationTime = System.currentTimeMillis();
				timer.start();
				runningAlgorythm.start();

			} else {
				algorithm.stopCalculation();
				algorithm.drawFinalResult();

			}
		}
	};

	private ActionListener updateTimeListener = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			long spentTime = System.currentTimeMillis() - startCalculationTime;
			if (algorithm.isCalculationProcessed() && spentTime < Long.valueOf(maxWorkTimeTf.getText()) * 1000) {
				currentTimeLbl.setText(String.valueOf(spentTime) + " мс.");
			} else {
				timer.stop();
				algorithm.stopCalculation();
				algorithm.drawFinalResult();
			}
		}
	};

	private ActionListener btnClearAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentIterationLbl.setText("0");
			lblOptimalTour.setText("0");
			panel.clearAll();
			panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
			if (resultWindow != null && resultWindow.isShowing()) {
				resultWindow.hide();
			}
			panel.repaint();
		}
	};

	private ActionListener btnTestChartAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			final TourChart demo = new TourChart("Мінімальний шлях по ітераціям", algorithm.getIterationDistances());
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);
		}
	};

	private ActionListener loadDataAction = new ActionListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(defDirForData));
			int ret = fileopen.showOpenDialog(contentPane);
			if (ret == JFileChooser.APPROVE_OPTION) {
				try {
					setCities((List<City>) SerializationHelper.deserializeFromFile(fileopen.getSelectedFile()));
				} catch (IOException | ClassNotFoundException e) {
					log.error(e);
				}
			}
		}
	};

	private ActionListener saveDataAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(defDirForData));
			int ret = fileopen.showSaveDialog(contentPane);
			if (ret == JFileChooser.APPROVE_OPTION) {
				try {
					SerializationHelper.serializeToFile(getCities(), fileopen.getSelectedFile());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	};

	public void setCities(List<City> cities) {
		panel.setCities(cities);
		panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		panel.repaint();
	}

	private void initializeData() {
		InitialData.maxIterationCount = Integer.valueOf(maxIterationCountTf.getText());
		InitialData.maxIterationCountWithoutImproving = Integer.valueOf(maxAffectIterationCountTf.getText());
		InitialData.mutationRate = Double.valueOf(mutationRateTf.getText());
		InitialData.populationCount = Integer.valueOf(populationCountTf.getText());
		InitialData.tournamentSize = Integer.valueOf(tournamentSizeTf.getText());
		InitialData.greedyInitialization = Boolean.valueOf(greedyInitializationChb.isSelected());
		// InitialData.useAdditionalCriteries =
		// Boolean.valueOf(useAdditionalCriteriesChb.isSelected());
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
			btnClear.setEnabled(true);
			btnGenerate.setEnabled(true);
		} else {
			btnCalculate.setText(stopCalculationText);
			btnCalculate.setBackground(Color.RED);
			btnClear.setEnabled(false);
			btnGenerate.setEnabled(false);
		}
	}

	public void setCurrentIteration(int currIteration) {
		currentIterationLbl.setText(String.valueOf(currIteration));
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
