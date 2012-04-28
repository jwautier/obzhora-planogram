package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackTemplateConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18.03.12
 * Time: 9:39
 * Шаблон стеллажа
 */
public class RackTemplate implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Состояние стеллажа
	 */
	private StateRack state_rack_template;
	/**
	 * Название
	 */
	private String name_rack_template;
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
	 * Сторона загрузи
	 */
	private LoadSide load_side;
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
	 * Пользователь изъявший для редактирования
	 */
	private Integer user_draft;
	/**
	 * Дата изъятия для редактирования
	 */
	private Date date_draft;

	public RackTemplate(Integer code_rack_template, StateRack state_rack_template, String name_rack_template, Integer length, Integer width, Integer height, LoadSide load_side, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer user_draft, Date date_draft) {
		this.code_rack_template = code_rack_template;
		this.state_rack_template=state_rack_template;
		this.name_rack_template = name_rack_template;
		this.length = length;
		this.width = width;
		this.height = height;
		this.load_side=load_side;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.user_draft = user_draft;
		this.date_draft = date_draft;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public StateRack getState_rack_template() {
		return state_rack_template;
	}

	public void setState_rack_template(StateRack state_rack_template) {
		this.state_rack_template = state_rack_template;
	}

	public void setState_rack_template(String state_rack_template) {
		this.state_rack_template = (state_rack_template != null) ? StateRack.valueOf(state_rack_template) : null;
	}

	public String getName_rack_template() {
		return name_rack_template;
	}

	public void setName_rack_template(String name_rack_template) {
		this.name_rack_template = name_rack_template;
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

	public LoadSide getLoad_side() {
		return load_side;
	}

	public String getLoad_sideAtStr() {
		return (load_side!=null)?load_side.name():null;
	}

	public void setLoad_side(LoadSide load_side) {
		this.load_side = load_side;
	}

	public void setLoad_side(String load_side) {
		this.load_side = (load_side != null) ? LoadSide.valueOf(load_side) : null;
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

	public RackTemplate(final ResultSet resultSet) throws SQLException {
		code_rack_template = resultSet.getInt(RackTemplateConst.CODE_RACK_TEMPLATE);
		setState_rack_template(resultSet.getString(RackTemplateConst.STATE_RACK_TEMPLATE));
		name_rack_template = resultSet.getString(RackTemplateConst.NAME_RACK_TEMPLATE);
		length = resultSet.getInt(RackTemplateConst.LENGTH);
		width = resultSet.getInt(RackTemplateConst.WIDTH);
		height = resultSet.getInt(RackTemplateConst.HEIGHT);
		setLoad_side(resultSet.getString(RackTemplateConst.LOAD_SIDE));
		code_rack_template = resultSet.getInt(RackTemplateConst.CODE_RACK_TEMPLATE);
		user_insert = resultSet.getInt(RackTemplateConst.USER_INSERT);
		date_insert = resultSet.getDate(RackTemplateConst.DATE_INSERT);
		user_update = resultSet.getInt(RackTemplateConst.USER_UPDATE);
		date_update = resultSet.getDate(RackTemplateConst.DATE_UPDATE);
		user_draft = resultSet.getInt(RackTemplateConst.USER_DRAFT);
		date_draft = resultSet.getDate(RackTemplateConst.DATE_DRAFT);
	}

	public RackTemplate(final JsonObject rackJson) {
		code_rack_template = JsonUtils.getInteger(rackJson, RackTemplateConst.CODE_RACK_TEMPLATE);
		setState_rack_template(JsonUtils.getString(rackJson, RackTemplateConst.STATE_RACK_TEMPLATE));
		name_rack_template = JsonUtils.getString(rackJson, RackTemplateConst.NAME_RACK_TEMPLATE);
		length = JsonUtils.getInteger(rackJson, RackTemplateConst.LENGTH);
		width = JsonUtils.getInteger(rackJson, RackTemplateConst.WIDTH);
		height = JsonUtils.getInteger(rackJson, RackTemplateConst.HEIGHT);
		setLoad_side(JsonUtils.getString(rackJson, RackTemplateConst.LOAD_SIDE));
		user_insert = JsonUtils.getInteger(rackJson, RackTemplateConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackJson, RackTemplateConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackJson, RackTemplateConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackJson, RackTemplateConst.DATE_UPDATE);
		user_draft = JsonUtils.getInteger(rackJson, RackTemplateConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(rackJson, RackTemplateConst.DATE_DRAFT);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackTemplateConst.STATE_RACK_TEMPLATE, (state_rack_template != null) ? state_rack_template.name() : null);
		jsonObject.addProperty(RackTemplateConst.NAME_RACK_TEMPLATE, name_rack_template);
		jsonObject.addProperty(RackTemplateConst.LENGTH, length);
		jsonObject.addProperty(RackTemplateConst.WIDTH, width);
		jsonObject.addProperty(RackTemplateConst.HEIGHT, height);
		jsonObject.addProperty(RackTemplateConst.LOAD_SIDE, (load_side != null) ? load_side.name() : null);
		jsonObject.addProperty(RackTemplateConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackTemplateConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackTemplateConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackTemplateConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(RackTemplateConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, RackTemplateConst.DATE_DRAFT, date_draft);
		return jsonObject;
	}
}
