package denorm.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterPipe {
    public static void main(String[] args) {
        List<Map<String, Object>> batch = new ArrayList<>();

        Map<String,Object> m1 = new HashMap<>();
        m1.put("||",2);
        m1.put("||",3);

        System.out.println(m1);
    }
}
