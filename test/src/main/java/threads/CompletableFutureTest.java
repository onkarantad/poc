package threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {

    static int thrdSize = 2;
    private static ExecutorService pool = Executors.newFixedThreadPool(thrdSize);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> completableFuture
//                = CompletableFuture.supplyAsync(() -> {return "Hello" ;},pool);
//
//        CompletableFuture<String> future = completableFuture
//                .thenApply(s -> {return s + " World";});
//
//        System.out.println(future.get());

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello",pool)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
//
       System.out.println(completableFuture.get());
//        pool.shutdown();
    }
}
