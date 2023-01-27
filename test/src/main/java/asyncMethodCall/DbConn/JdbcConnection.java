package asyncMethodCall.DbConn;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    public static Connection getConnection(String dbName,String dbHost,String dbPort,String dbSchema,
                                    String dbUser,String dbPassword) {
        String DB_URL = null;
        Connection connection = null;
        if(dbName.equalsIgnoreCase("mssql")){
            DB_URL = "jdbc:sqlserver://"+dbHost+":"+dbPort+";databaseName="+dbSchema+";"+"user="+dbUser+";"+"password="+dbPassword;
            System.out.println("url : "+DB_URL);
            try {
            	connection = DriverManager.getConnection(DB_URL);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (dbName.equalsIgnoreCase("PostgreSQL")){
            DB_URL = "jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbSchema+"?user="+dbUser+"&password="+dbPassword;
            System.out.println("url : "+DB_URL);
            try {
            	connection = DriverManager.getConnection(DB_URL);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
