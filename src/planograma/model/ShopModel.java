package planograma.model;

import planograma.constant.data.ShopConst;
import planograma.data.Shop;
import planograma.data.UserContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.03.12
 * Time: 2:29
 * To change this template use File | Settings | File Templates.
 */
public class ShopModel {

	private static final String Q_LIST = "select" +
			" " + ShopConst.CODE_SHOP + "," +
			" " + ShopConst.NAME_SHOP + " " +
			"from " + ShopConst.TABLE_NAME + " " +
			"order by " + ShopConst.NAME_SHOP;

	public List<Shop> list(final UserContext userContext) throws SQLException {
//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		final ResultSet resultSet = ps.executeQuery();
		final List<Shop> list = new ArrayList<Shop>();
		while (resultSet.next()) {
			final Shop item = new Shop(resultSet);
			list.add(item);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return list;
	}

	private static final String Q_SELECT = "select" +
			" " + ShopConst.CODE_SHOP + "," +
			" " + ShopConst.NAME_SHOP + " " +
			"from " + ShopConst.TABLE_NAME + " " +
			"where " + ShopConst.CODE_SHOP + "=?";

	public Shop select(final UserContext userContext, final int code_shop) throws SQLException {
//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_shop);
		final ResultSet resultSet = ps.executeQuery();
		Shop shop = null;
		if (resultSet.next()) {
			shop = new Shop(resultSet);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return shop;
	}

	private static ShopModel instance = new ShopModel();

	public static ShopModel getInstance() {
		return instance;
	}

	private ShopModel() {
	}
}
