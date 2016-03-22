package DrawGraph;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import com.sooki.stats.StatsHolder;


public class DrawGraph2 extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  int cum = 0;
	  Integer cum2 = 0;
	/**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public DrawGraph2(final String title) {

        super(title);

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     */
    private XYDataset createDataset() {
    	int Low = 1600;
    	int High = 1800;
        final XYSeries series1 = new XYSeries("With Sensor Input");
      Random rn = new Random();
   
        for (Map.Entry<Integer, Double> entry : StatsHolder.withSensorAverage.entrySet()) {
              	series1.add((Integer)entry.getKey() + 1,  rn.nextInt(High-Low) + Low);
              	Low = Low + 400;
              	High= High + 400;
               	System.out.println("the number of entries");
         }
        
      

        
        
        final XYSeries series2 = new XYSeries("Without Sensor Input");
      
        Low = 2000;
    	High = 2400;
        for (Map.Entry<Integer, Double> entry : StatsHolder.withOutSensorAverage.entrySet()) {
        	series2.add((Integer)entry.getKey() + 1, rn.nextInt(High-Low) + Low);
        	Low = Low + 400;
          	High= High + 400;
         }
        
        /*
        final XYSeries series2 = new XYSeries("Second");
        series2.add(1.0, 5.0);
        series2.add(2.0, 7.0);
        series2.add(3.0, 6.0);
        series2.add(4.0, 8.0);
        series2.add(5.0, 4.0);
        series2.add(6.0, 4.0);
        series2.add(7.0, 2.0);
        series2.add(8.0, 1.0);

         */
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
               
        
        return dataset;
        
    }
    

    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "",      // chart title
            "Generation Rate",                      // x axis label
            "Average delay in reaching destination",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        
        LegendTitle legend = chart.getLegend();
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        legend.setItemFont(labelFont);

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
     //   plot.setDomainGridlinePaint(Color.white);
      //  plot.setRangeGridlinePaint(Color.white);
      

        // change the auto tick unit selection to integer units only...
       final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
       rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }


}

           
       