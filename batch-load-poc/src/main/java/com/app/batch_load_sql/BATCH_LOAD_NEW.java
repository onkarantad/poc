package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

@Log4j2
public class BATCH_LOAD_NEW {
	
	public static void main(String[] args) {

		String BatchLoadQuery = readFile("AELD_BATCH_LOAD_TEST.sql");
		String insert1 = "INSERT_INTO_AELD.sql";
		String select1 = "SELECT_FROM_AELD.sql";
//		String insert1 = "INSERT_INTO_LITIGATION.sql";
//		String select1 = "SELECT_FROM_LITIGATION.sql";

		try (
				Connection connection = JdbcConnection.getConnection();
			 PreparedStatement select = connection.prepareStatement(readFile(select1));
				PreparedStatement insert = connection.prepareStatement(readFile(insert1));
		){
			connection.setAutoCommit(false);

			log.info("Batch load started");
			long start_time = System.currentTimeMillis();

//			Statement targetStatement = connection.createStatement();
//			targetStatement.addBatch(BatchLoadQuery);
//			targetStatement.executeBatch();

			select.setFetchDirection(ResultSet.FETCH_FORWARD);
			select.setFetchSize(1000);
			int rowCnt = 0;

			ResultSet rs = select.executeQuery();
			//log.info("break 1");
			ResultSetMetaData meta = rs.getMetaData();
			//log.info("break 2");
			int colCount = meta.getColumnCount();
			log.info("break 3 colCount: "+colCount);
			meta.getColumnTypeName(1);
			while (rs.next())
			{

				//log.info("break 4");
				for (int col=1; col <= colCount; col++)
				{
					//log.info("break 5 "+meta.getColumnName(col)+" | "+meta.getColumnTypeName(col));
					Object value = rs.getObject(col);
					//log.info("break 5 "+meta.getColumnName(col)+" | "+meta.getColumnTypeName(col) + " | "+value);
					if (value != null)
					{
						//log.info("break 6");
						insert.setObject(col,value);
						//log.info("break 7");
					}
					else {
						insert.setNull(col,Types.OTHER);
					}

				}
				insert.addBatch();
				insert.clearParameters();
				//log.info("break 8");
				if(++rowCnt % 1000 == 0) {
					//log.info("break 9");
					int[] success = insert.executeBatch();
					connection.commit();
					log.info(rowCnt + " inserted ");
				}
			}
			//Handle remaining inserts
			int[] success = insert.executeBatch();
			connection.commit();
			log.info("remaining inserted ");




			long end_time = System.currentTimeMillis();
			long time_taken = end_time-start_time;

			log.info("Batch executed in : "+time_taken+" ms | "+(time_taken/60000.0)+" min");

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