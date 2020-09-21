package cl.sepidesign.herbs.utils;

import java.text.Normalizer;
import java.text.Normalizer.Form;

public class StringUtils {

	public static String removeDiacriticalMarks(String string) {
		return Normalizer.normalize(string, Form.NFD).replaceAll(
				"\\p{InCombiningDiacriticalMarks}+", "");
	}
}
