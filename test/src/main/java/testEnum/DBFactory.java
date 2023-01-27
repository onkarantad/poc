package testEnum;



public class DBFactory {


    public static String getQueries(String type) {
        switch (DBFactoryEnum.valueOf(type.toUpperCase())) {
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
