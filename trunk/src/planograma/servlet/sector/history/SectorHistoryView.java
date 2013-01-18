package planograma.servlet.sector.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.*;
import planograma.exception.UnauthorizedException;
import planograma.model.history.RackHModel;
import planograma.model.history.RackStateHModel;
import planograma.model.history.RackStateInSectorHModel;
import planograma.model.history.SectorHModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_SECTOR_HISTORY_VIEW)
public class SectorHistoryView extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_HISTORY_VIEW;

	private static final Logger LOG = Logger.getLogger(SectorHistoryView.class);

	private SectorHModel sectorModel;
	private RackHModel rackModel;
	private RackStateHModel rackStateHModel;
	private RackStateInSectorHModel rackStateInSectorHModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorHModel.getInstance();
		rackModel = RackHModel.getInstance();
		rackStateHModel = RackStateHModel.getInstance();
		rackStateInSectorHModel = RackStateInSectorHModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final JsonArray rackStateList = new JsonArray();
		final JsonArray rackStateInSectorList = new JsonArray();
		final UserContext userContext = getUserContext(session);
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final Date date = new Date(requestData.getAsJsonObject().get("date").getAsLong());

		final Sector sector = sectorModel.select(userContext, code_sector, date);
		final List<Rack> list = rackModel.list(userContext, code_sector, date);
		for (final Rack rack : list) {
			jsonArray.add(rack.toJsonObject());
			RackState rackState = rackStateHModel.select(userContext, rack.getCode_rack(), date);
			if (rackState == null) {
				rackState = new RackState(rack.getCode_rack(), EStateRack.D, null, null, null, null, null, null);
			}
			rackStateList.add(rackState.toJsonObject());
			RackStateInSector rackStateInSector = rackStateInSectorHModel.select(userContext, rack.getCode_rack(), date);
			if (rackStateInSector == null) {
				rackStateInSector = new RackStateInSector(rack.getCode_rack(), EStateRack.D, null, null, null, null, null, null);
			}
			rackStateInSectorList.add(rackStateInSector.toJsonObject());
		}
		jsonObject.add("sector", sector.toJsonObject());
		jsonObject.add("rackList", jsonArray);
		jsonObject.add("rackStateList", rackStateList);
		jsonObject.add("rackStateInSectorList", rackStateInSectorList);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
