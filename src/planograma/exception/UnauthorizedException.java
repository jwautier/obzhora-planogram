package planograma.exception;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.02.12
 * Time: 2:29
 * To change this template use File | Settings | File Templates.
 */
public class UnauthorizedException extends Exception {
	private static UnauthorizedException instance = new UnauthorizedException();

	public static UnauthorizedException getInstance() {
		return instance;
	}

	private UnauthorizedException() {
		super("Unauthorized");
	}
}
