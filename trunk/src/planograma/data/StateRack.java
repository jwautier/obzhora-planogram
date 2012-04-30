package planograma.data;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.04.12
 * Time: 20:45
 * Состояние стеллажа
 */
public enum StateRack {
	/**
	 * Активен
	 */
	A,
	/**
	 * Черновик
	 */
	D,
	/**
	 * Неактивно
	 */
	NA;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
