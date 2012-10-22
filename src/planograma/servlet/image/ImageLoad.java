package planograma.servlet.image;

import org.apache.log4j.Logger;
import planograma.constant.SessionConst;
import planograma.constant.UrlConst;
import planograma.data.UserContext;
import planograma.model.ImageModel;
import planograma.utils.FileUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 26.04.12
 * Time: 5:30
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_IMAGE_LOAD + "*")
public class ImageLoad extends HttpServlet {

	public static final String URL = UrlConst.URL_IMAGE_LOAD;

	public static final Logger LOG = Logger.getLogger(ImageLoad.class);

	private ImageModel imageModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageModel = ImageModel.getInstance();
	}


	public void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		long time = System.currentTimeMillis();
		try {
			int code_image = Integer.parseInt(request.getPathInfo().substring(1));
			final HttpSession session = request.getSession(false);
			final UserContext userContext = (UserContext) session.getAttribute(SessionConst.SESSION_USER);
			final InputStream inputStream = imageModel.select(userContext, code_image);
			final OutputStream outputStream = resp.getOutputStream();
			final long size = FileUtils.copy(inputStream, outputStream);
			resp.setContentType("image/jpeg");
			resp.setContentLength((int) size);
		} catch (Exception e) {
			LOG.error("Error load image", e);
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
	}

}
