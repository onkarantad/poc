package mapTolist;

import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class MapoList {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("101","aa");
        map.put("102","bb");
        map.put("103","cc");
        map.put("104","dd");
        map.put("105","dd");
        map.put("106","dd");

        List<Object> aa=map.entrySet().parallelStream().flatMap(i->Stream.of(i)).collect(Collectors.toList());

//        aa.parallelStream().forEach(i->{
//            System.out.println(i.toString().split("=")[0]);
//        });

       // System.out.println(aa);

        TreeMap<String, String> sorted = new TreeMap<String, String>(map);

//        System.out.println(sorted.su);

//        //int end = 50;
//        int start = 0;
//        int total_length = 233;
//        int subrange_length = total_length/4;
//        int current_start =0;
//        for (int i = 0; i < 5; ++i) {
//            int current_end = current_start + subrange_length;
//            System.out.println("Smaller range: [" + current_start + ", " + (current_end) + "]");
//            current_start += subrange_length;
//            if(current_end==total_length){
//                break;
//            }
//        }

//        List<TreeMap<String, String>> list1 = new ArrayList<>();
//        int splitBy = 1;
//        TreeMap<String,String> smallMap = new TreeMap<>();
//        List<String> keyList = new ArrayList<>(map.keySet());
//        for(int total = keyList.size(), i = 0, j = Math.min(i + splitBy, total); i < total; i = j, j = Math.min(i + splitBy, total)){
//            smallMap.clear();
//            for(String key : keyList.subList(i, j))
//                smallMap.put(key, map.get(key));
//            list1.add(new TreeMap<>(smallMap));
//        }

       // System.out.println(list1);

//        List<Map.Entry<Integer, Object>> singleList = map.entrySet()
//                .stream()
//                .collect(Collectors.toList());

 //       List<Pair<?, ?>> ListOfPair = getListOfPairFromMap(map);

//        ListOfPair.stream().forEach(i->{
//            Integer a = (Integer) i.getKey();
//            String b = (String) i.getValue();
////            System.out.println("a: "+a);
////            System.out.println("b: "+b);
//            System.out.println(i.getKey()+":"+i.getValue());
//        });




    }

//    public static List<Pair<?, ?>> getListOfPairFromMap(Map<?, ?> map){
//        List<Pair<?, ?>> ListOfPair = new ArrayList<>();
//        map.entrySet()
//                .parallelStream()
//                // .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .forEachOrdered(e -> ListOfPair.add(new Pair<>(e.getKey(), e.getValue())));
//        return ListOfPair;
//    }
}
