package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class CustomFutureBatchLoad {


    private static ExecutorService pool;

    public static List<Future> callFuture(List<List<Object>> timeOffsetsYear, String BatchLoadQuery, String src_count_query, int batch_size) {

        if (pool == null) {
            log.warn("LookupThrdSize -> " + 5);
            pool = Executors.newFixedThreadPool(5);
        }
        Connection connection2 = DBCP2.getConnection();
        List<Object> timeOffsetsYear1 = Arrays.asList(Timestamp.valueOf("1800-01-01 00:00:00"), Timestamp.valueOf("2000-01-01 00:00:00"));
        recrCompute(timeOffsetsYear1, connection2, BatchLoadQuery, src_count_query, batch_size, "100-y");
        List<List<Object>> timeOffsetsYear2 = commonUtils.getTimeOffsets(Timestamp.valueOf("2000-01-01 00:00:00"), Timestamp.from(Instant.now()), "2-y");
        List<Future> futureList = new LinkedList<>();
        for (List<Object> timeOffsetYear : timeOffsetsYear2) {
            Connection connection = DBCP2.getConnection();
            //recrCompute(timeOffsetYear, connection, BatchLoadQuery, src_count_query, batch_size, "100-y");
            Future future = noWaitExec(timeOffsetYear, BatchLoadQuery, src_count_query, batch_size, pool);
            futureList.add(future);
        }

        //isChunkExecuting(CustomFutureBatchLoad.futureList);


//        for (Future future : futureList) {
//            while (!future.isDone()) {
//                try {
//                    System.out.println("thread is waiting ...");
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        return futureList;

    }

    public static Future<Void> noWaitExec(List<Object> timeOffsetYear, String BatchLoadQuery, String src_count_query, int chunk_size, ExecutorService pool) {
        return pool.submit(new Callable<Void>() {
            public Void call() {
                long chunk_start_time_y = System.currentTimeMillis();
                String SUBSrcCountQuery = null;
                Object SUBSrcCount = null;
                try (Connection connection = DBCP2.getConnection();
                     Statement targetStatement = connection.createStatement();) {
                    SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetYear.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetYear.get(1) + "'");
                    SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
                    String SUBBatchLoadQuery = null;
                    log.info("SUBSrcCount: " + SUBSrcCount);

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
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    static AtomicInteger loopCount = new AtomicInteger(0);
    static List<Future> futureList = new LinkedList<>();

    public static void recrCompute(List<Object> timeOffset1, Connection connection, String BatchLoadQuery, String src_count_query, int chunk_size, String batch_time_type) {

        log.info(batch_time_type + " | timeOffset1: " + timeOffset1 + " | recrCompute: " + (loopCount.incrementAndGet()));
        Statement targetStatement = null;
        try {
            targetStatement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<List<Object>> timeOffsets = commonUtils.getTimeOffsets(timeOffset1.get(0), timeOffset1.get(1), batch_time_type);
        for (List<Object> timeOffset : timeOffsets) {
            long chunk_start_time = System.currentTimeMillis();
            String SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffset.get(0) + "'").replace("'?business_end_date'", "'" + timeOffset.get(1) + "'");
            Object SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
            if ((int) SUBSrcCount > chunk_size) {
                if (batch_time_type.equalsIgnoreCase("1-y"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "3-m");
                else if (batch_time_type.equalsIgnoreCase("3-m"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "1-m");
                else if (batch_time_type.equalsIgnoreCase("1-m"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "7-d");
                else if (batch_time_type.equalsIgnoreCase("7-d"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "1-d");
                else if (batch_time_type.equalsIgnoreCase("100-y"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "20-y");
                else if (batch_time_type.equalsIgnoreCase("20-y"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "5-y");
                else if (batch_time_type.equalsIgnoreCase("5-y"))
                    recrCompute(timeOffset, connection, BatchLoadQuery, src_count_query, chunk_size, "1-y");
            } else {
                runBatchLoadQuery(chunk_start_time, BatchLoadQuery, targetStatement, timeOffset, SUBSrcCount);
                //String out = runBatchLoadQuery(chunk_start_time, BatchLoadQuery, targetStatement, timeOffset, SUBSrcCount);
                //log.info("-> " + out);
            }

        }
    }

//    public void compute(){
//        String SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetYear.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetYear.get(1) + "'");
//        SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
//        String SUBBatchLoadQuery = null;
//        log.info("SUBSrcCount: "+SUBSrcCount);
//
//        if ((int) SUBSrcCount > chunk_size) {
//            List<List<Object>> timeOffsetsMonth = commonUtils.getTimeOffsets(timeOffsetYear.get(0), timeOffsetYear.get(1), "3-m");
//            log.info("timeOffsetsMonth: " + timeOffsetsMonth);
//            for (List<Object> timeOffsetMonth : timeOffsetsMonth) {
//                long chunk_start_time_m = System.currentTimeMillis();
//                SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth.get(1) + "'");
//                SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
//                if ((int) SUBSrcCount > chunk_size) {
//                    List<List<Object>> timeOffsetsMonth1 = commonUtils.getTimeOffsets(timeOffsetMonth.get(0), timeOffsetMonth.get(1), "1-m");
//                    log.info("timeOffsetsMonth1: " + timeOffsetsMonth1);
//                    for (List<Object> timeOffsetMonth1 : timeOffsetsMonth1) {
//                        long chunk_start_time_m1 = System.currentTimeMillis();
//                        SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetMonth1.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetMonth1.get(1) + "'");
//                        SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
//                        if ((int) SUBSrcCount > chunk_size) {
//                            List<List<Object>> timeOffsetsWeek = commonUtils.getTimeOffsets(timeOffsetMonth1.get(0), timeOffsetMonth1.get(1), "7-d");
//                            log.info("timeOffsetsWeek: " + timeOffsetsWeek);
//                            for (List<Object> timeOffsetWeek : timeOffsetsWeek) {
//                                long chunk_start_time_w = System.currentTimeMillis();
//                                SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetWeek.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetWeek.get(1) + "'");
//                                SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
//                                if ((int) SUBSrcCount > chunk_size) {
//                                    List<List<Object>> timeOffsetsDay = commonUtils.getTimeOffsets(timeOffsetWeek.get(0), timeOffsetWeek.get(1), "1-d");
//                                    log.info("timeOffsetsDay: " + timeOffsetsDay);
//                                    for (List<Object> timeOffsetDay : timeOffsetsDay) {
//                                        long chunk_start_time_d = System.currentTimeMillis();
//                                        SUBSrcCountQuery = src_count_query.replace("'?business_start_date'", "'" + timeOffsetDay.get(0) + "'").replace("'?business_end_date'", "'" + timeOffsetDay.get(1) + "'");
//                                        SUBSrcCount = commonUtils.oneRetriveJDBC(SUBSrcCountQuery, connection);
//
//                                        String out = runBatchLoadQuery(chunk_start_time_d, BatchLoadQuery, targetStatement, timeOffsetDay, SUBSrcCount);
//                                        log.info("d " + out);
//                                    }
//                                } else {
//                                    String out = runBatchLoadQuery(chunk_start_time_w, BatchLoadQuery, targetStatement, timeOffsetWeek, SUBSrcCount);
//                                    log.info("w " + out);
//                                }
//                            }
//                        } else {
//                            String out = runBatchLoadQuery(chunk_start_time_m1, BatchLoadQuery, targetStatement, timeOffsetMonth1, SUBSrcCount);
//                            log.info("m1 " + out);
//                        }
//                    }
//                } else {
//
//                    String out = runBatchLoadQuery(chunk_start_time_m, BatchLoadQuery, targetStatement, timeOffsetMonth, SUBSrcCount);
//                    log.info("m " + out);
//                }
//            }
//        } else {
//            String out = runBatchLoadQuery(chunk_start_time_y, BatchLoadQuery, targetStatement, timeOffsetYear, SUBSrcCount);
//            log.info("y " + out);
//        }
//    }

    public static void isChunkExecuting(List<Future> callFuture) {
        log.warn("Future_Executing...");
        callFuture.stream().forEach(i -> {
            while (!i.isDone()) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                log.info("i-> " + i.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

    }


    public static void closeExecutor() {
        pool.shutdown();
    }

    static AtomicInteger insertedCount = new AtomicInteger(0);
    public static String runBatchLoadQuery(long chunk_start_time, String BatchLoadQuery, Statement targetStatement, List<Object> timeOffset, Object SUBSrcCount) {

        if((int)SUBSrcCount > 0){
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
        }
        //connection.commit();
        long chunk_end_time = System.currentTimeMillis();
        long chunk_time_taken = chunk_end_time - chunk_start_time;
        //log.info("d chunk inserted in : " + chunk_time_taken + " ms | " + (chunk_time_taken / 60000.0) + " min - timeOffsetDay: " + timeOffset + " | chunk_count: " + SUBSrcCount);
        return "chunk inserted in : " + chunk_time_taken + " ms | " + (chunk_time_taken / 60000.0)
                + " min - timeOffset: " + timeOffset + " | chunk_count: " + SUBSrcCount + " | insertedCount: "
                + insertedCount.get();
    }

    public static Future<String> runBatchLoadQueryFUT(long chunk_start_time, String BatchLoadQuery, Statement targetStatement, List<Object> timeOffset, Object SUBSrcCount) {
        return pool.submit(new Callable<String>() {
            public String call() {
                insertedCount.addAndGet((int) SUBSrcCount);
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
                return "chunk inserted in : " + chunk_time_taken + " ms | " + (chunk_time_taken / 60000.0)
                        + " min - timeOffset: " + timeOffset + " | chunk_count: " + SUBSrcCount + " | insertedCount: "
                        + insertedCount.get();
            }
        });
    }



}