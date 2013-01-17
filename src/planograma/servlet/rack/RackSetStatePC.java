package planograma.servlet.rack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.EStateRack;
import planograma.data.RackState;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.RackStateModel;
import planograma.model.SecurityModel;
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
 * изменение состояния стеллажа на выполнен
 */
@WebServlet("/" + UrlConst.URL_RACK_SET_STATE_PC)
public class RackSetStatePC extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_SET_STATE_PC;

	private static final Logger LOG = Logger.getLogger(RackSetStatePC.class);

	private RackStateModel rackStateModel;
	private SecurityModel securityModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackStateModel = RackStateModel.getInstance();
		securityModel = SecurityModel.getInstance();
	}

	@Override
	protected JsonObject execute(HttpSession session, JsonElement requestData) throws UnauthorizedException, SQLException {
		long time = System.currentTimeMillis();
		time = System.currentTimeMillis() - time;
		final JsonObject jsonObject = new JsonObject();
		final int code_rack = requestData.getAsJsonObject().get(RackConst.CODE_RACK).getAsInt();
		final UserContext userContext = getUserContext(session);
		final RackState rackState = rackStateModel.selectRackState(userContext, code_rack);
		boolean canSetStatePC =
				// стеллаж в состоянии утвержден
				rackState.getState_rack() == EStateRack.A
						&&
						// есть право на подтверждение выполнения стеллажа
						(securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_PC)
								// есть доступ к глобальному выполнению стеллажей зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC));
		if (canSetStatePC) {
			rackStateModel.changestate(userContext, code_rack, null, EStateRack.PC);
		}
		jsonObject.addProperty("canSetStatePC", canSetStatePC);
		LOG.debug(time + " ms");
		return jsonObject;
	}
}
