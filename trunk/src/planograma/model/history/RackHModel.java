package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.constant.data.history.RackHConst;
import planograma.data.Rack;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class RackHModel {

	private static final Logger LOG = Logger.getLogger(RackHModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + RackHConst.CODE_RACK + "," +
			" " + RackHConst.NAME_RACK + "," +
			" " + RackHConst.RACK_BARCODE + "," +
			" " + RackHConst.LENGTH + "," +
			" " + RackHConst.WIDTH + "," +
			" " + RackHConst.HEIGHT + "," +
			" " + RackHConst.CODE_SECTOR + "," +
			" " + RackHConst.X_COORD + "," +
			" " + RackHConst.Y_COORD + "," +
			" " + RackHConst.ANGLE + "," +
			" " + RackHConst.LOAD_SIDE + "," +
			" " + RackHConst.CODE_RACK_TEMPLATE + "," +
			" " + RackHConst.LOCK_SIZE + "," +
			" " + RackHConst.LOCK_MOVE + "," +
			" " + RackHConst.TYPE_RACK + "," +
			" " + RackHConst.USER_INSERT + "," +
			" " + RackHConst.DATE_INSERT + "," +
			" " + RackHConst.USER_INSERT + " " + RackConst.USER_UPDATE + "," +
			" " + RackHConst.DATE_INSERT + " " + RackConst.DATE_UPDATE + "," +
			" " + RackHConst.TYPE_OPERATION + "," +
			" " + RackHConst.REAL_LENGTH + "," +
			" " + RackHConst.REAL_WIDTH + "," +
			" " + RackHConst.REAL_HEIGHT + "," +
			" " + RackHConst.X_OFFSET + "," +
			" " + RackHConst.Y_OFFEST + "," +
			" " + RackHConst.Z_OFFEST + " " +
			"from " + RackHConst.TABLE_NAME + " ";

	private static final String Q_LIST = Q_SELECT_FROM +
			"where " +
			" " + RackHConst.TYPE_OPERATION + "<> 'D'" +
			" and " +
			" (" + RackHConst.CODE_RACK + ", " + RackHConst.DATE_INSERT + ") in " +
			"  (select ss1." + RackHConst.CODE_RACK + ", max(ss1." + RackHConst.DATE_INSERT + ")" +
			"   from " + RackHConst.TABLE_NAME + " ss1" +
			"   where " +
			"    ss1." + RackHConst.CODE_SECTOR + "=?" +
			"    and ss1." + RackHConst.DATE_INSERT + " <= ?" +
			"   group by ss1." + RackHConst.CODE_RACK +
			"  )";

	public List<Rack> list(final UserContext userContext, final Integer code_sector, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setLong(1, code_sector);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		final ResultSet resultSet = ps.executeQuery();
		final List<Rack> list = new ArrayList<Rack>();
		while (resultSet.next()) {
			final Rack item = new Rack(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return list;
	}

	private static final String Q_SELECT = Q_SELECT_FROM +
			"where " + RackHConst.CODE_RACK + " = ?" +
			" and " + RackHConst.DATE_INSERT + " <= ?" +
			"order by " + RackHConst.DATE_INSERT + " desc";

	public Rack select(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		ps.setTimestamp(2, new Timestamp(date.getTime()));
		ps.setMaxRows(1);
		final ResultSet resultSet = ps.executeQuery();
		Rack rack = null;
		if (resultSet.next()) {
			final String type_operation = resultSet.getString(RackHConst.TYPE_OPERATION);
			if (!type_operation.equals("D")) {
				rack = new Rack(resultSet);
			}
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return rack;
	}

	private static RackHModel instance = new RackHModel();

	public static RackHModel getInstance() {
		return instance;
	}

	private RackHModel() {
	}
}
