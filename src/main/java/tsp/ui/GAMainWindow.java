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
	private JCheckBox greedyInitializationChb;

	private static final String defDirForData = "inputFiles/.";
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 957, 688);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel for drawing
		panel = new CitiesPanel();
		panel.setMainWindow(this);
		panel.setBounds(10, 25, 657, 620);
		contentPane.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
//		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041D\u0410\u041B\u0410\u0428\u0422\u0423\u0412\u0410\u041D\u041D\u042F \u0410\u041B\u0413\u041E\u0420\u0418\u0422\u041C\u0423", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(677, 21, 270, 179);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
//		panel.set

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(10, 19, 83, 14);
		panel_2.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.LEFT);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(126, 16, 131, 21);
		panel_2.add(cbAlgorithm);
		
				showDrawingChbx = new JCheckBox("Показати результати");
				showDrawingChbx.setBounds(6, 149, 151, 23);
				panel_2.add(showDrawingChbx);
				showDrawingChbx.setHorizontalAlignment(SwingConstants.LEFT);
				showDrawingChbx.setSelected(true);
				
				JLabel label_5 = new JLabel("Вірогідність мутації:");
				label_5.setBounds(10, 48, 132, 14);
				panel_2.add(label_5);
				label_5.setHorizontalAlignment(SwingConstants.LEFT);
				
				mutationRateTf = new JTextField();
				mutationRateTf.setBounds(152, 43, 46, 20);
				panel_2.add(mutationRateTf);
				mutationRateTf.setText(String.valueOf(InitialData.mutationRate));
				mutationRateTf.setColumns(10);
				
				populationCountTf = new JTextField();
				populationCountTf.setBounds(152, 97, 46, 20);
				panel_2.add(populationCountTf);
				populationCountTf.setText(String.valueOf(InitialData.populationCount));
				populationCountTf.setColumns(10);
				
				JLabel label_7 = new JLabel("Розмір популяції:");
				label_7.setBounds(10, 100, 132, 14);
				panel_2.add(label_7);
				label_7.setHorizontalAlignment(SwingConstants.LEFT);
				
				tournamentSizeTf = new JTextField();
				tournamentSizeTf.setBounds(152, 70, 46, 20);
				panel_2.add(tournamentSizeTf);
				tournamentSizeTf.setText(String.valueOf(InitialData.tournamentSize));
				tournamentSizeTf.setColumns(10);
				
				JLabel label_6 = new JLabel("Розмір турніру:");
				label_6.setBounds(10, 73, 132, 14);
				panel_2.add(label_6);
				label_6.setHorizontalAlignment(SwingConstants.LEFT);
				
				JLabel label_8 = new JLabel("%");
				label_8.setBounds(202, 43, 55, 19);
				panel_2.add(label_8);
				
				greedyInitializationChb = new JCheckBox("Ініціалізація жадібним алгоритмом");
				greedyInitializationChb.setBounds(6, 123, 251, 23);
				panel_2.add(greedyInitializationChb);
				greedyInitializationChb.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041A\u0415\u0420\u0423\u0412\u0410\u041D\u041D\u042F", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(677, 201, 270, 186);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(10, 87, 247, 23);
		panel_1.add(btnCalculate);
		btnCalculate.setEnabled(false);
		btnCalculate.setBackground(Color.GREEN);
		
				btnClear = new JButton("Очистити");
				btnClear.setBounds(10, 121, 247, 23);
				panel_1.add(btnClear);
				
						btnGenerate = new JButton("Генерувати");
						btnGenerate.setBounds(10, 53, 247, 23);
						panel_1.add(btnGenerate);
						
								JButton btnTestchart = new JButton("Графік результатів");
								btnTestchart.setBounds(10, 155, 247, 23);
								panel_1.add(btnTestchart);
								
								JLabel label = new JLabel("Кількість міст:");
								label.setBounds(10, 28, 115, 14);
								panel_1.add(label);
								label.setHorizontalAlignment(SwingConstants.LEFT);
								
								citiesCountTf = new JTextField();
								citiesCountTf.setBounds(112, 25, 46, 20);
								panel_1.add(citiesCountTf);
								citiesCountTf.setColumns(10);
								citiesCountTf.setText("20");
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
										setCities(cities);
									}
								});
				btnClear.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						currentIterationTf.setText("0");
						lblOptimalTour.setText("0");
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

				} else {
					setCalculated(true);
					algorithm.stopCalculation();
					algorithm.drawFinalResult();
					
				}
			}
		});
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u0415\u0417\u0423\u041B\u042C\u0422\u0410\u0422\u0418", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(677, 493, 270, 152);
		contentPane.add(panel_4);
		panel_4.setLayout(null);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(6, 23, 115, 14);
		panel_4.add(labelConstOptTour);
		labelConstOptTour.setHorizontalAlignment(SwingConstants.LEFT);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(152, 16, 83, 21);
		panel_4.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));
		resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());
		
		JLabel label_4 = new JLabel("Поточна ітерація:");
		label_4.setBounds(6, 48, 115, 14);
		panel_4.add(label_4);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		
		currentIterationTf = new JTextField();
		currentIterationTf.setBounds(128, 45, 132, 20);
		panel_4.add(currentIterationTf);
		currentIterationTf.setEditable(false);
		currentIterationTf.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Час роботи:");
		lblNewLabel.setBounds(6, 73, 115, 14);
		panel_4.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(128, 70, 132, 20);
		panel_4.add(textField);
		textField.setColumns(10);
		for (AlgorithmType alType : AlgorithmType.values()) {
			cbAlgorithm.addItem(alType);
		}
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041A\u0420\u0418\u0422\u0415\u0420\u0406\u0407 \u0417\u0423\u041F\u0418\u041D\u0423", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(677, 393, 270, 99);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblMax_1 = new JLabel("Max. кількість ітерацій");
		lblMax_1.setBounds(6, 19, 150, 14);
		panel_3.add(lblMax_1);
		lblMax_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		maxIterationCountTf = new JTextField();
		maxIterationCountTf.setBounds(207, 16, 46, 20);
		panel_3.add(maxIterationCountTf);
		maxIterationCountTf.setText(String.valueOf(InitialData.maxIterationCount));
		maxIterationCountTf.setColumns(10);
		
		JLabel lblMax = new JLabel("Max. ітерацій без покращення");
		lblMax.setBounds(6, 47, 197, 14);
		panel_3.add(lblMax);
		lblMax.setHorizontalAlignment(SwingConstants.LEFT);
		lblMax.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		
		maxAffectIterationCountTf = new JTextField();
		maxAffectIterationCountTf.setBounds(207, 44, 46, 20);
		panel_3.add(maxAffectIterationCountTf);
		maxAffectIterationCountTf.setText(String.valueOf(InitialData.maxIterationCountWithoutImproving));
		maxAffectIterationCountTf.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		maxAffectIterationCountTf.setColumns(10);
		
		JLabel lblMin = new JLabel("Min. допустиме покращення шляху");
		lblMin.setBounds(6, 75, 197, 14);
		panel_3.add(lblMin);
		lblMin.setHorizontalAlignment(SwingConstants.LEFT);
		lblMin.setToolTipText("Мінімально допустиме значення на яке може бути покращенний шлях");
		
		minPathImprovingTf = new JTextField();
		minPathImprovingTf.setBounds(207, 72, 46, 20);
		panel_3.add(minPathImprovingTf);
		minPathImprovingTf.setText(String.valueOf(InitialData.minPathImproving));
		minPathImprovingTf.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(80, 0, 576, 21);
		contentPane.add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Файл");
		menuBar.add(mnNewMenu);
		
		JMenuItem saveDataToFileItem = new JMenuItem("Зберегти данні");
		saveDataToFileItem.addActionListener(new ActionListener() {
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
		});
		mnNewMenu.add(saveDataToFileItem);
		
		JMenuItem loadDataFromFileItem = new JMenuItem("Загрузити данні");
		loadDataFromFileItem.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileopen = new JFileChooser();
				fileopen.setCurrentDirectory(new File(defDirForData));
				int ret = fileopen.showOpenDialog(contentPane);
			    if (ret == JFileChooser.APPROVE_OPTION) {
			    	try {
			    		setCities((List<City>)SerializationHelper.deserializeFromFile(fileopen.getSelectedFile()));
					} catch (IOException | ClassNotFoundException e) {
						log.error(e);
					}
			    }
		    	
			}
		});
		mnNewMenu.add(loadDataFromFileItem);
	}

	public void setCities(List<City> cities) {
		panel.setCities(cities);
		panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
		panel.repaint();
	}

	private void initializeData() {
		InitialData.maxIterationCount = Integer.valueOf(maxIterationCountTf.getText());
		InitialData.maxIterationCountWithoutImproving = Integer.valueOf(maxAffectIterationCountTf.getText());
		InitialData.minPathImproving = Double.valueOf(minPathImprovingTf.getText());
		InitialData.mutationRate = Double.valueOf(mutationRateTf.getText());
		InitialData.populationCount = Integer.valueOf(populationCountTf.getText());
		InitialData.tournamentSize = Integer.valueOf(tournamentSizeTf.getText());
		InitialData.greedyInitialization = Boolean.valueOf(greedyInitializationChb.isSelected());

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
