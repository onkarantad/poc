package threads;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class SquareCalculator {    
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    public Future<Integer> calculate(Integer input) {   //call rest API funtion     
        return executor.submit(() -> {
            Thread.sleep(5000);
            return input * input; //return C_ID
        });
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //Map<Map,future> -> {{entity_details, feed=Q1 },future}
        Future<Integer> future = new SquareCalculator().calculate(10);//rest call rest API (BG)
        int count=0;
        Thread.sleep(4900);//handling 
        System.out.println("process workflow..");
        while(!future.isDone()) {
            System.out.println("Calculating...:"+count++);
            Thread.sleep(300);
        }
        Integer result = future.get();
        System.out.println("future out : "+result);
        closeExecutor();
        System.out.println("--------");
    }
     static void closeExecutor(){
        executor.shutdown();
    }
}