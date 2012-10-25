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
import planograma.utils.JsonUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final Logger LOG = Logger.getLogger(SectorSave.class);

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
		final Map<Rack, Integer> copyFromCodeRackList = new HashMap<Rack, Integer>();
		for (int i = 0; i < rackListJson.size(); i++) {
			final JsonObject rackJson = rackListJson.get(i).getAsJsonObject();
			final Rack rack = new Rack(rackJson);
			rackList.add(rack);
			final Integer copy_from_code_rack = JsonUtils.getInteger(rackJson, "copy_from_code_rack");
			if (copy_from_code_rack != null && copy_from_code_rack != 0) {
				copyFromCodeRackList.put(rack, copy_from_code_rack);
			}
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
						rackWaresModel.delete(userContext, rackWares.getCode_wares_on_rack());
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
			rackModel.insert(userContext, newRack);
			final Integer copy_from_code_rack = copyFromCodeRackList.get(newRack);
			if (copy_from_code_rack != null) {
				// копируем полки с ранее созданого стеллажа
				final List<RackShelf> copyRackShelfList = rackShelfModel.list(userContext, copy_from_code_rack);
				for (RackShelf rackShelf : copyRackShelfList) {
					final RackShelf newRackShelf = new RackShelf(newRack.getCode_rack(), null, rackShelf.getX_coord(), rackShelf.getY_coord(),
							rackShelf.getShelf_height(), rackShelf.getShelf_width(), rackShelf.getShelf_length(),
							rackShelf.getAngle(), rackShelf.getType_shelf(), null, null, null, null);
					rackShelfModel.insert(userContext, newRackShelf);
				}
				// копируем товары с ранее созданого стеллажа
				final List<RackWares> copyRackWaresList = rackWaresModel.list(userContext, copy_from_code_rack);
				for (RackWares rackWares : copyRackWaresList) {
					final RackWares newRackWares = new RackWares(newRack.getCode_rack(), rackWares.getCode_wares(),
							rackWares.getCode_unit(), null, rackWares.getType_wares_on_rack(), rackWares.getOrder_number_on_rack(),
							rackWares.getPosition_x(), rackWares.getPosition_y(), rackWares.getWares_length(), rackWares.getWares_width(),
							rackWares.getWares_height(), rackWares.getCount_length_on_shelf(), null, null, null, null, null, null, null, null);
					rackWaresModel.insert(userContext, newRackWares);
				}
			} else if (newRack.getCode_rack_template() != null && newRack.getCode_rack_template() != 0) {
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
