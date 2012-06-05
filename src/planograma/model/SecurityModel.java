package planograma.model;

import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 05.06.12
 * Time: 1:29
 * To change this template use File | Settings | File Templates.
 */
public class SecurityModel {

	public static final String Q_CAN_ACCESS = "select ADM.CAN_I_GET_THIS_OBJECT(?) intRES from dual";

	public boolean canAccess(final UserContext userContext, final int code_object) {
		boolean result = false;
		try {
			final Connection connection = userContext.getConnection();
			final PreparedStatement ps = connection.prepareStatement(Q_CAN_ACCESS);
			ps.setInt(1, code_object);
			final ResultSet resultSet = ps.executeQuery();
			if (resultSet.next()) {
				final int uid = resultSet.getInt(1);
				result = uid > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	private static SecurityModel instance = new SecurityModel();

	public static SecurityModel getInstance() {
		return instance;
	}

	private SecurityModel() {
	}
}
