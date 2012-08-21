package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.*;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.*;
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

	public static final Logger LOG = Logger.getLogger(SectorSave.class);

	private SectorModel sectorModel;
	private RackModel rackModel;
	private RackShelfModel rackShelfModel;
	private RackWaresModel rackWaresModel;
	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_SECTOR_EDIT);
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
					// удаление товаров со стеллажа
					for (final RackWares rackWares : rackWaresModel.list(userContext, oldRack.getCode_rack())) {
						rackWaresModel.delete(userContext, rackWares.getCode_wares());
					}
					// удаление полок
					for (final RackShelf rackShelf : rackShelfModel.list(userContext, oldRack.getCode_rack())) {
						rackShelfModel.delete(userContext, rackShelf.getCode_shelf());
					}
					// удаление стеллажа
					rackModel.delete(userContext, oldRack.getCode_rack());
				}
			}
		}
		for (final Rack newRack : rackList) {
			// запись была добавлена
			newRack.setCode_sector(sector.getCode_sector());
			if (newRack.getCode_rack_template() != null) {
				// копирование параметров полезной области из шаблона в новый стеллаж
				final RackTemplate rackTemplate = rackTemplateModel.select(userContext, newRack.getCode_rack_template());
				newRack.setReal_length(rackTemplate.getReal_length());
				newRack.setReal_width(rackTemplate.getReal_width());
				newRack.setReal_height(rackTemplate.getReal_height());
				newRack.setX_offset(rackTemplate.getX_offset());
				newRack.setY_offset(rackTemplate.getY_offset());
				newRack.setZ_offset(rackTemplate.getZ_offset());
			}
			rackModel.insert(userContext, newRack);
			if (newRack.getCode_rack_template() != null) {
				// копируем полки с шаблонного стеллажа
				for (final RackShelfTemplate shelfTemplate : rackShelfTemplateModel.list(userContext, newRack.getCode_rack_template())) {
					final RackShelf shelf = new RackShelf(newRack.getCode_rack(), null,
							shelfTemplate.getX_coord(), shelfTemplate.getY_coord(),
							shelfTemplate.getShelf_height(), shelfTemplate.getShelf_width(), shelfTemplate.getShelf_length(),
							shelfTemplate.getAngle(), shelfTemplate.getType_shelf(), null, null, null, null);
					rackShelfModel.insert(userContext, shelf);
				}
			}
		}
		commit(userContext);
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(SectorConst.CODE_SECTOR, sector.getCode_sector());
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
