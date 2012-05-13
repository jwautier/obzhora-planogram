package planograma.data;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28.04.12
 * Time: 5:56
 * тип стеллажа
 */
public enum TypeRack {
	/**
	 * Стеллаж
	 */
	R,
	/**
	 * Касса
	 */
	WP,
	/**
	 * Мертвая зона
	 */
	DZ;

	private TypeRack() {

	}

	private String desc;
	private String color;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
