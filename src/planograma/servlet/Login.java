package planograma.servlet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.exception.InvalidLoginOrPassword;
import planograma.exception.NotAccessException;
import planograma.model.StateAllModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 17.02.12
 * Time: 6:52
 * To change this template use File | Settings | File Templates.
 */
@WebServlet('/' + UrlConst.URL_LOGIN)
public class Login extends AbstractAction {

	public static final String URL = UrlConst.URL_LOGIN;

	public static final Logger LOG = Logger.getLogger(Login.class);

	private StateAllModel stateAllModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		stateAllModel = StateAllModel.getInstance();
	}

	@Override
	protected JsonObject execute(final HttpSession session, final JsonElement requestData) throws Exception {
		long time = System.currentTimeMillis();
		final String login = requestData.getAsJsonObject().get("login").getAsString();
		final String password = requestData.getAsJsonObject().get("password").getAsString();
		if (login == null || password == null || login.isEmpty() || password.isEmpty())
			throw InvalidLoginOrPassword.getInstance();
		session.removeAttribute(SessionConst.SESSION_USER);
		final UserContext userContext = new UserContext(login, password);
		checkAccess(userContext, SecurityConst.ACCESS_MODULE);
		session.setAttribute(SessionConst.SESSION_USER, userContext);
		stateAllModel.initEnum(userContext);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return null;
	}
}
