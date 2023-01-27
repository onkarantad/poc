package Recurssion;

public class PrintNumWithoutLoop {
    static int a = 0;
    public static void main(String[] args) {
        printNum();
    }

    public static void printNum(){
        a++;
        System.out.println(a);
        if(a<10){
            printNum();
        }
    }
}
