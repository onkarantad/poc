package denorm.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestCmsStreamGroupBy {

	public static void main(String[] args) {

		List<Map<Object, Object>> mapList = new ArrayList<Map<Object, Object>>();

		Map<Object, Object> m1 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m2 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m3 = new LinkedHashMap<Object, Object>();

		m1.put(SSIAppConstants.TABLE_NAME, "product");
		m1.put(SSIAppConstants.Source_Tag_Value_1, "jsonataExpression");
		m1.put(SSIAppConstants.Source_Tag_Value_2, "dedup");
		m1.put(SSIAppConstants.Source_Tag_Value_3, "localhost:dedup");
		m1.put(SSIAppConstants.Transformation_Expression, "ApiKey");

		m2.put(SSIAppConstants.TABLE_NAME, "product");
		m2.put(SSIAppConstants.Source_Tag_Value_1, "jsonataExpression");
		m2.put(SSIAppConstants.Source_Tag_Value_2, "dedup");
		m2.put(SSIAppConstants.Source_Tag_Value_3, "localhost:dedu");
		m2.put(SSIAppConstants.Transformation_Expression, "ApiKey");

		mapList.add(m1);
		mapList.add(m2);

		System.out.println("list : " + mapList);

		String a = (String) mapList.stream().
				filter(f->f.get(SSIAppConstants.Transformation_Expression).toString().equalsIgnoreCase("ApiKey")).
				filter(u -> u.containsKey(SSIAppConstants.Source_Tag_Value_3)).
				map(u -> u.get(SSIAppConstants.Source_Tag_Value_3))
				.distinct().collect(Collectors.toList()).get(0);

		System.out.println("requestURL >> "+a);



//				.filter(u -> u.containsKey(SSIAppConstants.union))
//				.map(u -> u.get(SSIAppConstants.union))
//				.distinct().count();

	}

}
