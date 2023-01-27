package denorm.test;

public class test3 {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("testQuery");
		
		
		String string = getString(builder);
		
		System.out.println("string : "+string);

	}
	
	public static String getString(Object query) {
		return  query.toString();
		
	}

}
