package planograma;

import java.util.ResourceBundle;

/**
 * User: poljakov
 * Date: 18.09.12
 * Time: 8:37
 */
public class PlanogramMessage {


	public static String RACK_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_HEIGHT_TOO_LITTLE");
	}
	public static String RACK_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_WIDTH_TOO_LITTLE");
	}
	public static String RACK_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_LENGTH_TOO_LITTLE");
	}
	public static String RACK_REAL_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_REAL_HEIGHT_TOO_LITTLE");
	}
	public static String RACK_REAL_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_REAL_WIDTH_TOO_LITTLE");
	}
	public static String RACK_REAL_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_REAL_LENGTH_TOO_LITTLE");
	}

	public static String RACK_SHELF_HEIGHT_TOO_LITTLE() {
		return getMessage("RACK_SHELF_HEIGHT_TOO_LITTLE");
	}

	public static String RACK_SHELF_WIDTH_TOO_LITTLE() {
		return getMessage("RACK_SHELF_WIDTH_TOO_LITTLE");
	}

	public static String RACK_SHELF_LENGTH_TOO_LITTLE() {
		return getMessage("RACK_SHELF_LENGTH_TOO_LITTLE");
	}

	public static String RACK_SHELF_OUTSIDE_RACK()
	{
		return getMessage("RACK_SHELF_OUTSIDE_RACK");
	}

	private static String getMessage(final String name) {
		final ResourceBundle resourceBundle = ResourceBundle.getBundle(PlanogramMessage.class.getSimpleName());
		final String result = resourceBundle.getString(name);
		return (result != null) ? result : "Message for "+name+" not found!";
	}
}
