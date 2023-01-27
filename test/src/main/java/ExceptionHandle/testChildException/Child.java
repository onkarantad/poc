package ExceptionHandle.testChildException;

public class Child {
    public static String callChild(int status)  {
        if (status==0){
            try {
                int a = 10/0;
            } catch (Exception e) {
                System.out.println("error in child");
                throw new ArithmeticException("cannot divide by zero");

            }
        }
        return "child is playing";
    }
}
