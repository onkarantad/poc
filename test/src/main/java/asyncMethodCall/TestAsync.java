package asyncMethodCall;

import asyncMethodCall.CallDb.DbCall;
import asyncMethodCall.DbConn.GetYamlMap;
import asyncMethodCall.DbConn.JdbcConnection;
import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.Logger;


public class TestAsync {
    private static final Logger log = LogManager.getLogger(TestAsync.class);
    static Faker faker = new Faker();
    private static Connection connection;
    public static void main(String[] args) throws SQLException, InterruptedException {

        int count = 10;

        Map<String, Object> yamlMap = GetYamlMap.getYamlmap();
        log.info("yamlMap: "+yamlMap);
        String excelPath = (String) yamlMap.get("excelPath");
        String sheetName = (String) yamlMap.get("sheetName");
        String dbName = (String) yamlMap.get("dbName");
        String dbHost = (String) yamlMap.get("dbHost");
        String dbPort = yamlMap.get("dbPort").toString();
        String dbSchema = (String) yamlMap.get("dbSchema");
        String dbUser = (String) yamlMap.get("dbUser");
        String dbPassword = (String) yamlMap.get("dbPassword");
        String sql = "INSERT INTO public.employee (id, \"name\", email, joining_date) VALUES(?, ?, ?, ?)";

        System.err.println();

        connection = JdbcConnection.getConnection(dbName, dbHost, dbPort, dbSchema, dbUser, dbPassword);

        Long start = System.currentTimeMillis();

//        DbCall.insertdata(count,connection,sql);

        CompletableFuture.runAsync(() -> {
            try {
                DbCall.insertdata(count,connection,sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

//        new Thread(() -> {
//            try {
//                DbCall.insertdata(count,connection,sql);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }).start();

        //newThread.join();

        System.out.println(count+" records inserted in "+(System.currentTimeMillis()-start)+" ms");
//        while (!cf1.isDone()) {
//        }
        System.out.println(count+" records inserted in "+(System.currentTimeMillis()-start)+" ms");

        Thread.sleep(200000);

    }
}
