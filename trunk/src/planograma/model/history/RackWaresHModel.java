package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.*;
import planograma.constant.data.history.RackWaresHConst;
import planograma.data.RackWares;
import planograma.data.UserContext;
import planograma.utils.FormattingUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 4:38
 * To change this template use File | Settings | File Templates.
 */
public class RackWaresHModel {

	private static final Logger LOG = Logger.getLogger(RackWaresHModel.class);

	private static final String Q_LIST = "select " +
			" rw." + RackWaresHConst.CODE_RACK + "," +
			" rw." + RackWaresHConst.CODE_WARES + "," +
			" rw." + RackWaresHConst.CODE_UNIT + "," +
			" rw." + RackWaresHConst.CODE_WARES_ON_RACK + "," +
			" rw." + RackWaresHConst.TYPE_WARES_ON_RACK + "," +
			" rw." + RackWaresHConst.ORDER_NUMBER_ON_RACK + "," +
			" rw." + RackWaresHConst.POSITION_X + "," +
			" rw." + RackWaresHConst.POSITION_Y + "," +
			" rw." + RackWaresHConst.WARES_LENGTH + "," +
			" rw." + RackWaresHConst.WARES_WIDTH + "," +
			" rw." + RackWaresHConst.WARES_HEIGHT + "," +
			" rw." + RackWaresHConst.COUNT_LENGTH_ON_SHELF + "," +
			" rw." + RackWaresHConst.USER_INSERT + "," +
			" rw." + RackWaresHConst.DATE_INSERT + "," +
			" rw." + RackWaresHConst.USER_INSERT + " " + RackWaresConst.USER_UPDATE + "," +
			" rw." + RackWaresHConst.DATE_INSERT + " " + RackWaresConst.DATE_UPDATE + "," +
			" wi." + WaresImageConst.CODE_IMAGE + "," +
			" w." + WaresConst.NAME_WARES + "," +
			" ud." + UnitDimensionConst.ABR_UNIT + "," +
			" au." + AdditionUnitConst.BAR_CODE + " " +
			"from " + RackWaresHConst.TABLE_NAME + " rw" +
			" join " + WaresConst.TABLE_NAME + " w on w." + WaresConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " " +
			" join " + UnitDimensionConst.TABLE_NAME + " ud on ud." + UnitDimensionConst.CODE_UNIT + " = rw." + RackWaresHConst.CODE_UNIT + " " +
			" join " + AdditionUnitConst.TABLE_NAME + " au on au." + AdditionUnitConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " and au." + AdditionUnitConst.CODE_UNIT + " = rw." + RackWaresHConst.CODE_UNIT + " " +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresHConst.CODE_WARES + " " +
			"where" +
			" rw." + RackWaresHConst.CODE_RACK + "=?" +
			" and rw." + RackWaresHConst.TYPE_OPERATION + " <> 'D'" +
			" and" +
			"  rw." + RackWaresHConst.DATE_INSERT + " = " +
			"  (" +
			"   select max(ss1." + RackWaresHConst.DATE_INSERT + ") " +
			"   from " + RackWaresHConst.TABLE_NAME + " ss1" +
			"   where " +
			"    ss1." + RackWaresHConst.CODE_RACK + "=?" +
			"    and ss1." + RackWaresHConst.DATE_INSERT + "<=?" +
			"  )" +
			"order by rw." + RackWaresHConst.ORDER_NUMBER_ON_RACK;

	public List<RackWares> list(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		ps.setInt(2, code_rack);
		ps.setTimestamp(3, new Timestamp(date.getTime()));
		final ResultSet resultSet = ps.executeQuery();
		final List<RackWares> list = new ArrayList<RackWares>();
		while (resultSet.next()) {
			final RackWares item = new RackWares(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:" + code_rack + ", date:" + FormattingUtils.datetime2String(date) + ")");
		return list;
	}

	private static RackWaresHModel instance = new RackWaresHModel();

	public static RackWaresHModel getInstance() {
		return instance;
	}

	private RackWaresHModel() {
	}
}
