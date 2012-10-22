package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.RackStateConst;
import planograma.data.UserContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: poljakov
 * Date: 19.10.12
 * Time: 16:55
 */
public class RackStateModel {

	public static final Logger LOG = Logger.getLogger(RackStateModel.class);

	public static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
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
