package planograma.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 01.06.12
 * Time: 5:21
 * To change this template use File | Settings | File Templates.
 */
public class NotAccessException extends Exception{
	private static NotAccessException instance = new NotAccessException();

	public static NotAccessException getInstance() {
		return instance;
	}

	private NotAccessException() {
		super("У вас недостаточно прав");
	}
}
