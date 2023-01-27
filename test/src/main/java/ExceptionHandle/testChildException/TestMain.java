package ExceptionHandle.testChildException;

public class TestMain {
    public static void main(String[] args) {
        try {
            String child = Child.callChild(0);
        }catch (Exception e){
            System.out.println("catch error in main class "+e);
            throw new ArithmeticException("-> "+e.toString());
        }

    }
}
