package planograma.model;

import planograma.constant.data.RackWaresConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresImageConst;
import planograma.data.RackWares;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 06.05.12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class RackWaresModel {
	public static final String Q_LIST = "select " +
			" rw."+RackWaresConst.CODE_RACK + ","+
			" rw."+RackWaresConst.CODE_WARES + ","+
			" rw."+RackWaresConst.CODE_UNIT + ","+
			" rw."+RackWaresConst.CODE_WARES_ON_SHELH + ","+
			" rw."+RackWaresConst.ORDER_NUMBER_SHELH + ","+
			" rw."+RackWaresConst.ORDER_NUMBER_ON_SHELH + ","+
			" rw."+RackWaresConst.POSITION_X + ","+
			" rw."+RackWaresConst.POSITION_Y + ","+
			" rw."+RackWaresConst.WARES_LENGTH + ","+
			" rw."+RackWaresConst.WARES_WIDTH + ","+
			" rw."+RackWaresConst.WARES_HEIGHT + ","+
			" rw."+RackWaresConst.COUNT_LENGTH_ON_SHELF + ","+
			" rw."+RackWaresConst.USER_INSERT + ","+
			" rw."+RackWaresConst.DATE_INSERT + ","+
			" rw."+RackWaresConst.USER_UPDATE + ","+
			" rw."+RackWaresConst.DATE_UPDATE + ","+
			" wi."+WaresImageConst.CODE_IMAGE + ","+
			"from " + RackWaresConst.TABLE_NAME + " rw" +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " " +
			"where " + RackWaresConst.CODE_RACK + " = ?";

	public List<RackWares> list(final UserContext userContext, final int code_rack) throws SQLException {
//		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_rack);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackWares> list = new ArrayList<RackWares>();
		while (resultSet.next()) {
			final RackWares item = new RackWares(resultSet);
			list.add(item);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return list;
	}

	public static final String Q_SELECT = "select " +
			" rw."+RackWaresConst.CODE_RACK + ","+
			" rw."+RackWaresConst.CODE_WARES + ","+
			" rw."+RackWaresConst.CODE_UNIT + ","+
			" rw."+RackWaresConst.CODE_WARES_ON_SHELH + ","+
			" rw."+RackWaresConst.ORDER_NUMBER_SHELH + ","+
			" rw."+RackWaresConst.ORDER_NUMBER_ON_SHELH + ","+
			" rw."+RackWaresConst.POSITION_X + ","+
			" rw."+RackWaresConst.POSITION_Y + ","+
			" rw."+RackWaresConst.WARES_LENGTH + ","+
			" rw."+RackWaresConst.WARES_WIDTH + ","+
			" rw."+RackWaresConst.WARES_HEIGHT + ","+
			" rw."+RackWaresConst.COUNT_LENGTH_ON_SHELF + ","+
			" rw."+RackWaresConst.USER_INSERT + ","+
			" rw."+RackWaresConst.DATE_INSERT + ","+
			" rw."+RackWaresConst.USER_UPDATE + ","+
			" rw."+RackWaresConst.DATE_UPDATE + ","+
			" wi."+WaresImageConst.CODE_IMAGE + ","+
			"from " + RackWaresConst.TABLE_NAME + " rw" +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi." + WaresImageConst.CODE_WARES + " = rw." + RackWaresConst.CODE_WARES + " " +
			"where " + RackWaresConst.CODE_WARES_ON_SHELH + " = ?";

	public RackWares select(final UserContext userContext, final int rackShelf) throws SQLException {
//		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, rackShelf);
		final ResultSet resultSet = ps.executeQuery();
		RackWares item = null;
		if (resultSet.next()) {
			item = new RackWares(resultSet);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return item;
	}
	// TODO insert update delete

	private static RackWaresModel instance = new RackWaresModel();

	public static RackWaresModel getInstance() {
		return instance;
	}

	private RackWaresModel() {
	}
}
