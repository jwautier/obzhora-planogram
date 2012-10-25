package planograma.servlet.racktemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.RackTemplateConst;
import planograma.data.RackShelfTemplate;
import planograma.data.RackTemplate;
import planograma.exception.UnauthorizedException;
import planograma.model.RackShelfTemplateModel;
import planograma.model.RackTemplateModel;
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
 * Date: 21.03.12
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_RACK_TEMPLATE_EDIT)
public class RackTemplateEdit extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_TEMPLATE_EDIT;

	private static final Logger LOG = Logger.getLogger(RackTemplateEdit.class);

	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final int code_rack_template = requestData.getAsJsonObject().get(RackTemplateConst.CODE_RACK_TEMPLATE).getAsInt();
		final RackTemplate rackTemplate = rackTemplateModel.select(getUserContext(session), code_rack_template);
		final List<RackShelfTemplate> list= rackShelfTemplateModel.list(getUserContext(session), code_rack_template);
		for (final RackShelfTemplate item:list){
			jsonArray.add(item.toJsonObject());
		}
		jsonObject.add("rackTemplate", rackTemplate.toJsonObject());
		jsonObject.add("rackShelfTemplateList", jsonArray);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
