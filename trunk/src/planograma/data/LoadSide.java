package planograma.data;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.04.12
 * Time: 20:52
 * Сторона загрузки
 */
public enum LoadSide {
	/**
	 * С переди
	 */
	F,
	/**
	 * Сверху
	 */
	U;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}