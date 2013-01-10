package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.PlanogramMessage;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.*;
import planograma.data.geometry.Rack2D;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.exception.EntityFieldException;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.*;
import planograma.servlet.AbstractAction;
import planograma.servlet.rack.RackMinDimensionsValidation;
import planograma.utils.JsonUtils;
import planograma.utils.geometry.Intersection2DUtils;

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
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
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
		final List<Rack2D> rack2DList =new ArrayList<Rack2D>(rackListJson.size());
		final Map<Rack, Integer> copyFromCodeRackList = new HashMap<Rack, Integer>();
		for (int i = 0; i < rackListJson.size(); i++) {
			final JsonObject rackJson = rackListJson.get(i).getAsJsonObject();
			final Rack rack = new Rack(rackJson);
			rackList.add(rack);
			final Rack2D rack2D = new Rack2D(rack);
			rack2DList.add(rack2D);
			final Integer copy_from_code_rack = JsonUtils.getInteger(rackJson, "copy_from_code_rack");
			if (copy_from_code_rack != null && copy_from_code_rack != 0) {
				copyFromCodeRackList.put(rack, copy_from_code_rack);
			}
		}

		//	ПРОВЕРКИ
		List<EntityFieldException> fieldExceptionList = new ArrayList<EntityFieldException>();
		// Проверка параметров зала: высоты, ширины, длина должны быть больше 1000мм(не сохраняется)
		SectorMinDimensionsValidation.validate(fieldExceptionList, sector);
		// проверка стеллажей
		for (int i = 0; i < rackList.size(); i++) {
			final Rack rack = rackList.get(i);
			final Rack2D rack2D = rack2DList.get(i);

			// Проверка параметров стеллажа: высота, ширина, глубина, полезная высота, полезная ширина, полезная глубина больше 10мм
			RackMinDimensionsValidation.validate(fieldExceptionList, rack);
			// стеллаж не может выходить за пределы зала
			if (rack2D.getMinX() < 0 ||
					rack2D.getMaxX() > sector.getLength() ||
					rack2D.getMinY() < 0 ||
					rack2D.getMaxY() > sector.getWidth() ||
					rack.getHeight() < 0 ||
					rack.getHeight() > sector.getHeight()) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OUTSIDE_SECTOR(), Rack.class, i, rack.getCode_rack(), "outside"));
			}

			if (rack.getCode_rack() != null) {
				float dx;
				float dy;
				float dz;
				// относительно стороны загрузки
				if (rack.getLoad_side() == LoadSide.F) {
					dx = rack.getLength();
					dy = rack.getHeight();
					dz = rack.getWidth();
				} else {
					dx = rack.getLength();
					dy = rack.getWidth();
					dz = rack.getHeight();
				}
				final List<RackShelf> rackShelfList = rackShelfModel.list(userContext, rack.getCode_rack());
				//	стеллаж не может стать меньше чем расположеные на нем полки (не сохраняется, выделяется один из стеллажей)
				if (rackShelfList != null) {
					for (int j = 0; j < rackShelfList.size(); i++) {
						final RackShelf rackShelf = rackShelfList.get(i);
						final RackShelf2D shelf2D = new RackShelf2D(rackShelf);
						if (shelf2D.getMinX() < 0 ||
								shelf2D.getMaxX() > dx ||
								shelf2D.getMinY() < 0 ||
								shelf2D.getMaxY() > dy ||
								rackShelf.getShelf_length() < 0 ||
								rackShelf.getShelf_length() > dz) {
							fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OVERFLOW_SHELF(), Rack.class, i, rack.getCode_rack(), "rack_overflow_shelf"));
						}
					}
				}

				// относительно стороны загрузки
				if (rack.getLoad_side() == LoadSide.F) {
					dx = rack.getReal_length();
					dy = rack.getReal_height();
					dz = rack.getReal_width();
				} else {
					dx = rack.getReal_length();
					dy = rack.getReal_width();
					dz = rack.getReal_height();
				}
				//	полезная зона не может стать меньше чем расположеные на нем товары(не сохраняется)
				final List<RackWares> rackWaresList = rackWaresModel.list(userContext, rack.getCode_rack());
				if (rackWaresList != null) {
					for (int j = 0; j < rackWaresList.size(); i++) {
						final RackWares rackWares = rackWaresList.get(i);
						final RackWares2D rackWares2D = new RackWares2D(rackWares);
						if (rackWares2D.getMinX() < 0 ||
								rackWares2D.getMaxX() > dx ||
								rackWares2D.getMinY() < 0 ||
								rackWares2D.getMaxY() > dy ||
								rackWares.getWares_length() * rackWares.getCount_length_on_shelf() < 0 ||
								rackWares.getWares_length() * rackWares.getCount_length_on_shelf() > dz) {
							fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_OVERFLOW_WARES(), Rack.class, i, rack.getCode_rack(), "rack_overflow_wares"));
						}
					}
				}
			}
		}
		// TODO
		//	стеллажи не могут пересекаться(не сохраняется, выделяется один из стеллажей)
		for (int i = 0; i < rack2DList.size(); i++){
			for (int j=i+1; j<rack2DList.size(); j++){
				final Rack2D a=rack2DList.get(i);
				final Rack2D b=rack2DList.get(j);
				Intersection2DUtils.intersection(a,b);
			}
		}

		final JsonObject jsonObject = new JsonObject();
		if (fieldExceptionList.isEmpty()) {
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
		jsonObject.addProperty(SectorConst.CODE_SECTOR, sector.getCode_sector());
		} else {
			JsonArray jsonArray = new JsonArray();
			for (final EntityFieldException entityFieldException : fieldExceptionList) {
				jsonArray.add(entityFieldException.toJSON());
			}
			jsonObject.add("errorField", jsonArray);
		}
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
