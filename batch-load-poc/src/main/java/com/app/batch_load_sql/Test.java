package com.app.batch_load_sql;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        List<Map< Object, Object >> DBConfigMapList = new ArrayList<>();
        Map<Object, Object> m1 = new HashMap<>();
        m1.
        m1.put("ttn","ta");
        m1.put("st","a");
        m1.put("jc","source_table");
        Map<Object, Object> m2 = new HashMap<>();
        m2.put("ttn","ta");
        m2.put("st","ab");
        m2.put("jc","source_table");
        Map<Object, Object> m3 = new HashMap<>();
        m3.put("ttn","tb");
        m3.put("st","bb");
        m3.put("jc","sb");
        Map<Object, Object> m4 = new HashMap<>();
        m4.put("ttn","ta");
        m4.put("st","b");
        m4.put("jc","lookup_table");
        DBConfigMapList.add(m1);
        DBConfigMapList.add(m2);
        DBConfigMapList.add(m3);
        DBConfigMapList.add(m4);
        System.out.println("DBConfigMapList : "+DBConfigMapList);

        List<Object> a = DBConfigMapList.stream()
                .filter(i->i.get("ttn").equals("ta"))
                .filter(i->i.get("jc").equals("source_table"))
                .map(i->i.get("st")).distinct().collect(Collectors.toList());
        System.out.println("DBConfigMapList : "+DBConfigMapList);
        System.out.println("a : "+a + "   "+a.size());

        a.size();

        String bb = "AC_ENTRY.UPDATE_DATE";
        System.out.println(bb.split("\\.")[1]);

        List<String> aa = Arrays.asList("a","b");
        System.out.println("aa: "+aa.toString());

        String query = "Insert into SI_CORE_DWH_SSI.DWH_JSON  (AUD_BATCH_ID, AUD_SRC_SYS_NM, AUD_SUB_BATCH_ID, col1, col2, col3, END_DATE, FLAG, ID, LANGUAGE_ID, LAST_UPDATE_DATE, NAME) Select 2 AS AUD_BATCH_ID, 'IDIT' AS AUD_SRC_SYS_NM, 69 AS AUD_SUB_BATCH_ID, CAST (JSON_VALUE(EXT_DATA, '$.col4.tag1') AS VARCHAR) AS col1, CAST (JSON_VALUE(EXT_DATA, '$.col2') AS INTEGER) AS col2, CAST (JSON_VALUE(EXT_DATA, '$.col4.tag4.tag33') AS DATETIME2) AS col3, null AS END_DATE, 'YES' AS FLAG, ODS_JSON.ID AS ID, ODS_JSON.LANGUAGE_ID AS LANGUAGE_ID, ODS_JSON.UPDATE_DATE AS LAST_UPDATE_DATE, ODS_JSON.NAME AS NAME FROM SI_CORE_IDIT_ODS_SSI.ODS_JSON ODS_JSON 'a?chunkQ1' union Select 2 AS AUD_BATCH_ID, 'IDIT' AS AUD_SRC_SYS_NM, 69 AS AUD_SUB_BATCH_ID, CAST (JSON_VALUE(EXT_DATA, '$.col4.tag1') AS VARCHAR) AS col1, CAST (JSON_VALUE(EXT_DATA, '$.col2') AS INTEGER) AS col2, CAST (JSON_VALUE(EXT_DATA, '$.col4.tag4.tag33') AS DATETIME2) AS col3, null AS END_DATE, 'YES' AS FLAG, ODS_JSON_NEW.ID AS ID, ODS_JSON_NEW.LANGUAGE_ID AS LANGUAGE_ID, ODS_JSON_NEW.UPDATE_DATE AS LAST_UPDATE_DATE, ODS_JSON_NEW.NAME AS NAME FROM SI_CORE_IDIT_ODS_SSI.ODS_JSON_NEW ODS_JSON_NEW where ODS_JSON_NEW.LANGUAGE_ID=222 'a?chunkQ2'.";
        Pattern pattern = Pattern.compile("'[a-zA-Z]\\?[cC][hH][uU][nN][kK][Qq]\\d*'");
        Matcher matcher = pattern.matcher(query);
        List<String> listMatches = new ArrayList<String>();
        while (matcher.find()) {
            listMatches.add(matcher.group());
        }
            System.out.println(listMatches);

        LinkedList<Object> chunkFilterList = new LinkedList<>();
        Map<String,Object> chunkMap = new HashMap<>();
        chunkMap.put("chunkQ1","ODS_JSON.UPDATE_DATE > '1800-01-01' AND ODS_JSON.UPDATE_DATE <= GETDATE()");
        chunkMap.put("chunkQ2","ODS_JSON_NEW.UPDATE_DATE > '1800-01-01' AND ODS_JSON_NEW.UPDATE_DATE <= GETDATE()");
        chunkFilterList.add((Object)chunkMap);

       // System.out.println("query_before: "+query);
       // System.out.println("query_after: "+chunkRem(query));
       // System.out.println("query_after_replace: "+chunkAdd(query,chunkFilterList));

        String SOURCE_DATE_COL= "SI_CORE_IDIT_ODS_SSI.ODS_JSON.UPDATE_DATE oj,SI_CORE_IDIT_ODS_SSI.ODS_JSON_NEW.UPDATE_DATE ODS_JSON_NEW ";
        List<String> srcDateColList = Arrays.asList(SOURCE_DATE_COL.trim().split(","));
        System.out.println("srcDateColList: "+srcDateColList);
        //List<String> chunkFilterList1 = new ArrayList<>();
        List<String> chunkFilterList1 = srcDateColList.stream().map(i->i.trim().split(" ")[1]+"."+i.split(" ")[0].split("\\.")[2]).collect(Collectors.toList());
        System.out.println("chunkFilterList1: "+chunkFilterList1);

        Map<String,String> chunkColMap = new LinkedHashMap<>();
        IntStream.range(1, chunkFilterList1.size()+1)
                .forEach(i->chunkColMap.put("chunkQ"+i,chunkFilterList1.get(i-1)));
        System.out.println("chunkColMap: "+chunkColMap);

