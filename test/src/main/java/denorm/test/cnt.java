package denorm.test;

public class cnt {
    public static void main(String[] args) {
        int cnt =0;
        for(int i=0;i<10;i++){
            //cnt=cnt+1;
            ++cnt;
            System.out.println("row"+(cnt)+": started");
            System.out.println("row"+(cnt)+": ended");
        }
    }
}
