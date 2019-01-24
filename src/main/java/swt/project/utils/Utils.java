package swt.project.utils;
import org.apache.commons.codec.digest.DigestUtils;

public final class Utils {

	private Utils() {

	}

	public static String concatString(String word, String translate) {
		String result = word + " - " + translate;

		return result;
	}
	
	public static String passCoding(String password) {
		return DigestUtils.md5Hex(password);
	}
	
}
