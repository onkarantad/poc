package denorm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DenormJoiners {

	public static List<Map<Object, Object>> innerJoin2ListMap(List<Map<Object, Object>> lst1,
			List<Map<Object, Object>> lst2,Object id1,Object id2){


		List<Map<Object, Object>> joinedById = lst1.stream()
				.flatMap(m1 -> lst2.stream()
						.filter(y -> m1.get(id1).equals(y.get(id1)))
						.map(m2 -> new HashMap<Object, Object>() {{
							putAll(m1);
							putAll(m2);
						}}))
				.collect(Collectors.toList());
		return joinedById;
	}

//	public static List<Map<Object, Object>> LeftJoin2ListMap(List<Map<Object, Object>> list1,
//			List<Map<Object, Object>> list2, Object object, Object object2) {
//		List<Map<Object, Object>> merged = new ArrayList<Map<Object, Object>>();
//		for (Map<Object, Object> map1 : list1) {
//			boolean found = false;
//			for (Map<Object, Object> map2 : list2) {
//				if (map1.get(object).equals(map2.get(object2))) {
//					found = true;
//					Map<Object, Object> copy = new HashMap<Object, Object>();
//					copy.putAll(map1);
//					copy.putAll(map2);
//					merged.add(copy);
//				}
//			}
//			if (!found) {
//				merged.add(map1);
//			}
//		}
//		return merged;
//	}
	
	public static List<Map<Object, Object>> LeftJoin2ListMap(List<Map<Object, Object>> list1,
			List<Map<Object, Object>> list2, Object object) {
		List<Map<Object, Object>> merged = new ArrayList<Map<Object, Object>>();
		String [] a = object.toString().split(",");
		int cnt=0;
				for (Map<Object, Object> map1 : list1) {
					boolean found = false;
					int i=0;
					for (Map<Object, Object> map2 : list2) {
						cnt = 0;
						for(i=0; i<a.length; i+=2) {
							if (map1.get(a[i]).equals(map2.get(a[i+1]))) {
								cnt++;
							}
						}
						if (cnt == a.length/2) {
							found = true;
							Map<Object, Object> copy = new HashMap<Object, Object>();
							copy.putAll(map1);
							copy.putAll(map2);
							merged.add(copy);
						}
					}
					if (!found) {
						merged.add(map1);
					}
				}
			
		return merged;
	}
	
	public static List<Map<Object, Object>> CrossJoin2ListMap(List<Map<Object, Object>> list1,
			List<Map<Object, Object>> list2) {
		List<Map<Object, Object>> merged = new ArrayList<Map<Object, Object>>();
		for (Map<Object, Object> map1 : list1) {
			for (Map<Object, Object> map2 : list2) {
					Map<Object, Object> copy = new HashMap<Object, Object>();
					copy.putAll(map1);
					copy.putAll(map2);
					merged.add(copy);
			}
		}
		return merged;
	}
}
