package planograma.servlet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.exception.InvalidLoginOrPassword;

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

	@Override
	protected JsonObject execute(final HttpSession session, final JsonElement requestData) throws Exception {
		final String login = requestData.getAsJsonObject().get("login").getAsString();
		final String password = requestData.getAsJsonObject().get("password").getAsString();
		if (login == null || password == null || login.isEmpty() || password.isEmpty())
			throw InvalidLoginOrPassword.getInstance();
		session.removeAttribute(SessionConst.SESSION_USER);
		final UserContext userContext = new UserContext(login, password);
		session.setAttribute(SessionConst.SESSION_USER, userContext);
		return null;
	}
}
