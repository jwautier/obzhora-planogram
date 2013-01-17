package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.*;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.RackStateModel;
import planograma.model.SectorModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 21.03.12
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_SECTOR_EDIT)
public class SectorEdit extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_EDIT;

	private static final Logger LOG = Logger.getLogger(SectorEdit.class);

	private SectorModel sectorModel;
	private RackModel rackModel;
	private RackStateModel rackStateModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackStateModel=RackStateModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final JsonArray rackStateList = new JsonArray();
		final JsonArray rackStateInSectorList = new JsonArray();
		final UserContext userContext=getUserContext(session);
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final Sector sector = sectorModel.select(userContext, code_sector);
		final List<Rack> list = rackModel.list(userContext, code_sector);
		for (final Rack rack : list) {
			jsonArray.add(rack.toJsonObject());
			final RackState rackState= rackStateModel.selectRackState(userContext, rack.getCode_rack());
			rackStateList.add(rackState.toJsonObject());
			final RackStateInSector rackStateInSector= rackStateModel.selectRackStateInSector(userContext, rack.getCode_rack());
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
