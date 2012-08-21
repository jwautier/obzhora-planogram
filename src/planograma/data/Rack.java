package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18.03.12
 * Time: 9:39
 * стеллаж
 */
public class Rack implements IJsonObject {
	/**
	 * Код Этажа
	 */
	private Integer code_sector;
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Состояние стеллажа
	 */
	private StateRack state_rack;
	/**
	 * Название
	 */
	private String name_rack;
	/**
	 * Штрих код стеллажа
	 */
	private String rack_barcode;
	/**
	 * Длина, мм
	 */
	private Integer length;
	/**
	 * Ширина, мм
	 */
	private Integer width;
	/**
	 * Высота, мм
	 */
	private Integer height;
	/**
	 * Положение на этаже по длине
	 */
	private Integer x_coord;
	/**
	 * Положение на этаже по ширине
	 */
	private Integer y_coord;
	/**
	 * Угол поворота
	 */
	private Integer angle;
	/**
	 * Сторона загрузки
	 */
	private LoadSide load_side;
	/**
	 * Код шаблонна стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Запрет изменения размера стеллажа
	 */
	private boolean lock_size;
	/**
	 * Запрет перемещения стеллажа
	 */
	private boolean lock_move;
	/**
	 * Тип стеллажа (Стеллаж, касса...)
	 */
	private TypeRack type_race;
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
	/**
	 * Доступная длина, мм
	 */
	private Integer real_length;
	/**
	 * Доступная ширина, мм
	 */
	private Integer real_width;
	/**
	 * Доступная высота, мм
	 */
	private Integer real_height;
	/**
	 * Смещение доступной области относительно левой стороны обекта (со стороны загрузки)
	 */
	private Integer x_offset;
	/**
	 * Смещение доступной области относительно низа обекта (со стороны загрузки)
	 */
	private Integer y_offset;
	/**
	 * Смещение доступной области относительно дальней стенки (со стороны загрузки)
	 */
	private Integer z_offset;

	public Rack(Integer code_sector, Integer code_rack, StateRack state_rack, String name_rack, String rack_barcode, Integer length, Integer width, Integer height, Integer x_coord, Integer y_coord, Integer angle, LoadSide load_side, Integer code_rack_template, boolean lock_size, boolean lock_move, TypeRack type_race, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer user_draft, Date date_draft, Integer real_length, Integer real_width, Integer real_height, Integer x_offset, Integer y_offset, Integer z_offset) {
		this.code_sector = code_sector;
		this.code_rack = code_rack;
		this.state_rack = state_rack;
		this.name_rack = name_rack;
		this.rack_barcode = rack_barcode;
		this.length = length;
		this.width = width;
		this.height = height;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.angle = angle;
		this.load_side = load_side;
		this.code_rack_template = code_rack_template;
		this.lock_size = lock_size;
		this.lock_move = lock_move;
		this.type_race = type_race;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.user_draft = user_draft;
		this.date_draft = date_draft;
		this.real_length = real_length;
		this.real_width = real_width;
		this.real_height = real_height;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.z_offset = z_offset;
	}

	public Integer getCode_sector() {
		return code_sector;
	}

