package planograma.data;

import com.google.gson.JsonObject;
import planograma.constant.data.ImageConst;
import planograma.constant.data.RackWaresConst;
import planograma.constant.data.WaresImageConst;
import planograma.utils.JsonUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 18.03.12
 * Time: 9:39
 * товар стеллажа
 */
public class RackWares implements IJsonObject {
	/**
	 * Код стеллажа
	 */
	private Integer code_rack;
	/**
	 * Код товара
	 */
	private Integer code_wares;
	/**
	 * Код единицы измерения
	 */
	private Integer code_unit;
	/**
	 * Код товара на полке (первичный ключ)
	 */
	private Integer code_wares_on_shelf;
	/**
	 * Номер полки
	 */
	private Integer order_number_shelf;
	/**
	 * Номер по порядку па полке
	 */
	private Integer order_number_on_shelf;
	/**
	 * Положение на стеллаже по ширине
	 */
	private Integer position_x;
	/**
	 * Положение на стеллаже по высоте
	 */
	private Integer position_y;
	/**
	 * Длина(глубина), см
	 */
	private Integer wares_length;
	/**
	 * Ширина, см
	 */
	private Integer wares_width;
	/**
	 * Высота, см
	 */
	private Integer wares_height;
	/**
	 * Количество товара выстроеное в длину (глубину)
	 * расчитывается автоматически, но может редактироваться
	 */
	private Integer count_length_on_shelf;
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
	 * Код изображения
	 */
	private Integer code_image;


	public RackWares(Integer code_rack, Integer code_wares, Integer code_unit, Integer code_wares_on_shelf, Integer order_number_shelf, Integer order_number_on_shelf, Integer position_x, Integer position_y, Integer wares_length, Integer wares_width, Integer wares_height, Integer count_length_on_shelf, Integer user_insert, Date date_insert, Integer user_update, Date date_update, Integer code_image) {
		this.code_rack = code_rack;
		this.code_wares = code_wares;
		this.code_unit = code_unit;
		this.code_wares_on_shelf = code_wares_on_shelf;
		this.order_number_shelf = order_number_shelf;
		this.order_number_on_shelf = order_number_on_shelf;
		this.position_x = position_x;
		this.position_y = position_y;
		this.wares_length = wares_length;
		this.wares_width = wares_width;
		this.wares_height = wares_height;
		this.count_length_on_shelf = count_length_on_shelf;
		this.user_insert = user_insert;
		this.date_insert = date_insert;
		this.user_update = user_update;
		this.date_update = date_update;
		this.code_image = code_image;
	}

	public RackWares(final ResultSet resultSet) throws SQLException {
		code_rack = resultSet.getInt(RackWaresConst.CODE_RACK);
		code_wares = resultSet.getInt(RackWaresConst.CODE_WARES);
		code_unit = resultSet.getInt(RackWaresConst.CODE_UNIT);
		code_wares_on_shelf = resultSet.getInt(RackWaresConst.CODE_WARES_ON_SHELH);
		order_number_shelf = resultSet.getInt(RackWaresConst.ORDER_NUMBER_SHELH);
		order_number_on_shelf = resultSet.getInt(RackWaresConst.ORDER_NUMBER_ON_SHELH);
		position_x = resultSet.getInt(RackWaresConst.POSITION_X);
		position_y = resultSet.getInt(RackWaresConst.POSITION_Y);
		wares_length = resultSet.getInt(RackWaresConst.WARES_LENGTH);
		wares_width = resultSet.getInt(RackWaresConst.WARES_WIDTH);
		wares_height = resultSet.getInt(RackWaresConst.WARES_HEIGHT);
		count_length_on_shelf = resultSet.getInt(RackWaresConst.COUNT_LENGTH_ON_SHELF);
		user_insert = resultSet.getInt(RackWaresConst.USER_INSERT);
		date_insert = resultSet.getDate(RackWaresConst.DATE_INSERT);
		user_update = resultSet.getInt(RackWaresConst.USER_UPDATE);
		date_update = resultSet.getDate(RackWaresConst.DATE_UPDATE);
		code_image = resultSet.getInt(WaresImageConst.CODE_IMAGE);
	}

	public RackWares(final JsonObject jsonObject) {
		code_rack = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_RACK);
		code_wares = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_WARES);
		code_unit = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_UNIT);
		code_wares_on_shelf = JsonUtils.getInteger(jsonObject, RackWaresConst.CODE_WARES_ON_SHELH);
		order_number_shelf = JsonUtils.getInteger(jsonObject, RackWaresConst.ORDER_NUMBER_SHELH);
		order_number_on_shelf = JsonUtils.getInteger(jsonObject, RackWaresConst.ORDER_NUMBER_ON_SHELH);
		position_x = JsonUtils.getInteger(jsonObject, RackWaresConst.POSITION_X);
		position_y = JsonUtils.getInteger(jsonObject, RackWaresConst.POSITION_Y);
		wares_length = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_LENGTH);
		wares_width = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_WIDTH);
		wares_height = JsonUtils.getInteger(jsonObject, RackWaresConst.WARES_HEIGHT);
		count_length_on_shelf = JsonUtils.getInteger(jsonObject, RackWaresConst.COUNT_LENGTH_ON_SHELF);
		user_insert = JsonUtils.getInteger(jsonObject, RackWaresConst.USER_INSERT);
		date_insert = JsonUtils.getDate(jsonObject, RackWaresConst.DATE_INSERT);
		user_update = JsonUtils.getInteger(jsonObject, RackWaresConst.USER_UPDATE);
		date_update = JsonUtils.getDate(jsonObject, RackWaresConst.DATE_UPDATE);
		code_image = JsonUtils.getInteger(jsonObject, WaresImageConst.CODE_IMAGE);
	}

	@Override
	public JsonObject toJsonObject() {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(RackWaresConst.CODE_RACK, code_rack);
		jsonObject.addProperty(RackWaresConst.CODE_WARES, code_wares);
		jsonObject.addProperty(RackWaresConst.CODE_UNIT, code_unit);
		jsonObject.addProperty(RackWaresConst.CODE_WARES_ON_SHELH, code_wares_on_shelf);
		jsonObject.addProperty(RackWaresConst.ORDER_NUMBER_SHELH, order_number_shelf);
		jsonObject.addProperty(RackWaresConst.ORDER_NUMBER_ON_SHELH, order_number_on_shelf);
		jsonObject.addProperty(RackWaresConst.POSITION_X, position_x);
		jsonObject.addProperty(RackWaresConst.POSITION_Y, position_y);
		jsonObject.addProperty(RackWaresConst.WARES_LENGTH, wares_length);
		jsonObject.addProperty(RackWaresConst.WARES_WIDTH, wares_width);
		jsonObject.addProperty(RackWaresConst.WARES_HEIGHT, wares_height);
		jsonObject.addProperty(RackWaresConst.COUNT_LENGTH_ON_SHELF, count_length_on_shelf);
		jsonObject.addProperty(RackWaresConst.USER_INSERT, user_insert);
		JsonUtils.set(jsonObject, RackWaresConst.DATE_INSERT, date_insert);
		jsonObject.addProperty(RackWaresConst.USER_UPDATE, user_update);
		JsonUtils.set(jsonObject, RackWaresConst.DATE_UPDATE, date_update);
		jsonObject.addProperty(WaresImageConst.CODE_IMAGE, code_image);
		return jsonObject;
	}
}
