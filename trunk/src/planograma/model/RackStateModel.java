package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.constant.data.RackStateInSectorConst;
import planograma.data.EStateRack;
import planograma.data.RackState;
import planograma.data.RackStateInSector;
import planograma.data.UserContext;

import java.sql.*;

/**
 * User: poljakov
 * Date: 19.10.12
 * Time: 16:55
 */
public class RackStateModel {

	private static final Logger LOG = Logger.getLogger(RackStateModel.class);

	private static final String Q_SELECT_FROM_RACK_STATE = "SELECT" +
			" " + RackStateConst.CODE_RACK + "," +
			" " + RackStateConst.STATE_RACK + "," +
			" " + RackStateConst.DATE_DRAFT + "," +
			" " + RackStateConst.USER_DRAFT + "," +
			" " + RackStateConst.DATE_ACTIVE + "," +
			" " + RackStateConst.USER_ACTIVE + "," +
			" " + RackStateConst.DATE_COMPLETE + "," +
			" " + RackStateConst.USER_COMPLETE + " " +
			"FROM " + RackStateConst.TABLE_NAME + " ";


	private static final String Q_SELECT_RACK_STATE = Q_SELECT_FROM_RACK_STATE +
			"WHERE " + RackStateConst.CODE_RACK + " = ?";

	public RackState selectRackState(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT_RACK_STATE);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		RackState rack = null;
		if (resultSet.next()) {
			rack = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return rack;
	}


	private static final String Q_SELECT_FROM_RACK_STATE_IN_SECTOR = "SELECT" +
			" " + RackStateInSectorConst.CODE_RACK + "," +
			" " + RackStateInSectorConst.STATE_RACK + "," +
			" " + RackStateInSectorConst.DATE_DRAFT + "," +
			" " + RackStateInSectorConst.USER_DRAFT + "," +
			" " + RackStateInSectorConst.DATE_ACTIVE + "," +
			" " + RackStateInSectorConst.USER_ACTIVE + "," +
			" " + RackStateInSectorConst.DATE_COMPLETE + "," +
			" " + RackStateInSectorConst.USER_COMPLETE + " " +
			"FROM " + RackStateInSectorConst.TABLE_NAME + " ";


	private static final String Q_SELECT_RACK_STATE_IN_SECTOR = Q_SELECT_FROM_RACK_STATE_IN_SECTOR +
			"WHERE " + RackStateInSectorConst.CODE_RACK + " = ?";

	public RackStateInSector selectRackStateInSector(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT_RACK_STATE_IN_SECTOR);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		RackStateInSector rack = null;
		if (resultSet.next()) {
			rack = new RackStateInSector(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ")");
		return rack;
	}

	private static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
			":" + RackStateConst.CODE_RACK + ", " +
			":rack_state, " +
			":rack_state_in_sector)}";

	public void changestate(final UserContext userContext, final int code_rack, final EStateRack state_rack_in_sector, final EStateRack state_rack) throws SQLException {
		long time = System.currentTimeMillis();
		if (state_rack_in_sector != null || state_rack != null) {
			final Connection connection = userContext.getConnection();
			final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
			callableStatement.setInt(RackStateConst.CODE_RACK, code_rack);
			callableStatement.setString("rack_state", (state_rack != null) ? state_rack.name() : null);
			callableStatement.setString("rack_state_in_sector", (state_rack_in_sector != null) ? state_rack_in_sector.name() : null);
			callableStatement.execute();
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static RackStateModel instance = new RackStateModel();

	public static RackStateModel getInstance() {
		return instance;
	}

	private RackStateModel() {
	}
}
