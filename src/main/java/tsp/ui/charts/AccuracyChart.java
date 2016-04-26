package tsp.ui.charts;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import tsp.utils.Result;

public class AccuracyChart extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	private Result gaRes;
	private Result greedyRes;
	private Result branchBoundRes;

	public AccuracyChart( String applicationTitle , String chartTitle, Result gaRes, Result greedyRes, Result branchBoundRes ) 
	{
		super( applicationTitle );   
		this.gaRes = gaRes;
		this.greedyRes = greedyRes;
		this.branchBoundRes = branchBoundRes;
		JFreeChart barChart = ChartFactory.createBarChart(
				chartTitle,           
				"Точність",            
				"Результат",            
				createDataset(),          
				PlotOrientation.VERTICAL,           
				true, true, false);

		ChartPanel chartPanel = new ChartPanel( barChart );        
		chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
		setContentPane( chartPanel ); 


	}
	private CategoryDataset createDataset( )
	{
		String[] labels = {"GA", "Greedy", "BranchBound"};       
		final String accuracyType = "Точність(кількість знах опт. шляху)";      
		final DefaultCategoryDataset dataset = 
				new DefaultCategoryDataset( );  

		int gaPoints = 0, greedyPoints = 0;
		List<Integer> gaDistances = gaRes.getDistances();
		List<Integer> greedyDistances = greedyRes.getDistances();
		for(int i = 0; i < gaDistances.size(); i++) {
			int optDist = branchBoundRes.getDistances().stream().min(Integer::min).get();
			gaPoints += gaDistances.get(i).equals(optDist) ? 1 : 0;
			greedyPoints += greedyDistances.get(i).equals(optDist) ? 1 : 0;
		}
		double[] accuracy = {gaPoints, greedyPoints, branchBoundRes.getDistances().size()};
		addDataInPercentage(dataset, accuracyType, labels, accuracy);

		return dataset; 
	}

	private void addDataInPercentage(DefaultCategoryDataset ds, String type, String[] labels, double[] values) {

		for (int i = 0; i < labels.length; i++) {
			ds.addValue(values[i], labels[i], type);
		}
	}

}
