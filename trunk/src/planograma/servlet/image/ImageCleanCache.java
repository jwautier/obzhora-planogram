package planograma.servlet.image;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.exception.UnauthorizedException;
import planograma.model.ImageModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 3:17
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_IMAGE_CLEAN_CACHE)
public class ImageCleanCache extends AbstractAction {
	public static final String URL = UrlConst.URL_IMAGE_CLEAN_CACHE;

	private ImageModel imageModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageModel = ImageModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		getUserContext(session);
		imageModel.clearCache();
		return null;
	}
}
