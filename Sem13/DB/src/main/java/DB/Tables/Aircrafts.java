package DB.Tables;

import DB.AirtransDBConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;


public class Aircrafts extends BaseTable{
    public Aircrafts(){
        super("aircrafts");
    }

    public void createTable() {
        File createTableQuery = new File("./src/main/sqlQueries/AircraftsCreateTable.sql");
        if (!AirtransDBConnection.executeSqlUpdateQueryFromFile(createTableQuery)){
            logger.log(Level.SEVERE ,"Failed to create Aircrafts table");
        } else{
            logger.info("Created Aircrafts table");
        }
    }

    public PreparedStatement getStatement() throws SQLException {
        String query = String.format("INSERT INTO %s VALUES (?, ?, ?)", tableName);
        return AirtransDBConnection.getConnection().prepareStatement(query);
    }

    public void addInsertionToBatch(PreparedStatement preparedStatement, String[] values){
        try {
            preparedStatement.setString(1, values[0]);
            preparedStatement.setString(2, values[1]);
            preparedStatement.setInt(3, Integer.parseInt(values[2]));
            preparedStatement.addBatch();
        } catch (SQLException e) {
            logger.log(Level.SEVERE , Arrays.toString(e.getStackTrace()));
        }
    }
}
