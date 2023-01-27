package threads;

public class TestCustomRecursiveAction {

    public static void main(String[] args) {
        CustomRecursiveAction c = new CustomRecursiveAction("abcdefghijklmnopqrstuvwxyz");
        c.compute();
    }
}
