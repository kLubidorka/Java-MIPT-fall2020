import DB.AirtransDB;
import DB.AirtransChartBuilder;
import DB.AirtransXlsxTableBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import DB.QueryRunner;
import org.junit.jupiter.api.*;

import static DB.AirtransDB.deleteFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AirtransTest {
    static QueryRunner queryRunner;

    @BeforeAll
    static void setup() {
        queryRunner = new QueryRunner(new AirtransDB());
        System.out.println("DB is ready");
    }

    @AfterAll
    static void removeDB() {
        assertTrue(deleteFile(new File("./db")));
        assertTrue(deleteFile(new File("./csv_data")));
        System.out.println("DB deleted");
    }

    @Test
    void queryB1Test() {
        ArrayList<String[]> result = queryRunner.getCitiesWithManyAirports();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB2Test() {
        ArrayList<String[]> result = queryRunner.getCitiesWithManyCancelledFlights(10);
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB3Test() {
        ArrayList<String[]> result = queryRunner.getShortestRoutes();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + " --> " + row[1] + ": " + row[2]);
        }
    }

    @Test
    void queryB4Test() {
        ArrayList<String[]> result = queryRunner.getCancellationStatistics();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB5ToTest() {
        ArrayList<String[]> result = queryRunner.getFlightsToMoscow();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB5FromTest() {
        ArrayList<String[]> result = queryRunner.getFlightsFromMoscow();
        assertNotNull(result);
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB6Test() {
        int result = queryRunner.discardAircraft("_");
        System.out.println(String.format("Deleted %d rows\n", result));
    }

    @Test
    void queryB7Test() {
        ArrayList<String[]> result = queryRunner.cancelFlights("01.08.2017", "15.08.2017");
        for (String[] row : result) {
            System.out.println(row[0] + ": " + row[1]);
        }
    }

    @Test
    void queryB8Test() {
        boolean result = queryRunner.addTicket("testtesttestt",
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
            AirtransXlsxTableBuilder tableBuilder = new AirtransXlsxTableBuilder(queryRunner);
            tableBuilder.b1CreateTable();
            tableBuilder.b2CreateTable(12);
            tableBuilder.b3CreateTable();
            tableBuilder.b4CreateTable();
            tableBuilder.b5ToCreateTable();
            tableBuilder.b5FromCreateTable();
            tableBuilder.b7CreateTable("01.08.2017", "15.08.2017");
        } catch (IOException e) {
            assert false;
        }
    }

    @Test
    void buildAllCharts() {
        final String OUTPUT_PNG_PATH = "./charts/";
        try {
            AirtransChartBuilder chartBuilder = new AirtransChartBuilder(queryRunner);
            chartBuilder.query4CreateBarChart(OUTPUT_PNG_PATH + "query4BarChart.png");
            chartBuilder.query5CreateBarCharts(OUTPUT_PNG_PATH + "query5ABarChart.png",
                    OUTPUT_PNG_PATH + "query5BBarChart.png");
            chartBuilder.query6CreateBarCharts(OUTPUT_PNG_PATH + "query6BarChart.png",
                    "01.08.2017",
                    "09.08.2017");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}