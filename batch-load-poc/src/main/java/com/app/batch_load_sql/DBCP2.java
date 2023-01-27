package com.app.batch_load_sql;

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class DBCP2 {

    private static BasicDataSource dataSource;

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

    @SneakyThrows
    private static Connection getConnection(String dbName, String dbHost, String dbPort, String dbSchema, String dbUser, String dbPassword) {
        String DB_URL = null;
        if(dbName.equalsIgnoreCase("mssql")){
            DB_URL = "jdbc:sqlserver://"+dbHost+":"+dbPort+";databaseName="+dbSchema; //+";"+"user="+dbUser+";"+"password="+dbPassword+";rewriteBatchedStatements=true";
            System.out.println("url : "+DB_URL);
            if (dataSource == null) {
                BasicDataSource ds = new BasicDataSource();
                ds.setUrl(DB_URL);
                ds.setUsername(dbUser);
                ds.setPassword(dbPassword);
                ds.setMaxTotal(4);
                ds.setMinIdle(5);
                ds.setMaxIdle(100000);
                ds.setMaxOpenPreparedStatements(100);
                //ds.setMaxTotal(5);
                dataSource = ds;
            }

                return dataSource.getConnection();

        }
        else if (dbName.equalsIgnoreCase("PostgreSQL")){
            DB_URL = "jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbSchema+"?user="+dbUser+"&password="+dbPassword;
            System.out.println("url : "+DB_URL);
            System.out.println("url : "+DB_URL);
            if (dataSource == null) {
                BasicDataSource ds = new BasicDataSource();
                ds.setUrl(DB_URL);
                ds.setMinIdle(5);
                ds.setMaxIdle(100000);
                ds.setMaxOpenPreparedStatements(100);
                dataSource = ds;
            }
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        else if (dbName.equalsIgnoreCase("oracle")){
            //jdbc:oracle:thin:@si-core-db:1521:ORCL","system","Sapiens2021
            DB_URL = "jdbc:oracle:thin:@"+dbHost+":"+dbPort+":"+dbSchema;
            String DB_USER = dbUser;
            String DB_PASSWORD = dbPassword;
            System.out.println("url : "+DB_URL);

        }

        return null;
    }
}
