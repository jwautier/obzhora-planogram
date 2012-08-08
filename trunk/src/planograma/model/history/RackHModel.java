package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.UserContext;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 * TODO
 */
public class RackHModel {

	public static final Logger LOG = Logger.getLogger(RackHModel.class);

	public static final String Q_LIST = "select" +
			" " + RackConst.CODE_RACK + "," +
			" " + RackConst.STATE_RACK + "," +
			" " + RackConst.NAME_RACK + "," +
			" " + RackConst.RACK_BARCODE + "," +
			" " + RackConst.LENGTH + "," +
			" " + RackConst.WIDTH + "," +
			" " + RackConst.HEIGHT + "," +
			" " + RackConst.CODE_SECTOR + "," +
			" " + RackConst.X_COORD + "," +
			" " + RackConst.Y_COORD + "," +
			" " + RackConst.ANGLE + "," +
			" " + RackConst.LOAD_SIDE + "," +
			" " + RackConst.CODE_RACK_TEMPLATE + "," +
			" " + RackConst.LOCK_SIZE + "," +
			" " + RackConst.LOCK_MOVE + "," +
			" " + RackConst.TYPE_RACK + "," +
			" " + RackConst.USER_INSERT + "," +
			" " + RackConst.DATE_INSERT + "," +
			" " + RackConst.USER_UPDATE + "," +
			" " + RackConst.DATE_UPDATE + "," +
			" " + RackConst.USER_DRAFT + "," +
			" " + RackConst.DATE_DRAFT + " " +
			"from " + RackConst.TABLE_NAME + " " +
			"where " + RackConst.CODE_SECTOR + "=? " +
			"order by " + RackConst.NAME_RACK;

	public List<Rack> list(final UserContext userContext, final Integer code_sector, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setLong(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<Rack> list = new ArrayList<Rack>();
		while (resultSet.next()) {
			final Rack item = new Rack(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:"+code_sector+")");
		return list;
	}

	public static final String Q_SELECT = "select" +
			" " + RackConst.CODE_RACK + "," +
			" " + RackConst.STATE_RACK + "," +
			" " + RackConst.NAME_RACK + "," +
			" " + RackConst.RACK_BARCODE + "," +
			" " + RackConst.LENGTH + "," +
			" " + RackConst.WIDTH + "," +
			" " + RackConst.HEIGHT + "," +
			" " + RackConst.CODE_SECTOR + "," +
			" " + RackConst.X_COORD + "," +
			" " + RackConst.Y_COORD + "," +
			" " + RackConst.ANGLE + "," +
			" " + RackConst.LOAD_SIDE + "," +
			" " + RackConst.CODE_RACK_TEMPLATE + "," +
			" " + RackConst.LOCK_SIZE + "," +
			" " + RackConst.LOCK_MOVE + "," +
			" " + RackConst.TYPE_RACK + "," +
			" " + RackConst.USER_INSERT + "," +
			" " + RackConst.DATE_INSERT + "," +
			" " + RackConst.USER_UPDATE + "," +
			" " + RackConst.DATE_UPDATE + "," +
			" " + RackConst.USER_DRAFT + "," +
			" " + RackConst.DATE_DRAFT + " " +
			"from " + RackConst.TABLE_NAME + " " +
			"where " + RackConst.CODE_RACK + " = ?";

	public Rack select(final UserContext userContext, final int code_rack, final java.util.Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		Rack rack = null;
		if (resultSet.next()) {
			rack = new Rack(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:"+code_rack+")");
		return rack;
	}

	private static RackHModel instance = new RackHModel();

	public static RackHModel getInstance() {
		return instance;
	}

	private RackHModel() {
	}
}
