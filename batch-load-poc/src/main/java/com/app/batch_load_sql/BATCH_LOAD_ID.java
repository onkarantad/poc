package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class BATCH_LOAD_ID {

    public static void main(String[] args) {


//		String src_count_query_path = "sqls/litigation/LITIGATION_SRC_COUNT_QUERY.sql";
//		String batch_load_query_path = "sqls/litigation/LITIGATION_BATCH_LOAD_TEST_LIMIT.sql";
        String src_count_query_path = "sqls/accounting_entry_line_details/AELD_SRC_COUNT.sql";
        String batch_load_query_path = "sqls/accounting_entry_line_details/AELD_BATCH_LOAD_TEST_ID.sql";
        String id_query_path = "sqls/accounting_entry_line_details/AELD_GET_ID.sql";
        String trgt_count_query_path = "sqls/accounting_entry_line_details/AELD_TRGT_COUNT.sql";

        String BatchLoadQuery = commonUtils.readFile(batch_load_query_path);
        StringBuilder SBBatchLoadQuery = null;
        String SUBBatchLoadQuery = null;

        String src_count_query = commonUtils.readFile(src_count_query_path);
        String SUBSrcCountQuery = null;
        Object SUBSrcCount = null;


        try (Connection connection = DBCP2.getConnection();) {
            connection.setAutoCommit(true);

            Statement timeStatement = connection.createStatement();
            ResultSet rs = timeStatement.executeQuery(commonUtils.readFile(id_query_path));
            Object min_id = null;
            Object max_id = null;
            Timestamp min_timestamp = null;
            Timestamp max_timestamp = null;
//            while (rs.next()) {
//                min_id = rs.getObject(1);
//                max_id = rs.getObject(2);
//                //min_timestamp = rs.getTimestamp(1);
//                //max_timestamp = rs.getTimestamp(2);
//            }
            List<Map<String, Object>> result = commonUtils.DBUtilFindAll(commonUtils.readFile(id_query_path));
            log.info("result: " + result);
            min_id = result.get(0).get("min_id");
            max_id = result.get(0).get("max_id");
            log.info("min_id: " + min_id + " | " + "max_id: " + max_id);
            //log.info("min_timestamp: "+min_timestamp+" | "+"max_timestamp: "+max_timestamp);

            log.info("Batch load started");
            long start_time = System.currentTimeMillis();
            Statement targetStatement = connection.createStatement();
            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'1800-01-01 00:00:00.0'").replace("'?business_end_date'", "getDate()");
            Object src_count = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
            log.info("src_count: " + src_count);
            //List<List<Integer>> ranges = commonUtils.getRange((Integer) src_count,1000);
            //log.info("ranges: "+ranges);
//			int batch = 4;
//			int work_load = (int)Math.ceil((int)src_count/(double)batch);
//			log.info("work_load: "+work_load);t
//			int offset = 0;
            log.info("batch insertion started");
            int chunk_size = 200000;
            List<List<Long>> offsets=commonUtils.getOffsetsId((Long)min_id,(Long) max_id,chunk_size);
            log.info("offsets: " + offsets);
            for(List<Long> offset : offsets)
            {
                log.info("started: " + offset);
                SUBBatchLoadQuery = BatchLoadQuery;
                SUBBatchLoadQuery = BatchLoadQuery
                        .replace("'?business_start_date'", offset.get(0)+"")
                        .replace("'?business_end_date'", offset.get(1)+"");
                targetStatement.addBatch(SUBBatchLoadQuery);
                int[] success = targetStatement.executeBatch();
                connection.commit();
                targetStatement.clearBatch();
                log.info("inserted: " + offset);
            }

//            List<List<Object>> timeOffsetsYear = commonUtils.getTimeOffsets(min_id, max_id, "1-y");
//            log.info("timeOffsetsYear: " + timeOffsetsYear);


            //   SINGLE THREAD WORKING
/*
            for (List<Object> timeOffsetYear : timeOffsetsYear) {
                long chunk_start_time_y = System.currentTimeMillis();
                    connection.setAutoCommit(true);
                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetYear.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetYear.get(1) + "'");
                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                    if ((int) SUBSrcCount > chunk_size) {
                        List<List<Object>> timeOffsetsMonth = commonUtils.getTimeOffsets(timeOffsetYear.get(0), timeOffsetYear.get(1), "3-m");
                        log.info("timeOffsetsMonth: " + timeOffsetsMonth);
                        for (List<Object> timeOffsetMonth : timeOffsetsMonth) {
                            long chunk_start_time_m = System.currentTimeMillis();
                            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth.get(1) + "'");
                            SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                            if ((int) SUBSrcCount > chunk_size) {
                                List<List<Object>> timeOffsetsMonth1 = commonUtils.getTimeOffsets(timeOffsetMonth.get(0), timeOffsetMonth.get(1), "1-m");
                                log.info("timeOffsetsMonth1: " + timeOffsetsMonth1);
                                for (List<Object> timeOffsetMonth1 : timeOffsetsMonth1) {
                                    long chunk_start_time_m1 = System.currentTimeMillis();
                                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth1.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth1.get(1) + "'");
                                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                                    if ((int) SUBSrcCount > chunk_size) {
                                        List<List<Object>> timeOffsetsWeek = commonUtils.getTimeOffsets(timeOffsetMonth1.get(0), timeOffsetMonth1.get(1), "7-d");
                                        log.info("timeOffsetsWeek: " + timeOffsetsWeek);
                                        for (List<Object> timeOffsetWeek : timeOffsetsWeek) {
                                            long chunk_start_time_w = System.currentTimeMillis();
                                            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetWeek.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetWeek.get(1) + "'");
                                            SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                                            if ((int) SUBSrcCount > chunk_size) {
                                                List<List<Object>> timeOffsetsDay = commonUtils.getTimeOffsets(timeOffsetWeek.get(0), timeOffsetWeek.get(1), "1-d");
                                                log.info("timeOffsetsDay: " + timeOffsetsDay);
                                                for (List<Object> timeOffsetDay : timeOffsetsDay) {
                                                    long chunk_start_time_d = System.currentTimeMillis();
                                                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetDay.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetDay.get(1) + "'");
                                                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);

                                                    String out = runBatchLoadQuery(chunk_start_time_d, BatchLoadQuery, targetStatement, timeOffsetDay, SUBSrcCount);
                                                    log.info("d " + out);
                                                }
                                            } else {
                                                String out = runBatchLoadQuery(chunk_start_time_w, BatchLoadQuery, targetStatement, timeOffsetWeek, SUBSrcCount);
                                                log.info("w " + out);
                                            }
                                        }
                                    } else {
                                        String out = runBatchLoadQuery(chunk_start_time_m1, BatchLoadQuery, targetStatement, timeOffsetMonth1, SUBSrcCount);
                                        log.info("m1 " + out);
                                    }
                                }
                            } else {

                                String out = runBatchLoadQuery(chunk_start_time_m, BatchLoadQuery, targetStatement, timeOffsetMonth, SUBSrcCount);
                                log.info("m " + out);
                            }
                        }
                    } else {
                        String out = runBatchLoadQuery(chunk_start_time_y, BatchLoadQuery, targetStatement, timeOffsetYear, SUBSrcCount);
                        log.info("y " + out);
                    }
            }
 */

            long end_time = System.currentTimeMillis();
            long time_taken = end_time - start_time;

            log.info("Batch executed in : " + time_taken + " ms | " + (time_taken / 60000.0) + " min");
            Object trgt_count = commonUtils.oneRetriveJDBC(commonUtils.readFile(trgt_count_query_path), connection);
            log.info("trgt_count: " + trgt_count);

        } catch (SQLException e) {
            log.error("SUBBatchLoadQuery : " + SUBBatchLoadQuery);
            throw new RuntimeException(e);
        }
    }



    public static Result compute(List<Object> timeOffsetYear, String BatchLoadQuery, String src_count_query, int batch_size) {
        long chunk_start_time_y = System.currentTimeMillis();

        String SUBSrcCountQuery = null;
        Object SUBSrcCount = null;

        try (Connection connection = DBCP2.getConnection();
             Statement targetStatement = connection.createStatement();) {
            connection.setAutoCommit(true);
            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetYear.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetYear.get(1) + "'");
            SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
            String SUBBatchLoadQuery = null;

            //SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetYear.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetYear.get(1) + "'");
            //SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);

            if ((int) SUBSrcCount > batch_size) {
                List<List<Object>> timeOffsetsMonth = commonUtils.getTimeOffsets(timeOffsetYear.get(0), timeOffsetYear.get(1), "3-m");
                log.info("timeOffsetsMonth: " + timeOffsetsMonth);
                for (List<Object> timeOffsetMonth : timeOffsetsMonth) {
                    long chunk_start_time_m = System.currentTimeMillis();
                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth.get(1) + "'");
                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                    if ((int) SUBSrcCount > batch_size) {
                        List<List<Object>> timeOffsetsMonth1 = commonUtils.getTimeOffsets(timeOffsetMonth.get(0), timeOffsetMonth.get(1), "1-m");
                        log.info("timeOffsetsMonth1: " + timeOffsetsMonth1);
                        for (List<Object> timeOffsetMonth1 : timeOffsetsMonth1) {
                            long chunk_start_time_m1 = System.currentTimeMillis();
                            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth1.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth1.get(1) + "'");
                            SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                            if ((int) SUBSrcCount > batch_size) {
                                List<List<Object>> timeOffsetsWeek = commonUtils.getTimeOffsets(timeOffsetMonth1.get(0), timeOffsetMonth1.get(1), "7-d");
                                log.info("timeOffsetsWeek: " + timeOffsetsWeek);
                                for (List<Object> timeOffsetWeek : timeOffsetsWeek) {
                                    long chunk_start_time_w = System.currentTimeMillis();
                                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetWeek.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetWeek.get(1) + "'");
                                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                                    if ((int) SUBSrcCount > batch_size) {
                                        List<List<Object>> timeOffsetsDay = commonUtils.getTimeOffsets(timeOffsetWeek.get(0), timeOffsetWeek.get(1), "1-d");
                                        log.info("timeOffsetsDay: " + timeOffsetsDay);
                                        for (List<Object> timeOffsetDay : timeOffsetsDay) {
                                            long chunk_start_time_d = System.currentTimeMillis();
                                            SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetDay.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetDay.get(1) + "'");
                                            SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);

                                            String out = runBatchLoadQuery(chunk_start_time_d, BatchLoadQuery, targetStatement, timeOffsetDay, SUBSrcCount);
                                            log.info("d " + out);
                                        }
                                    } else {
                                        String out = runBatchLoadQuery(chunk_start_time_w, BatchLoadQuery, targetStatement, timeOffsetWeek, SUBSrcCount);
                                        log.info("w " + out);
                                    }
                                }
                            } else {
                                String out = runBatchLoadQuery(chunk_start_time_m1, BatchLoadQuery, targetStatement, timeOffsetMonth1, SUBSrcCount);
                                log.info("m1 " + out);
                            }
                        }
                    } else {

                        String out = runBatchLoadQuery(chunk_start_time_m, BatchLoadQuery, targetStatement, timeOffsetMonth, SUBSrcCount);
                        log.info("m " + out);
                    }
                }
            } else {
                String out = runBatchLoadQuery(chunk_start_time_y, BatchLoadQuery, targetStatement, timeOffsetYear, SUBSrcCount);
                log.info("y " + out);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    static AtomicInteger insertedCount = new AtomicInteger(0);
    public static String runBatchLoadQuery(long chunk_start_time, String BatchLoadQuery, Statement targetStatement, List<Object> timeOffset, Object SUBSrcCount) {
        //insertedCount = insertedCount+(int)SUBSrcCount;
        insertedCount.addAndGet((Integer) SUBSrcCount);
        String SUBBatchLoadQuery = BatchLoadQuery
                .replace("'?business_start_date'", "'" + timeOffset.get(0) + "'")
                .replace("'?business_end_date'", "'" + timeOffset.get(1) + "'");
        try {
            targetStatement.addBatch(SUBBatchLoadQuery);
            int[] success = targetStatement.executeBatch();
            //targetStatement.executeUpdate(SUBBatchLoadQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //connection.commit();
        long chunk_end_time = System.currentTimeMillis();
        long chunk_time_taken = chunk_end_time - chunk_start_time;
        //log.info("d chunk inserted in : " + chunk_time_taken + " ms | " + (chunk_time_taken / 60000.0) + " min - timeOffsetDay: " + timeOffset + " | chunk_count: " + SUBSrcCount);
        return "chunk inserted in : " + chunk_time_taken + " ms | " + (chunk_time_taken / 60000.0) + " min - timeOffset: " + timeOffset + " | chunk_count: " + SUBSrcCount + " | insertedCount: "+insertedCount.get();
    }


}