package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.RackConst;
import planograma.constant.data.history.RackHConst;
import planograma.constant.data.history.RackShelfHConst;
import planograma.constant.data.history.RackWaresHConst;
import planograma.constant.data.history.SectorHConst;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: poljakov
 * Date: 08.08.12
 * Time: 11:19
 */
public class HistoryModel {

	public static final Logger LOG = Logger.getLogger(HistoryModel.class);

	private final String Q_HISTORYMARK =
			" (select " + SectorHConst.DATE_INSERT + " from " + SectorHConst.TABLE_NAME + ") " +
					"union" +
					" (select " + RackHConst.DATE_INSERT + " from " + RackHConst.TABLE_NAME + ") " +
					"union" +
					" (select " + RackShelfHConst.DATE_INSERT + " from " + RackShelfHConst.TABLE_NAME + ") " +
					"union" +
					" (select " + RackWaresHConst.DATE_INSERT + " from " + RackWaresHConst.TABLE_NAME + ") " +
					"order by 1";

	private final String Q_HISTORYMARK_FOR_SECTOR =
			" (select " + SectorHConst.DATE_INSERT + " from " + SectorHConst.TABLE_NAME + " where " + SectorHConst.CODE_SECTOR + "=?) " +
					"union" +
					" (select " + RackHConst.DATE_INSERT + " from " + RackHConst.TABLE_NAME + " where " + RackHConst.CODE_SECTOR + "=?) " +
					"union" +
					" (select " + RackShelfHConst.DATE_INSERT + " from " + RackShelfHConst.TABLE_NAME + " where " + RackShelfHConst.CODE_RACK + " in (select ss1." + RackConst.CODE_RACK + " from " + RackConst.TABLE_NAME + " ss1 where ss1." + RackConst.CODE_SECTOR + "=?)) " +
					"union" +
					" (select " + RackWaresHConst.DATE_INSERT + " from " + RackWaresHConst.TABLE_NAME + " where " + RackWaresHConst.CODE_RACK + " in (select ss1." + RackConst.CODE_RACK + " from " + RackConst.TABLE_NAME + " ss1 where ss1." + RackConst.CODE_SECTOR + "=?)) " +
					"order by 1";

	private final String Q_HISTORYMARK_FOR_RACK =
			" (select " + RackHConst.DATE_INSERT + " from " + RackHConst.TABLE_NAME + " where " + RackHConst.CODE_RACK + "=?) " +
					"union" +
					" (select " + RackShelfHConst.DATE_INSERT + " from " + RackShelfHConst.TABLE_NAME + " where " + RackShelfHConst.CODE_RACK + " =?) " +
					"union" +
					" (select " + RackWaresHConst.DATE_INSERT + " from " + RackWaresHConst.TABLE_NAME + " where " + RackWaresHConst.CODE_RACK + " =?) " +
					"order by 1";

	public List<Date> getHistoryMark(final UserContext userContext) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_HISTORYMARK);
		final ResultSet resultSet = ps.executeQuery();
		final List<Date> list = new ArrayList<Date>();
		while (resultSet.next()) {
			final Date date = resultSet.getTimestamp(1);
			list.add(date);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	public List<Date> getHistoryMarkForSector(final UserContext userContext, final int code_sector) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_HISTORYMARK_FOR_SECTOR);
		ps.setInt(1, code_sector);
		ps.setInt(2, code_sector);
		ps.setInt(3, code_sector);
		ps.setInt(4, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		final List<Date> list = new ArrayList<Date>();
		while (resultSet.next()) {
			final Date date = resultSet.getTimestamp(1);
			list.add(date);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	public List<Date> getHistoryMarkForRack(final UserContext userContext, final int code_race) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_HISTORYMARK_FOR_RACK);
		ps.setInt(1, code_race);
		ps.setInt(2, code_race);
		ps.setInt(3, code_race);
		final ResultSet resultSet = ps.executeQuery();
		final List<Date> list = new ArrayList<Date>();
		while (resultSet.next()) {
			final Date date = resultSet.getTimestamp(1);
			list.add(date);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return list;
	}

	private static HistoryModel instance = new HistoryModel();

	public static HistoryModel getInstance() {
		return instance;
	}

	private HistoryModel() {
	}
}
