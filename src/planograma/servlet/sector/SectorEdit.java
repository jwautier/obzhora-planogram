package planograma.servlet.sector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.Rack;
import planograma.data.Sector;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
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

	public static final Logger LOG = Logger.getLogger(SectorEdit.class);

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
		long time = System.currentTimeMillis();
		final JsonObject jsonObject = new JsonObject();
		final JsonArray jsonArray = new JsonArray();
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final Sector sector = sectorModel.select(getUserContext(session), code_sector);
		final List<Rack> list = rackModel.list(getUserContext(session), code_sector);
		for (final Rack rack : list) {
			jsonArray.add(rack.toJsonObject());
		}
		jsonObject.add("sector", sector.toJsonObject());
		jsonObject.add("rackList", jsonArray);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
