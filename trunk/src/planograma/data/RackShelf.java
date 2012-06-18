package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackShelfConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 19.03.12
 * Time: 5:11
 * полка стеллажа
 */
public class RackShelf implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Код полки
	 */
	private Integer code_shelf;
	/**
	 * Положение на полке по ширине
	 */
	private Integer x_coord;
	/**
	 * Положение на полке по высоте
	 */
	private Integer y_coord;
	/**
	 * Высота, см
	 */
	private Integer shelf_height;
	/**
	 * Ширина, см
	 */
	private Integer shelf_width;
	/**
	 * Глубина, см
	 */
	private Integer shelf_length;
	/**
	 * Угол поворота
	 */
	private Integer angle;
	/**
	 * Тип полки, mz.state_all.part_state = -67 (?)
	 */
	private TypeShelf type_shelf;
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

	public RackShelf(Integer code_rack, Integer code_shelf, Integer x_coord, Integer y_coord, Integer shelf_height, Integer shelf_width, Integer shelf_length, Integer angle, TypeShelf type_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update) {
		this.code_rack = code_rack;
		this.code_shelf = code_shelf;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.shelf_height = shelf_height;
		this.shelf_width = shelf_width;
		this.shelf_length = shelf_length;
		this.angle = angle;
		this.type_shelf=type_shelf;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
	}

	public Integer getCode_rack() {
		return code_rack;
	}

	public void setCode_rack(Integer code_rack) {
		this.code_rack = code_rack;
	}

	public Integer getCode_shelf() {
		return code_shelf;
	}

	public void setCode_shelf(Integer code_shelf) {
		this.code_shelf = code_shelf;
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

	public Integer getShelf_height() {
		return shelf_height;
	}

	public void setShelf_height(Integer shelf_height) {
		this.shelf_height = shelf_height;
	}

	public Integer getShelf_width() {
		return shelf_width;
	}

	public void setShelf_width(Integer shelf_width) {
		this.shelf_width = shelf_width;
	}

	public Integer getShelf_length() {
		return shelf_length;
	}

	public void setShelf_length(Integer shelf_length) {
		this.shelf_length = shelf_length;
	}

	public Integer getAngle() {
		return angle;
	}

	public void setAngle(Integer angle) {
		this.angle = angle;
	}

	public TypeShelf getType_shelf() {
		return type_shelf;
	}
	public String getType_shelfAtStr() {
		return (type_shelf!=null)?type_shelf.name():null;
	}

	public void setType_shelf(TypeShelf type_shelf) {
		this.type_shelf = type_shelf;
	}

	public void setType_shelf(String type_shelf) {
		this.type_shelf = (type_shelf != null) ? TypeShelf.valueOf(type_shelf) : null;
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

	public RackShelf(final ResultSet resultSet) throws SQLException {
		code_rack = resultSet.getInt(RackShelfConst.CODE_RACK);
		code_shelf = resultSet.getInt(RackShelfConst.CODE_SHELF);
		x_coord = resultSet.getInt(RackShelfConst.X_COORD);
		y_coord = resultSet.getInt(RackShelfConst.Y_COORD);
		shelf_height = resultSet.getInt(RackShelfConst.SHELF_HEIGHT);
		shelf_width = resultSet.getInt(RackShelfConst.SHELF_WIDTH);
		shelf_length = resultSet.getInt(RackShelfConst.SHELF_LENGTH);
		angle = resultSet.getInt(RackShelfConst.ANGLE);
		setType_shelf(resultSet.getString(RackShelfConst.TYPE_SHELF));
		user_insert = resultSet.getInt(RackShelfConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(RackShelfConst.DATE_INSERT);
		user_update = resultSet.getInt(RackShelfConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(RackShelfConst.DATE_UPDATE);
	}

	public RackShelf(final JsonObject rackShelfJson) throws SQLException {
		code_rack = JsonUtils.getInteger(rackShelfJson, RackShelfConst.CODE_RACK);
		code_shelf = JsonUtils.getInteger(rackShelfJson, RackShelfConst.CODE_SHELF);
		x_coord = JsonUtils.getInteger(rackShelfJson, RackShelfConst.X_COORD);
		y_coord = JsonUtils.getInteger(rackShelfJson, RackShelfConst.Y_COORD);
		shelf_height = JsonUtils.getInteger(rackShelfJson, RackShelfConst.SHELF_HEIGHT);
		shelf_width = JsonUtils.getInteger(rackShelfJson, RackShelfConst.SHELF_WIDTH);
		shelf_length = JsonUtils.getInteger(rackShelfJson, RackShelfConst.SHELF_LENGTH);
		angle = JsonUtils.getInteger(rackShelfJson, RackShelfConst.ANGLE);
		setType_shelf(JsonUtils.getString(rackShelfJson, RackShelfConst.TYPE_SHELF));
		user_insert = JsonUtils.getInteger(rackShelfJson, RackShelfConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackShelfJson, RackShelfConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackShelfJson, RackShelfConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackShelfJson, RackShelfConst.DATE_UPDATE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackShelfConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackShelfConst.CODE_SHELF, code_shelf);
		jsonObject.addProperty(RackShelfConst.X_COORD, x_coord);
		jsonObject.addProperty(RackShelfConst.Y_COORD, y_coord);
		jsonObject.addProperty(RackShelfConst.SHELF_HEIGHT, shelf_height);
		jsonObject.addProperty(RackShelfConst.SHELF_WIDTH, shelf_width);
		jsonObject.addProperty(RackShelfConst.SHELF_LENGTH, shelf_length);
		jsonObject.addProperty(RackShelfConst.ANGLE, angle);
		jsonObject.addProperty(RackShelfConst.TYPE_SHELF, (type_shelf != null) ? type_shelf.name() : null);
		jsonObject.addProperty(RackShelfConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackShelfConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackShelfConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackShelfConst.DATE_UPDATE, date_update);
		return jsonObject;
	}
}
