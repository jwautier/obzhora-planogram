package planograma.servlet.image;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.ImageModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

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

	private static final Logger LOG = Logger.getLogger(ImageCleanCache.class);

	private ImageModel imageModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageModel = ImageModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final UserContext userContext=getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_IMAGE_CLEAN_CACHE);
		imageModel.clearCache();
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return null;
	}
}
