package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Log4j2
public class BATCH_LOAD_LIMIT {
	
	public static void main(String[] args) {


//		String src_count_query_path = "sqls/litigation/LITIGATION_SRC_COUNT_QUERY.sql";
//		String batch_load_query_path = "sqls/litigation/LITIGATION_BATCH_LOAD_TEST_LIMIT.sql";
		String src_count_query_path = "sqls/accounting_entry_line_details/AELD_SRC_COUNT.sql";
		String batch_load_query_path = "sqls/accounting_entry_line_details/AELD_BATCH_LOAD_TEST_LIMIT2.sql";


		String BatchLoadQuery = commonUtils.readFile(batch_load_query_path);
		StringBuilder SBBatchLoadQuery = null;
		String SUBBatchLoadQuery = null;

		try (Connection connection = DBCP2.getConnection();){
			connection.setAutoCommit(false);

			log.info("Batch load started");
			long start_time = System.currentTimeMillis();
			Statement targetStatement = connection.createStatement();
			Object src_count = commonUtils.oneRetriveJDBC( commonUtils.readFile(src_count_query_path),connection);
			log.info("src_count: "+src_count);
			//List<List<Integer>> ranges = commonUtils.getRange((Integer) src_count,1000);
			//log.info("ranges: "+ranges);
//			int batch = 4;
//			int work_load = (int)Math.ceil((int)src_count/(double)batch);
//			log.info("work_load: "+work_load);
//			int offset = 0;
			log.info("batch insertion started");
			int batch_size=800000;

			List<Integer> offsets = commonUtils.getOffsets((int)src_count,batch_size);
			log.info("offsets: "+offsets);


			for (Integer offset:offsets){
				log.debug("offset: "+offset+" | batchSize: "+batch_size);
				SUBBatchLoadQuery = BatchLoadQuery
						.replace("'?offset'",Integer.toString(offset))
						.replace("'?batchSize'", Integer.toString(batch_size));
				targetStatement.addBatch(SUBBatchLoadQuery);
				int[] success = targetStatement.executeBatch();
				connection.commit();
				log.info("inserted : "+(batch_size+offset));
			}


//			for (int i=0;i<batch;i++){
////				SBBatchLoadQuery = new StringBuilder();
////				SBBatchLoadQuery.append(BatchLoadQuery);
////				SBBatchLoadQuery.append(" OFFSET "+offset+" ROWS FETCH NEXT "+work_load+" ROWS ONLY ");
////				targetStatement.addBatch(SBBatchLoadQuery.toString());
//				BatchLoadQuery = BatchLoadQuery.replace("'?offset'",Integer.toString(offset)) .replace("'?batchSize'", Integer.toString(work_load));
//				targetStatement.addBatch(BatchLoadQuery);
//				int[] success = targetStatement.executeBatch();
//				connection.commit();
//				offset = offset+work_load;
//				log.info("inserted : "+offset);
//			}

			long end_time = System.currentTimeMillis();
			long time_taken = end_time-start_time;

			log.info("Batch executed in : "+time_taken+" ms | "+(time_taken/60000.0)+" min");

		} catch (SQLException e) {
			log.error("SUBBatchLoadQuery : "+SUBBatchLoadQuery );
			throw new RuntimeException(e);
		}


	}


}