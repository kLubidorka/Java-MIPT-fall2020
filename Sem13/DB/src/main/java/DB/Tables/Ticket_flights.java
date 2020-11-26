package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

public class Ticket_flights extends BaseTable {
    public Ticket_flights() throws SQLException {
        super("ticket_flights");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/TicketFlightsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Ticket Flights table");
        } else{
            logger.info("Created Ticket Flights table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values){
        try {
            preparedStatement.setString(1, values[0]);
            preparedStatement.setInt(2, Integer.parseInt(values[1]));
            preparedStatement.setString(3, values[2]);
            preparedStatement.setDouble(4, Double.parseDouble(values[3]));
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.log(Level.SEVERE , Arrays.toString(e.getStackTrace()));
        }
    }
}