//        for (String listMatch:listMatches){
//            String[] arr = listMatch.replace("'","").split("\\?");
//            System.out.println("--> "+((Map<String,Object>)chunkFilterList.get(0)).get(arr[1]));
//            if(arr[0].equalsIgnoreCase("w")) {
//                query = query.replace(listMatch," WHERE "+chunkMap.get(arr[1]));
//            }
//            if(arr[0].equalsIgnoreCase("a")){
//                query = query.replace(listMatch," AND "+chunkMap.get(arr[1]));
//            }
//        }
//
//        System.out.println("query: "+query);

        List<Object> listObj = new ArrayList<>();
        listObj.add("a");
        listObj.add("b");
        System.out.println("listObj b : "+listObj);
        listObj.remove(0);
        listObj.add(0,"c");
        System.out.println("listObj a : "+listObj);




    }
    public static String chunkRem(String query){
        String query1 = query;
        Pattern pattern = Pattern.compile("'[a-zA-Z]\\?[cC][hH][uU][nN][kK][Qq]\\d*'");
        Matcher matcher = pattern.matcher(query1);
        List<String> listMatches = new ArrayList<String>();
        while (matcher.find()) {
            listMatches.add(matcher.group());
        }
        for (String listMatch:listMatches){
            query1 = query1.replace(listMatch,"");
        }
        return query1;
    }
    public static String chunkAdd(String query, LinkedList<Object> chunkFilterList){
        String query1 = query;
        Pattern pattern = Pattern.compile("'[a-zA-Z]\\?[cC][hH][uU][nN][kK][Qq]\\d*'");
        Matcher matcher = pattern.matcher(query1);
        List<String> listMatches = new ArrayList<String>();
        while (matcher.find()) {
            listMatches.add(matcher.group());
        }
        for (String listMatch:listMatches){
            String[] arr = listMatch.replace("'","").split("\\?");
           // System.out.println("--> "+((Map<String,Object>)chunkFilterList.get(0)).get(arr[1]));
            if(arr[0].equalsIgnoreCase("w")){
                query1 = query1.replace(listMatch," WHERE "+((Map<String,Object>)chunkFilterList.get(0)).get(arr[1]));
            }
            if(arr[0].equalsIgnoreCase("a")){
                query1 = query1.replace(listMatch," AND "+((Map<String,Object>)chunkFilterList.get(0)).get(arr[1]));
            }
        }
        return query1;
    }
}
