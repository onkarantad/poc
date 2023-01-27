//package nrt;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import denorm.test.SSIAppConstants;
//
//public class test12 {
//
//	public static void main(String[] args) {
//		List<Map<Object, Object>> dedupMapList = new ArrayList<Map<Object, Object>>();
//
//		List<Map<Object, Object>> InsertList = new ArrayList<Map<Object, Object>>();
//
//		Map<Object, Object> dedupMap = new LinkedHashMap<Object, Object>();
//		dedupMap.put(SSIAppConstants.Target_Table_Name, "ENTITY_DETAILS_SIT_IDIT_VOs");
//		dedupMap.put(SSIAppConstants.Target_Column_Name, "CE_ID");
//		dedupMap.put(SSIAppConstants.Source_Tag_Value_1, "contactTest_date,contactTest_email,contactTest_tel");
//		dedupMap.put(SSIAppConstants.Source_Tag_Value_2,
//				"$filter(contactHistory.userContactVO.contactAddress, function($v, $i, $a){\r\n"
//						+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).updateDate\r\n"
//						+ "&'|'&$filter(contactHistory.userContactVO.contactAddress.addressVO, function($v, $i, $a){\r\n"
//						+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).cityName\r\n"
//						+ "&'|'&$filter(contactHistory.userContactVO.contactAddress.addressVO, function($v, $i, $a){\r\n"
//						+ "$v.($toMillis($v.updateDate)) = $max($a.($toMillis(updateDate)))}).zipCode");
//		dedupMap.put(SSIAppConstants.Transformation_Expression, "dedup_jnt");
//		dedupMap.put(SSIAppConstants.XPath_Loop, "contact");
//		dedupMap.put(SSIAppConstants.Target_Table_PK, "ENTITY_ID");
//
//		dedupMapList.add(dedupMap);
//
//		Map<Object, Object> inputM1 = new LinkedHashMap<Object, Object>();
//		Map<Object, Object> inputM2 = new LinkedHashMap<Object, Object>();
//
//		inputM1.put("ORG_SIC_DETAILS_ID", "4436147|101|IDIT");
//		inputM1.put("unions", "Q1");
//		inputM1.put("Target_Table_Name", "ORGANISATION_SIC_SIT_IDIT_VOs");
//		inputM1.put("LAST_UPDATE_DATE", "2021-03-03 12:58:26");
//
//		inputM2.put("Target_Table_Name", "ENTITY_DETAILS_SIT_IDIT_VOs");
//		inputM2.put("unions", "Q1");
//		inputM2.put("Target_Table_PK", "ENTITY_ID");
//		inputM2.put("ENTITY_NUM", "4436147");
//
//		InsertList.add(inputM1);
//		InsertList.add(inputM2);
//
//		System.out.println("InsertList : " + InsertList);
//
//		List<Map<Object, Object>> ceIdList = new ArrayList<Map<Object, Object>>();
//
//		Map<Object, Object> ceIdMap = new LinkedHashMap<Object, Object>();
//
//		ceIdMap.put("Target_Table_Name", "ENTITY_DETAILS_SIT_IDIT_VOs");
//		ceIdMap.put("CE_ID", "13");
//		ceIdMap.put("unions", "Q1");
//		ceIdList.add(ceIdMap);
//
//		List<Map<String, String>> merged = new ArrayList<Map<String, String>>();
//
//		for (Map<? extends Object, ? extends Object> map1 : InsertList) {
//		    boolean found = false;
//		    for (Map<? extends Object, ? extends Object> map2 : ceIdList) {
//		        if (map1.get("Target_Table_Name").equals(map2.get("Target_Table_Name")) &&
//		        		map1.get("unions").equals(map2.get("unions"))) {
//		            found = true;
//		            Map<String, String> copy = new HashMap<String, String>();
//		            copy.putAll((Map<? extends String, ? extends String>) map1);
//		            copy.putAll((Map<? extends String, ? extends String>) map2);
//		            merged.add(copy);
//		        }
//		    }
//		    if (!found) {
//		        merged.add((Map<String, String>) map1);
//		    }
//		}
//
//		System.out.println("merged : "+merged);
//
//
//	}
//
//}
