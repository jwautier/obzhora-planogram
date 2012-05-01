package planograma.data;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.04.12
 * Time: 21:08
 * Тип полки
 */
public enum TypeShelf {
	/**
	 * Мертвая зона
	 */
	DZ;

	private String desc;

	private String color;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}
}
