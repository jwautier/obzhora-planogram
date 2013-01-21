package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.constant.data.history.RackHConst;
import planograma.constant.data.history.RackStateHConst;
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

	private static final String Q_SELECT_FROM = "SELECT" +
			" o." + RackHConst.CODE_RACK + "," +
			" o." + RackHConst.NAME_RACK + "," +
			" o." + RackHConst.RACK_BARCODE + "," +
			" o." + RackHConst.LENGTH + "," +
			" o." + RackHConst.WIDTH + "," +
			" o." + RackHConst.HEIGHT + "," +
			" o." + RackHConst.CODE_SECTOR + "," +
			" o." + RackHConst.X_COORD + "," +
			" o." + RackHConst.Y_COORD + "," +
			" o." + RackHConst.ANGLE + "," +
			" o." + RackHConst.LOAD_SIDE + "," +
			" o." + RackHConst.CODE_RACK_TEMPLATE + "," +
			" o." + RackHConst.LOCK_SIZE + "," +
			" o." + RackHConst.LOCK_MOVE + "," +
			" o." + RackHConst.TYPE_RACK + "," +
			" o." + RackHConst.USER_INSERT + "," +
			" o." + RackHConst.DATE_INSERT + "," +
			" o." + RackHConst.USER_INSERT + " " + RackConst.USER_UPDATE + "," +
			" o." + RackHConst.DATE_INSERT + " " + RackConst.DATE_UPDATE + "," +
			" o." + RackHConst.TYPE_OPERATION + "," +
			" o." + RackHConst.REAL_LENGTH + "," +
			" o." + RackHConst.REAL_WIDTH + "," +
			" o." + RackHConst.REAL_HEIGHT + "," +
			" o." + RackHConst.X_OFFSET + "," +
			" o." + RackHConst.Y_OFFSET + "," +
			" o." + RackHConst.Z_OFFSET + " " +
			"FROM " + RackHConst.TABLE_NAME + " o ";

	private static final String Q_LIST = Q_SELECT_FROM +
			" WHERE " +
			" o." + RackHConst.TYPE_OPERATION + "<> 'D'" +
			" AND " +
			" o." + RackHConst.CODE_SECTOR + "=?" +
			" AND o." + RackHConst.DATE_INSERT + " IN " +
			"  (SELECT MAX(ss1." + RackHConst.DATE_INSERT + ")" +
			"   FROM " + RackHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackHConst.CODE_RACK + "= o." + RackHConst.CODE_RACK +
			"    AND ss1." + RackHConst.DATE_INSERT + " <= ?" +
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
			" WHERE o." + RackHConst.CODE_RACK + " = ?" +
			" AND o." + RackHConst.DATE_INSERT + " <= ?" +
			"ORDER BY o." + RackHConst.DATE_INSERT + " DESC";

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

	private static final String Q_LIST_A = Q_SELECT_FROM +
			" WHERE " +
			" o." + RackHConst.TYPE_OPERATION + "<> 'D'" +
			" AND o." + RackHConst.CODE_SECTOR + " = ?" +
			" AND o." + RackHConst.DATE_INSERT + " IN " +
			"  (SELECT MAX(ss1." + RackHConst.DATE_INSERT + ")" +
			"   FROM " + RackHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackHConst.CODE_RACK + " = o." + RackHConst.CODE_RACK +
			"    AND ss1." + RackHConst.DATE_INSERT + " <= " +
			"      (SELECT MAX(rs." + RackStateHConst.DATE_INSERT + ") " +
			"       FROM " + RackStateHConst.TABLE_NAME + " rs " +
			"       WHERE rs." + RackStateHConst.STATE_RACK + " IN ('A', 'PC') " +
			"        AND rs." + RackStateHConst.CODE_RACK + " = o." + RackHConst.CODE_RACK + ")" +
			"  )";

	public List<Rack> listA(final UserContext userContext, final Integer code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST_A);
		ps.setLong(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<Rack> list = new ArrayList<Rack>();
		while (resultSet.next()) {
			final Rack item = new Rack(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
		return list;
	}

	private static final String Q_LIST_PC = Q_SELECT_FROM +
			" WHERE " +
			" o." + RackHConst.TYPE_OPERATION + "<> 'D'" +
			" AND o." + RackHConst.CODE_SECTOR + " = ?" +
			" AND o." + RackHConst.DATE_INSERT + " IN " +
			"  (SELECT MAX(ss1." + RackHConst.DATE_INSERT + ")" +
			"   FROM " + RackHConst.TABLE_NAME + " ss1" +
			"   WHERE " +
			"    ss1." + RackHConst.CODE_RACK + " = o." + RackHConst.CODE_RACK +
			"    AND ss1." + RackHConst.DATE_INSERT + " <= " +
			"      (SELECT MAX(rs." + RackStateHConst.DATE_INSERT + ") " +
			"       FROM " + RackStateHConst.TABLE_NAME + " rs " +
			"       WHERE rs." + RackStateHConst.STATE_RACK + " IN ('PC') " +
			"        AND rs." + RackStateHConst.CODE_RACK + " = o." + RackHConst.CODE_RACK + ")" +
			"  )";

	public List<Rack> listPC(final UserContext userContext, final Integer code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST_PC);
		ps.setLong(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<Rack> list = new ArrayList<Rack>();
		while (resultSet.next()) {
			final Rack item = new Rack(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
		return list;
	}

	private static RackHModel instance = new RackHModel();

	public static RackHModel getInstance() {
		return instance;
	}

	private RackHModel() {
	}
}
