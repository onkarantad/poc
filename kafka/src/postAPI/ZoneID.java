package postAPI;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class ZoneID {
    public static void main(String[] args) {
        ZoneId zid;

        String Zone_Id = "Asia/Jerusalem";
        if (Zone_Id.isEmpty()) {
            zid = ZoneId.systemDefault();
        } else {
            zid = ZoneId.of(Zone_Id);
        }
        LocalDate nowDate = ZonedDateTime.now(zid).toLocalDate();

        // CURRENT_BATCH_START_DATE from context
       // Date previousDate = (Date) PRIOR_BATCH_START_DATE;
        //LocalDate priorDate = previousDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       // long diff = nowDate.compareTo(priorDate);

        System.out.println("zone: "+ZoneId.systemDefault());

        System.out.println("zzzzzzzzzzzz:   "+nowDate);
        System.out.println(ZonedDateTime.now(zid));

        Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(zid).toLocalDateTime());

        System.out.println("1: "+timestamp);


        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        System.out.println("india: " + dateFormat.format(currentDate));
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+5:30"));
        System.out.println("india: " + dateFormat.format(currentDate));

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println("GMT: " + dateFormat.format(currentDate));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3:00"));
        System.out.println("iserial: " + dateFormat.format(currentDate));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-07:00"));
        System.out.println("nowwwww: " + dateFormat.format(currentDate));

        String a = dateFormat.format(currentDate);


        long now = System.currentTimeMillis();
        System.out.println("now:::: "+new Timestamp(now));


        ZoneId zid1=  ZoneId.from(timestamp.toLocalDateTime());
        System.out.println("zid1: "+zid1);



    }
}
