package planograma.model.history;

import org.apache.log4j.Logger;
import planograma.constant.data.*;
import planograma.data.RackWares;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 15.06.12
 * Time: 4:38
 * To change this template use File | Settings | File Templates.
 * TODO
 */
public class RackWaresHModel {

	public static final Logger LOG = Logger.getLogger(RackWaresHModel.class);

	private static final String Q_SELECT_FROM = "select " +
			" rw." + RackWaresConst.CODE_RACK + "," +
			" rw." + RackWaresConst.CODE_WARES + "," +
			" rw." + RackWaresConst.CODE_UNIT + "," +
			" rw." + RackWaresConst.CODE_WARES_ON_RACK + "," +
			" rw." + RackWaresConst.TYPE_WARES_ON_RACK + "," +
			" rw." + RackWaresConst.ORDER_NUMBER_ON_RACK + "," +
			" rw." + RackWaresConst.POSITION_X + "," +
			" rw." + RackWaresConst.POSITION_Y + "," +
			" rw." + RackWaresConst.WARES_LENGTH + "," +
			" rw." + RackWaresConst.WARES_WIDTH + "," +
			" rw." + RackWaresConst.WARES_HEIGHT + "," +
			" rw." + RackWaresConst.COUNT_LENGTH_ON_SHELF + "," +
			" rw." + RackWaresConst.USER_INSERT + "," +
			" rw." + RackWaresConst.DATE_INSERT + "," +
			" rw." + RackWaresConst.USER_UPDATE + "," +
			" rw." + RackWaresConst.DATE_UPDATE + "," +
			" wi." + WaresImageConst.CODE_IMAGE + "," +
			" w." + WaresConst.NAME_WARES + "," +
			" ud." + UnitDimensionConst.ABR_UNIT + "," +
			" au." + AdditionUnitConst.BAR_CODE + " " +
			"from " + RackWaresConst.TABLE_NAME + " rw" +
			" join " + WaresConst.TABLE_NAME + " w on w." + WaresConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " " +
			" join " + UnitDimensionConst.TABLE_NAME + " ud on ud." + UnitDimensionConst.CODE_UNIT + " = rw." + RackWaresConst.CODE_UNIT + " " +
			" join " + AdditionUnitConst.TABLE_NAME + " au on au." + AdditionUnitConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " and au." + AdditionUnitConst.CODE_UNIT + " = rw." + RackWaresConst.CODE_UNIT + " " +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " ";

	public static final String Q_LIST = Q_SELECT_FROM +
			"where " + RackWaresConst.CODE_RACK + " = ? " +
			"order by " + RackWaresConst.ORDER_NUMBER_ON_RACK;

	public List<RackWares> list(final UserContext userContext, final int code_rack, final Date date) throws SQLException {
		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackWares> list = new ArrayList<RackWares>();
		while (resultSet.next()) {
			final RackWares item = new RackWares(resultSet);
			list.add(item);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms (code_rack:"+code_rack+")");
		return list;
	}

	private static RackWaresHModel instance = new RackWaresHModel();

	public static RackWaresHModel getInstance() {
		return instance;
	}

	private RackWaresHModel() {
	}
}
