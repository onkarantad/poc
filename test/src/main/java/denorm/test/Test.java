package denorm.test;

import java.util.LinkedHashMap;
import java.util.Map;

public class Test {

	public static void main(String[] args) {
		
		Map<String, Object> m = new LinkedHashMap<String, Object>();
		
		String sourceCntQuery = m.get("src_table_2") == null ? null : m.get("src_table_2").toString();
		m.put("aa", null);
		System.out.println(m.get("aa")==null);
//		
// 	String c = m.get("aa").toString();
 	System.out.println("cxx : "+(m.get("aa")== null));
//		
	
		
		System.out.println("sourceCntQuery : "+sourceCntQuery);
		
		StringBuilder agg_src_full_cnt_query=new StringBuilder();
		StringBuilder agg_src1_incr_cnt_query=new StringBuilder();
		
		
//		Object obj = null;
//		Object obj2 = null;
		
//		obj = (Double)obj;
//		obj2 = (Double)obj2;
		
		
//		System.out.println("obj : "+obj);
//		System.out.println("obj2 : "+obj2);		
//		
//		System.out.println("equals >> "+ (obj.equals(obj2)));
//		System.out.println("equals >> "+ (obj.equals(obj2)));
		
		String vv = null;
		
//		System.out.println(">>>>>>>>>>>>>>>>>>>isBlank : "+(vv.length()));
		System.out.println(">>>>>>>>>>>>>>>>>>>isEmpty : "+vv.isEmpty());
		//System.out.println(">>>>>>>>>>>>>>>>>>>isEmpty : "+vv.isBlank());
		
		
//		System.out.println("vv 11 " + (vv.replace("?1", "cc")));
//		
//		System.out.println("vv" + (vv == null));
		
		
		Object AGG_SRC_FULL_CNT = 123.4;
		Object AGG_TRGT_FULL_CNT = 123.4;
		Object AGG_SRC1_FULL_CNT = null;
		Object AGG_TRGT1_FULL_CNT = null;
		
		
		
		
		int matchFlag = (AGG_SRC1_FULL_CNT == null && AGG_TRGT1_FULL_CNT == null)
		? (AGG_SRC_FULL_CNT.equals(AGG_TRGT_FULL_CNT) ? 1 : 0)
		: (AGG_SRC_FULL_CNT.equals(AGG_TRGT_FULL_CNT) && AGG_SRC1_FULL_CNT.equals(AGG_TRGT1_FULL_CNT) ? 1
				: 0);
		
		System.out.println("matchFlag : " + matchFlag);
		
		System.out.println("<<>>"+(1==6?1:null));
	}

}
