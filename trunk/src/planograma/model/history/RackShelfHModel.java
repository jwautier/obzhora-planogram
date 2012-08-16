package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackShelfConst;
import planograma.constant.data.history.RackShelfHConst;
import planograma.data.RackShelf;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 28.03.12
 * Time: 20:15
 * To change this template use File | Settings | File Templates.
 */
public class RackShelfHModel {

	public static final Logger LOG = Logger.getLogger(RackShelfHModel.class);

	public static final String Q_LIST = "select " +
			" " + RackShelfHConst.CODE_RACK + "," +
			" " + RackShelfHConst.CODE_SHELF + "," +
			" " + RackShelfHConst.X_COORD + "," +
			" " + RackShelfHConst.Y_COORD + "," +
			" " + RackShelfHConst.SHELF_HEIGHT + "," +
			" " + RackShelfHConst.SHELF_WIDTH + "," +
			" " + RackShelfHConst.SHELF_LENGTH + "," +
			" " + RackShelfHConst.ANGLE + "," +
			" " + RackShelfHConst.TYPE_SHELF + "," +
			" " + RackShelfHConst.USER_INSERT + "," +
			" " + RackShelfHConst.DATE_INSERT + "," +
			" " + RackShelfHConst.USER_INSERT + " " + RackShelfConst.USER_UPDATE + "," +
			" " + RackShelfHConst.DATE_INSERT + " " + RackShelfConst.DATE_UPDATE + " " +
			"from " + RackShelfHConst.TABLE_NAME + " " +
			"where " +
			" " + RackShelfHConst.TYPE_OPERATION + "<>'D'" +
			" and " +
			" (" + RackShelfHConst.CODE_RACK + ", " + RackShelfHConst.DATE_INSERT + ") in " +
			"  (" +
			"   select ss1." + RackShelfHConst.CODE_RACK + ", max(ss1." + RackShelfHConst.DATE_INSERT + ")" +
			"   from " + RackShelfHConst.TABLE_NAME + " ss1" +
			"   where " +
			"    ss1." + RackShelfHConst.CODE_RACK + "=?" +
			"    and ss1." + RackShelfHConst.DATE_INSERT + "<=?" +
			"   group by ss1." + RackShelfHConst.CODE_RACK +
			"  )";

	public List<RackShelf> list(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		final ResultSet resultSet = ps.executeQuery();
		final List<RackShelf> list = new ArrayList<RackShelf>();
		while (resultSet.next()) {
			final RackShelf item = new RackShelf(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return list;
	}

	private static RackShelfHModel instance = new RackShelfHModel();

	public static RackShelfHModel getInstance() {
		return instance;
	}

	private RackShelfHModel() {
	}
}
