package testEnum;

import java.util.Arrays;

public class testEnum {
    public static void main(String[] args) {

        //System.out.println("" + DBFactoryEnum.values()[0]);

        System.out.println( DBFactory.getQueries("MSSQL"));
      //  System.out.println( DBFactory.getQueries("MSSQL"));

        int a = 3;

       // System.out.println("-> "+ (DBFactoryEnum.values()[a-1]) );

        System.out.println("-> "+  DBFactory2.getQueries(a));

        Object cc =null;

        Integer c = (Integer) cc;

        System.out.println("c-> "+c);

    }
}
