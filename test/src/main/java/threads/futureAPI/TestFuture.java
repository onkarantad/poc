package threads.futureAPI;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.*;

@Log4j2
public class TestFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        callFuture01();

    }

    public static void callFuture4() throws ExecutionException, InterruptedException {
        log.info("start");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(()->muliply(10,1));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(()->muliply(10,2));
        CompletableFuture<Integer> future3 = future1.thenCombine(future2,(f1,f2)->f1+f2);
        log.info("end");
        System.out.println(future3.get());

    }

    public static void callFuture3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture0 = CompletableFuture.supplyAsync(()->"Hello").thenApply(i->i+" World");
        System.out.println(completableFuture0.get());

        /**
         *  thenApply : returns completableFuture and again using completableFuture
         *  CompletableFuture<CompletableFuture<String>>
         */
        CompletableFuture<CompletableFuture<String>> completableFuture1 = CompletableFuture.supplyAsync(() -> "Hello").thenApply(i -> CompletableFuture.supplyAsync(() -> i + " World"));
        System.out.println(completableFuture1.get());

        /**
         * thenCompose: return unwrap value completableFuture
         */
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "Hello").thenCompose(i -> CompletableFuture.supplyAsync(() -> i + " World"));
        System.out.println(completableFuture2.get());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture<CompletableFuture<String>> completableFuture3 = CompletableFuture.supplyAsync(() -> "Hello").thenApplyAsync(i -> CompletableFuture.supplyAsync(() -> i + " World"),executorService);
        System.out.println(completableFuture3.get());
        executorService.shutdown();
    }



    public static void callFuture2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        log.info("start");
//        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> muliply(10, 2)).thenAccept(i->log.info(i));
//        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> muliply(10, 3)).thenAccept(i->log.info(i));
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> muliply(10, 2));
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> muliply(10, 3),executorService);
        log.info("end");
        while (!future1.isDone() && !future2.isDone()){
            //log.info("waiting completable future");
            sleep(1);
        }
        log.info(future1.get());
        log.info(future2.get());
        executorService.shutdown();
    }

    public static void callFuture1() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        log.info("start");
        Future<Integer> future3 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return muliply(10,2);
            }
        });
        Future<Integer> future4 = executorService.submit(() -> {
            return  muliply(10,3);
        });
        log.info("end");
        while (!future3.isDone() && !future3.isDone()){
           // log.info("waiting future");
            sleep(1);
        }

        executorService.shutdown();

        log.info(future3.get());
        log.info(future4.get());
    }

    public static void callFuture01() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        //log.info("start");
        Future<Integer> future3 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("future3 started");
                return muliply(10,2);
            }
        });
        Future<Integer> future4 = executorService.submit(() -> {
            log.info("future4 started");
            return  muliply(10,3);
        });
        //log.info("end");
        while (!future3.isDone() && !future3.isDone()){
            log.info(String.format(
                    "future1 is %s and future2 is %s",
                    future3.isDone() ? "done" : "not done",
                    future4.isDone() ? "done" : "not done"
            ));
            sleep(10);
        }

        log.info(future3.get());
        log.info(future4.get());
        //future3.cancel(true); //not working
        //future4.cancel(true); //not working
        executorService.shutdown();
    }

    public static void callFuture0() throws ExecutionException, InterruptedException {
        log.info("start");
        Future<Integer> future3 = Executors.newSingleThreadExecutor().submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return muliply(10,2);
            }
        });
        Future<Integer> future4 = Executors.newSingleThreadExecutor().submit(() -> {
            return  muliply(10,3);
        });
        log.info("end");
        while (!future3.isDone() && !future3.isDone()){
            //log.info("waiting future");
            sleep(1);
        }

        log.info(future3.get());
        log.info(future4.get());
        future3.cancel(true);
        future4.cancel(true);
    }

    public static int muliply(int a,int b)  {
        sleep(100);
        return a*b;
    }

    public static void sleep(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
