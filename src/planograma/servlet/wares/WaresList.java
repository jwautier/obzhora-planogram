package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresGroupConst;
import planograma.data.WaresGroup;
import planograma.data.wrapper.WaresWrapper;
import planograma.exception.UnauthorizedException;
import planograma.model.WaresModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 01.05.12
 * Time: 19:44
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_WARES_LIST)
public class WaresList extends AbstractAction {

	public static final String URL = UrlConst.URL_WARES_LIST;

	public static final Logger LOG = Logger.getLogger(WaresList.class);

	private WaresModel waresModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		waresModel = WaresModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		final int code_group = requestData.getAsJsonObject().get(WaresGroupConst.CODE_GROUP_WARES).getAsInt();

		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final List<WaresWrapper> list = waresModel.list(getUserContext(session), code_group);
		for (final WaresWrapper item : list) {
			jsonArray.add(item.toJsonObject());
		}
		jsonObject.add("waresList", jsonArray);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
