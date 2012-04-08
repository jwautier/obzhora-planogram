package planograma.test;

import planograma.constant.DBConst;
import planograma.constant.data.SectorConst;
import planograma.constant.data.ShopConst;

import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.03.12
 * Time: 1:57
 * To change this template use File | Settings | File Templates.
 */
public class InstallTestDB {
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		// Загружаем класс драйвера
		Class.forName(DBConst.DB_DRIVER);
		// Cоздаем соединение
		final Connection connection = DriverManager.getConnection(DBConst.DB_URL, DBConst.DB_ADMIN, DBConst.DB_ADMIN_PASSORD);

		Statement statement = connection.createStatement();

		// выполнить руками
		// SELECT pg_database.datname FROM pg_catalog.pg_database
//		statement.execute("CREATE DATABASE CENTRAL");
//		System.out.println("CREATE DATABASE CENTRAL");


		boolean userInit = statement.executeQuery("SELECT pg_user.usename FROM pg_catalog.pg_user where pg_user.usename='a_poljakov'").next();
		if (!userInit) {
			statement.execute("CREATE USER a_poljakov PASSWORD 'FecUnRec' SUPERUSER CREATEDB ");
			System.out.println("CREATE USER a_poljakov");
		}

		initSchema(connection, "mz");

		initSchema(connection, "eugene_saz");

		initTableSession(connection);

		initTableShop(connection);

		initTableSector(connection);

//		statement.execute("create table IF NOT EXISTS mz.state_all (" +
//				" PART_STATE number not null," +
//				" ABR_STATE  char(2) not null," +
//				" STATE  varchar2(256) not null," +
//				" STATE_SECTOR char(1) not null," +
//				" DESCRIPTION varchar2(256)," +
//				" PART_STATE_PARENT number," +
//				" IS_VERSION_29 varchar2," +
//				" primary key ("+SectorConst.CODE_SECTOR+")" +
//				");");
//		System.out.println("create table mz.state_all");

