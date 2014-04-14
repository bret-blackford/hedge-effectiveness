package misc;

public class CheckString {

	public static String check(String in) {
		String out = in;

		if (in == null) {
			out = "0";
		}

		return out.trim();
	}
}
