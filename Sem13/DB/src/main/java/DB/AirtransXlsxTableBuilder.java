package DB;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



public class AirtransXlsxTableBuilder {
    final static String OUTPUT_XLS_PATH = "./query_results/";

    private static void build_excel_table(String heading,
                                          String[] column_descriptions,
                                          ArrayList<String[]> data,
                                          String filePath) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(heading);
        Row firstRow = sheet.createRow(0);

        CellStyle style = book.createCellStyle();
        Font font = book.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        style.setLocked(true);

        int j = 0;
        for (String rawCell : column_descriptions) {
            Cell currentCell = firstRow.createCell(j++);
            currentCell.setCellValue(rawCell);
            currentCell.setCellStyle(style);
        }

        int i = 1;
        for (String[] rawRow : data) {
            Row row = sheet.createRow(i++);
            j = 0;
            for (String rawCell : rawRow) {
                Cell currentCell = row.createCell(j++);
                currentCell.setCellValue(rawCell);
            }
        }
        for (int x = 0; x < sheet.getRow(0).getPhysicalNumberOfCells(); x++) {
            sheet.autoSizeColumn(x);
        }
        book.write(new FileOutputStream(new File(filePath)));
        book.close();
    }

    public static void b1CreateTable() throws IOException {
        build_excel_table("Cities with many airports", new String[]{"City", "Airports"},
                AirtransDB.getCitiesWithManyAirports(), OUTPUT_XLS_PATH + "B1.xlsx");
    }

    public static void b2CreateTable(int citiesNum) throws IOException {
        build_excel_table("Cities with many flights cancelled", new String[]{"City", "Cancelled flights"},
                AirtransDB.getCitiesWithManyCancelledFlights(citiesNum), OUTPUT_XLS_PATH + "B2.xlsx");
    }

    public static void b3CreateTable() throws IOException {
        build_excel_table("Shortest routes", new String[]{"City", "Destination", "Travel time in minutes"},
                AirtransDB.getShortestRoutes(), OUTPUT_XLS_PATH + "B3.xlsx");
    }

    public static void b4CreateTable() throws IOException {
        build_excel_table("Cancelled flights", new String[]{"Month", "Cancelled flights"},
                AirtransDB.getCancellationStatistics(), OUTPUT_XLS_PATH + "B4.xlsx");
    }

    public static void b5FromCreateTable() throws IOException {
        build_excel_table("Flights from Moscow", new String[]{"Week day", "Flights from Moscow"},
                AirtransDB.getFlightsFromMoscow(), OUTPUT_XLS_PATH + "B5From.xlsx");
    }

    public static void b5ToCreateTable() throws IOException {
        build_excel_table("Flights to Moscow", new String[]{"Week day", "Flights to Moscow"},
                AirtransDB.getFlightsToMoscow(), OUTPUT_XLS_PATH + "B5To.xlsx");
    }

    public static void b7CreateTable(String from, String till) throws IOException {
        build_excel_table("Flights from Moscow", new String[]{"Date", "Foregone earnings of airlines"},
                AirtransDB.cancelFlights(from, till), OUTPUT_XLS_PATH + "B7.xlsx");
    }
}