package planograma.model;

import planograma.constant.data.RackConst;
import planograma.constant.data.RackShelfConst;
import planograma.constant.data.SectorConst;
import planograma.constant.data.StateAllConst;
import planograma.data.*;

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
	public static final String Q_LIST = "select * " +
			"from " + StateAllConst.TABLE_NAME + " " +
			"where " + StateAllConst.PART_STATE + " = ?";

	public List<StateAll> list(final UserContext userContext, int part_state) throws SQLException {
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, part_state);
		final ResultSet resultSet = ps.executeQuery();
		final List<StateAll> list = new ArrayList<StateAll>();
		while (resultSet.next()) {
			final StateAll item = new StateAll(resultSet);
			list.add(item);
		}
		return list;
	}

	public static final String Q_INIT = "select * " +
			"from " + StateAllConst.TABLE_NAME + " " +
			"where " + StateAllConst.PART_STATE + " in (?,?,?,?,?) " +
			"order by " + StateAllConst.PART_STATE;

	public void initEnum(final UserContext userContext) throws SQLException {
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_INIT);
		ps.setInt(1, RackConst.STATE_ALL_PART_STATE_STATE_RACK);		// -63 STATE_RACK
		ps.setInt(2, RackConst.STATE_ALL_PART_STATE_LOAD_SIDE);			// -64 LOAD_SIDE
		ps.setInt(3, SectorConst.STATE_ALL_PART_STATE_STATE_SECTOR);	// -65 STATE_SECTOR
		ps.setInt(4, RackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF);	// -67 TYPE_SHELF
		ps.setInt(5, RackConst.STATE_ALL_PART_STATE_TYPE_RACK);			// -68 TYPE_RACK
		final ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			final StateAll item = new StateAll(resultSet);
			switch (item.getPart_state()) {
				case SectorConst.STATE_ALL_PART_STATE_STATE_SECTOR:
					final StateSector stateSector = StateSector.valueOf(item.getAbr_state());
					stateSector.setDesc(item.getState());
					break;
				case RackConst.STATE_ALL_PART_STATE_STATE_RACK:
					final StateRack stateRack = StateRack.valueOf(item.getAbr_state());
					stateRack.setDesc(item.getState());
					break;
				case RackConst.STATE_ALL_PART_STATE_LOAD_SIDE:
					final LoadSide loadSide= LoadSide.valueOf(item.getAbr_state());
					loadSide.setDesc(item.getState());
					break;
				case RackConst.STATE_ALL_PART_STATE_TYPE_RACK:
					final TypeRack typeRack= TypeRack.valueOf(item.getAbr_state());
					typeRack.setDesc(item.getState());
					typeRack.setColor(item.getDescription());
					break;
				case RackShelfConst.STATE_ALL_PART_STATE_TYPE_SHELF:
					final TypeShelf typeShelf= TypeShelf.valueOf(item.getAbr_state());
					typeShelf.setDesc(item.getState());
					typeShelf.setColor(item.getDescription());
					break;
			}
		}
	}

	private static StateAllModel instance = new StateAllModel();

	public static StateAllModel getInstance() {
		return instance;
	}

	private StateAllModel() {
	}
}
