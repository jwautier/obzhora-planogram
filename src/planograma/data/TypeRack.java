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
	R("Стеллаж"),
	/**
	 * Касса
	 */
	WP("Касса"),
	/**
	 * Мертвая зона
	 */
	DZ("Мертвая зона");

	private String desc;
	private String color;

	private TypeRack(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
