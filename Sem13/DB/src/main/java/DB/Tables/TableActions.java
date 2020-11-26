package DB.Tables;

import java.sql.SQLException;

public interface TableActions {

    void createTable() throws SQLException;

    void insertDataFromCsv(String filePath);
}