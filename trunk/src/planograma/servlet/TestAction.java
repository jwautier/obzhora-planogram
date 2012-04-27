package planograma.servlet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SessionConst;
import planograma.data.WaresGroup;
import planograma.exception.UnauthorizedException;
import planograma.model.WaresGroupModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 4:06
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/test")
public class TestAction extends AbstractAction{

	public static final String URL = "test";

	@Override
	protected JsonObject execute(final HttpSession session, final JsonElement requestData) throws UnauthorizedException, SQLException {
		final WaresGroupModel waresGroupModel=WaresGroupModel.getInstance();
		List<WaresGroup> list= waresGroupModel.tree(getUserContext(session));
		return null;
	}

}
