package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.logging.Level;

public class Flights extends BaseTable {
    public Flights() throws SQLException {
        super("flights");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/FlightsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Flights table");
        } else{
            logger.info("Created Flights table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values) throws SQLException {
        preparedStatement.setString(1, values[0]);
        preparedStatement.setString(2, values[1]);
        preparedStatement.setTimestamp(3, BaseTable.parseTimestampWithTimezone(values[2]));
        preparedStatement.setTimestamp(4, BaseTable.parseTimestampWithTimezone(values[3]));
        preparedStatement.setString(5, values[4]);
        preparedStatement.setString(6, values[5]);
        preparedStatement.setString(7, values[6]);
        preparedStatement.setString(8, values[7]);
        if (values.length > 8 && !values[8].equals("")){
            preparedStatement.setTimestamp(9, BaseTable.parseTimestampWithTimezone(values[8]));
        } else{
            preparedStatement.setNull(9, Types.TIMESTAMP);
        }
        if (values.length > 9 && !values[9].equals("")){
            preparedStatement.setTimestamp(10, BaseTable.parseTimestampWithTimezone(values[9]));
        } else{
            preparedStatement.setNull(10, Types.TIMESTAMP);
        }
        preparedStatement.addBatch();
    }
}
