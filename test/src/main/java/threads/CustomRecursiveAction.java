package threads;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {

    private String workLoad = "";
    private static final int THRESHOLD = 2;

    static List<Object> l  = Collections
            .synchronizedList( new ArrayList<>());

    //static List<Object> l  = new ArrayList<>();

    public CustomRecursiveAction(String workLoad) {
        this.workLoad = workLoad;
    }

    @Override
    protected void compute() {

        if (workLoad.length() > THRESHOLD) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else {
            processing(workLoad);
        }
    }

    private Collection<CustomRecursiveAction> createSubtasks() {

        List<CustomRecursiveAction> subtasks = new ArrayList<>();

        String partOne = workLoad.substring(0, workLoad.length() / 2);
        String partTwo = workLoad.substring(workLoad.length() / 2, workLoad.length());

        System.out.println("partOne-> "+partOne);
        System.out.println("partTwo-> "+partTwo);

        subtasks.add(new CustomRecursiveAction(partOne));
        subtasks.add(new CustomRecursiveAction(partTwo));

        return subtasks;
    }

    private void processing(String work) {

        String result = work.toUpperCase();
        l.add(result);
        System.out.println("This result - (" + result + ") - was processed by " + Thread.currentThread()
            .getName() + "--> "+l);

    }
}