package eu.aniketos.trustworthiness.impl.messaging.util;

public class PropertyValidator {

	public static boolean isNumeric(String str) {
		
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
