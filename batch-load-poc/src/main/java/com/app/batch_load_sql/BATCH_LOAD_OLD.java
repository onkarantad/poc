package com.app.batch_load_sql;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import org.yaml.snakeyaml.Yaml;

public class BATCH_LOAD_OLD {
	
	public static void main(String[] args) {
		Yaml yaml = new Yaml();
		Reader yamlFile = null;
		try {
			yamlFile = new FileReader("config.yaml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map<String, Object> configMap = yaml.load(yamlFile);
		//configMap.entrySet().stream().forEach(i -> System.out.println(i.getKey() + " : " + i.getValue()));

		String excelPath = (String) configMap.get("excelPath");
		String sheetName = (String) configMap.get("sheetName");
		String dbName = (String) configMap.get("dbName");
		String dbHost = (String) configMap.get("dbHost");
		String dbPort = configMap.get("dbPort").toString();
		String dbSchema = (String) configMap.get("dbSchema");
		String dbUser = (String) configMap.get("dbUser");
		String dbPassword = (String) configMap.get("dbPassword");
		String sql = (String) configMap.get("sql");
		String BatchLoadQuery = readFile("LITIGATION_BATCH_LOAD_TEST.sql");


		try (Connection connection = JdbcConnection.getConnection(dbName, dbHost, dbPort, dbSchema, dbUser, dbPassword);){

			System.out.println("Batch load started");
			long start_time = System.currentTimeMillis();

			Statement targetStatement = connection.createStatement();
			targetStatement.addBatch(BatchLoadQuery);
			targetStatement.executeBatch();

			long end_time = System.currentTimeMillis();
			long time_taken = end_time-start_time;

			System.out.println("Batch executed in : "+time_taken+" ms | "+(time_taken/60000.0)+" min");

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


	}

	static String readFile(String path) {
		Charset encoding = StandardCharsets.UTF_8;
		byte[] encoded = new byte[0];
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new String(encoded, encoding);
	}

}