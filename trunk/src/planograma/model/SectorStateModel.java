package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.SectorStateConst;
import planograma.data.SectorState;
import planograma.data.StateSector;
import planograma.data.UserContext;

import java.sql.*;

/**
 * User: poljakov
 * Date: 19.10.12
 * Time: 17:13
 */
public class SectorStateModel {

	private static final Logger LOG = Logger.getLogger(SectorModel.class);

	private static final String Q_SELECT_FROM = "select" +
			" " + SectorStateConst.CODE_SECTOR + "," +
			" " + SectorStateConst.STATE_SECTOR + "," +
			" " + SectorStateConst.USER_DRAFT + "," +
			" " + SectorStateConst.DATE_DRAFT + "," +
			" " + SectorStateConst.USER_ACTIVE + "," +
			" " + SectorStateConst.DATE_ACTIVE + "," +
			" " + SectorStateConst.USER_COMPLETE + "," +
			" " + SectorStateConst.DATE_COMPLETE + " " +
			"from " + SectorStateConst.TABLE_NAME;

	private static final String Q_SELECT = Q_SELECT_FROM +
			" where " + SectorStateConst.CODE_SECTOR + " = ?";

	public SectorState select(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		SectorState sectorState = null;
		if (resultSet.next()) {
			sectorState = new SectorState(resultSet);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_sector:" + code_sector + ")");
		return sectorState;
	}

	private static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATESECTOR(" +
			":" + SectorStateConst.CODE_SECTOR + ", " +
			":" + SectorStateConst.STATE_SECTOR + ")}";

	public void changestate(final UserContext userContext, final int code_sector, final StateSector state_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(SectorStateConst.CODE_SECTOR, code_sector);
		callableStatement.setString(SectorStateConst.STATE_SECTOR, state_sector.name());
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
