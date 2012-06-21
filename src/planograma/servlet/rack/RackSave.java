package planograma.servlet.rack;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackShelfModel;
import planograma.model.RackModel;
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
@WebServlet("/" + UrlConst.URL_RACK_SAVE)
public class RackSave extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_SAVE;

	public static final Logger LOG = Logger.getLogger(RackSave.class);

	private RackModel rackModel;
	private RackShelfModel rackShelfModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackModel = RackModel.getInstance();
		rackShelfModel =RackShelfModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_EDIT);
		final JsonObject rackJson = requestData.getAsJsonObject().getAsJsonObject("rack");
		final Rack rack = new Rack(rackJson);
		final JsonArray shelfListJson = requestData.getAsJsonObject().getAsJsonArray("rackShelfList");
		final List<RackShelf> itemList = new ArrayList<RackShelf>(shelfListJson.size());
		for (int i = 0; i < shelfListJson.size(); i++) {
			final JsonObject rackShelfJson = shelfListJson.get(i).getAsJsonObject();
			final RackShelf item = new RackShelf(rackShelfJson);
			itemList.add(item);
		}
		if (rack.getCode_rack() == null) {
			// insert
			rackModel.insert(userContext, rack);
		} else {
			// update
			rackModel.update(userContext, rack);
			List<RackShelf> oldItemList = rackShelfModel.list(userContext, rack.getCode_rack());
			for (final RackShelf oldItem : oldItemList) {
				RackShelf findItem = null;
//				поиск среди сохраненых рание
				for (int i = 0; findItem == null && i < itemList.size(); i++) {
					final RackShelf currentItem = itemList.get(i);
					if (oldItem.getCode_shelf().equals(currentItem.getCode_shelf())) {
						findItem = currentItem;
//						запись была обновлена
						rackShelfModel.update(userContext, findItem);
						itemList.remove(i);
						i--;
					}
				}
				if (findItem == null) {
//					запись была удалена
					rackShelfModel.delete(userContext, oldItem.getCode_shelf());
				}
			}
		}
		for (final RackShelf newItem : itemList) {
//			запись была добавлена
			newItem.setCode_rack(rack.getCode_rack());
			rackShelfModel.insert(userContext, newItem);
		}
		commit(userContext);
		final JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty(RackConst.CODE_RACK, rack.getCode_rack());
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
