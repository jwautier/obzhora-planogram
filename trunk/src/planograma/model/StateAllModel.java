package planograma.model;

import planograma.constant.data.StateAllConst;
import planograma.data.StateAll;
import planograma.data.UserContext;
import planograma.data.wrapper.StateAllWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 20.03.12
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
public class StateAllModel {  
	public static final String Q_LIST ="select * "+
			"from " + StateAllConst.TABLE_NAME + " " +
			"where " + StateAllConst.PART_STATE + " = ?"; 
	public List<StateAll> list(final UserContext userContext, int part_state) throws SQLException {
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, part_state);
		final ResultSet resultSet = ps.executeQuery();
		final List<StateAll> list = new ArrayList<StateAll>();
		while (resultSet.next()) {
			final StateAll shop = new StateAll(resultSet);
			list.add(shop);
		}
		return list;
	}

	private static StateAllModel instance = new StateAllModel();

	public static StateAllModel getInstance() {
		return instance;
	}

	private StateAllModel() {
	}
}
