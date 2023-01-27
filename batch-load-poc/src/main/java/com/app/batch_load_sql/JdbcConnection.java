package com.app.batch_load_sql;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.yaml.snakeyaml.Yaml;

public class JdbcConnection {

    public static Connection getConnection(){
        Yaml yaml = new Yaml();
        Reader yamlFile = null;
        try {
            yamlFile = new FileReader("config.yaml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> configMap = yaml.load(yamlFile);
        String dbName = (String) configMap.get("dbName");
        String dbHost = (String) configMap.get("dbHost");
        String dbPort = configMap.get("dbPort").toString();
        String dbSchema = (String) configMap.get("dbSchema");
        String dbUser = (String) configMap.get("dbUser");
        String dbPassword = (String) configMap.get("dbPassword");
        return getConnection(dbName, dbHost, dbPort, dbSchema, dbUser, dbPassword);
    }
    public static Connection getConnection(String dbName,String dbHost,String dbPort,String dbSchema,
                                    String dbUser,String dbPassword) {
        String DB_URL = null;
        Connection connection = null;
        if(dbName.equalsIgnoreCase("mssql")){
            DB_URL = "jdbc:sqlserver://"+dbHost+":"+dbPort+";databaseName="+dbSchema+";"+"user="+dbUser+";"+"password="+dbPassword+";rewriteBatchedStatements=true";
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

        else if (dbName.equalsIgnoreCase("oracle")){
            //jdbc:oracle:thin:@si-core-db:1521:ORCL","system","Sapiens2021
            DB_URL = "jdbc:oracle:thin:@"+dbHost+":"+dbPort+":"+dbSchema;
            String DB_USER = dbUser;
            String DB_PASSWORD = dbPassword;
            System.out.println("url : "+DB_URL);
            try {
                connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if (dbName.equalsIgnoreCase("oracle_wallet")){
//            dbHost = "dbsrc_high";
//            dbSchema = "src/main/resources/Wallet_dbSRC";
            DB_URL="jdbc:oracle:thin:@"+dbHost+"?TNS_ADMIN="+dbSchema;
            String DB_USER = dbUser;
            String DB_PASSWORD = dbPassword;
            String CONN_FACTORY_CLASS_NAME="oracle.jdbc.pool.OracleDataSource";

            try{
                PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
                pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
                pds.setURL(DB_URL);
                pds.setUser(DB_USER);
                pds.setPassword(DB_PASSWORD);
                pds.setConnectionPoolName("JDBC_UCP_POOL");
                pds.setInitialPoolSize(5);
                pds.setMinPoolSize(5);
                pds.setMaxPoolSize(20);
                pds.setTimeoutCheckInterval(5);
                pds.setInactiveConnectionTimeout(10);

                System.out.println("url : "+DB_URL);

                // Get the database connection from UCP.
//                connection = pds.getConnection();
                try {
                	connection = pds.getConnection();
                    return connection;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //System.out.println("Available connections after checkout: " + pds.getAvailableConnectionsCount());
                //System.out.println("Borrowed connections after checkout: " + pds.getBorrowedConnectionsCount());
            }
            catch (SQLException e){
                System.out.println("UCPSample - " + "SQLException occurred : " + e.getMessage());
            }
            //System.out.println("Available connections after checkin: " + pds.getAvailableConnectionsCount());
            //System.out.println("Borrowed connections after checkin: " + pds.getBorrowedConnectionsCount());
        }

        return null;
    }
}
