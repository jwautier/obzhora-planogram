package planograma.data;


import com.google.gson.JsonObject;
import planograma.constant.data.SectorStateConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 26.02.12
 * Time: 4:14
 * Состояние зала (сектор, этаж)
 */
public class SectorState implements IJsonObject {
	/**
	 * Код сектора
	 */
	private Integer code_sector;
	/**
	 * Состояние зала
	 */
	private StateSector state_sector;
	/**
	 * пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * дата изъятия для редактирования
	 */
	private Date date_draft;
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

	public SectorState(Integer code_sector, StateSector state_sector, Integer user_draft, Date date_draft, Date date_active, Integer user_active, Date date_complete, Integer user_complete) {
		this.code_sector = code_sector;
		this.state_sector = state_sector;
		this.user_draft = user_draft;
		this.date_draft = date_draft;
		this.date_active = date_active;
		this.user_active = user_active;
		this.date_complete = date_complete;
		this.user_complete = user_complete;
	}

	public Integer getCode_sector() {
		return code_sector;
	}

	public void setCode_sector(Integer code_sector) {
		this.code_sector = code_sector;
	}

	public StateSector getState_sector() {
		return state_sector;
	}

	public void setState_sector(StateSector state_sector) {
		this.state_sector = state_sector;
	}

	public void setState_sector(String state_sector) {
		this.state_sector = (state_sector != null) ? StateSector.valueOf(state_sector) : null;
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

	public SectorState(final ResultSet resultSet) throws SQLException {
		code_sector = resultSet.getInt(SectorStateConst.CODE_SECTOR);
		setState_sector(resultSet.getString(SectorStateConst.STATE_SECTOR));
		user_draft = resultSet.getInt(SectorStateConst.USER_DRAFT);
		date_draft = resultSet.getTimestamp(SectorStateConst.DATE_DRAFT);
		user_active = resultSet.getInt(SectorStateConst.USER_ACTIVE);
		date_active = resultSet.getTimestamp(SectorStateConst.DATE_ACTIVE);
		user_complete = resultSet.getInt(SectorStateConst.USER_COMPLETE);
		date_complete = resultSet.getTimestamp(SectorStateConst.DATE_COMPLETE);
	}

	public SectorState(final JsonObject sectorJson) {
		code_sector = JsonUtils.getInteger(sectorJson, SectorStateConst.CODE_SECTOR);
		setState_sector(JsonUtils.getString(sectorJson, SectorStateConst.STATE_SECTOR));
		user_draft = JsonUtils.getInteger(sectorJson, SectorStateConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(sectorJson, SectorStateConst.DATE_DRAFT);
		user_active = JsonUtils.getInteger(sectorJson, SectorStateConst.USER_ACTIVE);
		date_active = JsonUtils.getDate(sectorJson, SectorStateConst.DATE_ACTIVE);
		user_complete = JsonUtils.getInteger(sectorJson, SectorStateConst.USER_COMPLETE);
		date_complete = JsonUtils.getDate(sectorJson, SectorStateConst.DATE_COMPLETE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(SectorStateConst.CODE_SECTOR, code_sector);
		jsonObject.addProperty(SectorStateConst.STATE_SECTOR, (state_sector != null) ? state_sector.name() : null);
		// TODO remove state_sector_desc
		jsonObject.addProperty("state_sector_desc", (state_sector != null) ? state_sector.getDesc() : null);
		jsonObject.addProperty(SectorStateConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, SectorStateConst.DATE_DRAFT, date_draft);
		jsonObject.addProperty(SectorStateConst.USER_ACTIVE, user_active);
		JsonUtils.set(jsonObject, SectorStateConst.DATE_ACTIVE, date_active);
		jsonObject.addProperty(SectorStateConst.USER_COMPLETE, user_complete);
		JsonUtils.set(jsonObject, SectorStateConst.DATE_COMPLETE, date_complete);
		return jsonObject;
	}
}
