package planograma.data;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 01.04.12
 * Time: 20:37
 * Состояния секторов планограм
 */
public enum StateSector {
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
	NA,
	/**
	 * Стеллажы соответствует положению в зале магазина
	 */
	PC;

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}