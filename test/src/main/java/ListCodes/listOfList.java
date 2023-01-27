package ListCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class listOfList {
    public static void main(String[] args) {
        List<Object> a= Arrays.asList(1,2,3,4,5);
        List<Object> b= Arrays.asList(6,7,8,9);

        List<List<Object>> aa = new ArrayList<>();
        aa.add(a);
        aa.add(b);

        System.out.println("aa: "+aa);

        aa.stream().forEach(i->{
            System.out.println(i.toString().replace("[","").replace("]",""));
        });


    }
}
