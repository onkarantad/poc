package denorm.test;

import java.util.*;

public class Split_optional {

	public static void main(String[] args) throws Exception {
//		String key = "aa|bb|cc";
//		List<String> key2 =Arrays.asList(key.split("\\|"));
//		System.out.println("resultMap key b: "+key);
//		key = String.join("|", Arrays.asList(key.split("|")));
//		System.out.println("resultMap key a: "+key);
//
//		System.out.println("resultMap key2: "+key2);

//		String[] keyArr = key.split("\\|");
//
//		System.out.println("0-> "+keyArr[0]);
//		System.out.println("1-> "+keyArr[1]);
//		System.out.println("2-> "+keyArr[2]);
		
		String a = "$filter(contactHistory.userContactVO.contactAddress, function($v, $i, $a){\r\n"
				+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).updateDate\r\n"
				+ "&'|'&$filter(contactHistory.userContactVO.contactAddress.addressVO, function($v, $i, $a){\r\n"
				+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).cityName\r\n"
				+ "&'|'&$filter(contactHistory.userContactVO.contactAddress.addressVO, function($v, $i, $a){\r\n"
				+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).zipCode";
		
		String[] b = a.split("&'\\|'&");
		List<String> jsonataExprList = Arrays.asList(a.split("&'\\|'&"));
		
		System.out.println("jsonataExprList: "+jsonataExprList);
		
		System.out.println("0-> "+b[0]);
		System.out.println("1-> "+b[1]);
		System.out.println("2-> "+b[2]);

		Map<String,String> nn = new LinkedHashMap<>();
		nn.put("aa","xxxxxxxxxx");
		nn.put("BB","valueBB");


		Optional<String> v = Optional.ofNullable(nn.get("aa"));
		String v1 = nn.get("BB");

		System.out.println("BB: "+v1.toString());
		System.out.println("aa: "+v.orElseThrow(() -> new Exception("aa is null")));

		//Optional<String> k = Optional.of(String.valueOf(v));

//		System.out.println("v : "+v.orElse(null));
//		System.out.println("v : "+v.get().length());

	}

}
