package com.hotmail.joatin37.jcore.core;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.language.Lang.LangTag;

public class PlayerData {

	private static HashMap<String, PlayerValues> playerdata;

	public enum Data {
		LANGUAGE, SITELANGUAGE, DISPLAYNAME, PASSWORD, ;
	}

	public PlayerData() {

	}

	public Object put(JavaPlugin plugin, String player, String key, Object value) {
		return null;
	}

	public Object putGlobal(JavaPlugin plugin, String player, String key,
			Object value, boolean Protected) {
		return null;
	}

	public Object get(JavaPlugin plugin, String player, String key) {
		return null;
	}

	public Object getGlobal(String player, String key) {
		return null;
	}

	public Object getGlobal(String player, Data key) {
		return null;
	}

	public LangTag getLanguage(String player) {
		return null;
	}

	private class PlayerValues extends HashMap<String, Object> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public synchronized Object put(String key, Object value) {
			return null;
		}

		@Override
		public synchronized void putAll(
				Map<? extends String, ? extends Object> m) {

		}

		@Override
		public synchronized Object get(Object key) {
			if (!(key instanceof String)) {
				// TODO a exception must be thrown here
			}
			return super.get(key);

		}
	}
}
