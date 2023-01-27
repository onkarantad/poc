//package threads;
//
//import asyncMethodCall.TestAsync;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//public class FutureTest {
//    private static final Logger log = LogManager.getLogger(FutureTest.class);
//    private static final int BATCH_SIZE = 4;
//
//    public static void main(String[] args) throws IOException {
//
//        //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("big_file.csv"), "utf-8"));
//
//        String reader = "abcdef&ghijkl&mnopq&rstvu&wxyz";
//        List<String> split = Arrays.asList(reader.split("&"));
//
//        ExecutorService pool = Executors.newFixedThreadPool(8);
//        //String line = reader;
//        List<String> batch = new ArrayList<>(BATCH_SIZE);
//        List<Future> results = new LinkedList<>();
//        for(String line : split) {
//            batch.add(line);
//            if (batch.size() >= BATCH_SIZE) {
//                System.out.println("inside if");
//                Future<List> f = noWaitExec(batch, pool);
//                results.add(f);
//                batch = new ArrayList<>(BATCH_SIZE);
//            } else {
//                System.out.println("inside else");
//                Future<List> f = noWaitExec(batch, pool);
//                results.add(f);
//            }
//        }
//
//
//        for (Future future : results) {
//            try {
//                Object object = future.get();
//                log.info("fut.get -> "+object);
//            } catch (Exception e) {
//                // Manage this....
//            }
//        }
//
//
//    }
//    private static Future<List> noWaitExec(final List<String> batch, ExecutorService pool) {
//        return pool.submit(new Callable<List>() {
//            public List call() throws Exception {
//                List result = new ArrayList<>(batch.size());
//                for (String string : batch) {
//                    result.add(process(string));
//                }
//                log.warn(result);
//                return result;
//            }
//
//        });
//    }
//
//    private static Object process(String string) {
//        // Your process ....
//        return string;
//    };
//}
