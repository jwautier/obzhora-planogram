package planograma.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 23.03.12
 * Time: 5:01
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {

	public static Integer getInteger(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement.isJsonNull())
			return null;
		String s = jsonElement.getAsString();
		int index = s.indexOf('.');
		if (index >= 0) {
			s = s.substring(0, index);
		} else {
			index = s.indexOf(',');
			if (index >= 0) {
				s = s.substring(0, index);
			}
		}
		return (s.isEmpty()) ? null : Integer.valueOf(s);
	}

	public static String getString(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement.isJsonNull())
			return null;
		return jsonElement.getAsString();
	}

	public static Date getDate(final JsonObject jsonObject, final String key) {
		final JsonElement jsonElement = jsonObject.get(key);
		if (jsonElement.isJsonNull())
			return null;
		return new Date(jsonElement.getAsLong());
	}

	public static void set(final JsonObject jsonObject, final String key, final Date value) {
		jsonObject.addProperty(key, (value != null) ? value.getTime() : null);
	}
}