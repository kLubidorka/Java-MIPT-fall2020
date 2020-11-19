import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionSimpleExample {
    private static final String DB_URL = "jdbc:h2:./db/TestDatabase";
    private static final String DB_Driver = "org.h2.Driver";
    private static Connection connection;
    private static Logger logger;

    public static void main(String[] args) throws IOException {
        loadDriver();
        connect();
        createTableFromFile();
        fillDb();
    }

    static void loadDriver() {
        logger = Logger.getLogger(DBConnectionSimpleExample.class.getName());
        try {
            Class.forName(DB_Driver);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Driver not Found");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    static void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to connect to the DB");
        }
        logger.info("Opened connection to the DB");
    }

    static void reconnect() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to reopen connection to the DB");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    static void createTable() {
        String createString =
                "drop table if exists TEST_TABLE;" +
                        "create table TEST_TABLE " +
                        "(NAME varchar(30) NOT NULL, " +
                        "ID integer NOT NULL, " +
                        "PRIMARY KEY (NAME)); ";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createString);
            logger.log(Level.INFO, "Created table");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Failed to create table");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    static void createTableFromFile() throws IOException {
        String[] queries = parseQueriesFromFile(new File("./src/main/sql/create_table.sql"));
        for (String query : queries){
            if (!query.trim().isEmpty()){
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(query);
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Failed to create table");
                    logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
                }
            }
        }
        logger.log(Level.INFO, "Created table");
    }

    static String[] parseQueriesFromFile(File file) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString().split(";");
        }
    }

    static void fillDb(){
        performQuery("insert into TEST_TABLE values('Dmitry', 12)");
    }

    static void processResult(){
        ResultSet resultSet =  performQueryWithResult("select * from TEST_TABLE");
    }

    static void performQuery(String query){
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            logger.log(Level.INFO, "Query success");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error processing query");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    static ResultSet performQueryWithResult(String query){
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet =  stmt.executeQuery(query);
            logger.log(Level.INFO, "Query success");
            return resultSet;
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error processing query");
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw new RuntimeException("Query fail");
        }
    }
}
