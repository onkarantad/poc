package StringCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegixTest {
    public static void main(String[] args) {

//        String string = "var1[value1], var2[value2], var3[value3]";
//        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");

//        String string = "concat(AC_AGENT_ACCOUNT_GROUP.ID,'|','101','|','IDIT',AC_AGENT.NAME)";
//        Pattern pattern = Pattern.compile("([a-zA-z]+\\w)\\.(\\w+)");

        String string = "(\n" +
                "select\n" +
                "c1.contact_id,c1.BANK_ID,c1.BANK_NAME, c1.BRANCH_NAME,\n" +
                "c1.ACCOUNT_NR, c1.IBAN_REFERENCE, c1.BRANCH_ID,\n" +
                "c1.DISCONTINUE_DATE, c1.BIC_CODE, c1.BANK_ACCOUNT_CURRENCY_ID,\n" +
                "c1.TYPE, c1.UPDATE_DATE\n" +
                "from\n" +
                "(select\n" +
                "ID,CONTACT_ID, BANK_ID,BANK_NAME, BRANCH_NAME, ACCOUNT_NR,\n" +
                "IBAN_REFERENCE, BRANCH_ID, DISCONTINUE_DATE, BIC_CODE,\n" +
                "BANK_ACCOUNT_CURRENCY_ID, type,update_date\n" +
                "from SI_IDIT_ODS.cn_contact_BANK_ACCOUNT) c1\n" +
                "join\n" +
                "(select max(id) id,contact_id\n" +
                "from SI_IDIT_ODS.cn_contact_BANK_ACCOUNT\n" +
                "where DISCONTINUE_DATE is NULl and type = 1\n" +
                "group by contact_id ) c2\n" +
                "on\n" +
                "c1.id=c2.id\n" +
                ")";
        Pattern pattern = Pattern.compile("([a-zA-z]+\\w)\\.(\\w+)");

        Matcher matcher = pattern.matcher(string);

        List<String> listMatches = new ArrayList<String>();

        while (matcher.find()) {
            listMatches.add(matcher.group());
        }

        for (String s : listMatches) {
            System.out.println(s);
        }
    }
}
