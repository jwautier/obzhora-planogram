package planograma.model;

import planograma.constant.data.AdditionUnitConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresImageConst;
import planograma.data.UserContext;
import planograma.data.wrapper.WaresWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 5:20
 * To change this template use File | Settings | File Templates.
 */
public class WaresModel {

	private static final String Q_LIST = "select" +
			" w." + WaresConst.CODE_WARES + "," +
			" u." + AdditionUnitConst.CODE_UNIT + "," +
			" w." + WaresConst.ARTICL + "," +
			" w." + WaresConst.NAME_WARES + "," +
			" u." + AdditionUnitConst.LENGTH + "," +
			" u." + AdditionUnitConst.WIDTH + "," +
			" u." + AdditionUnitConst.HEIGHT + "," +
			" u." + AdditionUnitConst.BAR_CODE + "," +
			" wi."+WaresImageConst.CODE_IMAGE+" " +
			"from" +
			" " + AdditionUnitConst.TABLE_NAME + " u" +
			" join " + WaresConst.TABLE_NAME + " w on w."+ WaresConst.CODE_WARES +" = u." + AdditionUnitConst.CODE_WARES +
			" left join " + WaresImageConst.TABLE_NAME + " wi on wi."+WaresImageConst.CODE_WARES+" = w."+ WaresConst.CODE_WARES+" " +
			"where" +
			" u."+ AdditionUnitConst.LENGTH +">0" +
			" and u."+ AdditionUnitConst.WIDTH + ">0" +
			" and u." + AdditionUnitConst.HEIGHT +">0 " +
			"order by w."+ WaresConst.NAME_WARES;

	public List<WaresWrapper> list(final UserContext userContext, Integer code_group) throws SQLException {
//		long time = System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		//TODO
		final ResultSet resultSet = ps.executeQuery();
		final List<WaresWrapper> list = new ArrayList<WaresWrapper>();
		while (resultSet.next()) {
			final WaresWrapper item = new WaresWrapper(resultSet);
			list.add(item);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return list;
	}

	private static WaresModel instance = new WaresModel();

	public static WaresModel getInstance() {
		return instance;
	}

	private WaresModel() {
	}
}
