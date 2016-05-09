package tsp.ui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TourChart extends JFrame {
	private static final long serialVersionUID = 1L;
	private  Map<Integer, Double> distances;

	public TourChart( String chartTitle, Map<Integer, Double> distances ) 
	{
		super(chartTitle);
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.distances = distances;
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         chartTitle ,
	         "Номер ітерації" ,
	         "Мінімальний шлях" ,
	         createDataset() ,
	         PlotOrientation.VERTICAL ,
	         true , true , true);
	         
	      ChartPanel chartPanel = new ChartPanel( xylineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 700 ) );
	      final XYPlot plot = xylineChart.getXYPlot( );
	      plot.setBackgroundPaint(Color.WHITE);
	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	      
	      double minY = getMinY();
	      double maxY = getMaxY();
	        NumberAxis range = (NumberAxis) plot.getRangeAxis();
	        range.setRange(minY - minY * 0.1, maxY + maxY * 0.1);

	      renderer.setSeriesPaint( 0
	    		  , Color.GREEN );
	      renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
	      plot.setRenderer( renderer ); 
	      setContentPane( chartPanel );
        setContentPane(chartPanel);
	}
	
	private double getMaxY() {
	      return distances
		  .entrySet()
		  .stream()
		  .max( Map.Entry.comparingByValue(Double::compareTo) )
		  .get().getValue();
	}
	private double getMinY() {
	      return distances
		  .entrySet()
		  .stream()
		  .min( Map.Entry.comparingByValue(Double::compareTo) )
		  .get().getValue();
	}
	
private XYDataset createDataset() {
        
        final XYSeries series2 = new XYSeries("Маршрути");
        for (Integer key : distances.keySet()) {
        	series2.add(key, distances.get(key));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series2);
                
        return dataset;
        
    }
    
}
