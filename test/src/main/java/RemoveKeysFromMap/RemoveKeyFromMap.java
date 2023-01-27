package RemoveKeysFromMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RemoveKeyFromMap {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("a", "aa");
        map.put("b", "tt");
        map.put("c", "xx");

        Set<String> set = new HashSet<>();
        set.add("a");
        set.add("b");

        map.keySet().removeAll(set);

        System.out.println("map: "+map);
    }
}
