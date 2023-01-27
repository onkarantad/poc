package denorm.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cms {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		HashMap<Object, HashMap<Object, Object>> queriesMap = new LinkedHashMap<Object, HashMap<Object,Object>>();
		
		
		
		List<Map<Object, Object>> DbConfigMapList = new LinkedList<Map<Object,Object>>();
		Map<Object, Object> m1 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m2 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m3 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m4 = new LinkedHashMap<Object, Object>();
		Map<Object, Object> m5 = new LinkedHashMap<Object, Object>();
		m1.put(SSIAppConstants.Target_Table_Name, "fact fact_temp");
		m1.put(SSIAppConstants.src_table_1, "tbl_1");
		m1.put(SSIAppConstants.src_table_2, "temp_query");
//		m1.put(SSIAppConstants.src_table_2, null);
		m1.put(SSIAppConstants.src_table_2_Alias, "temp");
		m1.put(SSIAppConstants.on_clause, "on_clause_query");
		System.out.println(m1);
		
		m2.put(SSIAppConstants.Target_Table_Name, "fact fact_agg_src");
		m2.put(SSIAppConstants.src_table_1, "full_load_agg_src_query");
		m2.put(SSIAppConstants.src_table_2, "incr_load_agg_src_query");
		System.out.println(m2);
		
		m3.put(SSIAppConstants.Target_Table_Name, "fact fact_agg_trgt");
		m3.put(SSIAppConstants.src_table_1, "full_load_agg_trgt_query");
		m3.put(SSIAppConstants.src_table_2, "incr_load_agg_trgt_query");
		System.out.println(m3);
		
		m4.put(SSIAppConstants.Target_Table_Name, "fact fact_agg_src1");
		m4.put(SSIAppConstants.src_table_1, "full_load_agg_src1_query");
		m4.put(SSIAppConstants.src_table_2, "incr_load_agg_src1_query");
		System.out.println(m4);
		
		m5.put(SSIAppConstants.Target_Table_Name, "fact fact_agg_trgt1");
		m5.put(SSIAppConstants.src_table_1, "full_load_agg_trgt1_query");
		m5.put(SSIAppConstants.src_table_2, "incr_load_agg_trgt1_query");
		System.out.println(m5);
		
		DbConfigMapList.add(m1);
		DbConfigMapList.add(m2);
		DbConfigMapList.add(m3);
		DbConfigMapList.add(m4);
		DbConfigMapList.add(m5);
		
		System.out.println("ConfigJoinerMapList :-> " + DbConfigMapList);
		
		String WF_NAME = "fact";
		String component = "batch_load";
		
//		ConfigJoinerMapList.stream().
//		filter(f->f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_temp")
//				|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src")
//				|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt")
//				|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src1")
//				|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt1")
//				).
//		forEach(m->{
//			System.out.println("m : "+m);
//		});
		
		DbConfigMapList.stream().
		filter(f->f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_temp")
		|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src")
		|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt")
		|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src1")
		|| f.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt1")
		).
		forEach(m->{
			System.out.println("m : "+m);
			if(m.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_temp")) 
			{
				if(m.get(SSIAppConstants.src_table_1)==null){
					System.err.println("component-- <"+component+"> in "+m.get(SSIAppConstants.Target_Table_Name) +" src_table_1 is null");
				}
				if(m.get(SSIAppConstants.src_table_2)==null){
					System.err.println( "component-- <"+component+"> in "+m.get(SSIAppConstants.Target_Table_Name) +"  src_table_2 is null");
				}
				if(m.get(SSIAppConstants.src_table_2_Alias)==null){
					System.err.println( "component-- <"+component+"> in "+m.get(SSIAppConstants.Target_Table_Name) +"  src_table_2_Alias is null");
				}
				if(m.get(SSIAppConstants.on_clause)==null){
					System.err.println("component-- <"+component+"> in "+m.get(SSIAppConstants.Target_Table_Name) +"  on_clause is null");
				}
				String sourceCntQuery = m.get(SSIAppConstants.src_table_2).toString();
				System.out.println("sourceCountQuery : "+sourceCntQuery);
				
				if(queriesMap.containsKey(WF_NAME.toString())) {
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.sourceCntQuery,sourceCntQuery);
				}else{
					queriesMap.put(WF_NAME.toString(), new HashMap() {{put(SSIAppConstants.sourceCntQuery,sourceCntQuery);}});
				}
			}
			
			else if(m.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src")) 
			{
				String asfc = m.get(SSIAppConstants.src_table_1)== null? null : m.get(SSIAppConstants.src_table_1).toString();
				String asic = m.get(SSIAppConstants.src_table_2)== null? null : m.get(SSIAppConstants.src_table_2).toString();
				
				if(queriesMap.containsKey(WF_NAME.toString())) {
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_src_full_cnt_query,asfc);
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_src_incr_cnt_query,asic);
					}else{
					queriesMap.put(WF_NAME.toString(), new HashMap() {{
					put(SSIAppConstants.agg_src_full_cnt_query,asfc);
					put(SSIAppConstants.agg_src_incr_cnt_query,asic);
					}});
					}
			}
			else if(m.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt")) 
			{
				String atfc = m.get(SSIAppConstants.src_table_1)== null? null : m.get(SSIAppConstants.src_table_1).toString();
				String atic = m.get(SSIAppConstants.src_table_2)== null? null : m.get(SSIAppConstants.src_table_2).toString();
				if(queriesMap.containsKey(WF_NAME.toString())) {
					
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_trgt_full_cnt_query,atfc);
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_trgt_incr_cnt_query,atic);
				}else{
					queriesMap.put(WF_NAME.toString(), new HashMap() {{
						
						put(SSIAppConstants.agg_trgt_full_cnt_query,atfc);
						put(SSIAppConstants.agg_trgt_incr_cnt_query,atic);
						}});
				}
			}
			else if(m.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_src1")) 
			{
				String as1fc = m.get(SSIAppConstants.src_table_1)== null? null : m.get(SSIAppConstants.src_table_1).toString();
				String as1ic = m.get(SSIAppConstants.src_table_2)== null? null : m.get(SSIAppConstants.src_table_2).toString();
				
				if(queriesMap.containsKey(WF_NAME.toString())) {
					
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_src1_full_cnt_query,as1fc);
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_src1_incr_cnt_query,as1ic);
				}else{
					queriesMap.put(WF_NAME.toString(), new HashMap() {{
						
						put(SSIAppConstants.agg_src1_full_cnt_query,as1fc);
						put(SSIAppConstants.agg_src1_incr_cnt_query,as1ic);
						}});
				}
			}
			else if(m.get(SSIAppConstants.Target_Table_Name).toString().equalsIgnoreCase(WF_NAME+" "+WF_NAME+"_agg_trgt1")) 
			{
				String at1fc = m.get(SSIAppConstants.src_table_1)== null? null : m.get(SSIAppConstants.src_table_1).toString();
				String at1ic = m.get(SSIAppConstants.src_table_2)== null? null : m.get(SSIAppConstants.src_table_2).toString();
				
				if(queriesMap.containsKey(WF_NAME.toString())) {
					
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_trgt1_full_cnt_query,at1fc);
					queriesMap.get(WF_NAME.toString()).put(SSIAppConstants.agg_trgt1_incr_cnt_query,at1ic);
				}else{
					queriesMap.put(WF_NAME.toString(), new HashMap() {{
						
						put(SSIAppConstants.agg_trgt1_full_cnt_query,at1fc);
						put(SSIAppConstants.agg_trgt1_incr_cnt_query,at1ic);
						}});
				}

			}		
		});

		System.out.println("queriesMap :-> " + queriesMap);

	}
	
}
