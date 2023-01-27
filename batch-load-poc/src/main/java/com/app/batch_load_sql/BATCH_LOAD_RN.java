package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class BATCH_LOAD_RN {
	
	public static void main(String[] args) {


//		String src_count_query_path = "sqls/litigation/LITIGATION_SRC_COUNT_QUERY.sql";
//		String batch_load_query_path = "sqls/litigation/LITIGATION_BATCH_LOAD_TEST_RN.sql";
		String src_count_query_path = "sqls/accounting_entry_line_details/AELD_SRC_COUNT.sql";
		String batch_load_query_path = "sqls/accounting_entry_line_details/AELD_BATCH_LOAD_TEST_RN.sql";

		String BatchLoadQuery = commonUtils.readFile(batch_load_query_path);
		StringBuilder SBBatchLoadQuery = null;

		try (Connection connection = DBCP2.getConnection();){
			connection.setAutoCommit(false);

			System.out.println("Batch load started");
			long start_time = System.currentTimeMillis();
			Statement targetStatement = connection.createStatement();
			Object src_count = commonUtils.oneRetriveJDBC( commonUtils.readFile(src_count_query_path),connection);
			log.info("src_count: "+src_count);
			List<List<Integer>> ranges = commonUtils.getRange((Integer) src_count,1000);
			log.info("ranges: "+ranges);
			for (List<Integer> range :ranges){
				SBBatchLoadQuery = new StringBuilder();
				log.info("batch insertion started");
				SBBatchLoadQuery.append(BatchLoadQuery);
				SBBatchLoadQuery.append(" where rowNumber between "+range.get(0)+" and "+range.get(1));
				targetStatement.addBatch(SBBatchLoadQuery.toString());
				int[] success = targetStatement.executeBatch();
				connection.commit();
				log.info("inserted : "+range);
			}

			long end_time = System.currentTimeMillis();
			long time_taken = end_time-start_time;

			System.out.println("Batch executed in : "+time_taken+" ms | "+(time_taken/60000.0)+" min");

		} catch (SQLException e) {
			log.error("SBBatchLoadQuery : "+SBBatchLoadQuery );
			throw new RuntimeException(e);
		}


	}


}