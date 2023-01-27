package com.app.batch_load_sql;

import com.microsoft.sqlserver.jdbc.SQLServerBulkCopy;
import customExceptions.ConfigMappingException;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Log4j2
public class ODS_LOAD_TIME {
    public static void main(String[] args) {

    }

    public void ScrToTargetWithDateLoadSIT(Object table1, Object table2, Connection from, Connection to, String Query,
                                           List<Map<Object, Object>> DBConfigMapList, int INCR_LOAD_IND) {
        try {
            if (table1 == null)
                throw new ConfigMappingException("<" + ODS_LOAD_TIME.class.getSimpleName() + "> table1 is null");
            if (table2 == null)
                throw new ConfigMappingException("<" + ODS_LOAD_TIME.class.getSimpleName() + "> table2 is null");

            if (from == null || from.isClosed())
                throw new ConfigMappingException("<" + ODS_LOAD_TIME.class.getSimpleName()
                        + "> Source database connection is not established");

            if (to == null || to.isClosed())
                throw new ConfigMappingException("<" + ODS_LOAD_TIME.class.getSimpleName()
                        + "> target database connection is not established");
            if (Query == null)
                throw new ConfigMappingException("<" + ODS_LOAD_TIME.class.getSimpleName() + "> Query is null");
            if (DBConfigMapList == null || DBConfigMapList.size() == 0)
                throw new ConfigMappingException(
                        "<" + ODS_LOAD_TIME.class.getSimpleName() + "> DBConfigMapList is null");
            try (PreparedStatement s1 = from.prepareStatement(Query); ResultSet rs = s1.executeQuery()) {
                s1.setFetchSize(5000);
                long startTime = System.currentTimeMillis();
                ResultSetMetaData meta = rs.getMetaData();
                int batchTotal = 0;
                int count = 0;
                to.setAutoCommit(false);
                List<String> columns = new LinkedList<String>();
                for (int i = 1; i <= meta.getColumnCount(); i++)
                    columns.add(meta.getColumnName(i));

                AtomicReference<String> merge_condition = new AtomicReference<>();

                DBConfigMapList.stream().filter(f -> f.get("Target_Table_Name").equals(table2))
                        .map(p -> Arrays.asList(p.get("Target_Table_PK").toString().split(","))).distinct()
                        .forEach(pk -> {
                            merge_condition.set(pk.stream().map(c -> "a." + c + " = incoming." + c)
                                    .collect(Collectors.joining(" AND ")));
                        });
                String TargetQuery = null;

                if (INCR_LOAD_IND == 0) {
                    TargetQuery = "INSERT INTO " + table2 + " (" + columns.stream().collect(Collectors.joining(", "))
                            + ") VALUES (" + columns.stream().map(c -> "?").collect(Collectors.joining(", ")) + ")";
                } else {
                    TargetQuery = "Merge INTO " + table2 + " a USING ( Select "
                            + columns.stream().map(c -> "? " + c).collect(Collectors.joining(", "))
                            // +" FROM "+ table2 +
                            + ") incoming ON (" + merge_condition + ") WHEN MATCHED THEN UPDATE SET "
                            + columns.stream().map(c -> "a." + c + " = incoming." + c).collect(Collectors.joining(", "))// .replace("a.AUD_IU_FLAG
                            // =
                            // incoming.AUD_IU_FLAG",
                            // "a.AUD_IU_FLAG
                            // =
                            // 1")
                            + " WHEN NOT MATCHED THEN INSERT (" + columns.stream().collect(Collectors.joining(", "))
                            + ") VALUES ("
                            + columns.stream().map(c -> "incoming." + c).collect(Collectors.joining(", ")) + ");";
                }

                log.debug("TargetQuery : " + TargetQuery);


                try (PreparedStatement s2 = to.prepareStatement(TargetQuery)) {

                    while (rs.next()) {
                        for (int i = 1; i <= meta.getColumnCount(); i++) {
                            if (meta.getColumnTypeName(i).equals("TIMESTAMP")) {
                                s2.setTimestamp(i, rs.getTimestamp(i));
                            } else {
                                s2.setObject(i, rs.getObject(i));
                            }

                        }

                        s2.addBatch();
                        if (batchTotal++ == 5000) {
                            int[] result = s2.executeBatch();
                            to.commit();
                            s2.clearBatch();
                            batchTotal = 0;
                            log.debug("loaded a batch of 5000 :" + (count++));
                        }

                    }
                    if (batchTotal > 0) {
                        int[] result = s2.executeBatch();
                        to.commit();
                    }
                    //from.commit();
                    //to.commit();
                    log.debug("record end :" + convertTime(System.currentTimeMillis()));
                    long timeTaken = System.currentTimeMillis() - startTime;
                    log.debug("time taken for entire load in milliseconds: " + timeTaken);

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public static String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
