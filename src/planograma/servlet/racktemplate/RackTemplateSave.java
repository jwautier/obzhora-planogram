package planograma.servlet.racktemplate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.PlanogramMessage;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.VerificationConst;
import planograma.constant.data.RackShelfTemplateConst;
import planograma.constant.data.RackTemplateConst;
import planograma.data.LoadSide;
import planograma.data.RackShelfTemplate;
import planograma.data.RackTemplate;
import planograma.data.UserContext;
import planograma.data.geometry.RackShelfTemplate2D;
import planograma.exception.EntityFieldException;
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

	private static final Logger LOG = Logger.getLogger(RackTemplateSave.class);

	private RackTemplateModel rackTemplateModel;
	private RackShelfTemplateModel rackShelfTemplateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackTemplateModel = RackTemplateModel.getInstance();
		rackShelfTemplateModel = RackShelfTemplateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
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

		//	ПРОВЕРКИ
		List<EntityFieldException> fieldExceptionList = new ArrayList<EntityFieldException>();
		//	параметры высоты, ширины, глубины, полезная высота, полезная ширина, полезная глубина меньше 10мм(не сохраняется)
		if (rackTemplate.getHeight() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_HEIGHT_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.HEIGHT));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_LENGTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.LENGTH));
			}
		}
		if (rackTemplate.getWidth() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_LENGTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.LENGTH));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_HEIGHT_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.HEIGHT));
			}
		}
		if (rackTemplate.getLength() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WIDTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.WIDTH));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_WIDTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.WIDTH));
			}
		}
		if (rackTemplate.getReal_height() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_HEIGHT_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_HEIGHT));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_LENGTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_LENGTH));
			}
		}
		if (rackTemplate.getReal_width() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_LENGTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_LENGTH));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_HEIGHT_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_HEIGHT));
			}
		}
		if (rackTemplate.getReal_length() < VerificationConst.MIN_RACK_DIMENSIONS) {
			if (rackTemplate.getLoad_side() == LoadSide.F) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_WIDTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_WIDTH));
			} else {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_REAL_WIDTH_TOO_LITTLE(), RackTemplate.class, 0, rackTemplate.getCode_rack_template(), RackTemplateConst.REAL_WIDTH));
			}
		}

		// полки по высоте глубине и ширине должны быть больше 5мм(не сохраняется, выделяется одна из полок)
		for (int i = 0; i < itemList.size(); i++) {
			final RackShelfTemplate newItem = itemList.get(i);
			if (newItem.getShelf_width() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_WIDTH_TOO_LITTLE(), RackShelfTemplate.class, i, newItem.getCode_shelf_template(), RackShelfTemplateConst.SHELF_WIDTH));
			}
			if (newItem.getShelf_height() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_HEIGHT_TOO_LITTLE(), RackShelfTemplate.class, i, newItem.getCode_shelf_template(), RackShelfTemplateConst.SHELF_HEIGHT));
			}
			if (newItem.getShelf_length() < VerificationConst.MIN_RACK_SHELF_DIMENSIONS) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_LENGTH_TOO_LITTLE(), RackShelfTemplate.class, i, newItem.getCode_shelf_template(), RackShelfTemplateConst.SHELF_LENGTH));
			}
		}
		// полка не может выходить за пределы стеллажа(не сохраняется, выделяется одна из полок)
		float dx;
		float dy;
		float dz;
		// относительно стороны загрузки
		if (rackTemplate.getLoad_side() == LoadSide.F) {
			dx = rackTemplate.getLength();
			dy = rackTemplate.getHeight();
			dz = rackTemplate.getWidth();
		} else {
			dx = rackTemplate.getLength();
			dy = rackTemplate.getWidth();
			dz = rackTemplate.getHeight();
		}
		for (int i = 0; i < itemList.size(); i++) {
			final RackShelfTemplate rackShelfTemplate = itemList.get(i);
			final RackShelfTemplate2D shelf2D = new RackShelfTemplate2D(rackShelfTemplate);
			if (shelf2D.getMinX() < 0 ||
					shelf2D.getMaxX() > dx ||
					shelf2D.getMinY() < 0 ||
					shelf2D.getMaxY() > dy ||
					rackShelfTemplate.getShelf_length() < 0 ||
					rackShelfTemplate.getShelf_length() > dz) {
				fieldExceptionList.add(new EntityFieldException(PlanogramMessage.RACK_SHELF_OUTSIDE_RACK(), RackShelfTemplate.class, i, shelf2D.getRackShelfTemplate().getCode_shelf_template(), "outside"));
			}
		}

		final JsonObject jsonObject = new JsonObject();
		if (fieldExceptionList.isEmpty()) {
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
			jsonObject.addProperty(RackTemplateConst.CODE_RACK_TEMPLATE, rackTemplate.getCode_rack_template());
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
