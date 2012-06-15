package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.RackWares;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackWaresHModel;
import planograma.model.RackWaresModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.05.12
 * Time: 6:34
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_RACK_WARES_PLACEMENT_SAVE)
public class RackWaresPlacementSave extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_WARES_PLACEMENT_SAVE;

	private RackWaresModel rackWaresModel;
	private RackWaresHModel rackWaresHModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackWaresModel = RackWaresModel.getInstance();
		rackWaresHModel = RackWaresHModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_WARES_PLACEMENT);
		final int code_rack = requestData.getAsJsonObject().getAsJsonPrimitive(RackConst.CODE_RACK).getAsInt();
		final JsonArray rackWaresListJson = requestData.getAsJsonObject().getAsJsonArray("rackWaresList");
		final List<RackWares> itemList = new ArrayList<RackWares>(rackWaresListJson.size());
		for (int i = 0; i < rackWaresListJson.size(); i++) {
			final JsonObject itemJson = rackWaresListJson.get(i).getAsJsonObject();
			final RackWares item = new RackWares(itemJson);
			itemList.add(item);
		}
		List<RackWares> oldItemList = rackWaresModel.list(userContext, code_rack);
		final int version=rackWaresHModel.nextVersion(userContext);
		for (final RackWares oldItem : oldItemList) {
			RackWares findItem = null;
//				поиск среди сохраненых рание
			for (int i = 0; findItem == null && i < itemList.size(); i++) {
				final RackWares currentItem = itemList.get(i);
				if (oldItem.getCode_wares_on_rack().equals(currentItem.getCode_wares_on_rack())) {
					findItem = currentItem;
//					запись была обновлена
					rackWaresModel.update(userContext, findItem, version);
					itemList.remove(i);
					i--;
				}
			}
			if (findItem == null) {
//				запись была удалена
				rackWaresModel.delete(userContext, oldItem.getCode_wares_on_rack());
			}
		}
		for (final RackWares newItem : itemList) {
//			запись была добавлена
			newItem.setCode_rack(code_rack);
			rackWaresModel.insert(userContext, newItem, version);
		}
		commit(userContext);
		return null;
	}

}
