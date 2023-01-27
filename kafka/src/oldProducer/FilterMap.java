package oldProducer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import  java.util.stream.Collectors;

public class FilterMap {
	public static void main(String[] args) {
		
		List<Map<String, String>> lm = new ArrayList<Map<String,String>>();
		
		Map<String, String> m1= new LinkedHashMap<String, String>();
		m1.put("k1", "v1");
		m1.put("k2", "v2");
		Map<String, String> m2= new LinkedHashMap<String, String>();
		m2.put("k3", "v3");
		m2.put("k4", "v4");
		
		lm.add(m1);
		lm.add(m2);
		
		String key="k4";
		String value="v4";
		
		System.out.println("before : "+lm);
		List<Map<String, String>> lm2 = lm.stream()
        .filter(map -> map.containsKey(key) && map.get(key).equals(value))
        .collect(Collectors.toList());
		System.out.println("after : "+lm2);
		
		Map<String, String> lm3 = lm.stream()
		        .filter(map -> map.containsKey(key) && map.get(key).equals(value))
		        .collect(Collectors.toList()).get(0);
		System.out.println("map : "+lm3);
				
	}
}
