import java.sql.Connection;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionSimpleExample {
    private static final String DB_URL = "jdbc:h2:./db/TestDatabase";
    private static final String DB_Driver = "org.h2.Driver";
    private static Connection connection;
    private static Logger logger;

    public static void main(String[] args) {
        init();
    }

    static void init(){
        logger = Logger.getLogger(DBConnectionSimpleExample.class.getName());
        try{
            Class.forName(DB_Driver);
        } catch (ClassNotFoundException e){
            logger.log(Level.SEVERE ,"Driver not Found");
            logger.log(Level.SEVERE , Arrays.toString(e.getStackTrace()));
        }
    }
}
