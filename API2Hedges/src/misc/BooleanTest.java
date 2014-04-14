package misc;

public class BooleanTest {
	
	public static String test(String _boolean) {
		
		String out = "";
		
		if( _boolean.equals("1")) {
			out = "TRUE";
		} else {
			out = "FALSE";
		}
		
		return out;
	}
}
