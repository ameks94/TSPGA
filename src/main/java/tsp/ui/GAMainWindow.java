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

	/**
	 * Create the frame.
	 */
	public GAMainWindow() {
		super("GA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 852, 688);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Panel for drawing
		panel = new CitiesPanel();
		panel.setBounds(10, 11, 657, 634);
		contentPane.add(panel);
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel lblAlgorithm = new JLabel("Алгоритм");
		lblAlgorithm.setBounds(711, 11, 81, 14);
		contentPane.add(lblAlgorithm);
		lblAlgorithm.setHorizontalAlignment(SwingConstants.CENTER);

		cbAlgorithm = new JComboBox<AlgorithmType>();
		cbAlgorithm.setBounds(677, 36, 150, 21);
		contentPane.add(cbAlgorithm);

		btnCalculate = new JButton(startCalculationText);
		btnCalculate.setBounds(677, 107, 150, 23);
		contentPane.add(btnCalculate);
		btnCalculate.setBackground(Color.GREEN);

		JButton btnClear = new JButton("Очистити");
		btnClear.setBounds(677, 175, 150, 23);
		contentPane.add(btnClear);

		JButton btnGenerate = new JButton("Генерувати");
		btnGenerate.setBounds(677, 141, 150, 23);
		contentPane.add(btnGenerate);

		JButton btnTestchart = new JButton("Тестувати");
		btnTestchart.setBounds(677, 209, 150, 23);
		contentPane.add(btnTestchart);

		JLabel labelConstOptTour = new JLabel("Найкоротший шлях:");
		labelConstOptTour.setBounds(687, 243, 115, 14);
		contentPane.add(labelConstOptTour);

		lblOptimalTour = new JLabel("");
		lblOptimalTour.setBounds(698, 268, 83, 23);
		contentPane.add(lblOptimalTour);
		lblOptimalTour.setForeground(Color.GREEN);
		lblOptimalTour.setFont(new Font("Tahoma", Font.PLAIN, 16));

		showDrawingChbx = new JCheckBox("Показати результати");
		showDrawingChbx.setSelected(true);
		showDrawingChbx.setBounds(676, 75, 151, 23);
		contentPane.add(showDrawingChbx);

		JLabel lblNewLabel = new JLabel("Кількість міст:");
		lblNewLabel.setBounds(687, 302, 103, 14);
		contentPane.add(lblNewLabel);

		JLabel citiesCountLbl = new JLabel("0");
		citiesCountLbl.setBounds(722, 327, 46, 14);
		contentPane.add(citiesCountLbl);
		btnTestchart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});

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
				if (getCities() == null || getCities().isEmpty())
					return;
				citiesCountLbl.setText(String.valueOf(getCities().size()));
				if (btnCalculate.getText().equals(startCalculationText)) {
					setCalculated(false);
					panel.setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));

					if (isDrawingNeeded()) {
						if (resultWindow != null && resultWindow.isShowing()) {
							resultWindow.hide();
						}
						resultWindow = new ResultWindow(getOutputPanel(), getOutputLable());
						resultWindow.setVisible(true);
					}

					AlgorithmType selectedType = (AlgorithmType) cbAlgorithm.getSelectedItem();

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
