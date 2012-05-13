package planograma.data;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 12.05.12
 * Time: 14:34
 * Тип товара на полке
 */
public enum TypeRackWares {
	/**
	 * Тип не определен
	 */
	NA;

	private TypeRackWares() {
	}

	private String desc;
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
