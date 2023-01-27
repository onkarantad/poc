package StringCodes;

import java.util.Arrays;

public class FirstRepetativeElement {
    public static void main(String[] args) {
//        String key = "|gracej@hotmail.com|";
//        System.out.println(key);
//        key = String.join("|", Arrays.asList(key.split("\\|")));
//        System.out.println(key);

        String split = "@&";

        String aa = "abc@&ccc";
        System.out.println(aa.split(split)[0]);


    }
}
