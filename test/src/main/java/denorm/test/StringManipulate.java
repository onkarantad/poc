package denorm.test;

public class StringManipulate {

	public static void main(String[] args) {
		String a = "a_b_c_d";
		
		String b = a.replaceAll("_", ".");
		
		System.out.println("b: "+b);
		
		String c = b.substring(0, b.lastIndexOf("."));
		String d = b.substring(b.lastIndexOf(".")).replace(".", "");
		
		System.out.println("c-> "+c);
		System.out.println("d-> "+d);

	}

}
