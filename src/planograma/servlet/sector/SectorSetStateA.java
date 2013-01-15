package planograma.servlet.sector;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.SectorConst;
import planograma.data.SectorState;
import planograma.data.StateSector;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.SectorStateModel;
import planograma.model.SecurityModel;
import planograma.model.UserModel;
import planograma.servlet.AbstractAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * User: poljakov
 * Date: 15.01.13
 * Time: 9:50
 */
@WebServlet("/" + UrlConst.URL_SECTOR_SET_STATE_A)
public class SectorSetStateA extends AbstractAction {

	public static final String URL = UrlConst.URL_SECTOR_SET_STATE_A;

	private static final Logger LOG = Logger.getLogger(SectorSetStateA.class);

	private SectorStateModel sectorStateModel;
	private UserModel userModel;
	private SecurityModel securityModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sectorStateModel = SectorStateModel.getInstance();
		userModel = UserModel.getInstance();
		securityModel = SecurityModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		time = System.currentTimeMillis() - time;
		final JsonObject jsonObject = new JsonObject();
		final int code_sector = requestData.getAsJsonObject().get(SectorConst.CODE_SECTOR).getAsInt();
		final UserContext userContext = getUserContext(session);
		final SectorState sectorState = sectorStateModel.select(userContext, code_sector);
		boolean canSetStateA =
				// зал в состоянии черновик
				sectorState.getState_sector() == StateSector.D
						&&
						// и я являюсь редактором зала
						(sectorState.getUser_draft() == userModel.getCodeUser(userContext)
								// или есть право на пренудительное составление зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_SECTOR_STATE_SET_A));
		if (canSetStateA) {
			sectorStateModel.changestate(userContext, code_sector, StateSector.A);
		}
		jsonObject.addProperty("canSetStateA", canSetStateA);
		LOG.debug(time + " ms");
		return jsonObject;
	}

}
