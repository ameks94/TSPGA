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

import org.jfree.ui.RefineryUtilities;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.TSAlgorithmFactory;
import tsp.algorithms.TSAlgorithmFactory.AlgorithmType;
import tsp.ui.charts.TourChart;
import tsp.ui.drawers.DrawerFactory;
import tsp.ui.drawers.DrawerFactory.DrawerType;
import tsp.utils.DialogHelper;
import tsp.utils.GeneratorHelper;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.Box;

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
	private JTextField textField;
	private JTextField currentIterationTf;

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
		lblAlgorithm.setBounds(677, 11, 83, 14);
		contentPane.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.LEFT);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(793, 11, 131, 21);
		contentPane.add(cbAlgorithm);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(677, 320, 247, 23);
		btnCalculate.setEnabled(false);
		contentPane.add(btnCalculate);
		btnCalculate.setBackground(Color.GREEN);

		JButton btnClear = new JButton("Очистити");
		btnClear.setBounds(677, 354, 247, 23);
		contentPane.add(btnClear);

		JButton btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(677, 286, 247, 23);
		contentPane.add(btnGenerate);

		JButton btnTestchart = new JButton("Графік результатів");
		btnTestchart.setBounds(677, 388, 247, 23);
		contentPane.add(btnTestchart);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(677, 598, 115, 14);
		contentPane.add(labelConstOptTour);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(828, 591, 83, 21);
		contentPane.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));

		showDrawingChbx = new JCheckBox("Показати результати");
		showDrawingChbx.setHorizontalAlignment(SwingConstants.RIGHT);
		showDrawingChbx.setSelected(true);
		showDrawingChbx.setBounds(773, 39, 151, 23);
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
		label.setBounds(677, 72, 93, 14);
		contentPane.add(label);
		
		citiesCountTf = new JTextField();
		citiesCountTf.setBounds(793, 69, 46, 20);
		contentPane.add(citiesCountTf);
		citiesCountTf.setColumns(10);
		citiesCountTf.setText("20");
		
		JLabel lblMax_1 = new JLabel("Max. кількість ітерацій");
		lblMax_1.setBounds(677, 462, 150, 14);
		contentPane.add(lblMax_1);
		
		maxIterationCountTf = new JTextField();
		maxIterationCountTf.setBounds(878, 459, 46, 20);
		contentPane.add(maxIterationCountTf);
		maxIterationCountTf.setColumns(10);
		
		JLabel lblMax = new JLabel("Max. ітерацій без покращення");
		lblMax.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		lblMax.setBounds(677, 490, 197, 14);
		contentPane.add(lblMax);
		
		maxAffectIterationCountTf = new JTextField();
		maxAffectIterationCountTf.setToolTipText("Максимальна кількість ітерацій без покращення результату");
		maxAffectIterationCountTf.setColumns(10);
		maxAffectIterationCountTf.setBounds(878, 487, 46, 20);
		contentPane.add(maxAffectIterationCountTf);
		
		JLabel lblMin = new JLabel("Min. допустиме покращення шляху");
		lblMin.setToolTipText("Мінімально допустиме значення на яке може бути покращенний шлях");
		lblMin.setBounds(677, 518, 197, 14);
		contentPane.add(lblMin);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(878, 515, 46, 20);
		contentPane.add(textField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(677, 443, 234, 2);
		contentPane.add(separator);
		
		JLabel label_1 = new JLabel("Критерії зупину");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(750, 422, 115, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Результати");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(750, 557, 115, 14);
		contentPane.add(label_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(677, 578, 234, 2);
		contentPane.add(separator_1);
		
		JLabel label_3 = new JLabel("Керування");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(750, 255, 100, 14);
		contentPane.add(label_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(677, 276, 234, 2);
		contentPane.add(separator_2);
		
		JLabel label_4 = new JLabel("Поточна ітерація");
		label_4.setBounds(677, 631, 115, 14);
		contentPane.add(label_4);
		
		currentIterationTf = new JTextField();
		currentIterationTf.setEditable(false);
		currentIterationTf.setBounds(825, 625, 86, 20);
		contentPane.add(currentIterationTf);
		currentIterationTf.setColumns(10);
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
