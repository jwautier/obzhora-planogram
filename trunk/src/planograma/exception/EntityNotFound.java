package planograma.exception;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 20:53
 * To change this template use File | Settings | File Templates.
 */
public class EntityNotFound extends SQLException {
	public EntityNotFound(String entityName, int entityCode) {
		super("Entity \"" + entityName + "\" not found. Code: " + entityCode);
	}
}
