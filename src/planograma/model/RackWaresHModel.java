package planograma.model;

import org.apache.log4j.Logger;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 4:38
 * To change this template use File | Settings | File Templates.
 */
public class RackWaresHModel {

	public static final Logger LOG = Logger.getLogger(RackWaresHModel.class);

	private static final String Q_NEXT_VERSION = "SELECT EUGENE_SAZ.SEV_GEN_VERSION_WARES_ON_RACK.NEXTVAL FROM dual";

	public int nextVersion(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_NEXT_VERSION);
		final ResultSet resultSet = ps.executeQuery();
		int next_version = 0;
		if (resultSet.next()) {
			next_version = resultSet.getInt(1);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return next_version;
	}

	private static RackWaresHModel instance = new RackWaresHModel();

	public static RackWaresHModel getInstance() {
		return instance;
	}

	private RackWaresHModel() {
	}
}
