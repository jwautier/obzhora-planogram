package planograma.model;

import planograma.constant.data.RackTemplateConst;
import planograma.data.RackTemplate;
import planograma.data.UserContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 1:18
 * To change this template use File | Settings | File Templates.
 */
public class RackTemplateModel {

	public static final String Q_LIST = "select" +
			" " + RackTemplateConst.CODE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.STATE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.NAME_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.LENGTH + "," +
			" " + RackTemplateConst.WIDTH + "," +
			" " + RackTemplateConst.HEIGHT + "," +
			" " + RackTemplateConst.LOAD_SIDE + "," +
			" " + RackTemplateConst.USER_INSERT + "," +
			" " + RackTemplateConst.DATE_INSERT + "," +
			" " + RackTemplateConst.USER_UPDATE + "," +
			" " + RackTemplateConst.DATE_UPDATE + "," +
			" " + RackTemplateConst.USER_DRAFT + "," +
			" " + RackTemplateConst.DATE_DRAFT + " " +
			"from " + RackTemplateConst.TABLE_NAME + " " +
			"order by " + RackTemplateConst.NAME_RACK_TEMPLATE;

	public List<RackTemplate> list(final UserContext userContext) throws SQLException {
		//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_LIST);
		final ResultSet resultSet = ps.executeQuery();
		final List<RackTemplate> list = new ArrayList<RackTemplate>();
		while (resultSet.next()) {
			final RackTemplate item = new RackTemplate(resultSet);
			list.add(item);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return list;
	}

	public static final String Q_SELECT = "select" +
			" " + RackTemplateConst.CODE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.STATE_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.NAME_RACK_TEMPLATE + "," +
			" " + RackTemplateConst.LENGTH + "," +
			" " + RackTemplateConst.WIDTH + "," +
			" " + RackTemplateConst.HEIGHT + "," +
			" " + RackTemplateConst.LOAD_SIDE + "," +
			" " + RackTemplateConst.USER_INSERT + "," +
			" " + RackTemplateConst.DATE_INSERT + "," +
			" " + RackTemplateConst.USER_UPDATE + "," +
			" " + RackTemplateConst.DATE_UPDATE + "," +
			" " + RackTemplateConst.USER_DRAFT + "," +
			" " + RackTemplateConst.DATE_DRAFT + " " +
			"from " + RackTemplateConst.TABLE_NAME + " " +
			"where " + RackTemplateConst.CODE_RACK_TEMPLATE + " = ?";

	public RackTemplate select(final UserContext userContext, final int code_rack_template) throws SQLException {
		//		long time=System.currentTimeMillis();
		final Connection connection = userContext.getConnection();
		final PreparedStatement ps = connection.prepareStatement(Q_SELECT);
		ps.setInt(1, code_rack_template);
		final ResultSet resultSet = ps.executeQuery();
		RackTemplate rackTemplate = null;
		if (resultSet.next()) {
			rackTemplate = new RackTemplate(resultSet);
		}
//		System.out.println(System.currentTimeMillis()-time);
		return rackTemplate;
	}

	public static final String Q_INSERT_UPDATE = "{call :new_code_rack_template := EUGENE_SAZ.SEV_PKG_PLANOGRAMS.IURackTemplate(" +
			":mode, " +
			":" + RackTemplateConst.CODE_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.NAME_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.LENGTH + ", " +
			":" + RackTemplateConst.WIDTH + ", " +
			":" + RackTemplateConst.HEIGHT + ", " +
			":" + RackTemplateConst.LOAD_SIDE + ")}";

	public int insert(final UserContext userContext, final RackTemplate rackTemplate) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack_template", Types.INTEGER);
		callableStatement.setString("mode", "I");
		callableStatement.setObject(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		callableStatement.setString(RackTemplateConst.NAME_RACK_TEMPLATE, rackTemplate.getName_rack_template());
		callableStatement.setInt(RackTemplateConst.LENGTH, rackTemplate.getLength());
		callableStatement.setInt(RackTemplateConst.WIDTH, rackTemplate.getWidth());
		callableStatement.setInt(RackTemplateConst.HEIGHT, rackTemplate.getHeight());
		callableStatement.setString(RackTemplateConst.LOAD_SIDE, rackTemplate.getLoad_sideAtStr());
		callableStatement.execute();
		final int id = callableStatement.getInt("new_code_rack_template");
		rackTemplate.setCode_rack_template(id);
		return rackTemplate.getCode_rack_template();
	}

	public void update(final UserContext userContext, final RackTemplate rackTemplate) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_INSERT_UPDATE);
		callableStatement.registerOutParameter("new_code_rack_template", Types.INTEGER);
		callableStatement.setString("mode", "U");
		callableStatement.setObject(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		callableStatement.setString(RackTemplateConst.NAME_RACK_TEMPLATE, rackTemplate.getName_rack_template());
		callableStatement.setInt(RackTemplateConst.LENGTH, rackTemplate.getLength());
		callableStatement.setInt(RackTemplateConst.WIDTH, rackTemplate.getWidth());
		callableStatement.setInt(RackTemplateConst.HEIGHT, rackTemplate.getHeight());
		callableStatement.setString(RackTemplateConst.LOAD_SIDE, rackTemplate.getLoad_sideAtStr());
		callableStatement.execute();
	}

	public static final String Q_CHANGESTATE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.CHANGESTATERACK(" +
			":" + RackTemplateConst.CODE_RACK_TEMPLATE + ", " +
			":" + RackTemplateConst.STATE_RACK_TEMPLATE + ")}";

	public void changestate(final UserContext userContext, final int code_rack_template, final String state_rack_template) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_CHANGESTATE);
		callableStatement.setInt(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		callableStatement.setString(RackTemplateConst.STATE_RACK_TEMPLATE, state_rack_template);
		callableStatement.execute();
	}

	private static final String Q_DELETE = "{call EUGENE_SAZ.SEV_PKG_PLANOGRAMS.DRACKTEMPLATE(:" + RackTemplateConst.CODE_RACK_TEMPLATE + ")}";

	public void delete(final UserContext userContext, final int code_rack_template) throws SQLException {
		final Connection connection = userContext.getConnection();
		final CallableStatement callableStatement = connection.prepareCall(Q_DELETE);
		callableStatement.setInt(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		callableStatement.execute();
	}

	private static RackTemplateModel instance = new RackTemplateModel();

	public static RackTemplateModel getInstance() {
		return instance;
	}

	private RackTemplateModel() {
	}
}
