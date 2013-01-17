package planograma.servlet.rack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import planograma.constant.SecurityConst;
import planograma.constant.UrlConst;
import planograma.constant.data.RackConst;
import planograma.data.EStateRack;
import planograma.data.RackState;
import planograma.data.RackStateInSector;
import planograma.data.UserContext;
import planograma.exception.UnauthorizedException;
import planograma.model.RackStateModel;
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
 * проверка доступности состояний пользователю для указаного стеллажа
 */
@WebServlet("/" + UrlConst.URL_RACK_CAN_SET_STATE)
public class RackCanSetState extends AbstractAction {

	public static final String URL = UrlConst.URL_RACK_CAN_SET_STATE;

	private static final Logger LOG = Logger.getLogger(RackCanSetState.class);

	private RackStateModel rackStateModel;
	private UserModel userModel;
	private SecurityModel securityModel;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		rackStateModel = RackStateModel.getInstance();
		userModel = UserModel.getInstance();
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
		final RackStateInSector rackStateInSector = rackStateModel.selectRackStateInSector(userContext, code_rack);
		boolean canSetStateA =
				// стеллаж в состоянии черновик
				rackState.getState_rack() == EStateRack.D
						&&
						// и я являюсь редактором стеллажа
						(rackState.getUser_draft() == userModel.getCodeUser(userContext)
								// или есть право на пренудительное составление зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_A)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		boolean canSetStateInSectorA =
				// стеллаж в состоянии черновик
				rackStateInSector.getState_rack() == EStateRack.D
						&&
						// и я являюсь редактором стеллажа
						(rackStateInSector.getUser_draft() == userModel.getCodeUser(userContext)
								// или есть право на пренудительное составление зала
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_A)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_SECTOR_IN_SECTOR_A));
		boolean canSetStatePC =
				// стеллаж в состоянии составлен
				rackState.getState_rack() == EStateRack.A
						&&
						// и есть право на подтверждение выполнения стеллажа
						(securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_SET_PC)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC));
		boolean canSetStateInSectorPC =
				// стеллаж в состоянии составлен
				rackStateInSector.getState_rack() == EStateRack.A
						&&
						// и есть право на подтверждение выполнения стеллажа
						(securityModel.canAccess(userContext, SecurityConst.ACCESS_RACK_STATE_IN_SECTOR_SET_PC)
								|| securityModel.canAccess(userContext, SecurityConst.ACCESS_ALL_RACK_SET_STATE_SET_STATE_IN_SECTOR_PC));
		jsonObject.addProperty("rack_state", rackState.getState_rack().name());
		jsonObject.addProperty("rack_state_in_sector", rackStateInSector.getState_rack().name());
		jsonObject.addProperty("canSetStateA", canSetStateA);
		jsonObject.addProperty("canSetStateInSectorA", canSetStateInSectorA);
		jsonObject.addProperty("canSetStatePC", canSetStatePC);
		jsonObject.addProperty("canSetStateInSectorPC", canSetStateInSectorPC);
		LOG.debug(time + " ms");
		return jsonObject;
	}

}
