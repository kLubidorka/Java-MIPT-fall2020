package DB.Tables;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import DB.AirtransDBConnection;
import au.com.bytecode.opencsv.CSVReader;

public abstract class BaseTable {
    protected String tableName;
    static Logger logger;

    static {
        logger = Logger.getLogger(BaseTable.class.getName());
    }

    BaseTable(String tableName){
        this.tableName = tableName;
    }

    public String getTableName(){
        return tableName;
    }

    public abstract void createTable();

    public abstract void addInsertionToBatch(PreparedStatement preparedStatement, String[] values) throws SQLException;

    public abstract PreparedStatement getStatement() throws SQLException;

    public void insertDataFromCsv(String filePath) {
        try{
            FileReader file = new FileReader(filePath);
            CSVReader reader = new CSVReader(file, ',' , '"' , 0);
            PreparedStatement preparedStatement = getStatement();

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                addInsertionToBatch(preparedStatement, nextLine);
            }
            file.close();
            int[] rows = preparedStatement.executeBatch();
            AirtransDBConnection.getConnection().commit();
            int addedLinesNumber = Arrays.stream(rows).sum();
            logger.log(Level.INFO , String.format("Added %d lines to %s", addedLinesNumber, tableName));
        } catch (FileNotFoundException fileNotFound){
            logger.log(Level.SEVERE ,"Failed to read csv file");
        }
        catch (IOException | SQLException exception){
            logger.log(Level.SEVERE, exception.toString());
        }
    }

    public static Timestamp parseTimestampWithTimezone(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
        try {
            return new Timestamp(format.parse(time).getTime());
        } catch (ParseException e) {
            logger.log(Level.SEVERE, e.toString());
            return null;
        }
    }
}
