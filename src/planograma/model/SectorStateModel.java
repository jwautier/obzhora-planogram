package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.SectorStateConst;
import planograma.data.UserContext;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: poljakov
 * Date: 19.10.12
 * Time: 17:13
 */
public class SectorStateModel {

	public static final Logger LOG = Logger.getLogger(SectorModel.class);

	private static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATESECTOR(" +
			":" + SectorStateConst.CODE_SECTOR + ", " +
			":" + SectorStateConst.STATE_SECTOR + ")}";

	public void changestate(final UserContext userContext, final int code_sector, final String state_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(SectorStateConst.CODE_SECTOR, code_sector);
		callableStatement.setString(SectorStateConst.STATE_SECTOR, state_sector);
		callableStatement.execute();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

	private static SectorStateModel instance = new SectorStateModel();

	public static SectorStateModel getInstance() {
		return instance;
	}

	private SectorStateModel() {
	}
}
