package postAPI;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

public class getZone {
    public static void main(String[] args) {
       // Set<String> allZones = ZoneId.getAvailableZoneIds();
      //  System.out.println(allZones);

        ZoneId zid;

        String Zone_Id = "Asia/Jerusalem";
        if (Zone_Id.isEmpty()) {
            zid = ZoneId.systemDefault();
        } else {
            zid = ZoneId.of(Zone_Id);
        }
        LocalDate nowDate = ZonedDateTime.now(zid).toLocalDate();
        System.out.println("1: "+nowDate);
        System.out.println("2: "+ZonedDateTime.now(zid));
        Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(zid).toLocalDateTime());
        System.out.println("3: "+timestamp);

        System.out.println("4: "+getTimestampByZoneID("Asia/Jerusalem"));


    }

    public static Object getTimestampByZoneID(String Zone_Id) {
        ZoneId zid;
        if (Zone_Id.isEmpty()) {
            zid = ZoneId.systemDefault();
        } else {
            zid = ZoneId.of(Zone_Id);
        }
        LocalDate nowDate = ZonedDateTime.now(zid).toLocalDate();
        Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(zid).toLocalDateTime());
        return timestamp;
    }
}
