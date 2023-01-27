package asyncMethodCall;

import asyncMethodCall.DbConn.GetYamlMap;
import asyncMethodCall.DbConn.JdbcConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestMerge {
    private static final Logger log = LogManager.getLogger(TestMerge.class);
    private static Connection connection;

    public static void main(String[] args) throws IOException, SQLException {

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
        String sql1 = readFile("mergeQuery_individual.sql", StandardCharsets.UTF_8);
        String sql2 = readFile("mergeQuery_entity_driver_details.sql", StandardCharsets.UTF_8);

        connection = JdbcConnection.getConnection(dbName, dbHost, dbPort, dbSchema, dbUser, dbPassword);

        Statement statement1 = connection.createStatement();

//        int a =  statement1.executeUpdate(sql);
//        System.out.println("affected_rows: "+a);

        statement1.addBatch(sql1);
        statement1.addBatch(sql2);
        int affectedRows[]  =   statement1.executeBatch();
        List<Integer> list = Arrays.stream(affectedRows).boxed().collect(Collectors.toList());
        System.out.println("affected_rows: "+ list);





    }

    static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
