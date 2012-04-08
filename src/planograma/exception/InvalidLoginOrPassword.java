package planograma.exception;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 03.03.12
 * Time: 0:23
 * To change this template use File | Settings | File Templates.
 */
public class InvalidLoginOrPassword extends SQLException {

	private static InvalidLoginOrPassword instance = new InvalidLoginOrPassword();

	public static InvalidLoginOrPassword getInstance() {
		return instance;
	}

	private InvalidLoginOrPassword() {
		super("Invalid login or password");
	}
}
