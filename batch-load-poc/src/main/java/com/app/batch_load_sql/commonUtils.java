package com.app.batch_load_sql;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class commonUtils {
    public static void main(String[] args) throws SQLException {
//        System.out.println("ll: "+getRange(2979821,1000));
//        System.out.println("floor: "+(int)Math.ceil(2979821/(double)50));
//        System.out.println("floor: "+(int)Math.ceil(2979821/(double)1000));

        //       System.out.println("getOffsets: "+ getOffsets(2979821,800000));


//        List<List<Object>> a = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2008-11-06 13:40:32.000"),"1-y");
//        List<List<Object>> b = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2008-11-06 13:40:32.000"),"3-m");
//        List<List<Object>> c = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2015-08-22 13:40:32.000"),"15-d");
//        List<List<Object>> d = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2015-08-22 13:40:32.000"),"5-d");
//        List<List<Object>> e = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2015-08-22 13:40:32.000"),"3-d");
//        List<List<Object>> f = getTimeOffsets(Timestamp.valueOf("2008-08-06 10:43:00.000"),Timestamp.valueOf("2015-08-22 13:40:32.000"),"1-d");
//        log.info("year range : "+a);
//        log.info("month range : "+b);
//        log.info("day range : "+c);
//        log.info("day range : "+d);
//        log.info("day range : "+e);
//        log.info("day range : "+f);
//
//        log.info("z: "+Timestamp.valueOf("2008-08-06 10:43:00.000"));
//        log.info("z: "+Timestamp.from(Instant.now()));

        // System.out.println(getOffsetsId(45627l,3216475l,100000));

//        List<List<Object>> j = getTimeOffsets(Timestamp.valueOf("1800-01-01 00:00:00.0"),Timestamp.valueOf("2000-01-01 00:00:00.0"),"100-y");
//        log.info("4- "+(j.size())+" RANGE : "+j);
//        List<List<Object>> k = getTimeOffsets(Timestamp.valueOf("2000-01-01 00:00:00.0"),Timestamp.valueOf("2022-08-22 13:40:32.0"),"2-y");
//        log.info("5- "+(k.size())+" RANGE : "+k);

//        System.out.println(getOffsetsId(74902l,75362l,20));

//        String q = "select PRODUCT_ID,PRODUCT_DESCRIPTION_ID from DATAONE_DEV_NEW.SI_IDIT_ODS.T_PRODUCT";
//
//        Statement timeStatement = DBCP2.getConnection().createStatement();
//        ResultSet rs = timeStatement.executeQuery(q);
//        ResultSetMetaData pKMdata = rs.getMetaData();
//        Map<String, List<Object>> pkListOfMap = new HashMap<>();
//        HashMap<Object, Object> TablePKMap = new HashMap<Object, Object>();
//        TablePKMap.put("SI_IDIT_ODS.T_PRODUCT", "PRODUCT_ID,PRODUCT_DESCRIPTION_ID");
//        List<String> pkList = Arrays.asList(TablePKMap.get("SI_IDIT_ODS.T_PRODUCT").toString().split(","));
//        System.out.println("pkList: " + pkList);
//        List<Object> pks = new ArrayList<>();
////        key.add(value)
////                get(key)!=null
//
//        while (rs.next()) {
//            for (int i = 1; i <= pKMdata.getColumnCount(); i++) {
//
//                if (pkListOfMap.containsKey(pKMdata.getColumnName(i))) {
//                    pkListOfMap.get(pKMdata.getColumnName(i)).add(rs.getObject(pKMdata.getColumnName(i)));
//                } else {
//                    List<Object> tempPK = new ArrayList<>();
//                    tempPK.add(rs.getObject(pKMdata.getColumnName(i)));
//                    pkListOfMap.put(pKMdata.getColumnName(i), tempPK);
//                }
//
//                //System.out.println(pKMdata.getColumnName(i) + ":" + rs.getObject(pKMdata.getColumnName(i)));
//            }
//        }
//
//        System.out.println("pkListOfMap: "+pkListOfMap);
//        System.out.println("1: "+(pkListOfMap.get("PRODUCT_DESCRIPTION_ID").size()));
//        System.out.println("2: "+(pkListOfMap.get("PRODUCT_ID").size()));

        String tableSrc = "DATAONE_DEV_NEW.SI_IDIT_ODS.T_PRODUCT";
        String tablePK = "PRODUCT_ID,PRODUCT_DESCRIPTION_ID";
        List<Object> pkList = Arrays.asList(1,2,3,4);
        if (tablePK.split(",").length>0){
            //Arrays.asList(a.split(",")).stream().collect(Collectors.joining("|"));
            String b = Arrays.asList(tablePK.split(",")).stream().collect(Collectors.joining(",'|',"));
            System.out.println(getPKQueryConcat(tableSrc,b));
        }
        else {
            System.out.println(getPKQueryConcat(tableSrc,tablePK));
        }



    }

    static String getPKQuery(String tableSrc, String tablePK) {
        return "select " + tablePK + " from " + tableSrc;
    }
    static String getPKQueryConcat(String tableSrc, String tablePK) {
        return "select concat(" + tablePK + ") from " + tableSrc;
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

    public static List<List<Integer>> getRange(int count, int parts) {
        int size = Math.round((count - 1) / parts);
        List<List<Integer>> ll = new ArrayList<>();
        for (int i = 0; i <= parts; i++) {
            int inf = i + i * size;
            int sup = inf + size < count ? inf + size : count;
            ll.add(Arrays.asList(inf, sup));
            //result.push([inf, sup]);
            if (inf >= count || sup >= count) break;
        }
        return ll;
    }

    public static List<List<Long>> getOffsetsId(Long min_id, Long max_id, int batch_size) {
        List<Long> rangeList = new ArrayList<>();
        List<List<Long>> offset = new ArrayList<>();
        rangeList.add(min_id - 1l);
        Long current_batch_size = min_id;
        while (current_batch_size < max_id) {
            current_batch_size = current_batch_size + batch_size;
            rangeList.add(current_batch_size);
        }
        rangeList.remove(rangeList.size() - 1);
        rangeList.add(max_id + 1l);
        System.out.println("rangeList: " + rangeList);
        IntStream.range(0, rangeList.size() - 1)
                .forEach(i -> offset.add(Arrays.asList(rangeList.get(i), rangeList.get(i + 1))));

        return offset;
    }

    public static List<Integer> getOffsets(int count, int batch_size) {
        int parts = (int) Math.ceil(count / (double) batch_size);
        List<Integer> offset = new ArrayList<>();
        offset.add(0);
        int current_batch_size = 0;
        for (int i = 0; i < parts - 1; i++) {
            current_batch_size = current_batch_size + batch_size;
            offset.add(current_batch_size);
        }

        return offset;
    }

    public static List<Map<String, Object>> DBUtilFindAll(String query) {
        Connection con = null;
        List<Map<String, Object>> listOfMaps = null;
        try {
            con = DBCP2.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            listOfMaps = queryRunner.query(con, query, new MapListHandler());
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't query the database.", e);
        } finally {
            DbUtils.closeQuietly(con);
        }
        return listOfMaps;
    }

    public static Object oneRetriveJDBC(String query, Connection conn) {

        Object obj = null;
        if (query == null || query.length() < 1)
            throw new NullPointerException("<" + commonUtils.class.getSimpleName() + "> query is null");
        if (conn == null)
            throw new NullPointerException(
                    "<" + commonUtils.class.getSimpleName() + "> Connection is not established");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next())
                obj = rs.getObject(1);

        } catch (SQLException e) {
            try {
                log.error("<" + commonUtils.class.getSimpleName() + "\n query: " + query + "\n" + e.getLocalizedMessage());
                throw new SQLException("<" + commonUtils.class.getSimpleName() + "> " + query + " "
                        + e.getLocalizedMessage());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            log.error("<" + commonUtils.class.getSimpleName() + "\n query: " + query + "\n" + e.getLocalizedMessage());
            throw new NullPointerException("<" + commonUtils.class.getSimpleName() + "> " + query + " "
                    + e.getLocalizedMessage());
        }

        return obj;
    }

    public static List<List<Object>> getTimeOffsetsByChunk(Object min_time, Object max_time, Connection conn) {

        return null;
    }


    public static List<List<Object>> getTimeOffsets(Object min_time, Object max_time, String batch_time_type) {
        Timestamp min_timestamp = (Timestamp) min_time;
        log.debug("min_timestamp : " + min_timestamp);
//        min_timestamp = Timestamp.valueOf(min_timestamp.toLocalDateTime().minusDays(1));
//        log.info("min_timestamp-1 : "+min_timestamp);
        Timestamp max_timestamp = (Timestamp) max_time;
        String[] batch_time_typeARR = batch_time_type.split("-");
        String batch_offset_type = batch_time_typeARR[1];
        int batch_offset_range = Integer.parseInt(batch_time_typeARR[0]);
        List<Object> timeRanges = new ArrayList<>();
        if (batch_offset_type.equalsIgnoreCase("y")) {
            int max_timestamp_year = max_timestamp.toLocalDateTime().getYear();
            int cutoff_max_timestamp_year = max_timestamp_year;
            log.debug("max_timestamp_year: " + max_timestamp_year);
            Timestamp min_timestamp_plus = min_timestamp;
            //timeRanges.add(Timestamp.valueOf("1800-01-01 00:00:00.000"));
            timeRanges.add(min_timestamp);
            //while(min_timestamp_plus.toLocalDateTime().getYear() < cutoff_max_timestamp_year)
            while (min_timestamp_plus.toLocalDateTime().isBefore(max_timestamp.toLocalDateTime())) {
                min_timestamp_plus = Timestamp.valueOf(min_timestamp_plus.toLocalDateTime().plusYears(batch_offset_range));
                timeRanges.add(min_timestamp_plus);
            }
            timeRanges.remove(timeRanges.size() - 1);
            timeRanges.add(max_timestamp);
        } else if (batch_offset_type.equalsIgnoreCase("m")) {

            int max_timestamp_year = max_timestamp.toLocalDateTime().getYear();
            int max_timestamp_month = max_timestamp.toLocalDateTime().getMonthValue();
            int max_timestamp_day = max_timestamp.toLocalDateTime().getDayOfMonth();
            int cutoff_max_timestamp_year = max_timestamp_year;
            int cutoff_max_timestamp_month = max_timestamp_month;
            int cutoff_max_timestamp_day = max_timestamp_day;
            log.debug("max_timestamp_year: " + max_timestamp_year);
            log.debug("cutoff_max_timestamp_month: " + cutoff_max_timestamp_month);
            log.debug("cutoff_max_timestamp_day: " + cutoff_max_timestamp_day);
            log.debug("cutoff_max_timestamp_day: " + max_timestamp.toLocalDateTime());
            Timestamp min_timestamp_plus = min_timestamp;
            //timeRanges.add(Timestamp.valueOf("1800-01-01 00:00:00.000"));
            timeRanges.add(min_timestamp);
            while (min_timestamp_plus.toLocalDateTime().isBefore(max_timestamp.toLocalDateTime())) {
                min_timestamp_plus = Timestamp.valueOf(min_timestamp_plus.toLocalDateTime().plusMonths(batch_offset_range));
                timeRanges.add(min_timestamp_plus);
            }
            timeRanges.remove(timeRanges.size() - 1);
            timeRanges.add(max_timestamp);
        } else if (batch_offset_type.equalsIgnoreCase("d")) {
            int max_timestamp_year = max_timestamp.toLocalDateTime().getYear();
            int cutoff_max_timestamp_year = max_timestamp_year;
            log.debug("max_timestamp_year: " + max_timestamp_year);
            Timestamp min_timestamp_plus = min_timestamp;
            //timeRanges.add(Timestamp.valueOf("1800-01-01 00:00:00.000"));
            timeRanges.add(min_timestamp);
            while (min_timestamp_plus.toLocalDateTime().isBefore(max_timestamp.toLocalDateTime())) {
                min_timestamp_plus = Timestamp.valueOf(min_timestamp_plus.toLocalDateTime().plusDays(batch_offset_range));
                timeRanges.add(min_timestamp_plus);
            }
            timeRanges.remove(timeRanges.size() - 1);
            timeRanges.add(max_timestamp);
        }

        log.debug("timeRanges: " + timeRanges);

        List<List<Object>> timeOffset = new ArrayList<>();
        IntStream.range(0, timeRanges.size() - 1)
                .forEach(i -> timeOffset.add(Arrays.asList(timeRanges.get(i), timeRanges.get(i + 1))));

        return timeOffset;
    }

    @Deprecated
    public static List<List<Object>> getTimeOffsets1(Object min_time, Object max_time, String batch_time_type) {
        Timestamp min_timestamp = (Timestamp) min_time;
        log.info("min_timestamp : " + min_timestamp);
        min_timestamp = Timestamp.valueOf(min_timestamp.toLocalDateTime().minusDays(1));
        log.info("min_timestamp-1 : " + min_timestamp);
        Timestamp max_timestamp = (Timestamp) max_time;
        String[] batch_time_typeARR = batch_time_type.split("-");
        String batch_offset_type = batch_time_typeARR[1];
        int batch_offset_range = Integer.parseInt(batch_time_typeARR[0]);
        List<Object> timeRanges = new ArrayList<>();
        if (batch_offset_type.equalsIgnoreCase("y")) {
            int min_timestamp_year = min_timestamp.toLocalDateTime().getYear();
            int cutoff_min_timestamp_year = min_timestamp_year;
            log.info("min_timestamp_year: " + min_timestamp_year);
            Timestamp max_timestamp_minus = max_timestamp;
            timeRanges.add(max_timestamp_minus);
            while (max_timestamp_minus.toLocalDateTime().getYear() > cutoff_min_timestamp_year) {
                max_timestamp_minus = Timestamp.valueOf(max_timestamp_minus.toLocalDateTime().minusYears(batch_offset_range));
                timeRanges.add(max_timestamp_minus);
            }
            timeRanges.add(Timestamp.valueOf("1800-01-01 00:00:00.000"));
        } else if (batch_offset_type.equalsIgnoreCase("m")) {
            int min_timestamp_year = min_timestamp.toLocalDateTime().getYear();
            int cutoff_min_timestamp_year = min_timestamp_year;
            log.info("min_timestamp_year: " + min_timestamp_year);
            Timestamp max_timestamp_minus = max_timestamp;
            timeRanges.add(max_timestamp_minus);
            while (max_timestamp_minus.toLocalDateTime().getYear() > cutoff_min_timestamp_year) {
                max_timestamp_minus = Timestamp.valueOf(max_timestamp_minus.toLocalDateTime().minusMonths(batch_offset_range));
                timeRanges.add(max_timestamp_minus);
            }
            timeRanges.add(Timestamp.valueOf("1800-01-01 00:00:00.000"));
        }

        log.info("timeRanges: " + timeRanges);

        List<List<Object>> timeOffset = new ArrayList<>();
        timeOffset.add(Arrays.asList(min_timestamp, Timestamp.valueOf("2010-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2010-08-22 13:40:32.000"), Timestamp.valueOf("2012-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2012-08-22 13:40:32.000"), Timestamp.valueOf("2015-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2015-08-22 13:40:32.000"), Timestamp.valueOf("2018-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2018-08-22 13:40:32.000"), Timestamp.valueOf("2019-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2019-08-22 13:40:32.000"), Timestamp.valueOf("2020-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2020-08-22 13:40:32.000"), Timestamp.valueOf("2021-08-22 13:40:32.000")));
        timeOffset.add(Arrays.asList(Timestamp.valueOf("2021-08-22 13:40:32.000"), Timestamp.valueOf("2022-08-22 13:40:32.000")));


        return timeOffset;
    }


}

