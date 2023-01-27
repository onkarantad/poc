package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Client {

    private static final int N = 100;
    private static final int K = 4;

    public static void main(String[] args) {

        ArrayList<Double> list = new ArrayList<>();

        for (int j=0; j<N; j++){
            list.add(ThreadLocalRandom.current().nextDouble(1, 100));
        }

        for (int i=0; i<K; i++){
            new ClientThread(list.subList(i*N/K, i*N/K+N/K-1)).start();
        }
    }

}


class ClientThread extends Thread{

    private List<Double> list;

    public ClientThread(List<Double> vectorArg){
        list = vectorArg;
    }

    @Override
    public void run(){
        try {
//            Service s = (Service) Naming.lookup("rmi://localhost:1099/Servico");
            System.out.println("get(1): "+ list.get(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}