package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class Airports extends BaseTable{
    public Airports() throws SQLException {
        super("airports");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/AirportsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Airports table");
        } else{
            logger.info("Created Airports table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values) throws SQLException {
        preparedStatement.setString(1, values[0]);
        preparedStatement.setString(2, values[1]);
        preparedStatement.setString(3, values[2]);
        preparedStatement.setString(4, "POINT" + values[3].replace(',', ' '));
        preparedStatement.setString(5, values[4]);
        preparedStatement.addBatch();
    }
}
