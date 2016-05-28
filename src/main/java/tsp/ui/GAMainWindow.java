package tsp.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import tsp.algorithms.City;
import tsp.algorithms.InitialData;
import tsp.algorithms.TSAlgorithmFactory.AlgorithmType;
import tsp.controllers.MainController;
import tsp.ui.drawers.DrawerFactory;
import tsp.ui.drawers.DrawerFactory.DrawerType;

public class GAMainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(getClass().getName());
	private JPanel myContentPane;
	private CitiesPanel citiesPanel;
	private JComboBox<AlgorithmType> cbAlgorithm;
	private JLabel lblOptimalTour;
	private JButton btnCalculate;
	private JButton btnClear;
	private JButton btnGenerate;
	private JButton addCriteriasBtn;

	private JLabel currentIterationLbl;
	private JLabel currentTimeLbl;
	private final String startCalculationText = "Розрахувати";
	private final String stopCalculationText = "Зупинити обрахунок";
	private JTextField citiesCountTf;
	private JTextField maxIterationCountTf;
	private JTextField maxAffectIterationCountTf;
	private JTextField maxWorkTimeTf;
	private JTextField mutationRateTf;
	private JTextField populationCountTf;
	private JTextField tournamentSizeTf;
	private JCheckBox greedyInitializationChb;

	private JCheckBox showDistancesChb;
	private JCheckBox showTimesChb;
	private JCheckBox showCostsChb;
	private JCheckBox showDrawingChbx;
	
	private MainController mainController;

	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setTitle("Гентичний алгоритм");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 895, 750);
		myContentPane = new JPanel();
		myContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(myContentPane);
		myContentPane.setLayout(null);

		mainController = new MainController(this);
		
		// Panel for drawing
		citiesPanel = new CitiesPanel();
		citiesPanel.setMainWindow(this);
		citiesPanel.setBounds(10, 25, 601, 664);
		myContentPane.add(citiesPanel);
		citiesPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		// panel.setBackground(Color.WHITE);
		citiesPanel.setLayout(new BoxLayout(citiesPanel, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(610, 25, 268, 664);
		myContentPane.add(panel_5);
		panel_5.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 270, 168);
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
		cbAlgorithm.setBounds(126, 16, 131, 17);
		panel_2.add(cbAlgorithm);

		JLabel label_5 = new JLabel("Вірогідність мутації:");
		label_5.setBounds(6, 44, 132, 14);
		panel_2.add(label_5);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);

		mutationRateTf = new JTextField();
		mutationRateTf.setBounds(171, 40, 46, 20);
		panel_2.add(mutationRateTf);
		mutationRateTf.setText(String.valueOf(InitialData.mutationRate));
		mutationRateTf.setColumns(10);

		populationCountTf = new JTextField();
		populationCountTf.setBounds(171, 88, 46, 20);
		panel_2.add(populationCountTf);
		populationCountTf.setText(String.valueOf(InitialData.populationCount));
		populationCountTf.setColumns(10);

		JLabel label_7 = new JLabel("Розмір популяції:");
		label_7.setBounds(6, 91, 132, 14);
		panel_2.add(label_7);
		label_7.setHorizontalAlignment(SwingConstants.LEFT);

		tournamentSizeTf = new JTextField();
		tournamentSizeTf.setBounds(171, 63, 46, 20);
		panel_2.add(tournamentSizeTf);
		tournamentSizeTf.setText(String.valueOf(InitialData.tournamentSize));
		tournamentSizeTf.setColumns(10);

		JLabel label_6 = new JLabel("Розмір турніру:");
		label_6.setBounds(6, 69, 132, 14);
		panel_2.add(label_6);
		label_6.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel label_8 = new JLabel("%");
		label_8.setBounds(221, 40, 36, 19);
		panel_2.add(label_8);

		JLabel label_2 = new JLabel("Номер початкового міста:");
		label_2.setBounds(6, 114, 162, 14);
		panel_2.add(label_2);

		startCityIndexTf = new JTextField();
		startCityIndexTf.setText("1");
		startCityIndexTf.setBounds(171, 111, 46, 20);
		panel_2.add(startCityIndexTf);
		startCityIndexTf.setColumns(10);

		greedyInitializationChb = new JCheckBox("Ініціалізація жадібним алгоритмом");
		greedyInitializationChb.setBounds(6, 138, 251, 23);
		panel_2.add(greedyInitializationChb);
		greedyInitializationChb.setHorizontalAlignment(SwingConstants.LEFT);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 179, 270, 285);
		panel_5.add(panel_1);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u041A\u0415\u0420\u0423\u0412\u0410\u041D\u041D\u042F", TitledBorder.LEFT, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.setLayout(null);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(6, 107, 247, 23);
		panel_1.add(btnCalculate);
		btnCalculate.setEnabled(false);
		btnCalculate.setBackground(Color.GREEN);

		btnClear = new JButton("Очистити");
		btnClear.setBounds(6, 141, 247, 23);
		panel_1.add(btnClear);

		btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(6, 39, 247, 23);
		panel_1.add(btnGenerate);

		JButton btnTestchart = new JButton("Графік результатів");
		btnTestchart.setBounds(6, 176, 247, 23);
		panel_1.add(btnTestchart);

		JLabel label = new JLabel("Кількість міст:");
		label.setBounds(82, 14, 115, 14);
		panel_1.add(label);
		label.setHorizontalAlignment(SwingConstants.LEFT);

		citiesCountTf = new JTextField();
		citiesCountTf.setBounds(207, 11, 46, 20);
		panel_1.add(citiesCountTf);
		citiesCountTf.setColumns(10);
		citiesCountTf.setText("20");

		addCriteriasBtn = new JButton("Налаштувати критерії");
		addCriteriasBtn.addActionListener(mainController.addCriteriasBtnAction);
		addCriteriasBtn.setBounds(6, 73, 247, 23);
		panel_1.add(addCriteriasBtn);

		showDistancesChb = new JCheckBox("Показати відстані");
		showDistancesChb.setForeground(Color.BLUE);
		showDistancesChb.setBackground(UIManager.getColor("Label.background"));
		showDistancesChb.setSelected(InitialData.showDistances);
		showDistancesChb.setHorizontalAlignment(SwingConstants.LEFT);
		showDistancesChb.setBounds(6, 206, 140, 23);
		showDistancesChb.addActionListener((ActionEvent e) -> {
			InitialData.showDistances = ((JCheckBox) e.getSource()).isSelected();
		});
		panel_1.add(showDistancesChb);

		showCostsChb = new JCheckBox("Показати вартість");
		showCostsChb.setForeground(Color.RED);
		showCostsChb.setSelected(InitialData.showCostCriteria);
		showCostsChb.setHorizontalAlignment(SwingConstants.LEFT);
		showCostsChb.setBounds(6, 232, 140, 23);
		showCostsChb.addActionListener((ActionEvent e) -> {
			InitialData.showCostCriteria = ((JCheckBox) e.getSource()).isSelected();
		});
		panel_1.add(showCostsChb);

		showDrawingChbx = new JCheckBox("Показати результати");
		showDrawingChbx.setSelected(true);
		showDrawingChbx.setHorizontalAlignment(SwingConstants.LEFT);
		showDrawingChbx.setBounds(6, 258, 151, 23);
		panel_1.add(showDrawingChbx);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(0, 559, 270, 105);
		panel_5.add(panel_4);
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u0420\u0415\u0417\u0423\u041B\u042C\u0422\u0410\u0422\u0418", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		panel_4.setLayout(null);

		JLabel labelConstOptTour = new JLabel("Шлях:");
		labelConstOptTour.setBounds(6, 23, 115, 14);
		panel_4.add(labelConstOptTour);
		labelConstOptTour.setHorizontalAlignment(SwingConstants.LEFT);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(131, 16, 83, 21);
		panel_4.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JLabel label_4 = new JLabel("Поточна популяція:");
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
		panel_3.setBounds(0, 464, 270, 94);
		panel_5.add(panel_3);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"\u041A\u0420\u0418\u0422\u0415\u0420\u0406\u0407 \u0417\u0423\u041F\u0418\u041D\u0423",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setLayout(null);

		JLabel lblMax_1 = new JLabel("Max. кількість популяцій");
		lblMax_1.setBounds(6, 19, 150, 14);
		panel_3.add(lblMax_1);
		lblMax_1.setHorizontalAlignment(SwingConstants.LEFT);

		maxIterationCountTf = new JTextField();
		maxIterationCountTf.setBounds(207, 16, 53, 20);
		panel_3.add(maxIterationCountTf);
		maxIterationCountTf.setText(String.valueOf(InitialData.maxIterationCount));
		maxIterationCountTf.setColumns(10);

		JLabel lblMax = new JLabel("Max. популяцій без покращення");
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
		maxWorkTimeTf.setText("360");
		maxWorkTimeTf.setColumns(10);
		btnTestchart.addActionListener(mainController.btnTestChartAction);

		btnGenerate.addActionListener(mainController.btnGenerateAction);
		btnClear.addActionListener(mainController.btnClearAction);
		btnCalculate.addActionListener(mainController.btnCalculateAction);
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(10, 0, 941, 21);
		myContentPane.add(menuBar);

		JMenu mnNewMenu = new JMenu("Файл");
		menuBar.add(mnNewMenu);

		JMenuItem saveDataToFileItem = new JMenuItem("Зберегти данні");
		saveDataToFileItem.addActionListener(mainController.saveDataAction);
		mnNewMenu.add(saveDataToFileItem);

		JMenuItem loadDataFromFileItem = new JMenuItem("Загрузити данні");
		loadDataFromFileItem.addActionListener(mainController.loadDataAction);
		mnNewMenu.add(loadDataFromFileItem);

		showTimesChb = new JCheckBox("Показати час");
		showTimesChb.setBounds(10, 111, 93, 23);
		myContentPane.add(showTimesChb);
		showTimesChb.setForeground(Color.DARK_GRAY);
		showTimesChb.setSelected(InitialData.showTimeCriteria);
		showTimesChb.setHorizontalAlignment(SwingConstants.LEFT);
		showTimesChb.addActionListener((ActionEvent e) -> {
			InitialData.showTimeCriteria = ((JCheckBox) e.getSource()).isSelected();
		});
	}

	private JTextField startCityIndexTf;

	public void setCities(List<City> cities) {
		citiesPanel.setCities(cities);
		citiesPanel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		citiesPanel.repaint();
	}

	public void initializeData() {
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
		return citiesPanel.getCities();
	}
	
	public int getStartCityIndex() {
		return Integer.valueOf(startCityIndexTf.getText()) - 1;
	}

	public JLabel getOutputLable() {
		return lblOptimalTour;
	}

	public CitiesPanel getOutputPanel() {
		return citiesPanel;
	}

	public JButton getCalculateBtn() {
		return btnCalculate;
	}


	public GAMainWindow getMainWindow() {
		return this;
	}

	public JPanel getMyContentPane() {
		return myContentPane;
	}

	public void setMyContentPane(JPanel contentPane) {
		this.myContentPane = contentPane;
	}
	
	
	public CitiesPanel getCitiesPanel() {
		return citiesPanel;
	}

	public void setCitiesPanel(CitiesPanel citiesPanel) {
		this.citiesPanel = citiesPanel;
	}

	public JComboBox<AlgorithmType> getCbAlgorithm() {
		return cbAlgorithm;
	}

	public void setCbAlgorithm(JComboBox<AlgorithmType> cbAlgorithm) {
		this.cbAlgorithm = cbAlgorithm;
	}

	public JLabel getLblOptimalTour() {
		return lblOptimalTour;
	}

	public void setLblOptimalTour(JLabel lblOptimalTour) {
		this.lblOptimalTour = lblOptimalTour;
	}

	public JButton getBtnCalculate() {
		return btnCalculate;
	}

	public void setBtnCalculate(JButton btnCalculate) {
		this.btnCalculate = btnCalculate;
	}

	public JButton getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(JButton btnClear) {
		this.btnClear = btnClear;
	}

	public JButton getBtnGenerate() {
		return btnGenerate;
	}

	public void setBtnGenerate(JButton btnGenerate) {
		this.btnGenerate = btnGenerate;
	}

	public JButton getAddCriteriasBtn() {
		return addCriteriasBtn;
	}

	public void setAddCriteriasBtn(JButton addCriteriasBtn) {
		this.addCriteriasBtn = addCriteriasBtn;
	}

	public JLabel getCurrentIterationLbl() {
		return currentIterationLbl;
	}

	public void setCurrentIterationLbl(JLabel currentIterationLbl) {
		this.currentIterationLbl = currentIterationLbl;
	}

	public JLabel getCurrentTimeLbl() {
		return currentTimeLbl;
	}

	public void setCurrentTimeLbl(JLabel currentTimeLbl) {
		this.currentTimeLbl = currentTimeLbl;
	}

	public JTextField getCitiesCountTf() {
		return citiesCountTf;
	}

	public void setCitiesCountTf(JTextField citiesCountTf) {
		this.citiesCountTf = citiesCountTf;
	}

	public JTextField getMaxIterationCountTf() {
		return maxIterationCountTf;
	}

	public void setMaxIterationCountTf(JTextField maxIterationCountTf) {
		this.maxIterationCountTf = maxIterationCountTf;
	}

	public JTextField getMaxAffectIterationCountTf() {
		return maxAffectIterationCountTf;
	}

	public void setMaxAffectIterationCountTf(JTextField maxAffectIterationCountTf) {
		this.maxAffectIterationCountTf = maxAffectIterationCountTf;
	}

	public JTextField getMaxWorkTimeTf() {
		return maxWorkTimeTf;
	}

	public void setMaxWorkTimeTf(JTextField maxWorkTimeTf) {
		this.maxWorkTimeTf = maxWorkTimeTf;
	}

	public JTextField getMutationRateTf() {
		return mutationRateTf;
	}

	public void setMutationRateTf(JTextField mutationRateTf) {
		this.mutationRateTf = mutationRateTf;
	}

	public JTextField getPopulationCountTf() {
		return populationCountTf;
	}

	public void setPopulationCountTf(JTextField populationCountTf) {
		this.populationCountTf = populationCountTf;
	}

	public JTextField getTournamentSizeTf() {
		return tournamentSizeTf;
	}

	public void setTournamentSizeTf(JTextField tournamentSizeTf) {
		this.tournamentSizeTf = tournamentSizeTf;
	}

	public JCheckBox getGreedyInitializationChb() {
		return greedyInitializationChb;
	}

	public void setGreedyInitializationChb(JCheckBox greedyInitializationChb) {
		this.greedyInitializationChb = greedyInitializationChb;
	}

	public JCheckBox getShowDistancesChb() {
		return showDistancesChb;
	}

	public void setShowDistancesChb(JCheckBox showDistancesChb) {
		this.showDistancesChb = showDistancesChb;
	}

	public JCheckBox getShowTimesChb() {
		return showTimesChb;
	}

	public void setShowTimesChb(JCheckBox showTimesChb) {
		this.showTimesChb = showTimesChb;
	}

	public JCheckBox getShowCostsChb() {
		return showCostsChb;
	}

	public void setShowCostsChb(JCheckBox showCostsChb) {
		this.showCostsChb = showCostsChb;
	}

	public JCheckBox getShowDrawingChbx() {
		return showDrawingChbx;
	}

	public void setShowDrawingChbx(JCheckBox showDrawingChbx) {
		this.showDrawingChbx = showDrawingChbx;
	}

	public JTextField getStartCityIndexTf() {
		return startCityIndexTf;
	}

	public void setStartCityIndexTf(JTextField startCityIndexTf) {
		this.startCityIndexTf = startCityIndexTf;
	}

	public String getStartCalculationText() {
		return startCalculationText;
	}

	public String getStopCalculationText() {
		return stopCalculationText;
	}
	
	
}
