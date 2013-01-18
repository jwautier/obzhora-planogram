package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.constant.data.history.RackStateHConst;
import planograma.data.RackState;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class RackStateHModel {

	private static final Logger LOG = Logger.getLogger(RackStateHModel.class);

	private static final String Q_SELECT_FROM = "SELECT" +
			" " + RackStateHConst.CODE_RACK + "," +
			" " + RackStateHConst.STATE_RACK + "," +
			" " + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_DRAFT + "," +
			" " + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_DRAFT + "," +
			" " + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_ACTIVE + "," +
			" " + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_ACTIVE + "," +
			" " + RackStateHConst.USER_INSERT + " " + RackStateConst.USER_COMPLETE + "," +
			" " + RackStateHConst.DATE_INSERT + " " + RackStateConst.DATE_COMPLETE + " " +
			"FROM " + RackStateHConst.TABLE_NAME;

	private static final String Q_SELECT = Q_SELECT_FROM +
			" WHERE " + RackStateHConst.CODE_RACK + " = ?" +
			" AND " + RackStateHConst.DATE_INSERT + " <= ?" +
			"ORDER BY " + RackStateHConst.DATE_INSERT + " DESC";

	public RackState select(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		RackState rackState = null;
		if (resultSet.next()) {
			rackState = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return rackState;
	}

	private static RackStateHModel instance = new RackStateHModel();

	public static RackStateHModel getInstance() {
		return instance;
	}

	private RackStateHModel() {
	}
}
