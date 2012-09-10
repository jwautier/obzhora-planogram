package planograma.servlet.buffer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_BUFFER_GET)
public class BufferGet extends AbstractAction {

	public static final String URL = UrlConst.URL_BUFFER_GET;

	public static final Logger LOG = Logger.getLogger(BufferGet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final JsonArray copyObjectList= (JsonArray) session.getAttribute(BufferSet.NAME_COPY_OBJECT_LIST);
		session.setAttribute(BufferSet.NAME_COPY_OBJECT_LIST, copyObjectList);
		final JsonObject result=new JsonObject();
		result.add(BufferSet.NAME_COPY_OBJECT_LIST, copyObjectList);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return result;
	}
}
