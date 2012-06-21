package planograma.servlet.sector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.Rack;
import planograma.data.RackShelf;
import planograma.data.UserContext;
import planograma.exception.NotAccessException;
import planograma.exception.UnauthorizedException;
import planograma.model.RackModel;
import planograma.model.RackShelfModel;
import planograma.model.SectorModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 26.02.12
 * Time: 2:18
 * To change this template use File | Settings | File Templates.
 */
@WebServlet("/" + UrlConst.URL_SECTOR_REMOVE)
public class SectorRemove extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_REMOVE;

	public static final Logger LOG = Logger.getLogger(SectorRemove.class);

	private SectorModel sectorModel;
	private RackModel rackModel;
	private RackShelfModel rackShelfModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorModel = SectorModel.getInstance();
		rackModel = RackModel.getInstance();
		rackShelfModel = RackShelfModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException, NotAccessException {
		long time = System.currentTimeMillis();
		final UserContext userContext = getUserContext(session);
		checkAccess(userContext, SecurityConst.ACCESS_SECTOR_EDIT);
		final int code_sector = Integer.valueOf(requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsString());
		for (final Rack rack : rackModel.list(userContext, code_sector)) {
			for (final RackShelf rackShelf : rackShelfModel.list(userContext, rack.getCode_rack())) {
				rackShelfModel.delete(userContext, rackShelf.getCode_shelf());
			}
			rackModel.delete(userContext, rack.getCode_rack());
		}
		sectorModel.delete(userContext, code_sector);
		commit(userContext);
		time = System.currentTimeMillis() - time;
		LOG.debug(time + " ms");
		return null;
	}
}
