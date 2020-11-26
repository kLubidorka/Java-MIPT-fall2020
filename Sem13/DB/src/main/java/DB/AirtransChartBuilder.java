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
import java.util.ArrayList;


public class AirtransChartBuilder {
    private final QueryRunner queryRunner;
    private static final int chartWidth = 1920;
    private static final int chartHeight = 1080;

    public AirtransChartBuilder(QueryRunner runner) {
        queryRunner = runner;
    }

    private DefaultCategoryDataset fillDataset(ArrayList<String[]> data) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "default", row[0]);
        }
        return dataset;
    }

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

    public void query4CreateBarChart(String filepath) throws IOException {
        ArrayList<String[]> data = queryRunner.getCancellationStatistics();
        DefaultCategoryDataset dataset = fillDataset(data);
        String title = "Number of cancelled flights w.r.t month";
        String categoryAxis = "Month";
        String valueAxis = "Number of flights";

        JFreeChart barChart = ChartFactory.createBarChart(title, categoryAxis, valueAxis, dataset,
                PlotOrientation.VERTICAL, false, false, false);
        setupChart(barChart, title);
        ChartUtilities.saveChartAsPNG(new File(filepath), barChart, chartWidth, chartHeight);
    }

    public void query5CreateBarCharts(String filepath1, String filepath2) throws IOException {
        for (int i = 0; i < 2; i++) {
            ArrayList<String[]> data;
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

    public void query6CreateBarCharts(String filepath, String from, String till) throws IOException {
        ArrayList<String[]> data = queryRunner.cancelFlights(from, till);
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