	public void setCode_sector(Integer code_sector) {
		this.code_sector = code_sector;
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

	public String getName_rack() {
		return name_rack;
	}

	public void setName_rack(String name_rack) {
		this.name_rack = name_rack;
	}

	public String getRack_barcode() {
		return rack_barcode;
	}

	public void setRack_barcode(String rack_barcode) {
		this.rack_barcode = rack_barcode;
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

	public Integer getX_coord() {
		return x_coord;
	}

	public void setX_coord(Integer x_coord) {
		this.x_coord = x_coord;
	}

	public Integer getY_coord() {
		return y_coord;
	}

	public void setY_coord(Integer y_coord) {
		this.y_coord = y_coord;
	}

	public Integer getZ_offset() {
		return z_offset;
	}

	public void setZ_offset(Integer z_offset) {
		this.z_offset = z_offset;
	}

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public LoadSide getLoad_side() {
		return load_side;
	}

	public String getLoad_sideAtStr() {
		return (load_side != null) ? load_side.name() : null;
	}

	public void setLoad_side(LoadSide load_side) {
		this.load_side = load_side;
	}

	public void setLoad_side(String load_side) {
		this.load_side = (load_side != null) ? LoadSide.valueOf(load_side) : null;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public boolean isLock_size() {
		return lock_size;
	}

	public void setLock_size(boolean lock_size) {
		this.lock_size = lock_size;
	}

	public void setLock_size(String lock_size) {
		this.lock_size = "Y".equals(lock_size);
	}

	public boolean isLock_move() {
		return lock_move;
	}

	public void setLock_move(boolean lock_move) {
		this.lock_move = lock_move;
	}

	public void setLock_move(String lock_move) {
		this.lock_move = "Y".equals(lock_move);
	}

	public TypeRack getType_race() {
		return type_race;
	}

	public String getType_raceAtStr() {
		return (type_race != null) ? type_race.name() : null;
	}

	public void setType_race(TypeRack type_race) {
		this.type_race = type_race;
	}

	public void setType_race(String type_race) {
		this.type_race = (type_race != null) ? TypeRack.valueOf(type_race) : null;
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

	public Integer getReal_length() {
		return real_length;
	}

	public void setReal_length(Integer real_length) {
		this.real_length = real_length;
	}

	public Integer getReal_width() {
		return real_width;
	}

	public void setReal_width(Integer real_width) {
		this.real_width = real_width;
	}

	public Integer getReal_height() {
		return real_height;
	}

	public void setReal_height(Integer real_height) {
		this.real_height = real_height;
	}

	public Integer getX_offset() {
		return x_offset;
	}

	public void setX_offset(Integer x_offset) {
		this.x_offset = x_offset;
	}

	public Integer getY_offset() {
		return y_offset;
	}

	public void setY_offset(Integer y_offset) {
		this.y_offset = y_offset;
	}

	public Rack(final ResultSet resultSet) throws SQLException {
		code_sector = resultSet.getInt(RackConst.CODE_SECTOR);
		code_rack = resultSet.getInt(RackConst.CODE_RACK);
		setState_rack(resultSet.getString(RackConst.STATE_RACK));
		name_rack = resultSet.getString(RackConst.NAME_RACK);
		rack_barcode = resultSet.getString(RackConst.RACK_BARCODE);
		length = resultSet.getInt(RackConst.LENGTH);
		width = resultSet.getInt(RackConst.WIDTH);
		height = resultSet.getInt(RackConst.HEIGHT);
		x_coord = resultSet.getInt(RackConst.X_COORD);
		y_coord = resultSet.getInt(RackConst.Y_COORD);
		angle = resultSet.getInt(RackConst.ANGLE);
		setLoad_side(resultSet.getString(RackConst.LOAD_SIDE));
		code_rack_template = resultSet.getInt(RackConst.CODE_RACK_TEMPLATE);
		setLock_size(resultSet.getString(RackConst.LOCK_SIZE));
		setLock_move(resultSet.getString(RackConst.LOCK_MOVE));
		setType_race(resultSet.getString(RackConst.TYPE_RACK));
		user_insert = resultSet.getInt(RackConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(RackConst.DATE_INSERT);
		user_update = resultSet.getInt(RackConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(RackConst.DATE_UPDATE);
		user_draft = resultSet.getInt(RackConst.USER_DRAFT);
		date_draft = resultSet.getTimestamp(RackConst.DATE_DRAFT);
		real_length = resultSet.getInt(RackConst.REAL_LENGTH);
		real_width = resultSet.getInt(RackConst.REAL_WIDTH);
		real_height = resultSet.getInt(RackConst.REAL_HEIGHT);
		x_offset = resultSet.getInt(RackConst.X_OFFSET);
		y_offset = resultSet.getInt(RackConst.Y_OFFSET);
		z_offset = resultSet.getInt(RackConst.Z_OFFSET);
	}

	public Rack(final JsonObject rackJson) {
		code_sector = JsonUtils.getInteger(rackJson, RackConst.CODE_SECTOR);
		code_rack = JsonUtils.getInteger(rackJson, RackConst.CODE_RACK);
		setState_rack(JsonUtils.getString(rackJson, RackConst.STATE_RACK));
		name_rack = JsonUtils.getString(rackJson, RackConst.NAME_RACK);
		rack_barcode = JsonUtils.getString(rackJson, RackConst.RACK_BARCODE);
		length = JsonUtils.getInteger(rackJson, RackConst.LENGTH);
		width = JsonUtils.getInteger(rackJson, RackConst.WIDTH);
		height = JsonUtils.getInteger(rackJson, RackConst.HEIGHT);
		x_coord = JsonUtils.getInteger(rackJson, RackConst.X_COORD);
		y_coord = JsonUtils.getInteger(rackJson, RackConst.Y_COORD);
		angle = JsonUtils.getInteger(rackJson, RackConst.ANGLE);
		setLoad_side(JsonUtils.getString(rackJson, RackConst.LOAD_SIDE));
		code_rack_template = JsonUtils.getInteger(rackJson, RackConst.CODE_RACK_TEMPLATE);
		lock_size = JsonUtils.getBoolean(rackJson, RackConst.LOCK_SIZE);
		lock_move = JsonUtils.getBoolean(rackJson, RackConst.LOCK_MOVE);
		setType_race(JsonUtils.getString(rackJson, RackConst.TYPE_RACK));
		user_insert = JsonUtils.getInteger(rackJson, RackConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackJson, RackConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackJson, RackConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackJson, RackConst.DATE_UPDATE);
		user_draft = JsonUtils.getInteger(rackJson, RackConst.USER_DRAFT);
		date_draft = JsonUtils.getDate(rackJson, RackConst.DATE_DRAFT);
		real_length = JsonUtils.getInteger(rackJson, RackConst.REAL_LENGTH);
		real_width = JsonUtils.getInteger(rackJson, RackConst.REAL_WIDTH);
		real_height = JsonUtils.getInteger(rackJson, RackConst.REAL_HEIGHT);
		x_offset = JsonUtils.getInteger(rackJson, RackConst.X_OFFSET);
		y_offset = JsonUtils.getInteger(rackJson, RackConst.Y_OFFSET);
		z_offset = JsonUtils.getInteger(rackJson, RackConst.Z_OFFSET);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackConst.CODE_SECTOR, code_sector);
		jsonObject.addProperty(RackConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackConst.STATE_RACK, getState_rackAtStr());
		jsonObject.addProperty(RackConst.NAME_RACK, name_rack);
		jsonObject.addProperty(RackConst.RACK_BARCODE, rack_barcode);
		jsonObject.addProperty(RackConst.LENGTH, length);
		jsonObject.addProperty(RackConst.WIDTH, width);
		jsonObject.addProperty(RackConst.HEIGHT, height);
		jsonObject.addProperty(RackConst.X_COORD, x_coord);
		jsonObject.addProperty(RackConst.Y_COORD, y_coord);
		jsonObject.addProperty(RackConst.ANGLE, angle);
		jsonObject.addProperty(RackConst.LOAD_SIDE, getLoad_sideAtStr());
		jsonObject.addProperty(RackConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackConst.LOCK_SIZE, (lock_size) ? "Y" : "N");
		jsonObject.addProperty(RackConst.LOCK_MOVE, (lock_move) ? "Y" : "N");
		jsonObject.addProperty(RackConst.TYPE_RACK, getType_raceAtStr());
		jsonObject.addProperty(RackConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(RackConst.USER_DRAFT, user_draft);
		JsonUtils.set(jsonObject, RackConst.DATE_DRAFT, date_draft);
		jsonObject.addProperty(RackConst.REAL_LENGTH, real_length);
		jsonObject.addProperty(RackConst.REAL_WIDTH, real_width);
		jsonObject.addProperty(RackConst.REAL_HEIGHT, real_height);
		jsonObject.addProperty(RackConst.X_OFFSET, x_offset);
		jsonObject.addProperty(RackConst.Y_OFFSET, y_offset);
		jsonObject.addProperty(RackConst.Z_OFFSET, z_offset);
		return jsonObject;
	}
}
