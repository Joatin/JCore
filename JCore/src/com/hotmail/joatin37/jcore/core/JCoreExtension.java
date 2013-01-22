package com.hotmail.joatin37.jcore.core;

import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.joatin37.jcore.economy.IEconomy;
import com.hotmail.joatin37.jcore.language.TagReplacer;

public abstract class JCoreExtension extends JavaPlugin {

	public abstract void Save();

	public abstract int SaveProgres();

	public abstract TagReplacer getTagReplacer();

	public abstract boolean requiresSQL();

	public abstract boolean requiresEconomy();

	public final IEconomy getEconomy() {
		return null;
	}

	public final void sendDebug(String message) {
		if (Core.isDebugg()) {
			super.getLogger().info("[Debug]: " + message);
		}
	}
}
