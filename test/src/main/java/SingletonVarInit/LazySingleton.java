package SingletonVarInit;

public class LazySingleton {
  private static volatile LazySingleton instance = null;

  public static int MAX_CONNECTIONS;
 
  // private constructor
  private LazySingleton() {
  }
 
  public static LazySingleton getInstance() {
    if (instance == null) {
      synchronized (LazySingleton.class) {
        // Double check
        if (instance == null) {
          instance = new LazySingleton();
        }
      }
    }
    return instance;
  }
}