package planograma.model;

import org.apache.log4j.Logger;
import planograma.constant.data.SectorHConst;
import planograma.data.UserContext;
import planograma.data.wrapper.SectorHVersionWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 5:52
 * To change this template use File | Settings | File Templates.
 */
public class SectorHModel {

	public static final Logger LOG = Logger.getLogger(SectorHModel.class);

	public static final String Q_LIST = "select" +
			" " + SectorHConst.VERSION + "," +
			" " + SectorHConst.DATE_INSERT + "," +
			" " + SectorHConst.USER_INSERT + "," +
			" ADM.ADM_GET_NAME_USER (" + SectorHConst.USER_INSERT + ") user_fullname " +
			"from " + SectorHConst.TABLE_NAME + " " +
			"where " + SectorHConst.CODE_SECTOR + "=? " +
			"order by " + SectorHConst.DATE_INSERT + " desc";

	public List<SectorHVersionWrapper> list(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<SectorHVersionWrapper> list = new ArrayList<SectorHVersionWrapper>();
		while (resultSet.next()) {
			final SectorHVersionWrapper item = new SectorHVersionWrapper(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static SectorHModel instance = new SectorHModel();

	public static SectorHModel getInstance() {
		return instance;
	}

	private SectorHModel() {
	}
}