		connection.commit();
		connection.close();
	}

	private static void initSchema(final Connection connection, final String schemeName) throws SQLException {
		final PreparedStatement ps = connection.prepareStatement("SELECT 1 FROM pg_catalog.pg_namespace where pg_namespace.nspname=?");
		ps.setString(1, schemeName);
		boolean init = ps.executeQuery().next();
		if (!init) {
			final Statement statement = connection.createStatement();
			statement.execute("CREATE SCHEMA " + schemeName);
			System.out.println("CREATE SCHEMA " + schemeName);
		}
	}

	private static boolean checkInitTable(final Connection connection, final String schemaName, final String tableName) throws SQLException {
		final PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM pg_catalog.pg_tables where pg_tables.schemaname=? and pg_tables.tablename=?");
		preparedStatement.setString(1, schemaName.toLowerCase());
		preparedStatement.setString(2, tableName.toLowerCase());
		final boolean init = preparedStatement.executeQuery().next();
		return init;
	}

	private static void initTableSession(final Connection connection) throws SQLException {
		boolean init = checkInitTable(connection, "mz", "gua_my_session");
		if (!init) {
			final Statement statement = connection.createStatement();
			statement.execute("create table mz.gua_my_session (" +
					" sid integer not null," +	// IDENTITY
					" \"SERIAL#\" integer not null," +
					" audsid integer not null," +
					" \"USER#\" integer not null," +
					" ownerid integer not null," +
					" \"SCHEMA#\" integer not null," +
					" primary key (\"sid\")" +
					")");
			System.out.println("create table MZ.gua_my_session");

//		statement.execute("INSERT INTO mz.gua_my_session VALUES (1,1,1,1,1,1)");
//		System.out.println("INSERT INTO mz.gua_my_session ");
//
//		statement.execute("create procedure mz.gua_profile_role.SetRole(in s1 varchar(256)) " +
//				"BEGIN " +
//				"END");
//		System.out.println("create procedure mz.gua_profile_role.SetRole ");
		}
	}

	private static void initTableShop(final Connection connection) throws SQLException {
		boolean init = checkInitTable(connection, "mz", "v_box_shop");
		if (!init) {
			final Statement statement = connection.createStatement();
			statement.execute("create table IF NOT EXISTS " + ShopConst.TABLE_NAME + " (" +
					" " + ShopConst.CODE_SHOP + " BIGINT not null," +
					" " + ShopConst.NAME_SHOP + " character varying (80) not null," +
					" primary key (" + ShopConst.CODE_SHOP + ")" +
					");");
			System.out.println("create table " + ShopConst.TABLE_NAME);

			final PreparedStatement ps = connection.prepareStatement("INSERT INTO " + ShopConst.TABLE_NAME + " VALUES (?, ?)");
			ps.setInt(1, 8);
			ps.setString(2, "ТП Обжора 1");
			ps.execute();
			ps.setInt(1, 6);
			ps.setString(2, "ТП Обжора 2");
			ps.execute();
			ps.setInt(1, 7);
			ps.setString(2, "ТП Обжора 3");
			ps.execute();
			ps.setInt(1, 9);
			ps.setString(2, "ТП Обжора 4");
			ps.execute();
			ps.setInt(1, 10);
			ps.setString(2, "ТП Обжора 5");
			ps.execute();
			ps.setInt(1, 12);
			ps.setString(2, "ТП Обжора 6");
			ps.execute();
			ps.setInt(1, 17);
			ps.setString(2, "ТП Обжора 7");
			ps.execute();
			ps.setInt(1, 18);
			ps.setString(2, "ТП Обжора 8");
			ps.execute();
			ps.setInt(1, 19);
			ps.setString(2, "ТП Обжора 9");
			ps.execute();
			ps.setInt(1, 20);
			ps.setString(2, "ТП Обжора 10");
			ps.execute();
			ps.setInt(1, 21);
			ps.setString(2, "ТП Обжора 11");
			ps.execute();
			ps.setInt(1, 22);
			ps.setString(2, "ТП Обжора 12");
			ps.execute();
			ps.setInt(1, 23);
			ps.setString(2, "ТП Обжора 14");
			ps.execute();
			ps.setInt(1, 46);
			ps.setString(2, "ТП Обжора 15");
			ps.execute();
		}
	}

	private static void initTableSector(final Connection connection) throws SQLException {
		final boolean init = checkInitTable(connection, "EUGENE_SAZ", "SEV_PL_SHOP_SECTOR");
		if (!init) {
			final Statement statement = connection.createStatement();
			statement.execute("create table IF NOT EXISTS " + SectorConst.TABLE_NAME + " (" +
					" " + SectorConst.CODE_SHOP + " BIGINT not null," +
					" " + SectorConst.CODE_SECTOR + " BIGINT not null," +
					" " + SectorConst.NAME_SECTOR + " character varying(80) not null," +
					" " + SectorConst.LENGTH + " integer not null," +
					" " + SectorConst.WIDTH + " integer not null," +
					" " + SectorConst.HEIGHT + " integer not null," +
					" " + SectorConst.STATE_SECTOR + " character varying(1) not null," +
					" " + SectorConst.USER_INSERT + " BIGINT not null," +
					" " + SectorConst.DATE_INSERT + " date not null," +
					" " + SectorConst.USER_UPDATE + " BIGINT not null," +
					" " + SectorConst.DATE_UPDATE + " date not null," +
					" primary key (" + SectorConst.CODE_SECTOR + ")" +
					");");
			System.out.println("create table " + SectorConst.TABLE_NAME);
			String s3 = "CREATE OR REPLACE TRIGGER EUGENE_SAZ.SEV_IE_PL_SHOP_SECTOR\n" +
					"BEFORE INSERT OR UPDATE\n" +
					"ON EUGENE_SAZ.SEV_PL_SHOP_SECTOR\n" +
					"REFERENCING NEW AS New OLD AS Old\n" +
					"FOR EACH ROW\n" +
					"BEGIN\n" +
					"\n" +
					"IF INSERTING THEN\n" +
					"\n" +
					"   :NEW.DATE_INSERT := SYSDATE;\n" +
					"   :NEW.USER_INSERT := mz.getcodeuser;\n" +
					"    \n" +
					"   :NEW.DATE_UPDATE := SYSDATE;\n" +
					"   :NEW.USER_UPDATE := mz.getcodeuser;\n" +
					"\n" +
					"END IF;\n" +
					"\n" +
					"IF UPDATING THEN\n" +
					"\n" +
					"   :NEW.DATE_UPDATE := SYSDATE;\n" +
					"   :NEW.USER_UPDATE := mz.getcodeuser;\n" +
					"\n" +
					"END IF;\n" +
					"\n" +
					"   EXCEPTION\n" +
					"     WHEN OTHERS THEN\n" +
					"       -- Consider logging the error and then re-raise\n" +
					"       RAISE;\n" +
					"END SEV_IE_PL_SHOP_SECTOR;";
			statement.execute(s3);


			String s1 = "create function EUGENE_SAZ.IESECTOR(" +
					"INTMODE varchar2, " +
					"INTCODE_SECTOR_IN number, " +
					"INTCODE_SHOP number, " +
					"INTNAME_SECTOR varchar2, " +
					"INTLENGTH number, " +
					"INTWIDTH number, " +
					"INTHEIGHT number, " +
					"INTSIGN_ACTIVITY varchar2" +
					") returns number;";
			String s2 = "CREATE OR REPLACE FUNCTION  MAIN_INS\n" +
					"(\n" +
					"\t_idmain integer,\n" +
					"\t_name_potreb varchar(50),\n" +
					"\t_adres varchar(100),\n" +
					"\t_type_metter varchar(30),\n" +
					"\t_num_metter varchar(20),\n" +
					"\t_num_tu integer,\n" +
					"\t_num_protoc varchar(10),\n" +
					"\t_fio_ust varchar(30),\n" +
					"\t_date_ust date\n" +
					")\n" +
					"RETURNS void AS\n" +
					"BEGIN\n" +
					"\tinsert into main \n" +
					"\t(\n" +
					"\t\tidmain,\n" +
					"\t\tname_potreb,\n" +
					"\t\tadres,\n" +
					"\t\ttype_metter,\n" +
					"\t\tnum_metter,\n" +
					"\t\tnum_tu,\n" +
					"\t\tnum_protoc,\n" +
					"\t\tfio_ust,\n" +
					"\t\tdate_ust\n" +
					"\t)\n" +
					"\tvalues\n" +
					"\t(\n" +
					"\t\t_idmain,\n" +
					"\t\t_name_potreb,\n" +
					"\t\t_adres,\n" +
					"\t\t_type_metter,\n" +
					"\t\t_num_metter,\n" +
					"\t\t_num_tu,\n" +
					"\t\t_num_protoc,\n" +
					"\t\t_fio_ust,\n" +
					"\t\t_date_ust\n" +
					"\t);\n" +
					"end";
		}
	}

	private static void initTableRack() {
		String s1 = "create table EUGENE_SAZ.SEV_PL_RACK_IN_SECTOR (" +
				"  CODE_SECTOR number not null," +
				"  CODE_RACK number not null," +
				"  NAME_RACK varchar2(80) not null," +
				"  RACK_BARCODE varchar2(128) not null," +
				"  X_COORD number not null," +
				"  Y_COORD number not null," +
				"  LENGTH number not null," +
				"  WIDTH number not null," +
				"  HEIGHT number not null," +
				"  LOAD_SIDE varchar2(2) not null," +
				"  USER_INSERT number not null," +
				"  DATE_INSERT date not null," +
				"  USER_UPDATE number not null," +
				"  DATE_UPDATE date not null," +
				"  VERSION number not null," +
				"  DATE_BEGIN_VERSION date not null," +
				"  DATE_END_VERSION date not null," +
				"  STATE_RACK varchar2(2) not null," +
				"  primary key (code_rack)" +
				");";
	}
}
