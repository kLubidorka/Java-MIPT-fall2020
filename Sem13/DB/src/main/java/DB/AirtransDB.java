package DB;

import DB.Tables.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AirtransDB {
    private final static String CSV_PATH = "./csv_data/";
    private final static String QUERIES_PATH = "./src/main/sqlQueries/";
    private final static String DATA_SOURCE = "https://storage.yandexcloud.net/airtrans-small/";
    private static ArrayList<BaseTable> tables;
    static Logger logger;

    static {
        logger = Logger.getLogger(AirtransDB.class.getName());
        try {
            tables = new ArrayList<>(Arrays.asList(new Aircrafts(), new Airports(), new Boarding_passes(),
                    new Bookings(), new Flights(), new Seats(), new Ticket_flights(), new Tickets()));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            AirtransDBConnection.closeConnection();
        }
    }

    public static void dropAllTables() {
        AirtransDBConnection.executeSqlUpdateQueryFromFile(new File(QUERIES_PATH + "DropAllTables.sql"));
    }

    public static void createTables() {
        for (BaseTable table : tables) {
            table.createTable();
        }
    }

    private static void addConstraints() {
        AirtransDBConnection.executeSqlUpdateQueryFromFile(new File(QUERIES_PATH + "AddConstraints.sql"));
    }

    private static void loadTablesFromCsv() {
        for (BaseTable table : tables){
            table.insertDataFromCsv(CSV_PATH + table.getTableName() + ".csv");
        }
    }

    private static void downloadFile(String file_url, String placeToWrite, String fileName) throws IOException {
        URL url = new URL(file_url);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        Path path = new File(placeToWrite + fileName).toPath();
        Files.copy(inputStream, path);
    }

    public static void downloadCsv() throws IOException {
        File csvFolder = new File(CSV_PATH);
        if (csvFolder.exists()) {
            deleteFile(csvFolder);
        }
        assert csvFolder.mkdir();
        for (BaseTable table : tables) {
            String tableName = table.getTableName();
            String url = String.format(DATA_SOURCE + "%s.csv", tableName);
            downloadFile(url, CSV_PATH, tableName + ".csv");
        }
    }

    public static boolean deleteFile(File fileToDelete) {
        File[] allContents = fileToDelete.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFile(file);
            }
        }
        return fileToDelete.delete();
    }

    public AirtransDB(boolean isDbReady, boolean isDbDownloaded) throws IOException {
        if (isDbReady && !isDbDownloaded) {
            throw new RuntimeException("Not even downloaded database cannot be ready!");
        }

        if (!isDbDownloaded) {
            downloadCsv();
        }

        if (!isDbReady) {
            dropAllTables();
            createTables();
            loadTablesFromCsv();
            addConstraints(); // loads faster without constraints
        }
    }

    private static ResultSet executeSelectQuery(String sqlFileName) {
        return AirtransDBConnection.executeSqlSelectQueryFromFile(new File(QUERIES_PATH + sqlFileName));
    }

    private static String getCityFromJson(String jsonStr) {
        jsonStr = jsonStr.substring(1, jsonStr.length() - 1).replaceAll("\\\\", "");
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonElement = jsonParser.parse(jsonStr).getAsJsonObject();
        return jsonElement.get("ru").toString().replaceAll("\"", "");
    }

    /**
     * @return cities with more than one airport
     */
    public static ArrayList<String[]> getCitiesWithManyAirports() {
        ArrayList<String[]> returnValue = new ArrayList<>();
        ResultSet queryResult = executeSelectQuery("task_B/B1.sql");
        try {
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[2];
                result[0] = getCityFromJson(queryResult.getString("city"));
                result[1] = queryResult.getString("airports_in_city");
                returnValue.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * @param citiesNum - number of cities with greatest number of cancelled flights
     * @return List of citiesNum cities with greatest number of cancelled flights in descending order
     * String[0] -- city, String[1] -- number of cancelled flights in city
     */
    public static ArrayList<String[]> getCitiesWithManyCancelledFlights(int citiesNum) {
        ArrayList<String[]> returnValue = new ArrayList<>();
        try {
            File file = new File("./src/main/sqlQueries/task_B/B2.sql");
            String query = AirtransDBConnection.parseQueryFromFile(file)[0];
            PreparedStatement preparedStatement = AirtransDBConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, citiesNum);
            ResultSet queryResult = preparedStatement.executeQuery();
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[2];
                result[0] = getCityFromJson(queryResult.getString("city"));
                result[1] = queryResult.getString("cancelled_flights");
                returnValue.add(result);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * @return shortest routes from all the cities with their duration
     */
    public static ArrayList<String[]> getShortestRoutes() {
        ArrayList<String[]> returnValue = new ArrayList<>();
        try {
            ResultSet queryResult = executeSelectQuery("task_B/B3.sql");
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[3];
                result[0] = getCityFromJson(queryResult.getString("from_city"));
                result[1] = getCityFromJson(queryResult.getString("to_city"));
                result[2] = queryResult.getString("duration");
                returnValue.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * @return number of cancelled flights with respect to months
     */
    public static ArrayList<String[]> getCancellationStatistics() {
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь",
                "Ноябрь", "Декабрь"};
        ArrayList<String[]> returnValue = new ArrayList<>();
        try {
            ResultSet queryResult = executeSelectQuery("task_B/B4.sql");
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[2];
                result[0] = months[queryResult.getInt("month") - 1];
                result[1] = queryResult.getString("cancellations");
                returnValue.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private static ArrayList<String[]> getMoscowFlights(boolean isFrom) {
        ArrayList<String[]> returnValue = new ArrayList<>();
        try {
            ResultSet queryResult;
            if (isFrom) {
                queryResult = executeSelectQuery("task_B/B5-2.sql");
            } else {
                queryResult = executeSelectQuery("task_B/B5-1.sql");
            }
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[2];
                result[0] = queryResult.getString("day");
                result[1] = queryResult.getString("flights");
                returnValue.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * @return number of flights to Moscow with respect to dates
     */
    public static ArrayList<String[]> getFlightsToMoscow() {
        return getMoscowFlights(false);
    }

    /**
     * @return number of flights from Moscow with respect to dates
     */
    public static ArrayList<String[]> getFlightsFromMoscow() {
        return getMoscowFlights(true);
    }

    /**
     * Deletes all the flights (in a cascade way) of aircraft of selected model
     *
     * @return number of deleted rows
     */
    public static int discardAircraft(String aircraft) {
        try {
            File file = new File(QUERIES_PATH + "task_B/B6.sql");
            String query = AirtransDBConnection.parseQueryFromFile(file)[0];
            PreparedStatement preparedStatement = AirtransDBConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, aircraft);
            return preparedStatement.executeUpdate();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Cancels all the flights in range [startDate, endDate]
     *
     * @return Airlines' foregone earnings with respect to days in range [startDate, endDate]
     */
    public static ArrayList<String[]> cancelFlights(String startDate, String endDate) {
        ArrayList<String[]> returnValue = new ArrayList<>();
        try {
            File file = new File(QUERIES_PATH + "task_B/B7.sql");
            String[] query = AirtransDBConnection.parseQueryFromFile(file);
            PreparedStatement preparedStatement = AirtransDBConnection.getConnection().prepareStatement(query[0]);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.executeUpdate();
            AirtransDBConnection.getConnection().commit();

            PreparedStatement preparedStatement1 = AirtransDBConnection.getConnection().prepareStatement(query[1]);
            preparedStatement1.setString(1, startDate);
            preparedStatement1.setString(2, endDate);

            ResultSet queryResult = preparedStatement1.executeQuery();
            queryResult.beforeFirst();
            while (queryResult.next()) {
                String[] result = new String[2];
                result[0] = queryResult.getString("d");
                result[1] = queryResult.getString("sum");
                returnValue.add(result);
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * Adds ticket to database. All the values except specified ones must be bot null
     *
     * @param contact_data null if unknown
     * @param boarding_no  null if unknown
     * @param seat_no      null if unknown
     * @return true if ticket was added, false if wasn't. Latter means that something is wrong with parameters
     */
    public static boolean addTicket(String ticket_no,
                                    String book_ref,
                                    String book_date,
                                    String passenger_id,
                                    String passenger_name,
                                    String contact_data,
                                    String fare_conditions,
                                    int amount,
                                    String flight_id,
                                    String boarding_no,
                                    String seat_no) {
        try {
            if (ticket_no == null || book_ref == null || book_date == null || passenger_id == null
                    || passenger_name == null || fare_conditions == null || flight_id == null) {
                throw new IllegalArgumentException("Only contact_data, boarding_no and seat_no can be null");
            }
            File file = new File(QUERIES_PATH + "task_B/B8.sql");
            String[] query = AirtransDBConnection.parseQueryFromFile(file);

            if (seat_no != null) {
                PreparedStatement checkData = AirtransDBConnection.getConnection().prepareStatement(query[0]);
                checkData.setString(1, flight_id);
                checkData.setString(2, seat_no);
                checkData.setString(3, fare_conditions);

                ResultSet queryResult = checkData.executeQuery();
                queryResult.beforeFirst();
                if (!queryResult.next()) {
                    throw new IllegalArgumentException("incorrect flight_id, seat_no or fare_conditions");
                }
            }
            PreparedStatement updateBooking = AirtransDBConnection.getConnection().prepareStatement(query[1]);
            updateBooking.setString(3, book_ref);
            updateBooking.setString(2, book_date);
            updateBooking.setInt(1, amount);
            updateBooking.executeUpdate();

            PreparedStatement addTicket = AirtransDBConnection.getConnection().prepareStatement(query[2]);
            addTicket.setString(1, ticket_no);
            addTicket.setString(2, book_ref);
            addTicket.setString(3, passenger_id);
            addTicket.setString(4, passenger_name);
            addTicket.setString(5, contact_data != null ? contact_data : "NULL");
            addTicket.executeUpdate();

            PreparedStatement addTicketFlight = AirtransDBConnection.getConnection().prepareStatement(query[3]);
            addTicketFlight.setString(1, ticket_no);
            addTicketFlight.setString(2, flight_id);
            addTicketFlight.setString(3, fare_conditions);
            addTicketFlight.setInt(4, amount);
            addTicketFlight.executeUpdate();

            if (boarding_no != null && seat_no != null) {
                PreparedStatement addBoardingPass = AirtransDBConnection.getConnection().prepareStatement(query[4]);
                addBoardingPass.setString(1, ticket_no);
                addBoardingPass.setString(2, flight_id);
                addBoardingPass.setString(3, boarding_no);
                addBoardingPass.setString(4, seat_no);
                addBoardingPass.executeUpdate();
            }
            AirtransDBConnection.getConnection().commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "Ticket was not added");
            return false;
        }
        return true;
    }
}
