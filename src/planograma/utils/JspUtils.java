package planograma.utils;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 15.03.12
 * Time: 4:18
 * To change this template use File | Settings | File Templates.
 */
public class JspUtils {
	private static int maxMenuTitleLength = 28;

	public static String toMenuTitle(String title) {
		final StringBuilder res = new StringBuilder(title);
		int add = maxMenuTitleLength - res.length();
		while (add > 0) {
			res.append("&nbsp;");
			add--;
		}
		return res.toString();
	}
}
