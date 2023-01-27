package testEnum;



public class DBFactory2 {


    public static String getQueries(Object type) {
        switch ((DBFactoryEnum.values()[Integer.parseInt(type.toString())-1])) {
            case MSSQL:
                return "MSSQLIMPL";
            case POSTGRES:
                return "POSTGRESIMPL";
            case ORACLE:
                return "ORACLEIMPL";
            case SYBASE:
                return "SYBASEIMPL";
            default:
                return null;
        }
    }
}
