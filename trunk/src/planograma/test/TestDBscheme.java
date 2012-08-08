package planograma.test;

import planograma.constant.data.*;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * User: poljakov
 * Date: 08.08.12
 * Time: 8:43
 */
public class TestDBscheme {

	private static final String Q_TEST_COLUMN = "select column_name from ALL_TAB_COLUMNS where owner||'.'||table_name=? order by column_name";

	public static void main(String args[]) throws SQLException {
		UserContext userContext = null;
		try {
			userContext = new UserContext("", "");
			final Connection connection = userContext.getConnection();
			final PreparedStatement psColumns = connection.prepareStatement(Q_TEST_COLUMN);

			// основные таблицы
			checkTable(psColumns, SectorConst.TABLE_NAME,
					SectorConst.CODE_SHOP,
					SectorConst.CODE_SECTOR,
					SectorConst.STATE_SECTOR,
					SectorConst.NAME_SECTOR,
					SectorConst.LENGTH,
					SectorConst.WIDTH,
					SectorConst.HEIGHT,
					SectorConst.USER_INSERT,
					SectorConst.DATE_INSERT,
					SectorConst.USER_UPDATE,
					SectorConst.DATE_UPDATE,
					SectorConst.USER_DRAFT,
					SectorConst.DATE_DRAFT);
			checkTable(psColumns, RackConst.TABLE_NAME,
					RackConst.CODE_SECTOR,
					RackConst.CODE_RACK,
					RackConst.NAME_RACK,
					RackConst.RACK_BARCODE,
					RackConst.X_COORD,
					RackConst.Y_COORD,
					RackConst.LENGTH,
					RackConst.WIDTH,
					RackConst.HEIGHT,
					RackConst.LOAD_SIDE,
					RackConst.USER_INSERT,
					RackConst.DATE_INSERT,
					RackConst.USER_UPDATE,
					RackConst.DATE_UPDATE,
					RackConst.STATE_RACK,
					RackConst.ANGLE,
					RackConst.CODE_RACK_TEMPLATE,
					RackConst.DATE_DRAFT,
					RackConst.USER_DRAFT,
					RackConst.LOCK_SIZE,
					RackConst.LOCK_MOVE,
					RackConst.TYPE_RACK);
			checkTable(psColumns, RackShelfConst.TABLE_NAME,
					RackShelfConst.CODE_RACK,
					RackShelfConst.CODE_SHELF,
					RackShelfConst.X_COORD,
					RackShelfConst.Y_COORD,
					RackShelfConst.SHELF_HEIGHT,
					RackShelfConst.SHELF_WIDTH,
					RackShelfConst.SHELF_LENGTH,
					RackShelfConst.ANGLE,
					RackShelfConst.TYPE_SHELF,
					RackShelfConst.USER_INSERT,
					RackShelfConst.DATE_INSERT,
					RackShelfConst.USER_UPDATE,
					RackShelfConst.DATE_UPDATE
			);
			checkTable(psColumns, RackWaresConst.TABLE_NAME,
					RackWaresConst.CODE_RACK,
					RackWaresConst.CODE_WARES,
					RackWaresConst.CODE_UNIT,
					RackWaresConst.CODE_WARES_ON_RACK,
					RackWaresConst.TYPE_WARES_ON_RACK,
					RackWaresConst.ORDER_NUMBER_ON_RACK,
					RackWaresConst.POSITION_X,
					RackWaresConst.POSITION_Y,
					RackWaresConst.WARES_LENGTH,
					RackWaresConst.WARES_WIDTH,
					RackWaresConst.WARES_HEIGHT,
					RackWaresConst.COUNT_LENGTH_ON_SHELF,
					RackWaresConst.USER_INSERT,
					RackWaresConst.DATE_INSERT,
					RackWaresConst.USER_UPDATE,
					RackWaresConst.DATE_UPDATE
			);
			checkTable(psColumns, RackTemplateConst.TABLE_NAME,
					RackTemplateConst.CODE_RACK_TEMPLATE,
					RackTemplateConst.NAME_RACK_TEMPLATE,
					RackTemplateConst.LENGTH,
					RackTemplateConst.WIDTH,
					RackTemplateConst.HEIGHT,
					RackTemplateConst.LOAD_SIDE,
					RackTemplateConst.STATE_RACK_TEMPLATE,
					RackTemplateConst.USER_INSERT,
					RackTemplateConst.DATE_INSERT,
					RackTemplateConst.USER_UPDATE,
					RackTemplateConst.DATE_UPDATE,
					RackTemplateConst.DATE_DRAFT,
					RackTemplateConst.USER_DRAFT
			);
			checkTable(psColumns, RackShelfTemplateConst.TABLE_NAME,
					RackShelfTemplateConst.CODE_RACK_TEMPLATE,
					RackShelfTemplateConst.CODE_SHELF_TEMPLATE,
					RackShelfTemplateConst.X_COORD,
					RackShelfTemplateConst.Y_COORD,
					RackShelfTemplateConst.SHELF_HEIGHT,
					RackShelfTemplateConst.SHELF_WIDTH,
					RackShelfTemplateConst.SHELF_LENGTH,
					RackShelfTemplateConst.ANGLE,
					RackShelfTemplateConst.TYPE_SHELF,
					RackShelfTemplateConst.USER_INSERT,
					RackShelfTemplateConst.DATE_INSERT,
					RackShelfTemplateConst.USER_UPDATE,
					RackShelfTemplateConst.DATE_UPDATE
			);

			// таблицы истории
			checkTable(psColumns, SectorHConst.TABLE_NAME,
					SectorHConst.CODE_SHOP,
					SectorHConst.CODE_SECTOR,
					SectorHConst.NAME_SECTOR,
					SectorHConst.LENGTH,
					SectorHConst.WIDTH,
					SectorHConst.HEIGHT,
					SectorHConst.STATE_SECTOR,
					SectorHConst.USER_INSERT,
					SectorHConst.DATE_INSERT,
					SectorHConst.TYPE_OPERATION,
					SectorHConst.USER_DRAFT,
					SectorHConst.DATE_DRAFT);
			checkTable(psColumns, RackHConst.TABLE_NAME,
					RackHConst.CODE_SECTOR,
					RackHConst.CODE_RACK,
					RackHConst.NAME_RACK,
					RackHConst.RACK_BARCODE,
					RackHConst.X_COORD,
					RackHConst.Y_COORD,
					RackHConst.LENGTH,
					RackHConst.WIDTH,
					RackHConst.HEIGHT,
					RackHConst.LOAD_SIDE,
					RackHConst.USER_INSERT,
					RackHConst.DATE_INSERT,
					RackHConst.STATE_RACK,
					RackHConst.ANGLE,
					RackHConst.CODE_RACK_TEMPLATE,
					RackHConst.DATE_DRAFT,
					RackHConst.USER_DRAFT,
					RackHConst.LOCK_SIZE,
					RackHConst.LOCK_MOVE,
					RackHConst.TYPE_RACK,
					RackHConst.TYPE_OPERATION);
			checkTable(psColumns, RackShelfHConst.TABLE_NAME,
					RackShelfHConst.CODE_RACK,
					RackShelfHConst.CODE_SHELF,
					RackShelfHConst.X_COORD,
					RackShelfHConst.Y_COORD,
					RackShelfHConst.SHELF_HEIGHT,
					RackShelfHConst.SHELF_WIDTH,
					RackShelfHConst.SHELF_LENGTH,
					RackShelfHConst.ANGLE,
					RackShelfHConst.TYPE_SHELF,
					RackShelfHConst.USER_INSERT,
					RackShelfHConst.DATE_INSERT,
					RackShelfHConst.TYPE_OPERATION);
			checkTable(psColumns, RackWaresHConst.TABLE_NAME,
					RackWaresHConst.CODE_RACK,
					RackWaresHConst.CODE_WARES,
					RackWaresHConst.CODE_UNIT,
					RackWaresHConst.CODE_WARES_ON_RACK,
					RackWaresHConst.TYPE_WARES_ON_RACK,
					RackWaresHConst.ORDER_NUMBER_ON_RACK,
					RackWaresHConst.POSITION_X,
					RackWaresHConst.POSITION_Y,
					RackWaresHConst.WARES_LENGTH,
					RackWaresHConst.WARES_WIDTH,
					RackWaresHConst.WARES_HEIGHT,
					RackWaresHConst.COUNT_LENGTH_ON_SHELF,
					RackWaresHConst.USER_INSERT,
					RackWaresHConst.DATE_INSERT,
					RackWaresHConst.TYPE_OPERATION);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (userContext != null && userContext.getConnection() != null) {
				userContext.getConnection().rollback();
				userContext.getConnection().close();
			}

		}
	}

	private static void checkTable(final PreparedStatement psColumns, String tableName, String... columnsName) throws SQLException {
		psColumns.setString(1, tableName);
		final List<String> realColumns = new ArrayList<String>();
		final ResultSet resultSet = psColumns.executeQuery();
		if (!resultSet.next()) {
			System.out.println("Table " + tableName + " NOT FOUND");
		} else {
			realColumns.add(resultSet.getString(1).toLowerCase());
			while (resultSet.next()) {
				realColumns.add(resultSet.getString(1).toLowerCase());
			}
		}
		final List<String> testColumns;
		if (columnsName != null) {
			testColumns = Arrays.asList(columnsName);
		} else {
			testColumns = new ArrayList<String>();
		}
		for (int i = 0; i < testColumns.size(); i++) {
			final String testColumnName = testColumns.get(i);
			final int indexRealColumn = Collections.binarySearch(realColumns, testColumnName);
			if (indexRealColumn < 0)
				System.out.println("Table " + tableName + " column " + testColumnName + " NOT FOUND");
			else
				realColumns.remove(indexRealColumn);
		}
		for (int i = 0; i < realColumns.size(); i++) {
			System.out.println("Table " + tableName + " column " + realColumns.get(i) + " APPEND");
		}
	}
}
