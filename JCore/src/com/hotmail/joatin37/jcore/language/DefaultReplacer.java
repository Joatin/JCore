package com.hotmail.joatin37.jcore.language;

import java.util.List;

/**
 * Used when a plugin hasn't made their own.
 * 
 * @author Joatin
 * @since 1.0.0
 */
public class DefaultReplacer extends TagReplacer {

	/**
	 * Constructs a new instance
	 */
	public DefaultReplacer() {
		super();
	}

	@Override
	public String replace(String message, List<Object> replacements) {
		return null;
	}

}
