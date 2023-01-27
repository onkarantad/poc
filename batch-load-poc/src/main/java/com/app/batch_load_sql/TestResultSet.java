package com.app.batch_load_sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TestResultSet {
    public static void main(String[] args) throws SQLException {

        List<Object> getListOfPK = getListOfPKGen("");

        System.out.println("getListOfPKSIZE: " + getListOfPK.size());
        System.out.println("getListOfPK: " + getListOfPK);


    }


    public static List<Object> getListOfPKGen(String query) throws SQLException {
        query = "select PRODUCT_ID,PRODUCT_DESCRIPTION_ID from dbo.T_PRODUCT";

        Connection connection = DBCP2.getConnection();
        Statement timeStatement = connection.createStatement();
        ResultSet rs = timeStatement.executeQuery(query);

        ResultSetMetaData metadata = rs.getMetaData();
        int numberOfColumns = metadata.getColumnCount();

        rs.setFetchSize(1000);
        List<Object> pkList = new ArrayList<>();
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            String prifix = " and ";
            if(numberOfColumns>1)
                sb.append("(");
            for(int i=1 ; i<= numberOfColumns;i++){
//                    System.out.println(rs.getObject(i));
                sb.append(metadata.getColumnName(i)).append(" = ");
                sb.append(rs.getObject(i));
                sb.append(prifix);
            }
            sb.replace(sb.lastIndexOf("and"),sb.length(),"");
            if(numberOfColumns>1)
                sb.append(")");
            pkList.add(sb.toString());
        }
        return pkList;
    }


    public static List<Object> getListOfPKOracle(String query) throws SQLException {
        query = "select PRODUCT_ID,PRODUCT_DESCRIPTION_ID from dbo.T_PRODUCT";

        Connection connection = DBCP2.getConnection();
        Statement timeStatement = connection.createStatement();
        ResultSet rs = timeStatement.executeQuery(query);

        ResultSetMetaData metadata = rs.getMetaData();
        int numberOfColumns = metadata.getColumnCount();

        rs.setFetchSize(1000);
        List<Object> pkList = new ArrayList<>();
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            String prifix = "";
            if(numberOfColumns>1)
                sb.append("(");
            for(int i=1 ; i<= numberOfColumns;i++){

//                    System.out.println(rs.getObject(i));
                sb.append(prifix).append(rs.getObject(i));
                prifix= ",";

            }
            if(numberOfColumns>1)
                sb.append(")");
            pkList.add(sb.toString());
        }
        return pkList;
    }



    public static List<Object> getListOfPK(String query) throws SQLException {
        query = "select concat('''',PRODUCT_ID,'|',PRODUCT_DESCRIPTION_ID,'''') from dbo.T_PRODUCT";
        Connection connection = DBCP2.getConnection();
        Statement timeStatement = connection.createStatement();
        ResultSet rs = timeStatement.executeQuery(query);
        rs.setFetchSize(1000);
        List<Object> pkList = new ArrayList<>();
        while (rs.next()) {
            pkList.add(rs.getObject(1));
        }
        return pkList;
    }

    public static List<List<Object>> getListListOfPK(String query, int chunk_size) throws SQLException {
        Connection connection = DBCP2.getConnection();
        Statement timeStatement = connection.createStatement();
        ResultSet rs = timeStatement.executeQuery(query);
        rs.setFetchSize(1000);
        List<List<Object>> pkList = new ArrayList<>();
        List<Object> subPkList = new ArrayList<>();
        AtomicInteger cnt = new AtomicInteger(0);
        while (rs.next()) {
            cnt.incrementAndGet();
            if (cnt.get() <= chunk_size) {
                subPkList.add(rs.getObject(1));
                if (subPkList.size() == chunk_size) {
                    pkList.add(subPkList);
                    subPkList.clear();
                    cnt.set(0);
                }
            }


        }
        return pkList;
    }
}
