import DB.AirtransDB;
import DB.AirtransChartBuilder;
import DB.AirtransXlsxTableBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static DB.AirtransDB.deleteFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AirtransTest {
    @BeforeAll
    static void setup() {
        try {
            new AirtransDB(false, false);
            System.out.println("DB is ready");
        } catch (IOException e) {
            assert false;
        }
    }

    @AfterAll
    static void removeDB() {
        assertTrue(deleteFile(new File("./db")));
        assertTrue(deleteFile(new File("./csv_data")));
        System.out.println("DB deleted");
    }

    @Test
    void queryB1Test() {
        ArrayList<String[]> result = AirtransDB.getCitiesWithManyAirports();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB2Test() {
        ArrayList<String[]> result = AirtransDB.getCitiesWithManyCancelledFlights(10);
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB3Test() {
        ArrayList<String[]> result = AirtransDB.getShortestRoutes();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + " --> " + row[1] + ": " + row[2]);
        }
    }

    @Test
    void queryB4Test() {
        ArrayList<String[]> result = AirtransDB.getCancellationStatistics();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB5ToTest() {
        ArrayList<String[]> result = AirtransDB.getFlightsToMoscow();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB5FromTest() {
        ArrayList<String[]> result = AirtransDB.getFlightsFromMoscow();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB6Test() {
        int result = AirtransDB.discardAircraft("_");
        System.out.println(String.format("Deleted %d rows\n", result));
    }

    @Test
    void queryB7Test() {
        ArrayList<String[]> result = AirtransDB.cancelFlights("01.08.2017", "15.08.2017");
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB8Test() {
        boolean result = AirtransDB.addTicket("testtesttestt",
                "0002D8",
                "2017-08-07 21:40:00+03",
                "6865 913231",
                "SEREGA JUKOVSKY",
                null,
                "Business",
                10000,
                "5502",
                null,
                null
        );
        if (result) {
            System.out.println("Ticket added");
        } else {
            System.out.println("Ticket not added");
            assert false;
        }
    }

    @Test
    void getAllTables() {
        try {
            AirtransXlsxTableBuilder.b1CreateTable();
            AirtransXlsxTableBuilder.b2CreateTable(12);
            AirtransXlsxTableBuilder.b3CreateTable();
            AirtransXlsxTableBuilder.b4CreateTable();
            AirtransXlsxTableBuilder.b5ToCreateTable();
            AirtransXlsxTableBuilder.b5FromCreateTable();
            AirtransXlsxTableBuilder.b7CreateTable("01.08.2017", "15.08.2017");
        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    void buildAllCharts() {
        final String OUTPUT_PNG_PATH = "./charts/";
        try {
            AirtransChartBuilder.query4CreateBarChart(OUTPUT_PNG_PATH + "query4BarChart.png");
            AirtransChartBuilder.query5CreateBarCharts(OUTPUT_PNG_PATH + "query5ABarChart.png",
                    OUTPUT_PNG_PATH + "query5BBarChart.png");
            AirtransChartBuilder.query6CreateBarCharts(OUTPUT_PNG_PATH + "query6BarChart.png",
                    "01.08.2017",
                    "09.08.2017");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}