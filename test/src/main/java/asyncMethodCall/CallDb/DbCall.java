package asyncMethodCall.CallDb;

import com.github.javafaker.Faker;

import java.sql.*;
import java.util.Date;

public class DbCall {
    static Faker faker = new Faker();
    public static void insertdata(int count, Connection connection ,String sql) throws SQLException {

        //Long start = System.currentTimeMillis();

        for (int i = 0;i < count ; i++) {
            PreparedStatement pstatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstatement.setInt(1,(101+i));
            pstatement.setString(2, faker.name().name());
            pstatement.setString(3, faker.internet().emailAddress());
            pstatement.setTimestamp(4, new Timestamp(new Date().getTime()));

           int a =  pstatement.executeUpdate();
            System.out.println("affected_rows: "+a);
//            pstatement.addBatch();
//            pstatement.executeBatch();
            ResultSet result = pstatement.getGeneratedKeys();
            int id = 0;
            if (result.next()) {
                id = result.getInt(1);
            }
            System.out.println("Last Inserted ID:" + id);
        }

       //Long end = System.currentTimeMillis();

        //System.out.println(count+" records inserted in "+(end-start)+" ms");
    }
}
