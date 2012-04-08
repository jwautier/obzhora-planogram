package planograma.data.wrapper;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 20.03.12
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class StateAllWrapper {
	/**
	 * ключь
	 */
	private final String abr_state;
	/**
	 * значение
	 */
	private final String state;

	public StateAllWrapper(String abr_state, String state) {
		this.abr_state = abr_state;
		this.state = state;
	}

	public String getAbr_state() {
		return abr_state;
	}

	public String getState() {
		return state;
	}
}
