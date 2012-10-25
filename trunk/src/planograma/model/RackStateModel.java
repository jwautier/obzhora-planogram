package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.data.RackState;
import planograma.data.UserContext;

import java.sql.*;

/**
 * User: poljakov
 * Date: 19.10.12
 * Time: 16:55
 */
public class RackStateModel {

	private static final Logger LOG = Logger.getLogger(RackStateModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + RackStateConst.CODE_RACK + "," +
			" " + RackStateConst.STATE_RACK + "," +
			" " + RackStateConst.DATE_DRAFT + "," +
			" " + RackStateConst.USER_DRAFT + "," +
			" " + RackStateConst.DATE_ACTIVE + "," +
			" " + RackStateConst.USER_ACTIVE + "," +
			" " + RackStateConst.DATE_COMPLETE + "," +
			" " + RackStateConst.USER_COMPLETE + " " +
			"from " + RackStateConst.TABLE_NAME + " ";


	private static final String Q_SELECT = Q_SELECT_FROM +
			"where " + RackStateConst.CODE_RACK + " = ?";

	public RackState select(final UserContext userContext, final int code_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		RackState rack = null;
		if (resultSet.next()) {
			rack = new RackState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:"+code_rack+")");
		return rack;
	}

	private static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
			":" + RackStateConst.CODE_RACK + ", " +
			":" + RackStateConst.STATE_RACK + ")}";

	public void changestate(final UserContext userContext, final int code_rack, final String state_rack) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(RackStateConst.CODE_RACK, code_rack);
		callableStatement.setString(RackStateConst.STATE_RACK, state_rack);
		callableStatement.execute();
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
