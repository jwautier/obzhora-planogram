package planograma.servlet.racktemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackTemplateConst;
import planograma.data.RackShelfTemplate;
import planograma.data.RackTemplate;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackShelfTemplateModel;
import planograma.model.RackTemplateModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 23:58
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_RACK_TEMPLATE_SAVE)
public class RackTemplateSave extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_TEMPLATE_SAVE;

	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel=RackShelfTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_TEMPLATE_EDIT);
		final JsonObject rackTemplateJson = requestData.getAsJsonObject().getAsJsonObject("rackTemplate");
		final RackTemplate rackTemplate = new RackTemplate(rackTemplateJson);
		final JsonArray shelfListJson = requestData.getAsJsonObject().getAsJsonArray("rackShelfTemplateList");
		final List<RackShelfTemplate> itemList = new ArrayList<RackShelfTemplate>(shelfListJson.size());
		for (int i = 0; i < shelfListJson.size(); i++) {
			final JsonObject rackJson = shelfListJson.get(i).getAsJsonObject();
			final RackShelfTemplate item = new RackShelfTemplate(rackJson);
			itemList.add(item);
		}
		if (rackTemplate.getCode_rack_template() == null) {
			// insert
			rackTemplateModel.insert(userContext, rackTemplate);
		} else {
			// update
			rackTemplateModel.update(userContext, rackTemplate);
			List<RackShelfTemplate> oldItemList = rackShelfTemplateModel.list(userContext, rackTemplate.getCode_rack_template());
			for (final RackShelfTemplate oldItem : oldItemList) {
				RackShelfTemplate findItem = null;
//				поиск среди сохраненых рание
				for (int i = 0; findItem == null && i < itemList.size(); i++) {
					final RackShelfTemplate currentItem = itemList.get(i);
					if (oldItem.getCode_shelf_template().equals(currentItem.getCode_shelf_template())) {
						findItem = currentItem;
//						запись была обновлена
						rackShelfTemplateModel.update(userContext, findItem);
						itemList.remove(i);
						i--;
					}
				}
				if (findItem == null) {
//					запись была удалена
					rackShelfTemplateModel.delete(userContext, oldItem.getCode_shelf_template());
				}
			}
		}
		for (final RackShelfTemplate newItem : itemList) {
//			запись была добавлена
			newItem.setCode_rack_template(rackTemplate.getCode_rack_template());
			rackShelfTemplateModel.insert(userContext, newItem);
		}
		commit(userContext);
		final JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
		return jsonObject;
	}
}
