package planograma.servlet.wares;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.RackShelf;
import planograma.data.RackWares;
import planograma.data.UserContext;
import planograma.data.geometry.RackShelf2D;
import planograma.data.geometry.RackWares2D;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackShelfModel;
import planograma.model.RackWaresHModel;
import planograma.model.RackWaresModel;
import planograma.servlet.AbstractAction;
import planograma.servlet.wares.rackWaresPlacementSaveHelp.GroupRackWares;
import planograma.servlet.wares.rackWaresPlacementSaveHelp.GroupRackWaresComparator;
import planograma.utils.geometry.Intersection2DUtils;
import planograma.utils.geometry.Rectangle2D;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

	public static final Logger LOG = Logger.getLogger(RackWaresPlacementSave.class);

	private RackShelfModel rackShelfModel;
	private RackWaresModel rackWaresModel;
	private RackWaresHModel rackWaresHModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackShelfModel = RackShelfModel.getInstance();
		rackWaresModel = RackWaresModel.getInstance();
		rackWaresHModel = RackWaresHModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_RACK_WARES_PLACEMENT);
		final int code_rack = requestData.getAsJsonObject().getAsJsonPrimitive(RackConst.CODE_RACK).getAsInt();
		final JsonArray rackWaresListJson = requestData.getAsJsonObject().getAsJsonArray("rackWaresList");
		final List<RackWares2D> rackWares2DList = new ArrayList<RackWares2D>(rackWaresListJson.size());
		for (int i = 0; i < rackWaresListJson.size(); i++) {
			final JsonObject itemJson = rackWaresListJson.get(i).getAsJsonObject();
			final RackWares2D item = new RackWares2D(new RackWares(itemJson));
			rackWares2DList.add(item);
		}
		final List<RackShelf> rackShelfList = rackShelfModel.list(userContext, code_rack);
		final List<RackShelf2D> rackShelf2DList = new ArrayList<RackShelf2D>(rackShelfList.size());
		for (final RackShelf rackShelf : rackShelfList) {
			rackShelf2DList.add(new RackShelf2D(rackShelf));
		}
		// проставить номера
		final List<GroupRackWares> groups = split(rackShelf2DList, rackWares2DList);
		for (int i = 0; i < groups.size(); i++) {
			final GroupRackWares group = groups.get(i);
			for (final RackWares2D rackWares2D : group.getWaresInGroup()) {
				rackWares2D.getRackWares().setOrder_number_on_rack(i + 1);
			}
		}

		List<RackWares> oldItemList = rackWaresModel.list(userContext, code_rack);
		final int version = rackWaresHModel.nextVersion(userContext);
		for (final RackWares oldItem : oldItemList) {
			RackWares findItem = null;
//				поиск среди сохраненых рание
			for (int i = 0; findItem == null && i < rackWares2DList.size(); i++) {
				final RackWares currentItem = rackWares2DList.get(i).getRackWares();
				if (oldItem.getCode_wares_on_rack().equals(currentItem.getCode_wares_on_rack())) {
					findItem = currentItem;
//					запись была обновлена
					rackWaresModel.update(userContext, findItem, version);
					rackWares2DList.remove(i);
					i--;
				}
			}
			if (findItem == null) {
//				запись была удалена
				rackWaresModel.delete(userContext, oldItem.getCode_wares_on_rack());
			}
		}
		for (final RackWares2D newItem : rackWares2DList) {
//			запись была добавлена
			newItem.getRackWares().setCode_rack(code_rack);
			rackWaresModel.insert(userContext, newItem.getRackWares(), version);
		}
		commit(userContext);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return null;
	}

	private List<GroupRackWares> split(final List<RackShelf2D> rackShelf2DList, final List<RackWares2D> rackWares2DList) {
		final List<GroupRackWares> groups = new ArrayList();
		// обединение одинаковых товаров в группы
		float minWidth = Integer.MAX_VALUE;
		float minHeight = Integer.MAX_VALUE;

		for (final RackWares2D rackWares2D : rackWares2DList) {
			minWidth = Math.min(minWidth, rackWares2D.getMaxX() - rackWares2D.getMinX());
			minHeight = Math.min(minHeight, rackWares2D.getMaxY() - rackWares2D.getMinY());
			GroupRackWares groupRackWares = findGroup(groups, rackWares2D.getRackWares().getCode_wares());
			if (groupRackWares == null) {
				groupRackWares = new GroupRackWares(rackWares2D);
				groups.add(groupRackWares);
			} else {
				groupRackWares.add(rackWares2D);
			}
		}
		// проверить цельность группы (нет пересечений с другими товарами или полками)
		for (int i = 0; i < groups.size(); i++) {
			final GroupRackWares group = groups.get(i);
			if (!group.getWaresInGroup().isEmpty()) {
				for (final RackShelf2D rackShelf2D : rackShelf2DList) {
					if (!group.getWaresInGroup().isEmpty()) {
						final Rectangle2D rackShelfRectangle2D = rackShelf2D.getDescribedRectangle2D();
						final Rectangle2D intersection = Intersection2DUtils.intersection(group, rackShelfRectangle2D);
						if (intersection != null) {
							split(groups, group, intersection);
						}
					} else break;
				}
				if (!group.getWaresInGroup().isEmpty()) {
//					for (final RackWares2D rackWares2D : rackWares2DList) {
//						if (!group.getWaresInGroup().isEmpty()) {
//							if (group.getCode_wares() != rackWares2D.getRackWares().getCode_wares()) {
//								final Rectangle2D rackWaresRectangle2D = rackWares2D.getDescribedRectangle2D();
//								final Rectangle2D intersection = Intersection2DUtils.intersection(group, rackWaresRectangle2D);
//								if (intersection != null) {
//									split(groups, group, intersection);
//								}
//							}
//						} else break;
//					}

					for (int j = i + 1; !group.getWaresInGroup().isEmpty() && j < groups.size(); j++) {
						final GroupRackWares b = groups.get(j);
						if (group.getCode_wares() != b.getCode_wares()) {
							final Rectangle2D intersection = Intersection2DUtils.intersection(group, b);
							if (intersection != null) {
								split(groups, group, intersection);
							}
						}
					}
				}
			}
		}
		// удаление пустых групп
		final Iterator<GroupRackWares> iterator = groups.iterator();
		while (iterator.hasNext()) {
			final GroupRackWares group = iterator.next();
			if (group.getWaresInGroup().isEmpty())
				iterator.remove();
		}
		// проставить номера
		Collections.sort(groups, new GroupRackWaresComparator(minWidth / 2, minHeight / 2));
		return groups;
	}

	private void split(final List<GroupRackWares> groups, final GroupRackWares group, final Rectangle2D intersection) {
		final GroupRackWares g[] = new GroupRackWares[4];
		final List<RackWares2D> list = new ArrayList<RackWares2D>(group.getWaresInGroup());
		group.getWaresInGroup().clear();
		group.calc();

		for (int i = 0; i < list.size(); i++) {
			final RackWares2D rackWares2D = list.get(i);
			final int index;
			if (rackWares2D.getMaxY() < intersection.getMinY()) {
				index = 0;
			} else if (rackWares2D.getMinY() > intersection.getMaxY()) {
				index = 3;
			} else {
				if (rackWares2D.getMaxX() < intersection.getMinX()) {
					index = 1;
				} else if (rackWares2D.getMinX() > intersection.getMaxX()) {
					index = 2;
				} else {
					index = 555;
				}
			}
			if (index < 4) {
				if (g[index] == null) {
					g[index] = new GroupRackWares(rackWares2D);
				} else {
					g[index].add(rackWares2D);
				}
			} else {
				group.add(rackWares2D);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (g[i] != null) {
				groups.add(g[i]);
			}
		}
	}

	private GroupRackWares findGroup(final List<GroupRackWares> groups, int code_wares) {
		for (final GroupRackWares groupRackWares : groups) {
			if (groupRackWares.getCode_wares() == code_wares)
				return groupRackWares;
		}
		return null;
	}

}
