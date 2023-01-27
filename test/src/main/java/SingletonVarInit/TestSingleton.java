package SingletonVarInit;

public class TestSingleton {
    public static void main(String[] args) {
        LazySingleton ls = LazySingleton.getInstance();
        LazySingleton ls2 = LazySingleton.getInstance();

        System.out.println(ls==ls2);
        System.out.println(ls.equals(ls2));

        String test = "[\"101\"]";
        System.out.println(test);
        String outputText = test.replaceAll("[()-. ]+", "");

        System.out.println(outputText);
    }
}
