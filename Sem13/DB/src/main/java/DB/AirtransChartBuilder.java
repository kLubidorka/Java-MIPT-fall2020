package DB;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Creates charts representing the results of some QueryRunner queries
 */
public class AirtransChartBuilder {
    private final QueryRunner queryRunner;
    private static final int chartWidth = 1920;
    private static final int chartHeight = 1080;

    public AirtransChartBuilder(QueryRunner runner) {
        queryRunner = runner;
    }

    /**
     * Converts List of strings to JFreeChart dataset
     * @param data List of String.
     *             List[0] -- numeric values for Y axis
     *             List[1] -- string values for X axis
     * @return JFreeChart dataset
     */
    private DefaultCategoryDataset fillDataset(List<String[]> data) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "default", row[0]);
        }
        return dataset;
    }

    /**
     * Apply default settings to the chart and set up title
     * @param barChart chart to set up
     * @param title title
     */
    private void setupChart(JFreeChart barChart, String title) {
        CategoryPlot plot = barChart.getCategoryPlot();
        CategoryAxis axis = plot.getDomainAxis();

        Font font = new Font("Cambria", Font.BOLD, 25);
        axis.setTickLabelFont(font);
        Font font3 = new Font("Cambria", Font.BOLD, 30);
        barChart.setTitle(new org.jfree.chart.title.TextTitle(title, new java.awt.Font("Cambria", java.awt.Font.BOLD, 40)));

        plot.getDomainAxis().setLabelFont(font3);
        plot.getRangeAxis().setLabelFont(font3);
        CategoryPlot categoryPlot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
    }

    /**
     * Creates a chart, representing the number of cancelled flights as a function of month
     * @param filepath where to store the chart
     * @throws IOException if a problem while saving the chart to the filesystem occurred
     */
    public void query4CreateBarChart(String filepath) throws IOException {
        List<String[]> data = queryRunner.getCancellationStatistics();
        DefaultCategoryDataset dataset = fillDataset(data);
        String title = "Number of cancelled flights w.r.t month";
        String categoryAxis = "Month";
        String valueAxis = "Number of flights";

        JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxis, valueAxis, dataset,
                PlotOrientation.VERTICAL, false, false, false);
        setupChart(barChart, title);
        ChartUtilities.saveChartAsPNG(new File(filepath), barChart, chartWidth, chartHeight);
    }

    /**
     * Creates two charts, representing the number of flights to (1st) and from (2nd) Moscow as a function of the weekday
     * @param filepath1 where to store the first chart
     * @param filepath2 where to store the second chart
     * @throws IOException if a problem while saving the chart to the filesystem occurred
     */
    public void query5CreateBarCharts(String filepath1, String filepath2) throws IOException {
        for (int i = 0; i < 2; i++) {
            List<String[]> data;
            data = i == 0 ? queryRunner.getFlightsToMoscow() : queryRunner.getFlightsFromMoscow();
            DefaultCategoryDataset dataset = fillDataset(data);
            String title = i == 0 ? "Number of flights to Moscow" : "Number of flights from Moscow";
            String categoryAxis = "Weekday";
            String valueAxis = "Number of flights";

            JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxis, valueAxis, dataset,
                    PlotOrientation.VERTICAL, false, false, false);
            setupChart(barChart, title);
            ChartUtilities.saveChartAsPNG(new File(i == 0 ? filepath1 : filepath2), barChart, chartWidth, chartHeight);
        }
    }

    /**
     * Creates a chart, representing the airway carrier's foregone earnings as a function of date
     * @param filepath where to store the chart
     * @param from String with the date corresponds to the start of the period. Format "dd.mm.yyyy"
     * @param till String with the date corresponds to the end of the period. Format "dd.mm.yyyy"
     * @throws IOException if a problem while saving the chart to the filesystem occurred
     */
    public void query6CreateBarCharts(String filepath, String from, String till) throws IOException {
        List<String[]> data = queryRunner.cancelFlights(from, till);
        DefaultCategoryDataset dataset = fillDataset(data);
        String title = "Foregone earnings w.r.t date";
        String categoryAxis = "Date";
        String valueAxis = "Foregone earnings";

        JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxis, valueAxis, dataset,
                PlotOrientation.VERTICAL, false, false, false);
        setupChart(barChart, title);
        ChartUtilities.saveChartAsPNG(new File(filepath), barChart, chartWidth, chartHeight);
    }
}
