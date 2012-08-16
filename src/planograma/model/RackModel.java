package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class RackModel {

	public static final Logger LOG = Logger.getLogger(RackModel.class);

	private static final String Q_SELECT_FROM = "select" +
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
			" " + RackConst.DATE_DRAFT + "," +
			" " + RackConst.REAL_LENGTH + "," +
			" " + RackConst.REAL_WIDTH + "," +
			" " + RackConst.REAL_HEIGHT + "," +
			" " + RackConst.X_OFFSET + "," +
			" " + RackConst.Y_OFFEST + " " +
			"from " + RackConst.TABLE_NAME + " ";

	public static final String Q_LIST = Q_SELECT_FROM +
			"where " + RackConst.CODE_SECTOR + "=? " +
			"order by " + RackConst.NAME_RACK;

	public List<Rack> list(final UserContext userContext, final Integer code_sector) throws SQLException {
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

	public static final String Q_SELECT = Q_SELECT_FROM +
			"where " + RackConst.CODE_RACK + " = ?";

	public Rack select(final UserContext userContext, final int code_rack) throws SQLException {
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

	public static final String Q_INSERT_UPDATE = "{call :new_code_rack := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IURACK(" +
			":mode, " +
			":" + RackConst.CODE_SECTOR + ", " +
			":" + RackConst.CODE_RACK + ", " +
			":" + RackConst.NAME_RACK + ", " +
			":" + RackConst.RACK_BARCODE + ", " +
			":" + RackConst.X_COORD + ", " +
			":" + RackConst.Y_COORD + ", " +
			":" + RackConst.LENGTH + ", " +
			":" + RackConst.WIDTH + ", " +
			":" + RackConst.HEIGHT + ", " +
			":" + RackConst.LOAD_SIDE + ", " +
			":" + RackConst.ANGLE + "," +
			":" + RackConst.CODE_RACK_TEMPLATE + "," +
			":" + RackConst.LOCK_SIZE + "," +
			":" + RackConst.LOCK_MOVE + "," +
			":" + RackConst.TYPE_RACK +"," +
			":" + RackConst.REAL_LENGTH +"," +
			":" + RackConst.REAL_WIDTH +"," +
			":" + RackConst.REAL_HEIGHT +"," +
			":" + RackConst.X_OFFSET +"," +
			":" + RackConst.Y_OFFEST +
			")}";

	public int insert(final UserContext userContext, final Rack rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setInt(RackConst.CODE_SECTOR, rack.getCode_sector());
		callableStatement.setObject(RackConst.CODE_RACK, rack.getCode_rack());
		callableStatement.setString(RackConst.NAME_RACK, rack.getName_rack());
		callableStatement.setString(RackConst.RACK_BARCODE, rack.getRack_barcode());
		callableStatement.setInt(RackConst.X_COORD, rack.getX_coord());
		callableStatement.setInt(RackConst.Y_COORD, rack.getY_coord());
		callableStatement.setInt(RackConst.LENGTH, rack.getLength());
		callableStatement.setInt(RackConst.WIDTH, rack.getWidth());
		callableStatement.setInt(RackConst.HEIGHT, rack.getHeight());
		callableStatement.setString(RackConst.LOAD_SIDE, rack.getLoad_sideAtStr());
		callableStatement.setInt(RackConst.ANGLE, rack.getAngle());
		callableStatement.setObject(RackConst.CODE_RACK_TEMPLATE, rack.getCode_rack_template());
		callableStatement.setString(RackConst.LOCK_SIZE, (rack.isLock_size()) ? "Y" : "N");
		callableStatement.setString(RackConst.LOCK_MOVE, (rack.isLock_move()) ? "Y" : "N");
		callableStatement.setString(RackConst.TYPE_RACK, rack.getType_raceAtStr());
		//TODO
		callableStatement.setInt(RackConst.REAL_LENGTH, rack.getLength());
		callableStatement.setInt(RackConst.REAL_WIDTH, rack.getWidth());
		callableStatement.setInt(RackConst.REAL_HEIGHT, rack.getHeight());
		callableStatement.setInt(RackConst.X_OFFSET, 0);
		callableStatement.setInt(RackConst.Y_OFFEST, 0);

		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_rack");
		rack.setCode_rack(id);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return rack.getCode_rack();
	}

	public void update(final UserContext userContext, final Rack rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setInt(RackConst.CODE_SECTOR, rack.getCode_sector());
		callableStatement.setObject(RackConst.CODE_RACK, rack.getCode_rack());
		callableStatement.setString(RackConst.NAME_RACK, rack.getName_rack());
		callableStatement.setString(RackConst.RACK_BARCODE, rack.getRack_barcode());
		callableStatement.setInt(RackConst.X_COORD, rack.getX_coord());
		callableStatement.setInt(RackConst.Y_COORD, rack.getY_coord());
		callableStatement.setInt(RackConst.LENGTH, rack.getLength());
		callableStatement.setInt(RackConst.WIDTH, rack.getWidth());
		callableStatement.setInt(RackConst.HEIGHT, rack.getHeight());
		callableStatement.setString(RackConst.LOAD_SIDE, rack.getLoad_sideAtStr());
		callableStatement.setInt(RackConst.ANGLE, rack.getAngle());
		callableStatement.setObject(RackConst.CODE_RACK_TEMPLATE, rack.getCode_rack_template());
		callableStatement.setString(RackConst.LOCK_SIZE, (rack.isLock_size()) ? "Y" : "N");
		callableStatement.setString(RackConst.LOCK_MOVE, (rack.isLock_move()) ? "Y" : "N");
		callableStatement.setString(RackConst.TYPE_RACK, rack.getType_raceAtStr());

		//TODO
		callableStatement.setInt(RackConst.REAL_LENGTH, rack.getLength());
		callableStatement.setInt(RackConst.REAL_WIDTH, rack.getWidth());
		callableStatement.setInt(RackConst.REAL_HEIGHT, rack.getHeight());
		callableStatement.setInt(RackConst.X_OFFSET, 0);
		callableStatement.setInt(RackConst.Y_OFFEST, 0);

		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	public static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
			":" + RackConst.CODE_RACK + ", " +
			":" + RackConst.STATE_RACK + ")}";

	public void changestate(final UserContext userContext, final int code_rack, final String state_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(RackConst.CODE_RACK, code_rack);
		callableStatement.setString(RackConst.STATE_RACK, state_rack);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DRack(:" + RackConst.CODE_RACK + ")}";

	public void delete(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackConst.CODE_RACK, code_rack);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:"+code_rack+")");
	}

	private static RackModel instance = new RackModel();

	public static RackModel getInstance() {
		return instance;
	}

	private RackModel() {
	}
}
