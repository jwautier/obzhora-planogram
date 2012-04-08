package planograma.model;

import planograma.constant.data.SectorConst;
import planograma.data.Sector;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 28.02.12
 * Time: 6:01
 * To change this template use File | Settings | File Templates.
 */

public class SectorModel {

	public static final String Q_LIST = "select" +
			" " + SectorConst.CODE_SHOP + "," +
			" " + SectorConst.CODE_SECTOR + "," +
			" " + SectorConst.STATE_SECTOR + "," +
			" " + SectorConst.NAME_SECTOR + "," +
			" " + SectorConst.LENGTH + "," +
			" " + SectorConst.WIDTH + "," +
			" " + SectorConst.HEIGHT + "," +
			" " + SectorConst.USER_INSERT + "," +
			" " + SectorConst.DATE_INSERT + "," +
			" " + SectorConst.USER_UPDATE + "," +
			" " + SectorConst.DATE_UPDATE + "," +
			" " + SectorConst.USER_DRAFT + "," +
			" " + SectorConst.DATE_DRAFT + " " +
			"from " + SectorConst.TABLE_NAME + " " +
			"where " + SectorConst.CODE_SHOP + "=? " +
			"order by " + SectorConst.NAME_SECTOR;

	public List<Sector> list(final UserContext userContext, final int code_shop) throws SQLException {
		//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		ps.setInt(1, code_shop);
		final ResultSet resultSet = ps.executeQuery();
		final List<Sector> list = new ArrayList<Sector>();
		while (resultSet.next()) {
			final Sector item = new Sector(resultSet);
			list.add(item);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return list;
	}

	public static final String Q_SELECT = "select" +
			" " + SectorConst.CODE_SHOP + "," +
			" " + SectorConst.CODE_SECTOR + "," +
			" " + SectorConst.STATE_SECTOR + "," +
			" " + SectorConst.NAME_SECTOR + "," +
			" " + SectorConst.LENGTH + "," +
			" " + SectorConst.WIDTH + "," +
			" " + SectorConst.HEIGHT + "," +
			" " + SectorConst.USER_INSERT + "," +
			" " + SectorConst.DATE_INSERT + "," +
			" " + SectorConst.USER_UPDATE + "," +
			" " + SectorConst.DATE_UPDATE + "," +
			" " + SectorConst.USER_DRAFT + "," +
			" " + SectorConst.DATE_DRAFT + " " +
			"from " + SectorConst.TABLE_NAME + " " +
			"where " + SectorConst.CODE_SECTOR + " = ?";

	public Sector select(final UserContext userContext, final int code_sector) throws SQLException {
		//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_sector);
		final ResultSet resultSet = ps.executeQuery();
		Sector sector = null;
		if (resultSet.next()) {
			sector = new Sector(resultSet);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return sector;
	}

	public static final String Q_INSERT_UPDATE = "{call :new_code_sector := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IUSECTOR(" +
			":mode, " +
			":" + SectorConst.CODE_SECTOR + ", " +
			":" + SectorConst.CODE_SHOP + ", " +
			":" + SectorConst.NAME_SECTOR + ", " +
			":" + SectorConst.LENGTH + ", " +
			":" + SectorConst.WIDTH + ", " +
			":" + SectorConst.HEIGHT + ")}";

	public int insert(final UserContext userContext, final Sector sector) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_sector", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setObject(SectorConst.CODE_SECTOR, sector.getCode_sector());
		callableStatement.setInt(SectorConst.CODE_SHOP, sector.getCode_shop());
		callableStatement.setString(SectorConst.NAME_SECTOR, sector.getName_sector());
		callableStatement.setInt(SectorConst.LENGTH, sector.getLength());
		callableStatement.setInt(SectorConst.WIDTH, sector.getWidth());
		callableStatement.setInt(SectorConst.HEIGHT, sector.getHeight());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_sector");
		sector.setCode_sector(id);
		return sector.getCode_sector();
	}

	public void update(final UserContext userContext, final Sector sector) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_sector", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setObject(SectorConst.CODE_SECTOR, sector.getCode_sector());
		callableStatement.setInt(SectorConst.CODE_SHOP, sector.getCode_shop());
		callableStatement.setString(SectorConst.NAME_SECTOR, sector.getName_sector());
		callableStatement.setInt(SectorConst.LENGTH, sector.getLength());
		callableStatement.setInt(SectorConst.WIDTH, sector.getWidth());
		callableStatement.setInt(SectorConst.HEIGHT, sector.getHeight());
		callableStatement.execute();
	}

	public static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATESECTOR(" +
			":" + SectorConst.CODE_SECTOR + ", " +
			":" + SectorConst.STATE_SECTOR + ")}";

	public void changestate(final UserContext userContext, final int code_sector, final String state_sector) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(SectorConst.CODE_SECTOR, code_sector);
		callableStatement.setString(SectorConst.STATE_SECTOR, state_sector);
		callableStatement.execute();
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DSector(:" + SectorConst.CODE_SECTOR + ")}";

	public void delete(final UserContext userContext, final int code_sector) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(SectorConst.CODE_SECTOR, code_sector);
		callableStatement.execute();
	}

	private static SectorModel instance = new SectorModel();

	public static SectorModel getInstance() {
		return instance;
	}

	private SectorModel() {
	}
}
