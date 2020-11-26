package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class Boarding_passes extends BaseTable {
    public Boarding_passes() throws SQLException {
        super("boarding_passes");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/BoardingPassesCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Boarding Passes table");
        } else{
            logger.info("Created Boarding Passes table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values) throws SQLException {
        preparedStatement.setString(1, values[0]);
        preparedStatement.setInt(2, Integer.parseInt(values[1]));
        preparedStatement.setInt(3, Integer.parseInt(values[2]));
        preparedStatement.setString(4, values[3]);
        preparedStatement.addBatch();
    }
}
