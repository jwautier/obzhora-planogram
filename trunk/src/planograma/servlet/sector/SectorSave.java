package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.Rack;
import planograma.data.Sector;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.SectorModel;
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
@WebServlet("/" + UrlConst.URL_SECTOR_SAVE)
public class SectorSave extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_SAVE;

	private SectorModel sectorModel;
	private RackModel rackModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		final UserContext userContext = getUserContext(session);
		final JsonObject sectorJson = requestData.getAsJsonObject().getAsJsonObject("sector");
		final Sector sector = new Sector(sectorJson);
		final JsonArray rackListJson = requestData.getAsJsonObject().getAsJsonArray("rackList");
		final List<Rack> rackList = new ArrayList<Rack>(rackListJson.size());
		for (int i = 0; i < rackListJson.size(); i++) {
			final JsonObject rackJson = rackListJson.get(i).getAsJsonObject();
			final Rack rack = new Rack(rackJson);
			rackList.add(rack);
		}
		// TODO проверка выхода зя пределы зала
		// TODO проверка пересечения стеллажей

		if (sector.getCode_sector() == null) {
			// insert
			sectorModel.insert(userContext, sector);
		} else {
			// update
			sectorModel.update(userContext, sector);
			List<Rack> oldRackList = rackModel.list(userContext, sector.getCode_sector());
			for (final Rack oldRack : oldRackList) {
				Rack findRack = null;
				// поиск среди сохраненых рание
				for (int i = 0; findRack == null && i < rackList.size(); i++) {
					final Rack currentRack = rackList.get(i);
					if (oldRack.getCode_rack().equals(currentRack.getCode_rack())) {
						findRack = currentRack;
						// запись была обновлена
						rackModel.update(userContext, findRack);
						rackList.remove(i);
						i--;
					}
				}
				if (findRack == null) {
					// запись была удалена
					rackModel.delete(userContext, oldRack.getCode_rack());
				}
			}
		}
		for (final Rack newRack : rackList) {
			// запись была добавлена
			newRack.setCode_sector(sector.getCode_sector());
			rackModel.insert(userContext, newRack);
		}
		commit(userContext);
		final JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty(SectorConst.CODE_SECTOR, sector.getCode_sector());
		return jsonObject;
	}
}
