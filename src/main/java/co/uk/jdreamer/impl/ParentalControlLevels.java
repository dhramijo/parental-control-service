package co.uk.jdreamer.impl;


import java.util.HashMap;
import java.util.Map;

public enum ParentalControlLevels {
	U(0, "U"), 
	PG(1, "PG"), 
	MP12(2, "12"), 
	MP15(3, "15"), 
	MP18(4, "18");

	public final static Map<String, ParentalControlLevels> parentalControlLevelsMap = new HashMap<>();

	static {
		for (ParentalControlLevels pcLevels : ParentalControlLevels.values()) {
			parentalControlLevelsMap.put(pcLevels.stringValue, pcLevels);
		}
	}

	private final int value;
	private final String stringValue;

	ParentalControlLevels(int intValue, String stringValue) {
		this.value = intValue;
		this.stringValue = stringValue;
	}

	public static ParentalControlLevels getByString(String rating) {
		return parentalControlLevelsMap.get(rating);
	}

	public int getValue() {
		return value;
	}

}
