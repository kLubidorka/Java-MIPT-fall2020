package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class Bookings extends BaseTable {
    public Bookings() throws SQLException {
        super("bookings");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/BookingsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Bookings table");
        } else{
            logger.info("Created Bookings table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values) throws SQLException {
        preparedStatement.setString(1, values[0]);
        preparedStatement.setTimestamp(2, BaseTable.parseTimestampWithTimezone(values[1]));
        preparedStatement.setDouble(3, Double.parseDouble(values[2]));
        preparedStatement.addBatch();
    }
}
