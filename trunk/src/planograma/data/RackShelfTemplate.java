package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.RackShelfTemplateConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 19.03.12
 * Time: 5:11
 * полка шаблонного стеллажа
 */
public class RackShelfTemplate implements IJsonObject{
	/**
	 * Код шаблонного стеллажа
	 */
	private Integer code_rack_template;
	/**
	 * Код полки
	 */
	private Integer code_shelf_template;
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

	public RackShelfTemplate(Integer code_rack_template, Integer code_shelf_template, Integer x_coord, Integer y_coord, Integer shelf_height, Integer shelf_width, Integer shelf_length, Integer angle, TypeShelf type_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update) {
		this.code_rack_template = code_rack_template;
		this.code_shelf_template = code_shelf_template;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.shelf_height = shelf_height;
		this.shelf_width = shelf_width;
		this.shelf_length = shelf_length;
		this.angle = angle;
		this.type_shelf = type_shelf;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
	}

	public Integer getCode_rack_template() {
		return code_rack_template;
	}

	public void setCode_rack_template(Integer code_rack_template) {
		this.code_rack_template = code_rack_template;
	}

	public Integer getCode_shelf_template() {
		return code_shelf_template;
	}

	public void setCode_shelf_template(Integer code_shelf_template) {
		this.code_shelf_template = code_shelf_template;
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
		this.type_shelf = (type_shelf!=null)?TypeShelf.valueOf(type_shelf):null;
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

	public RackShelfTemplate(final ResultSet resultSet) throws SQLException {
		code_rack_template = resultSet.getInt(RackShelfTemplateConst.CODE_RACK_TEMPLATE);
		code_shelf_template = resultSet.getInt(RackShelfTemplateConst.CODE_SHELF_TEMPLATE);
		x_coord = resultSet.getInt(RackShelfTemplateConst.X_COORD);
		y_coord = resultSet.getInt(RackShelfTemplateConst.Y_COORD);
		shelf_height = resultSet.getInt(RackShelfTemplateConst.SHELF_HEIGHT);
		shelf_width = resultSet.getInt(RackShelfTemplateConst.SHELF_WIDTH);
		shelf_length = resultSet.getInt(RackShelfTemplateConst.SHELF_LENGTH);
		angle = resultSet.getInt(RackShelfTemplateConst.ANGLE);
		setType_shelf(resultSet.getString(RackShelfTemplateConst.TYPE_SHELF));
		user_insert = resultSet.getInt(RackShelfTemplateConst.USER_INSERT);
		date_insert = resultSet.getTimestamp(RackShelfTemplateConst.DATE_INSERT);
		user_update = resultSet.getInt(RackShelfTemplateConst.USER_UPDATE);
		date_update = resultSet.getTimestamp(RackShelfTemplateConst.DATE_UPDATE);
	}

	public RackShelfTemplate(final JsonObject rackShelfTemplateJson) throws SQLException {
		code_rack_template = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.CODE_RACK_TEMPLATE);
		code_shelf_template = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.CODE_SHELF_TEMPLATE);
		x_coord = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.X_COORD);
		y_coord = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.Y_COORD);
		shelf_height = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.SHELF_HEIGHT);
		shelf_width = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.SHELF_WIDTH);
		shelf_length = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.SHELF_LENGTH);
		angle = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.ANGLE);
		setType_shelf(JsonUtils.getString(rackShelfTemplateJson, RackShelfTemplateConst.TYPE_SHELF));
		user_insert = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.USER_INSERT);
		date_insert = JsonUtils.getDate(rackShelfTemplateJson, RackShelfTemplateConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(rackShelfTemplateJson, RackShelfTemplateConst.USER_UPDATE);
		date_update = JsonUtils.getDate(rackShelfTemplateJson, RackShelfTemplateConst.DATE_UPDATE);
	}
	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackShelfTemplateConst.CODE_RACK_TEMPLATE, code_rack_template);
		jsonObject.addProperty(RackShelfTemplateConst.CODE_SHELF_TEMPLATE, code_shelf_template);
		jsonObject.addProperty(RackShelfTemplateConst.X_COORD, x_coord);
		jsonObject.addProperty(RackShelfTemplateConst.Y_COORD, y_coord);
		jsonObject.addProperty(RackShelfTemplateConst.SHELF_HEIGHT, shelf_height);
		jsonObject.addProperty(RackShelfTemplateConst.SHELF_WIDTH, shelf_width);
		jsonObject.addProperty(RackShelfTemplateConst.SHELF_LENGTH, shelf_length);
		jsonObject.addProperty(RackShelfTemplateConst.ANGLE, angle);
		jsonObject.addProperty(RackShelfTemplateConst.TYPE_SHELF, (type_shelf!=null)?type_shelf.name():null);
		jsonObject.addProperty(RackShelfTemplateConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackShelfTemplateConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackShelfTemplateConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackShelfTemplateConst.DATE_UPDATE, date_update);
		return jsonObject;
	}
}
