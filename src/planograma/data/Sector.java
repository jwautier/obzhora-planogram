package planograma.data;


import com.google.gson.JsonObject;
import planograma.constant.data.SectorConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 26.02.12
 * Time: 4:14
 * Сектор (этаж)
 */
public class Sector implements IJsonObject {
	/**
	 * Код ТП
	 */
	private Integer code_shop;
	/**
	 * Код сектора
	 */
	private Integer code_sector;
	private StateSector state_sector;
	/**
	 * Название
	 */
	private String name_sector;
	/**
	 * Длина, см
	 */
	private Integer length;
	/**
	 * Ширина, см
	 */
	private Integer width;
	/**
	 * Высота, см
	 */
	private Integer height;
	/**
	 * Автор
	 */
	private Integer user_insert;
	/**
	 * Дата создания
	 */
	private Date date_insert;
	/**
	 * Редактор
	 */
	private Integer user_update;
	/**
	 * Дата изменения
	 */
	private Date date_update;
	/**
	 * пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * дата изъятия для редактирования
	 */
	private Date date_draft;

	public Sector(Integer code_shop, Integer code_sector, StateSector state_sector, String name_sector,
				  Integer length, Integer width, Integer height,
				  Integer user_insert, Date date_insert,
				  Integer user_update, Date date_update,
				  Integer user_draft, Date date_draft) {
		this.code_shop = code_shop;
		this.code_sector = code_sector;
		this.state_sector = state_sector;
		this.name_sector = name_sector;
		this.length = length;
		this.width = width;
		this.height = height;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.user_draft = user_draft;
		this.date_draft = date_draft;
	}

	public Integer getCode_shop() {
		return code_shop;
	}

	public void setCode_shop(Integer code_shop) {
		this.code_shop = code_shop;
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

	public String getName_sector() {
		return name_sector;
	}

	public void setName_sector(String name_sector) {
		this.name_sector = name_sector;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getUser_insert() {
		return user_insert;
	}

	public void setUser_insert(Integer user_insert) {
		this.user_insert = user_insert;
	}

	public Date getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(Date date_insert) {
		this.date_insert = date_insert;
	}

	public Integer getUser_update() {
		return user_update;
	}

	public void setUser_update(Integer user_update) {
		this.user_update = user_update;
	}

	public Date getDate_update() {
		return date_update;
	}

	public void setDate_update(Date date_update) {
		this.date_update = date_update;
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

	public Sector(final ResultSet resultSet) throws SQLException {
		code_shop = resultSet.getInt(SectorConst.CODE_SHOP);
		code_sector = resultSet.getInt(SectorConst.CODE_SECTOR);
		setState_sector(resultSet.getString(SectorConst.STATE_SECTOR));
		name_sector = resultSet.getString(SectorConst.NAME_SECTOR);
		length = resultSet.getInt(SectorConst.LENGTH);
		width = resultSet.getInt(SectorConst.WIDTH);
		height = resultSet.getInt(SectorConst.HEIGHT);
		user_insert = resultSet.getInt(SectorConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(SectorConst.DATE_INSERT);
		user_update = resultSet.getInt(SectorConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(SectorConst.DATE_UPDATE);
		user_draft = resultSet.getInt(SectorConst.USER_DRAFT);
		date_draft = resultSet.getTimestamp(SectorConst.DATE_DRAFT);
	}

	public Sector(final JsonObject sectorJson) {
		code_shop = JsonUtils.getInteger(sectorJson, SectorConst.CODE_SHOP);
		code_sector = JsonUtils.getInteger(sectorJson, SectorConst.CODE_SECTOR);
		setState_sector(JsonUtils.getString(sectorJson, SectorConst.STATE_SECTOR));
		name_sector = JsonUtils.getString(sectorJson, SectorConst.NAME_SECTOR);
		length = JsonUtils.getInteger(sectorJson, SectorConst.LENGTH);
		width = JsonUtils.getInteger(sectorJson, SectorConst.WIDTH);
		height = JsonUtils.getInteger(sectorJson, SectorConst.HEIGHT);
		user_insert = JsonUtils.getInteger(sectorJson, SectorConst.USER_INSERT);
		date_insert = JsonUtils.getDate(sectorJson, SectorConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(sectorJson, SectorConst.USER_UPDATE);
		date_update = JsonUtils.getDate(sectorJson, SectorConst.DATE_UPDATE);
		user_draft = JsonUtils.getInteger(sectorJson, SectorConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(sectorJson, SectorConst.DATE_DRAFT);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(SectorConst.CODE_SHOP, code_shop);
		jsonObject.addProperty(SectorConst.CODE_SECTOR, code_sector);
		jsonObject.addProperty(SectorConst.STATE_SECTOR, (state_sector != null) ? state_sector.name() : null);
		jsonObject.addProperty(SectorConst.NAME_SECTOR, name_sector);
		jsonObject.addProperty(SectorConst.LENGTH, length);
		jsonObject.addProperty(SectorConst.WIDTH, width);
		jsonObject.addProperty(SectorConst.HEIGHT, height);
		jsonObject.addProperty(SectorConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, SectorConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(SectorConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, SectorConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(SectorConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, SectorConst.DATE_DRAFT, date_draft);
		return jsonObject;
	}
}
