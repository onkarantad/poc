package ExceptionHandle;

public class TryCatchFinally {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();

       try {
           int a = 0;
           try {
               throw new NullPointerException("this is eeee");
           }
           catch (Exception e){
               System.out.println("catch 1");
               e.printStackTrace();
           }
           finally {
               sb.append("catch 1");
           }

       }catch (Exception e){
           // update
           System.out.println("catch 2");
           sb.append("catch 2");
           e.printStackTrace();
       }

        System.out.println("sb: "+sb);

    }
}
