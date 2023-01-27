package rejectedException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
 
public class RejectedExecutionExceptionExample {
 
    public static void main(String[] args) {
         
        ExecutorService executor = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(15));
 
        Worker tasks[] = new Worker[20];
        for(int i=0; i<20; i++){
           tasks[i] = new Worker(i);
           executor.execute(tasks[i]);
        }
           executor.shutdown();     
           executor.execute(tasks[0]);
    }
}