package planograma.data.wrapper;

import planograma.constant.data.history.SectorHConst;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 5:35
 * To change this template use File | Settings | File Templates.
 */
public class SectorHVersionWrapper {
	private final Date date_insert;
	private final int user_insert;
	private final String user_fullname;

	public SectorHVersionWrapper(final ResultSet resultSet) throws SQLException {
		date_insert = resultSet.getTimestamp(SectorHConst.DATE_INSERT);
		user_insert = resultSet.getInt(SectorHConst.USER_INSERT);
		user_fullname = resultSet.getString("user_fullname");
	}
}
