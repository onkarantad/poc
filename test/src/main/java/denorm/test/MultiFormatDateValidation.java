package denorm.test;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
 

public class MultiFormatDateValidation {
 
    /**
     * @param args
     */
    public static void main(String[] args) {
 
        System.out.println("Starting Data Validation Check");
        /* Below Test Case is format and validity test.
         * 2012 was a leap year and hence 29 is a valid date.
         * Change year to 2013 and test.
         */
 
       boolean b =  isDateValid("27/06/2016");
       System.out.println("b : "+b );
    }
    /*
     * Core Function for validation check.
     * 
     */
    public static boolean isDateValid(String dateValue)
    {
        boolean returnVal = false;
        /*
         * Set the permissible formats.
         * A better approach here would be to define all formats in a .properties file
         * and load the file during execution.
         */
        String[] permissFormats = new String[]{"MM/dd/yyyy hh:mm:ss.sss","MM/dd/yyyy hh:mm:ss","yyyy-MM-dd hh:mm:ss.sss","yyyy-MM-dd hh:mm","dd/MM/yyyy",
                "dd-MM-yyyy","MM-dd-yyyy","ddMMyyyy"};
        /*
         * Loop through array of formats and validate using JAVA API.
         */
        for (int i = 0; i < permissFormats.length; i++) 
        {
            try
            {
                SimpleDateFormat sdfObj = new SimpleDateFormat(permissFormats[i]);
                sdfObj.setLenient(false);
                sdfObj.parse(dateValue);
                returnVal = true;
                System.out.println("Looks like a valid date for Date Value :"+dateValue+": For Format:"+permissFormats[i]);
                break;
            }
            catch(ParseException e)
            {
//                System.out.println("Parse Exception Occured for Date Value :"+dateValue+":And Format:"+permissFormats[i]);
            }
        }
        return returnVal;
    }
}