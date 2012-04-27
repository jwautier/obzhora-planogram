package planograma.data.wrapper;

import planograma.constant.data.AdditionUnitConst;
import planograma.constant.data.WaresConst;
import planograma.constant.data.WaresImageConst;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 27.04.12
 * Time: 5:58
 * To change this template use File | Settings | File Templates.
 */
public class WaresWrapper {
	private Integer code_wares;
	private Integer code_unit;
	private String articl;
	private String name_wares;
	private Integer length;
	private Integer width;
	private Integer height;
	private String bar_code;
	private Integer code_image;

	public WaresWrapper(Integer code_wares, Integer code_unit, String articl, String name_wares, Integer length, Integer width, Integer height, String bar_code, Integer code_image) {
		this.code_wares = code_wares;
		this.code_unit = code_unit;
		this.articl = articl;
		this.name_wares = name_wares;
		this.length = length;
		this.width = width;
		this.height = height;
		this.bar_code = bar_code;
		this.code_image = code_image;
	}

	public Integer getCode_wares() {
		return code_wares;
	}

	public void setCode_wares(Integer code_wares) {
		this.code_wares = code_wares;
	}

	public Integer getCode_unit() {
		return code_unit;
	}

	public void setCode_unit(Integer code_unit) {
		this.code_unit = code_unit;
	}

	public String getArticl() {
		return articl;
	}

	public void setArticl(String articl) {
		this.articl = articl;
	}

	public String getName_wares() {
		return name_wares;
	}

	public void setName_wares(String name_wares) {
		this.name_wares = name_wares;
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

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public Integer getCode_image() {
		return code_image;
	}

	public void setCode_image(Integer code_image) {
		this.code_image = code_image;
	}

	public WaresWrapper(final ResultSet resultSet) throws SQLException {
		code_wares = resultSet.getInt(WaresConst.CODE_WARES);
		code_unit = resultSet.getInt(AdditionUnitConst.CODE_UNIT);
		articl = resultSet.getString(WaresConst.ARTICL);
		name_wares = resultSet.getString(WaresConst.NAME_WARES);
		length = resultSet.getInt(AdditionUnitConst.LENGTH);
		width = resultSet.getInt(AdditionUnitConst.WIDTH);
		height = resultSet.getInt(AdditionUnitConst.HEIGHT);
		bar_code =resultSet.getString(AdditionUnitConst.BAR_CODE);
		code_image = resultSet.getInt(WaresImageConst.CODE_IMAGE);
	}
}
