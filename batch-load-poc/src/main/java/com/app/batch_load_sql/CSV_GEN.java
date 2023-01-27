package com.app.batch_load_sql;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class CSV_GEN {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        String tableName = "SI_IDIT_ODS.CN_CONSENT";


        String query = "Select * from " + tableName;
        try (Connection connection = DBCP2.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query);) {
            File file = new File("csv_output/" + tableName + ".csv");
            FileWriter fw;
            if (file.exists()) {
                fw = new FileWriter(file, true);//if file exists append to file. Works fine.
            } else {
                file.createNewFile();
                fw = new FileWriter(file);
            }
            CSVWriter writer = new CSVWriter(fw);
            ResultSetMetaData Mdata = rs.getMetaData();
            int colCount = Mdata.getColumnCount();
            System.out.println("colCount: " + colCount);
            String tableHeader[] = new String[colCount];
            IntStream.range(0, colCount ).forEach(i -> {
                try {
                    tableHeader[i] = Mdata.getColumnName(i + 1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("tableHeader: " + Arrays.asList(tableHeader));
            writer.writeNext(tableHeader);

            String data[] = new String[colCount];
            System.out.println("fetchSize: " + rs.getFetchSize());
            rs.setFetchSize(500);
            while (rs.next()) {
                IntStream.range(0, colCount ).forEach(i -> {
                    try {
                        Object tempCol = rs.getObject(i + 1);
                        if (tempCol == null)
                            data[i] = "";
                        else
                            data[i] = rs.getObject(i + 1).toString();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                writer.writeNext(data);
            }
            writer.flush();
            long end = System.currentTimeMillis();
            long time_taken = end - start;
            System.out.println("Data entered in : " + time_taken + " ms | " + (time_taken / 60000.0) + " min");

        }
        catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
