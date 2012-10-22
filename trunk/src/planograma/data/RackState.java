package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackStateConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18.03.12
 * Time: 9:39
 * Состояние стеллажа
 */
public class RackState implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Состояние стеллажа
	 */
	private StateRack state_rack;
	/**
	 * дата изъятия для редактирования
	 */
	private Date date_draft;
	/**
	 * пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * дата составления (редактирование окончено)
	 */
	private Date date_active;
	/**
	 * пользователь перевевший в состояние составлено
	 */
	private Integer user_active;
	/**
	 * дата выполнения
	 */
	private Date date_complete;
	/**
	 * пользователь перевевший в состояние выполнено
	 */
	private Integer user_complete;

	public RackState(Integer code_rack, StateRack state_rack, Date date_draft, Integer user_draft, Date date_active, Integer user_active, Date date_complete, Integer user_complete) {
		this.code_rack = code_rack;
		this.state_rack = state_rack;
		this.date_draft = date_draft;
		this.user_draft = user_draft;
		this.date_active = date_active;
		this.user_active = user_active;
		this.date_complete = date_complete;
		this.user_complete = user_complete;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public StateRack getState_rack() {
		return state_rack;
	}

	public String getState_rackAtStr() {
		return (state_rack != null) ? state_rack.name() : null;
	}

	public void setState_rack(StateRack state_rack) {
		this.state_rack = state_rack;
	}

	public void setState_rack(String state_rack) {
		this.state_rack = (state_rack != null) ? StateRack.valueOf(state_rack) : null;
	}

	public Integer getUser_draft() {
		return user_draft;
	}

	public void setUser_draft(Integer user_draft) {
		this.user_draft = user_draft;
	}

	public Date getDate_draft() {
		return date_draft;
	}

	public void setDate_draft(Date date_draft) {
		this.date_draft = date_draft;
	}

	public Date getDate_active() {
		return date_active;
	}

	public void setDate_active(Date date_active) {
		this.date_active = date_active;
	}

	public Integer getUser_active() {
		return user_active;
	}

	public void setUser_active(Integer user_active) {
		this.user_active = user_active;
	}

	public Date getDate_complete() {
		return date_complete;
	}

	public void setDate_complete(Date date_complete) {
		this.date_complete = date_complete;
	}

	public Integer getUser_complete() {
		return user_complete;
	}

	public void setUser_complete(Integer user_complete) {
		this.user_complete = user_complete;
	}

	public RackState(final ResultSet resultSet) throws SQLException {
		code_rack = resultSet.getInt(RackStateConst.CODE_RACK);
		setState_rack(resultSet.getString(RackStateConst.STATE_RACK));
		user_draft = resultSet.getInt(RackStateConst.USER_DRAFT);
		date_draft = resultSet.getTimestamp(RackStateConst.DATE_DRAFT);
		user_active = resultSet.getInt(RackStateConst.USER_ACTIVE);
		date_active = resultSet.getTimestamp(RackStateConst.DATE_ACTIVE);
		user_complete = resultSet.getInt(RackStateConst.USER_COMPLETE);
		date_complete = resultSet.getTimestamp(RackStateConst.DATE_COMPLETE);
	}

	public RackState(final JsonObject rackJson) {
		code_rack = JsonUtils.getInteger(rackJson, RackStateConst.CODE_RACK);
		setState_rack(JsonUtils.getString(rackJson, RackStateConst.STATE_RACK));
		user_draft = JsonUtils.getInteger(rackJson, RackStateConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(rackJson, RackStateConst.DATE_DRAFT);
		user_active = JsonUtils.getInteger(rackJson, RackStateConst.USER_ACTIVE);
		date_active = JsonUtils.getDate(rackJson, RackStateConst.DATE_ACTIVE);
		user_complete = JsonUtils.getInteger(rackJson, RackStateConst.USER_COMPLETE);
		date_complete = JsonUtils.getDate(rackJson, RackStateConst.DATE_COMPLETE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackStateConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackStateConst.STATE_RACK, getState_rackAtStr());
		jsonObject.addProperty(RackStateConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, RackStateConst.DATE_DRAFT, date_draft);
		jsonObject.addProperty(RackStateConst.USER_ACTIVE, user_active);
		JsonUtils.set(jsonObject, RackStateConst.DATE_ACTIVE, date_active);
		jsonObject.addProperty(RackStateConst.USER_COMPLETE, user_complete);
		JsonUtils.set(jsonObject, RackStateConst.DATE_COMPLETE, date_complete);
		return jsonObject;
	}
}
