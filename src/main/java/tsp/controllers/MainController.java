package tsp.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.Timer;

import org.apache.log4j.Logger;
import org.jfree.ui.RefineryUtilities;

import tsp.algorithms.City;
import tsp.algorithms.TSAlgorithm;
import tsp.algorithms.TSAlgorithmFactory;
import tsp.algorithms.TSAlgorithmFactory.AlgorithmType;
import tsp.ui.ConfigCriteriasWindow;
import tsp.ui.GAMainWindow;
import tsp.ui.ResultWindow;
import tsp.ui.charts.TourChart;
import tsp.ui.drawers.DrawerFactory;
import tsp.ui.drawers.DrawerFactory.DrawerType;
import tsp.utils.FileHelper;
import tsp.utils.GeneratorHelper;
import tsp.utils.SerializationHelper;

public class MainController {
	private final Logger log = Logger.getLogger(getClass().getName());
	
	private GAMainWindow mainWindow;
	
	private TSAlgorithm algorithm;
	private Thread runningAlgorythm;
	private Timer timer;
	private long startCalculationTime;
	private ResultWindow resultWindow;
	
	public MainController(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
		resultWindow = new ResultWindow(mainWindow.getOutputPanel(), mainWindow.getOutputLable());
	}
	
	public DefaultListModel resultIterationArea() {
		return resultWindow.getResultModel();
	}
//	public DefaultListModel getTextArea() {
//	return resultWindow.getResultModel();
//}
	
	public MainController getMainController() {
		return this;
	}
	
	public ActionListener addCriteriasBtnAction = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			ConfigCriteriasWindow confWind = new ConfigCriteriasWindow(mainWindow.getMainWindow());
			confWind.setVisible(true);
		}
	};

	public ActionListener btnGenerateAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// JFrame frame = new JFrame("Кількість міст?");
			int citiesCount = Integer.valueOf(mainWindow.getCitiesCountTf().getText());
			if (citiesCount == 0) {
				return;
			}
			List<City> cities = GeneratorHelper.generateCities(citiesCount, mainWindow.getCitiesPanel().getWidth(),
					mainWindow.getCitiesPanel().getHeight(), 25);
			mainWindow.setCities(cities);
		}
	};

	public ActionListener btnCalculateAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (mainWindow.getBtnCalculate().getText().equals(mainWindow.getStartCalculationText())) {
				if (mainWindow.getCities() == null || mainWindow.getCities().isEmpty())
					return;
				mainWindow.setCalculated(false);
				mainWindow.getCitiesPanel().setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.CitiesPath));

				if (mainWindow.isDrawingNeeded()) {
					if (resultWindow != null && resultWindow.isShowing()) {
						resultWindow.hide();
					}
					resultWindow = new ResultWindow(mainWindow.getOutputPanel(), mainWindow.getOutputLable());
					resultWindow.setLocation(850, 0);
					resultWindow.setVisible(true);
				}

				AlgorithmType selectedType = (AlgorithmType) mainWindow.getCbAlgorithm().getSelectedItem();

				mainWindow.initializeData();
				algorithm = TSAlgorithmFactory.getTSAlgorithm(selectedType, getMainController());
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

	public ActionListener updateTimeListener = new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			long spentTime = System.currentTimeMillis() - startCalculationTime;
			if (algorithm.isCalculationProcessed() && spentTime < Long.valueOf(mainWindow.getMaxWorkTimeTf().getText()) * 1000) {
				mainWindow.getCurrentTimeLbl().setText(String.valueOf(spentTime) + " мс.");
			} else {
				timer.stop();
				algorithm.stopCalculation();
				algorithm.drawFinalResult();
			}
		}
	};

	public ActionListener btnClearAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainWindow.getCurrentIterationLbl().setText("0");
			mainWindow.getLblOptimalTour().setText("0");
			mainWindow.getCitiesPanel().clearAll();
			mainWindow.getCitiesPanel().setDrawerStrategy(DrawerFactory.getDrawerStrategy(DrawerType.Cities));
			
			if (resultWindow != null && resultWindow.isShowing()) {
				resultWindow.hide();
			}
			mainWindow.getCitiesPanel().repaint();
		}
	};

	public ActionListener btnTestChartAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			final TourChart demo = new TourChart("Мінімальний шлях по ітераціям", algorithm.getIterationDistances());
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);
		}
	};

	public ActionListener loadDataAction = new ActionListener() {
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(FileHelper.defDirForData));
			int ret = fileopen.showOpenDialog(mainWindow.getContentPane());
			if (ret == JFileChooser.APPROVE_OPTION) {
				try {
					mainWindow.setCities((List<City>) SerializationHelper.deserializeFromFile(fileopen.getSelectedFile()));
				} catch (IOException | ClassNotFoundException e) {
					log.error(e);
				}
			}
		}
	};

	public ActionListener saveDataAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fileopen = new JFileChooser();
			fileopen.setCurrentDirectory(new File(FileHelper.defDirForData));
			int ret = fileopen.showSaveDialog(mainWindow.getContentPane());
			if (ret == JFileChooser.APPROVE_OPTION) {
				try {
					SerializationHelper.serializeToFile(mainWindow.getCities(), fileopen.getSelectedFile());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	};
	
	public GAMainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(GAMainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
}
