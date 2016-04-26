package tsp.ui.charts;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import tsp.utils.Result;

public class TimeChart extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	private Result gaRes;
	private Result greedyRes;
	private Result branchBoundRes;

	public TimeChart( String applicationTitle , String chartTitle, Result gaRes, Result greedyRes, Result branchBoundRes ) 
	{
		super( applicationTitle );   
		this.gaRes = gaRes;
		this.greedyRes = greedyRes;
		this.branchBoundRes = branchBoundRes;
		JFreeChart barChart = ChartFactory.createBarChart(
				chartTitle,           
				"Час",            
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
		final String timeType = "Середній час роботи";         
		final DefaultCategoryDataset dataset = 
				new DefaultCategoryDataset( );  

		double[] timeValues = {gaRes.getAverageTimeSec(), greedyRes.getAverageTimeSec(), branchBoundRes.getAverageTimeSec()};
		addDataInPercentage(dataset, timeType, labels, timeValues);

		return dataset; 
	}

	private void addDataInPercentage(DefaultCategoryDataset ds, String type, String[] labels, double[] values) {
		for (int i = 0; i < labels.length; i++) {
			ds.addValue(values[i], labels[i], type);
		}
	}

}
