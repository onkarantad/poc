package denorm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LeftOuterJoinMapList {
	
	public static void main(String[] args) {
		
		List<Map<Object, Object>> InsertList = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> inputM1 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> inputM2 = new LinkedHashMap<Object, Object>();
		inputM1.put("ORG_SIC_DETAILS_ID", "4436147|101|IDIT");
		inputM1.put("unions", "Q1");
		inputM1.put("Target_Table_Name", "ORGANISATION_SIC_SIT_IDIT_VOs");
		inputM1.put("LAST_UPDATE_DATE", "2021-03-03 12:58:26");
		inputM2.put("Target_Table_Name", "ENTITY_DETAILS_SIT_IDIT_VOs");
		inputM2.put("unions", "Q1");
		inputM2.put("Target_Table_PK", "ENTITY_ID");
		inputM2.put("ENTITY_NUM", "4436147");
		InsertList.add(inputM1);
		InsertList.add(inputM2);
		System.out.println("InsertList : " + InsertList);
		
		List<Map<Object, Object>> ceIdList = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> inputM3 = new LinkedHashMap<Object, Object>();
		inputM3.put("Target_Table_Name", "ENTITY_DETAILS_SIT_IDIT_VOs");
		inputM3.put("unions", "Q1");
		inputM3.put("CE_ID", "12");
		ceIdList.add(inputM3);
		System.out.println("ceIdList : " + ceIdList);
		
		List<Map<Object,Object>> merged = new ArrayList<Map<Object,Object>>();
		for (Map<Object,Object> map1 : InsertList) {
		    boolean found = false;
		    for (Map<Object,Object> map2 : ceIdList) {
		        if (map1.get("unions").equals(map2.get("unions"))&&
		        		map1.get("Target_Table_Name").equals(map2.get("Target_Table_Name"))) {
		            found = true;
		            Map<Object,Object> copy = new HashMap<>();
		            copy.putAll(map1);
		            copy.putAll(map2);
		            merged.add(copy);
		        }
		    }
		    if (!found) {
		        merged.add(map1);
		    }
		}

		System.out.println("merged : " + merged);
	}
	
}

