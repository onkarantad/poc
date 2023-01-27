package denorm.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestCmsStream {

	public static void main(String[] args) {

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		Map<String, Object> m1 = new LinkedHashMap<String, Object>();
		Map<String, Object> m2 = new LinkedHashMap<String, Object>();
		Map<String, Object> m3 = new LinkedHashMap<String, Object>();

		m1.put(SSIAppConstants.TABLE_NAME, "product");
		m2.put(SSIAppConstants.TABLE_NAME, "product");
		m3.put(SSIAppConstants.TABLE_NAME, "product");
		m1.put(SSIAppConstants.Transformation_Expression, "dedup");
		m2.put(SSIAppConstants.Transformation_Expression, "concat");
		m3.put(SSIAppConstants.Transformation_Expression, "case");

		mapList.add(m1);
		mapList.add(m2);
		mapList.add(m3);

		System.out.println("list : " + mapList);

		mapList = mapList.stream().map(m -> m.entrySet().stream().filter(map -> !map.getValue().toString().equalsIgnoreCase("dedup"))
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()))).collect(Collectors.toList());

		mapList = mapList.stream().map(m -> m.entrySet().stream().filter(map -> !map.getKey().toString().equalsIgnoreCase("Transformation_Expression"))
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()))).collect(Collectors.toList());

//		for(Map<String, Object> child : mapList){
//		    for(Object key : child.keySet()){
//		                if(child.get(key).toString().equalsIgnoreCase("dedup"))
//		                    child.remove(key);
//		    }
//		}
		System.out.println("list : " + mapList);
	}

}
