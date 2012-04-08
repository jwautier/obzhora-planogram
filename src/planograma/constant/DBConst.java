package planograma.constant;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.03.12
 * Time: 1:45
 * To change this template use File | Settings | File Templates.
 */
public interface DBConst {

// H2
//	public static final String DB_ADMIN="SA";
//	public static final String DB_ADMIN_PASSORD="";
//	public static final String DB_DRIVER ="org.h2.Driver";
//	public static final String DB_URL="jdbc:h2:file:C:\\Dropbox\\work\\Obzhora\\Planograma\\db/planogram";

// postgresql
//	public static final String DB_ADMIN="test";
//	public static final String DB_ADMIN_PASSORD="testdisnetpro";
//	public static final String DB_DRIVER ="org.postgresql.Driver";
//	public static final String DB_URL="jdbc:postgresql://127.0.0.1:5432/CENTRAL";

	// oracle
	public static final String DB_ADMIN = "";
	public static final String DB_ADMIN_PASSORD = "";
	public static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String DB_URL = "jdbc:oracle:thin:@//192.168.0.6:1521/CENTRAL";
}
