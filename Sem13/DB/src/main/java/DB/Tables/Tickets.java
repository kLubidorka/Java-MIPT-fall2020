package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;

public class Tickets extends BaseTable {
    public Tickets() throws SQLException {
        super("tickets");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/TicketsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Tickets table");
        } else{
            logger.info("Created Tickets table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values){
        try {
            preparedStatement.setString(1, values[0]);
            preparedStatement.setString(2, values[1]);
            preparedStatement.setString(3, values[2]);
            preparedStatement.setString(4, values[3]);
            preparedStatement.setString(5, values[4]);
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.log(Level.SEVERE , Arrays.toString(e.getStackTrace()));
        }
    }
}